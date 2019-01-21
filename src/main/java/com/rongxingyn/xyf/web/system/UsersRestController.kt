package com.rongxingyn.xyf.web.system

import com.rongxingyn.xyf.domain.tables.pojos.TableTime
import com.rongxingyn.xyf.service.backstage.TableTimeService
import com.rongxingyn.xyf.service.system.UsersService
import com.rongxingyn.xyf.service.utils.BCryptUtils
import com.rongxingyn.xyf.web.bean.backstage.users.UsersBean
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import com.rongxingyn.xyf.web.utils.SmallPropsUtils
import com.rongxingyn.xyf.web.vo.backstage.setting.ResetPasswordVo
import com.rongxingyn.xyf.web.vo.backstage.users.UsersDisabledVo
import com.rongxingyn.xyf.web.vo.backstage.users.UsersLockedVo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.security.Principal
import java.sql.Timestamp
import java.util.*
import javax.annotation.Resource
import javax.validation.Valid

@RestController
@RequestMapping("/web/backstage/users")
open class UsersRestController {

    @Resource
    open lateinit var usersService: UsersService

    @Resource
    open lateinit var tableTimeService: TableTimeService

    /**
     * 更新密码
     *
     * @param resetPasswordVo 数据
     * @param bindingResult 检验
     * @param principal 安全检查
     * @return true or false
     */
    @PutMapping("/reset_password")
    fun resetPassword(@Valid resetPasswordVo: ResetPasswordVo, bindingResult: BindingResult, principal: Principal): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val users = usersService.findByUsername(principal.name)
            if (resetPasswordVo.newPassword == resetPasswordVo.okPassword) {
                users!!.password = BCryptUtils.bCryptPassword(resetPasswordVo.okPassword!!)
                usersService.update(users)
                ajaxUtils.success().msg("更新密码成功")
            } else {
                ajaxUtils.fail().msg("密码不一致")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 列表数据
     *
     * @param serverWebExchange 请求数据
     * @return list data
     */
    @GetMapping("/data")
    fun data(serverWebExchange: ServerWebExchange): Mono<DataTablesUtils<UsersBean>> {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        val headers = ArrayList<String>()
        headers.add("select")
        headers.add("username")
        headers.add("real_name")
        headers.add("sex")
        headers.add("contact")
        headers.add("address")
        headers.add("disabled")
        headers.add("account_expired")
        headers.add("account_locked")
        headers.add("operator")
        val dataTablesUtils = DataTablesUtils<UsersBean>(serverWebExchange.request, headers)
        val records = usersService.findAllByPage(dataTablesUtils)
        var usersBean: List<UsersBean> = ArrayList()
        if (!ObjectUtils.isEmpty(records) && records.isNotEmpty) {
            usersBean = records.into(UsersBean::class.java)
        }
        dataTablesUtils.data = usersBean
        dataTablesUtils.setiTotalRecords(usersService.countAll().toLong())
        dataTablesUtils.setiTotalDisplayRecords(usersService.countByCondition(dataTablesUtils).toLong())
        return Mono.just(dataTablesUtils)
    }

    /**
     * 批量更改状态
     *
     * @param usersDisabledVo 数据
     * @return true注销成功
     */
    @PutMapping("/disabled")
    fun disabled(@Valid usersDisabledVo: UsersDisabledVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            if (StringUtils.hasLength(usersDisabledVo.userIds)) {
                usersService.updateDisabled(SmallPropsUtils.StringIdsToStringList(usersDisabledVo.userIds!!), usersDisabledVo.disabled!!)
                saveTableTime()
                ajaxUtils.success().msg("更新状态成功")
            } else {
                ajaxUtils.fail().msg("更新状态失败")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 批量更改状态
     *
     * @param usersLockedVo 数据
     * @return true 成功
     */
    @PutMapping("/lock")
    fun lock(@Valid usersLockedVo: UsersLockedVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            if (StringUtils.hasLength(usersLockedVo.userIds)) {
                usersService.updateLocked(SmallPropsUtils.StringIdsToStringList(usersLockedVo.userIds!!), usersLockedVo.accountLocked!!)
                saveTableTime()
                ajaxUtils.success().msg("更新状态成功")
            } else {
                ajaxUtils.fail().msg("更新状态失败")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 批量更改状态
     *
     * @param username 数据
     * @return true 重置成功
     */
    @PutMapping("/reset/{username}")
    fun reset(@PathVariable username: String): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        val name = StringUtils.trimAllWhitespace(username)
        val users = usersService.findByUsername(name)
        if (Objects.nonNull(users)) {
            users!!.password = BCryptUtils.bCryptPassword(users.username)
            usersService.update(users)
            saveTableTime()
            ajaxUtils.success().msg("更新成功")
        } else {
            ajaxUtils.fail().msg("用户不存在")
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 保存时间到tableTime
     */
    private fun saveTableTime() {
        val tableTime = TableTime()
        tableTime.tableName = "USERS"
        tableTime.dealTime = Timestamp(System.currentTimeMillis())
        tableTimeService.save(tableTime)
    }
}