package com.rongxingyn.xyf.filter

import com.rongxingyn.xyf.security.AjaxAuthenticationCode
import com.rongxingyn.xyf.service.system.UsersService
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import org.springframework.web.context.support.WebApplicationContextUtils
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by zbeboy 2017-11-02 .
 **/
class SecurityLoginFilter : Filter {
    override fun destroy() {}

    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val request = servletRequest as HttpServletRequest
        val response = servletResponse as HttpServletResponse
        if ("POST".equals(request.method, ignoreCase = true) && request.requestURI.endsWith("/login")) {
            val username = StringUtils.trimWhitespace(request.getParameter("username"))
            val password = StringUtils.trimWhitespace(request.getParameter("password"))

            if (!StringUtils.hasLength(username)) {// 邮箱不为空
                response.writer.print(AjaxAuthenticationCode.USERNAME_IS_BLANK)
                return
            }

            if (!StringUtils.hasLength(password)) {// 密码不为空
                response.writer.print(AjaxAuthenticationCode.PASSWORD_IS_BLANK)
                return
            }

            val context = request.session.servletContext
            val ctx = WebApplicationContextUtils
                    .getWebApplicationContext(context)
            val usersService = ctx!!
                    .getBean("usersService") as UsersService
            val users = usersService.findByUsername(username)

            if (ObjectUtils.isEmpty(users)) {// 用户是否存在
                response.writer.print(AjaxAuthenticationCode.USERNAME_IS_NOT_EXIST_CODE)
                return
            }

            if (ObjectUtils.isEmpty(users!!.enabled) || users.enabled <= 0) {// 用户是否已被注销
                response.writer.print(AjaxAuthenticationCode.USERNAME_IS_ENABLES)
                return
            }
            filterChain!!.doFilter(servletRequest, servletResponse)
        } else {
            filterChain!!.doFilter(servletRequest, servletResponse)
        }
    }

    override fun init(p0: FilterConfig?) {}
}