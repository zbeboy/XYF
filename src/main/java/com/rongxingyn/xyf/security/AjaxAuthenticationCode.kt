package com.rongxingyn.xyf.security

/**
 * Created by zbeboy 2017-11-02 .
 * 安全登录错误码
 **/
class AjaxAuthenticationCode {
    companion object {
        /*
        权限异常
        */
        @JvmField
        val AU_ERROR_CODE = 1

        /*
        全部正确
        */
        @JvmField
        val OK_CODE = 3

        /*
        账号不存在
        */
        @JvmField
        val USERNAME_IS_NOT_EXIST_CODE = 5

        /*
        密码为空
        */
        @JvmField
        val PASSWORD_IS_BLANK = 7

        /*
        邮箱为空
        */
        @JvmField
        val USERNAME_IS_BLANK = 8

        /*
        账号已被注销
        */
        @JvmField
        val USERNAME_IS_ENABLES = 10
    }
}