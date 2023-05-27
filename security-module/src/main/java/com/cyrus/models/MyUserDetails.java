package com.cyrus.models;

import com.cyrus.models.PO.UserModelPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MyUserDetails implements UserDetails {

    private RedisDataDTO user;

    public MyUserDetails fromUserModelPO(UserModelPO user) {
        MyUserDetails build = MyUserDetails.builder()
                .user(RedisDataDTO.fromUSerModelPO(user))
                .build();
        System.out.println(build.user);
        System.out.println(build);
        return build;
    }

    @Override
    public String toString() {
        return this.getUsername() +
                this.getPassword() +
                this.getAuthorities().toString() +
                this.isAccountNonExpired() +
                this.isAccountNonLocked() +
                this.isEnabled() +
                this.isCredentialsNonExpired();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER_ROLE");
//        if(StringUtils.isEmpty(user.getRole())){
//            authority = new SimpleGrantedAuthority(user.getRole());
//        }
//
//        authorities.add(authority);
//        return authorities;
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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
