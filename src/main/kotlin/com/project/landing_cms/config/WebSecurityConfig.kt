package com.project.landing_cms.config

import com.project.landing_cms.service.impl.MemberServiceImpl
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
class WebSecurityConfig(private val memberServiceImpl: MemberServiceImpl) {

    private var ignoring = arrayOf(
        "/plugins/**",
        "/scss/**",
        "/js/**",
        "/demo/**",
        "/css/**",
        "/img/**",
        "/upload/**"
    )

    private var whiteList = arrayOf(
        "/login",
        "/logout",
        "/join",
        "/reg",
        "/members/**",
        "/lbCheck"
    )

    private var authList = arrayOf(
        "ROLE_ADMIN",
        "ROLE_LANDING-MANAGER",
        "ROLE_BOARD-MANAGER"
    )

    @Bean
    @Throws(Exception::class)
    fun filterChainConfigure(security: HttpSecurity): SecurityFilterChain {
        security
            .cors(Customizer.withDefaults())
            .csrf(Customizer.withDefaults())
            .authorizeHttpRequests { authorize -> authorize
                .requestMatchers("/landing/**", "/landing/**", "/dataKey/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(*whiteList).permitAll()
                .requestMatchers(*ignoring).permitAll()
                .requestMatchers("/**").hasAnyAuthority(*authList)
                .anyRequest().authenticated()
            }
            .formLogin { login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login-proc")
                .usernameParameter("memberId")
                .passwordParameter("password")
                .successHandler(LoginSuccessHandler())
                .failureHandler(LoginFailureHandler()).permitAll()
            }
            .logout { logout -> logout
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
            }
            .sessionManagement(Customizer.withDefaults())
            .rememberMe { remember -> remember
                .userDetailsService(memberServiceImpl)
                .alwaysRemember(true)
                .rememberMeCookieName("remember-me")
            }

        return security.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}