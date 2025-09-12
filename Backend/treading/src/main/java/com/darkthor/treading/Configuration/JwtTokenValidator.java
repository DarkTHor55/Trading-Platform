package com.darkthor.treading.Configuration;

import com.darkthor.treading.Configuration.JwtConstant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.SecretKey;


public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken=request.getHeader(JwtConstant.JWT_HEADER);
        if(jwtToken!=null){
            try{
                SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SCERECT_KEY.getBytes());
                Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(jwtToken).getBody();
                String email=String.valueOf(claims.get("email"));
                String authorities=String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> authorityList= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication auth=new UsernamePasswordAuthenticationToken(email,null,authorityList);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception a){
                throw new RuntimeException("invalid Token...");
            }
        }
        filterChain.doFilter(request,response);

    }
    
}
