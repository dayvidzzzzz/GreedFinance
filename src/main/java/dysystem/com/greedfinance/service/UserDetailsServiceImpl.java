package dysystem.com.greedfinance.service;

import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.utils.TenantContext;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NonNull String login) throws UsernameNotFoundException {
        String tenantId = TenantContext.getCurrentTenantId();

        return userRepository.findByUsernameOrEmailAndTenantId(login, tenantId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + login + " for tenant: " + tenantId));
    }
}