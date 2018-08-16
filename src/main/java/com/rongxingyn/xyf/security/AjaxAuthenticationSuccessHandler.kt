package com.rongxingyn.xyf.security

import com.rongxingyn.xyf.security.AjaxAuthenticationCode
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by zbeboy 2017-11-02 .
 * Spring Security success handler, specialized for Ajax requests.
 **/
@Component
class AjaxAuthenticationSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse,
                                         authentication: Authentication) {
        response.writer.print(AjaxAuthenticationCode.OK_CODE)
    }
}