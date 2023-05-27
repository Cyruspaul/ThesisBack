package com.cyrus.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Redis data dto.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RedisDataDTO {

    private String username;
    private String password;
    private String role;
    private boolean enabled = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;

//    public static RedisDataDTO fromUSerModelPO(UserModelPO userModelPO){
//        return RedisDataDTO.builder()
//                .username(userModelPO.getUsername())
//                .password(userModelPO.getPassword())
//                .role(userModelPO.getRole())
//                .enabled(true)
//                .credentialsNonExpired(true)
//                .accountNonExpired(true)
//                .accountNonLocked(true)
//                .build();
//    }

}
