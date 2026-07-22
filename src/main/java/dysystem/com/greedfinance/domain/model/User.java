package dysystem.com.greedfinance.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createAt;
    private boolean active;

    // ✅ APENAS IDs - não mapeia objetos completos
    private String tenantId;          // ← ID do Tenant
    private Collection<Long> roleIds; // ← IDs das Roles (Many-to-Many)

    // ❌ NÃO TER OBJETOS COMPLETOS
    // private Tenant tenant;
    // private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna uma coleção vazia ou busca as roles de outro lugar
        // Isso será tratado pelo Security Service
        return new ArrayList<>();
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    // Método auxiliar para criar User com roles carregadas
    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles;
    }
}