package intern.server.security.user;

import intern.server.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
public class UserPrincipal implements UserDetails {

    @Getter
    private final String id;

    @Getter
    private final String email;

    private String password;

    private final Collection<? extends GrantedAuthority> authorities;

    @Setter
    private Map<String, Object> attributes;

    public UserPrincipal(String id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }


    public static UserPrincipal create(User user, List<String> roles) {
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        log.info("authorities user: {}", authorities);

        UserPrincipal userPrincipal =  new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassWord(),
                authorities
        );

        return userPrincipal;
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes, List<String> roles) {

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        log.info("Authorities for user {}: {}", user.getUserName(), authorities);

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassWord(),
                authorities
        );
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
