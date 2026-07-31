package dysystem.com.greedfinance.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    private boolean firstAccess;
    private String tenantId;
    private Collection<Long> roleIds;
    private List<String> accountsId;
    private List<Long> savingIds;
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null || roles.isEmpty())
            return List.of();

        return roles.stream()
                .map(role -> (GrantedAuthority) role)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles;
    }
}