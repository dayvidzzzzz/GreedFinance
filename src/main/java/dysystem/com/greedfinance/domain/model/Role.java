package dysystem.com.greedfinance.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements GrantedAuthority {

    private Long id;
    private String name;

    @Override
    public @Nullable String getAuthority() {
        return name.startsWith("ROLE_") ? name : "ROLE_" + name;
    }
}
