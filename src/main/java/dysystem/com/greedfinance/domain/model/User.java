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
    private Tenant tenant;
    private Collection<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
