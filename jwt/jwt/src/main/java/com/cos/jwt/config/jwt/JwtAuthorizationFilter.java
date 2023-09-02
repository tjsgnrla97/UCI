package com.cos.jwt.config.jwt;

// 시큐리티가 filter 가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는 것이 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 타지 않음

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.Auth.PrincipalDetails;
import com.cos.jwt.model.RedisLogout;
import com.cos.jwt.model.User;
import com.cos.jwt.repository.RedisLogoutRepository;
import com.cos.jwt.repository.UserRepository;
import com.cos.jwt.service.UserService;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    UserRepository userRepository;

    RedisLogoutRepository redisLogoutRepository;
    //    private RedisCheck redisCheck;
    UserService userService;



    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, RedisLogoutRepository redisLogoutRepository, UserService userService) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.redisLogoutRepository = redisLogoutRepository;
        this.userService = userService;
    }

    //인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //super.doFilterInternal(request, response, chain);
        System.out.println("인증이나 권한이 필요한 주소 요청이 됨");
        System.out.println(request);

//        String uri = request.getRequestURI();
//        System.out.println(uri);
//        if("user/test".equals(uri)) {
//            chain.doFilter(request, response);
//        } else {
//            super.doFilter(request, response, chain);
//        }

        //bearer 붙어있음
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("헤더있는지확인" + jwtHeader); //값 : Bearer eamn123brasurfeajkrebn

        //Optional<RedisLogout> redisLogout = userService.getRedisLogoutByToken(jwtHeader);


//
//
        //header가 있는지 확인
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            response.sendError(403,"로그인 후 사용해주세요");
            chain.doFilter(request,response);
            return;
        }


        //JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
        String jwtToken = request.getHeader("Authorization").replace("Bearer ","");

        String email =
                JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken).getClaim("email").asString();

        System.out.println(email);
        //서명이 정상적으로 됨
        if(email!=null) {
            System.out.println("email정상");
            User userEntity = userRepository.findByEmail(email);
            System.out.println("userEntity : " + userEntity);
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            //System.out.println("PrincipalDetails : " + principalDetails.getUsername()+"sdasdasd");


            Optional<RedisLogout> redisLogout = userService.getRedisLogoutByToken(jwtHeader);

            if(redisLogout.isPresent()) {
                System.out.println("DB에 값이 있다는 뜻");
                response.sendError(403,"다시 로그인 해주세요");
                chain.doFilter(request,response);
                return;
            } else {
                System.out.println("db에 값이 없어서 null값을 가져옴");
                //jwt토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

                //세션공간. 강제로 시큐리티의 세션에 접근하여 Authentuication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

                chain.doFilter(request,response);

            }



        }

    }
}
