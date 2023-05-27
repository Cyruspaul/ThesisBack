package com.cyrus.models.DTO;

import com.cyrus.models.PO.UserModelPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModelDTO {

    private String username;
    private String password;
    private String role;
    private String rolename;
    private String token;

    public UserModelPO mapToPo() {
        return UserModelPO.builder()
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
