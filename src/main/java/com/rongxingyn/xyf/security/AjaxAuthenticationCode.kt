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
        const val AU_ERROR_CODE = 1

        /*
        全部正确
        */
        const val OK_CODE = 3

        /*
        密码为空
        */
        const val PASSWORD_IS_BLANK = 7

        /*
        邮箱为空
        */
        const val USERNAME_IS_BLANK = 8

        /*
        账号已被注销
        */
        const val USERNAME_IS_ENABLES = 10

        /*
        账号已过期
        */
        const val USERNAME_IS_EXPIRED = 11

        /*
        账号已锁
        */
        const val USERNAME_IS_LOCKED = 12

        /*
        账号凭证已过期
        */
        const val CREDENTIALS_EXPIRED = 13
    }
}