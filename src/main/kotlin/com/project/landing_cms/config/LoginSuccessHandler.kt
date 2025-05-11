package com.project.landing_cms.config

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.io.IOException

@Configuration
class LoginSuccessHandler: AuthenticationSuccessHandler {

    /**
     * 로그인 성공 핸들러
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse,
                                         authentication: Authentication) {

        if (authentication.isAuthenticated) {
            response.sendRedirect("/")
        } else {
            val map: MutableMap<String, Any> = HashMap()
            map["msg"] = "인증된 사용자가 아닙니다."
            map["memberId"] = authentication.name
            request.setAttribute("error", map)
            request.getRequestDispatcher("/login?error=true").forward(request, response)
        }
    }
}