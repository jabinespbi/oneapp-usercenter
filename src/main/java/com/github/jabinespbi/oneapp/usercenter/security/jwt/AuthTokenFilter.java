package com.github.jabinespbi.oneapp.usercenter.security.jwt;

import com.github.jabinespbi.oneapp.usercenter.security.exception.FailedToGetJwtFromRequestHeaderException;
import com.github.jabinespbi.oneapp.usercenter.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.lang.String.format;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            var jwt = getJwtFromRequest(request);
            jwtUtils.validateJwtToken(jwt);

            var username = jwtUtils.getUserNameFromJwtToken(jwt);
            var userDetails = userDetailsService.loadUserByUsername(username);
            var authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            var webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);

            authentication.setDetails(webAuthenticationDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SignatureException e) {
            logger.error("AuthTokenFilter: Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("AuthTokenFilter: Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            // TODO: Implement refresh token
            logger.error("AuthTokenFilter: JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("AuthTokenFilter: JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("AuthTokenFilter: JWT claims string is empty: {}", e.getMessage());
        } catch (FailedToGetJwtFromRequestHeaderException e) {
            logger.error(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Failed to get JWT from request header");
        } catch (Exception e) {
            logger.error("AuthTokenFilter: Failed to set user for authentication", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) throws FailedToGetJwtFromRequestHeaderException {
        var headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        var errorMessage = format("AuthTokenFilter: Failed to get JWT from request header %s", headerAuth);
        throw new FailedToGetJwtFromRequestHeaderException(errorMessage);
    }
}
