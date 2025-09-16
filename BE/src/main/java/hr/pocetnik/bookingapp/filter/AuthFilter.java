package hr.pocetnik.bookingapp.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
public class AuthFilter extends OncePerRequestFilter {

    private final SecretKey key;

    // Use constructor injection for dependencies - it's a best practice
    public AuthFilter(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 1. Check if the header exists and is in the correct format
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Let the request continue
            return;
        }

        // 2. Extract the token
        String token = authHeader.substring(7);

        try {
            // 3. Parse and validate the token
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // 4. Extract claims and create an Authentication object
            // It's common to use the user ID or username as the "principal"
            String userId = claims.get("Id", String.class);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    AuthorityUtils.createAuthorityList("ROLE_USER")
            );

            // 5. Set the user as authenticated in Spring Security's context
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // If the token is invalid, clear the context and let the request fail later
            SecurityContextHolder.clearContext();
            // You can log the error here if you want
        }

        // 6. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}