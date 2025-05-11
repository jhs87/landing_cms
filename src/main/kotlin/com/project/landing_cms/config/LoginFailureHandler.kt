package com.project.landing_cms.config

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import java.io.IOException
import java.net.UnknownHostException

@Configuration
class LoginFailureHandler : AuthenticationFailureHandler {
    val defaultErrorMsg = "알 수 없는 에러가 발생했습니다."
    val userNotFound = "존재하지 않는 사용자입니다."
    val badCredentials = "아이디 또는 비밀번호가 일치하지 않습니다."
    val internalAuth = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요."
    val acNotFound = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
    val defaultFailureUrl = "/login?error=true"

    /**
     * 로그인 실패 핸들러
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     * @throws UnknownHostException
     */
    @Throws(IOException::class, ServletException::class, UnknownHostException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest, response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        var errorMsg = defaultErrorMsg
        errorMsg = when (exception) {
            is AuthenticationServiceException -> userNotFound
            is BadCredentialsException -> badCredentials
            is InternalAuthenticationServiceException -> internalAuth
            is AuthenticationCredentialsNotFoundException -> acNotFound
            else -> error(errorMsg)
        }

        val memberId = request.getParameter("memberId")
        request.setAttribute("errorMsg", errorMsg)
        request.setAttribute("memberId", memberId)
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response)
    }
}