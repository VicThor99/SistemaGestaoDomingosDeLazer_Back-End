package br.com.domingosdelazer.SistemaGestao.security.jwt;

import br.com.domingosdelazer.SistemaGestao.service.impl.UsuarioServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UsuarioServiceImpl userService;

    public JWTAuthFilter(JWTService jwtService, UsuarioServiceImpl userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain)
            throws ServletException, IOException {

        String auth = httpServletRequest.getHeader("Authorization");

        if(!StringUtils.isEmpty(auth) && auth.startsWith("Bearer")){
            String token = auth.split(" ")[1];
            if(jwtService.validToken(token)){
                UserDetails userDetails = userService.loadUserByUsername(jwtService.getUsernameUser(token));
                UsernamePasswordAuthenticationToken upat =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
