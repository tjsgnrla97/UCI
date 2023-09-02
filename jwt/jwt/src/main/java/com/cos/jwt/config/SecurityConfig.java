package com.cos.jwt.config;

import com.cos.jwt.config.jwt.JwtAuthenticationFilter;
import com.cos.jwt.config.jwt.JwtAuthorizationFilter;
//import com.cos.jwt.filter.MyFilter3;
import com.cos.jwt.repository.RedisLogoutRepository;
import com.cos.jwt.repository.UserRepository;
import com.cos.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
//**1** Spring Security를 사용하기 위해서 Spring Security Filter Chain을 사용한다는 것을 명시해야함
//이것은 WebSecurityConfigurerAdapter를 상속받은 클래스에 @EnableWebSecurity 어노테이션을 달아주면 해결
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    private final RedisLogoutRepository redisLogoutRepository;

    private final UserService userService;

    static final String[] WHITELIST = {
            "/user/join",
            "/user/logout",
            "/swagger-ui/**",
            "/socket/**",
            "/receive/**",
            "/send/**",
            "/room",
            "/room/create",
            "/room/roomSeq/**",
            "/room/**"
    };

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**")
                .antMatchers("/h2-console/**", "/swagger-ui.html#/**")
                .antMatchers(WHITELIST);
    }

    //HttpSecurity : 인증, 인가의 세부적인 기능을 설정할 수 있도록 API를 제공해주는 클래스스
   @Override
   protected void configure(HttpSecurity http) throws Exception {
       //http.addFilterBefore(new LogoutFilter(), BasicAuthenticationFilter.class);
       http.csrf().disable();
       http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .addFilter(corsFilter) //@CrossOrigin(인증x), 시큐리티 필터에 등록 인증(O)
               .formLogin().disable()
               .httpBasic().disable();
       http.authorizeRequests().antMatchers(WHITELIST).permitAll();
       http
               .authorizeRequests()
               .antMatchers("/api/v1/user/**")
               .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') ")
               .antMatchers("/api/v1/admin/**")
               .access("hasRole('ROLE_ADMIN')");
//               .anyRequest().permitAll();
       http
               .addFilter(new JwtAuthenticationFilter(authenticationManager())) //AuthenticationManager
               .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, redisLogoutRepository, userService));

   }
}
