package dysystem.com.greedfinance.config.security;

import dysystem.com.greedfinance.utils.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        try {
            String tenantId = null;

            if (requestURI.contains("/login/")) {
                tenantId = extractTenantFromPath(requestURI);
            } else {
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    if (tokenProvider.isValid(token))
                        tenantId = tokenProvider.extractTenant(token);
                }
            }

            if (tenantId != null && !tenantId.isEmpty())
                TenantContext.setCurrentTenantId(tenantId);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            TenantContext.clear();
        }
    }

    private String extractTenantFromPath(String path) {
        String[] parts = path.split("/");
        for (int i = 0; i < parts.length; i++)
            if ("login".equals(parts[i]) && i + 1 < parts.length)
                return parts[i + 1];
        return null;
    }
}