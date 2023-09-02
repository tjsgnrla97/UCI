package com.cos.jwt.config.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.Auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import com.auth0.jwt.JWT;

// 스프링시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// /login 요청해서 username, password 전송하면(post)
// UsernamePasswordAuthenticationFilter 동작을 함

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");

        //1. username, password 받아서
        //2. 정상인지 로그인 시도 authenticationManager로 로그인 시도를 하면
        // PrincipalDetailsService 호출 loadUserByUsername() 함수 실행됨

        //3. PrincipalDetails를 세션에 담고 (이걸 세션에 안담으면 권한관리 불가__ 권한관리 안할거면 세션에 안담아도 됨)
        //4. JWT토큰을 만들어서 응답해주면 됨

        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while((input = br.readLine())!=null) {
//                System.out.println(input);
//            }
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            //authenticationToken 토큰 만들었음
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            //PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨
            //authenticationManager에 토큰을 넣어서 던지면 인증을 해준다. 인증을 하면 authentication을 받는다.
            //이 authentication에는 내 로그인한 정보가 담긴다.
            //DB에 있는 username과 password가 일치한다
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // => 로그인이 되었다는 뜻
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료됨 : " + principalDetails.getUser().getUsername()); //로그인 정상적으로 되었다는 뜻
            //System.out.println(request.getInputStream().toString()); //이 바이트 안에 id,pw가 담겨져있음

            // 로그인이 정상적으로 되었다면 authentication 객체가 session영역에 저장을 해야하고, 그 방법이 return 해주면 됨
            //리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는것
            //굳이 jwt토큰을 사용하면서 세션을 만들 이유가 없음. 단지 권한 처리때문에 session 넣어준것


            return authentication;
        } catch (IOException e) {
           e.printStackTrace();
        }
        return null;
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    //jwt토큰을 만들어서 request 요청한 사용자에게 JWT토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨:인증이 완료되었다는 뜻");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        //jwt토큰 생성
        String jwtToken = JWT.create()
                .withSubject("cos토큰") //
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*100)))//만료시간 현재시간 + 설정시간
                .withClaim("id", principalDetails.getUser().getSeq())//withclaim은 비공개 클레임
                .withClaim("email", principalDetails.getUser().getEmail())
                .sign(Algorithm.HMAC512("cos")); //저 값으로 sign하는 것

        response.addHeader("Authorization", "Bearer "+jwtToken); //postman에서 확인 시 header에 Authorization에 jwt토큰이 옴
    }
}
