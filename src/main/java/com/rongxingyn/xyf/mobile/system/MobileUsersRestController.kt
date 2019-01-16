package com.rongxingyn.xyf.mobile.system

import com.rongxingyn.xyf.domain.tables.pojos.Authorities
import com.rongxingyn.xyf.domain.tables.pojos.Users
import com.rongxingyn.xyf.mobile.vo.users.LoginVo
import com.rongxingyn.xyf.mobile.vo.users.RegisterVo
import com.rongxingyn.xyf.security.AuthBook
import com.rongxingyn.xyf.service.system.AuthoritiesService
import com.rongxingyn.xyf.service.system.UsersService
import com.rongxingyn.xyf.service.utils.BCryptUtils
import com.rongxingyn.xyf.web.utils.AjaxUtils
import org.apache.commons.lang3.BooleanUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*
import javax.annotation.Resource
import javax.validation.Valid

@RestController
@RequestMapping("/mobile")
open class MobileUsersRestController {

    @Resource
    open lateinit var usersService: UsersService

    @Resource
    open lateinit var authoritiesService: AuthoritiesService

    /**
     * 登录
     *
     * @param loginVo 数据
     * @param bindingResult 检验
     * @return true or false
     */
    @GetMapping("/login")
    fun login(@Valid loginVo: LoginVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val users = usersService.findByUsername(loginVo.username!!)
            if (Objects.nonNull(users)) {
                if (BooleanUtils.isFalse(users!!.accountExpired == 1.toByte())) {
                    if (BooleanUtils.isFalse(users.accountLocked == 1.toByte())) {
                        if (BooleanUtils.isFalse(users.credentialsExpired == 1.toByte())) {
                            if (BCryptUtils.bCryptPasswordMatches(loginVo.password!!, users.password)) {
                                ajaxUtils.success().msg("登录成功")
                            } else {
                                ajaxUtils.fail().msg("密码错误")
                            }
                        } else {
                            ajaxUtils.fail().msg("账号凭证过期")
                        }
                    } else {
                        ajaxUtils.fail().msg("账号已被锁")
                    }
                } else {
                    ajaxUtils.fail().msg("账号已过期")
                }
            } else {
                ajaxUtils.fail().msg("账号不存在")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 注册
     *
     * @param registerVo 数据
     * @param bindingResult 检验
     * @return true or false
     */
    @GetMapping("/register")
    fun register(@Valid registerVo: RegisterVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val name = StringUtils.trimWhitespace(registerVo.username!!)
            val checkUsers = usersService.findByUsername(name)
            if (Objects.isNull(checkUsers)) {
                val users = Users()
                users.username = registerVo.username
                users.password = BCryptUtils.bCryptPassword(registerVo.password!!)
                users.disabled = 0
                users.accountExpired = 0
                users.accountLocked = 0
                users.credentialsExpired = 0
                users.address = registerVo.address
                users.realName = registerVo.realName
                users.sex = registerVo.sex
                users.contact = registerVo.contact
                usersService.save(users)
                val authorities = Authorities()
                authorities.username = registerVo.username
                authorities.authority = AuthBook.ROLE_USERS.name
                authoritiesService.save(authorities)
                ajaxUtils.success().msg("注册成功")
            } else {
                ajaxUtils.fail().msg("该账号已被注册")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }
}