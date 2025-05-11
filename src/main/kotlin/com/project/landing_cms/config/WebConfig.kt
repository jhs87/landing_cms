package com.project.landing_cms.config

import com.project.landing_cms.service.BoardService
import com.project.landing_cms.service.MemberRelateKeyService
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan(basePackages = ["com.project"])
class WebConfig(
    private val memberRelateKeyService: MemberRelateKeyService,
    private val boardService: BoardService
    ): WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOrigins(
                "http://localhost:8080/",
                "https://hasooproject.net"
            )
            .maxAge(3000)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(CustomHandlerInterceptor(memberRelateKeyService, boardService))
            .excludePathPatterns("/css/**", "/img/**", "/js/**", "/scss/**", "/plugins/**");
    }
}