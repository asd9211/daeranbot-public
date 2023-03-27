package com.project.drbot.common.config.security;

import com.project.drbot.common.config.interceptor.SingleVisitInterceptor;
import com.project.drbot.common.config.filter.JwtAuthFilter;
import com.project.drbot.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    private final SingleVisitInterceptor singleVisitInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(36000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(singleVisitInterceptor)
                .addPathPatterns("/daeran/**");
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .antMatchers(
                            "/images/**",
                            "/js/**",
                            "/css/**",
                            "/main",
                            "/board/**"
                    );

        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement() // jwt를 사용하기 위해 session 해제
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AuthURL.USER_PATH + "/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(AuthURL.ADMIN_PATH + "/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
                .antMatchers("/community/regist/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll()
                .and()
//                .addFilterBefore(new IpThrottlingFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedPage("/error/403.html")
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
//                .formLogin()
//                .loginPage("/auth/login/page")
//                .loginProcessingUrl("/auth/login")
//                .defaultSuccessUrl("/main")
//                .failureHandler( // 로그인 실패 후 핸들러
//                        new AuthenticationFailureHandler() {
//                            @Override
//                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
//                                System.out.println("exception: " + exception.getMessage());
//                                response.sendRedirect("/login");
//                            } // 익명 객체 사용
//                        })
//                .and()
                .logout()
                .logoutSuccessUrl("/main");

        return http.build();
    }
}
