package com.rongxingyn.xyf.security

import com.rongxingyn.xyf.security.AjaxAuthenticationCode
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by zbeboy 2017-11-02 .
 * Returns a 401 error code (Unauthorized) to the client, when Ajax authentication fails.
 **/
@Component
class AjaxAuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse,
                                         exception: AuthenticationException) {
        response.writer.print(AjaxAuthenticationCode.AU_ERROR_CODE)
    }
}