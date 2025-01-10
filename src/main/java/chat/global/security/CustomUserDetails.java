package chat.global.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String role;
    private String password;

    private CustomUserDetails(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = "USER";
    }

    public static CustomUserDetails of(Long id, String username, String password) {
        return new CustomUserDetails(id, username, password);
    }

    public static CustomUserDetails of(Long id) {
        return new CustomUserDetails(id, "", "");
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getUsername() {
        return this.username;
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
}