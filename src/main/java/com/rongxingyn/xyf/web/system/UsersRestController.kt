package com.rongxingyn.xyf.web.system

import com.rongxingyn.xyf.service.system.UsersService
import com.rongxingyn.xyf.service.utils.BCryptUtils
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.vo.backstage.setting.ResetPasswordVo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import java.security.Principal
import javax.annotation.Resource
import javax.validation.Valid

@Controller
@RequestMapping("/web/backstage/users")
open class UsersRestController {

    @Resource
    open lateinit var usersService: UsersService

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
}