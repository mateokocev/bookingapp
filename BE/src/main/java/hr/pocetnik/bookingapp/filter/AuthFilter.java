package hr.pocetnik.bookingapp.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class AuthFilter extends GenericFilterBean {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null) {
            // Check if the header starts with "Bearer "
            if (authHeader.startsWith("Bearer ")) {
                // Extract the token by removing "Bearer "
                String token = authHeader.substring(7);

                try {

                    // Parse and validate the token
                    Claims claims = Jwts.parser()
                            .verifyWith((SecretKey) key)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();

                    // Extract the userId from the claims
                    Integer userId = claims.get("Id", Integer.class);
                    httpRequest.setAttribute("Id", userId);

                } catch (Exception e) {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or expired token");
                    return;
                }
            } else {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token must be in the format: Bearer [token]");
                return;
            }
        } else {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}