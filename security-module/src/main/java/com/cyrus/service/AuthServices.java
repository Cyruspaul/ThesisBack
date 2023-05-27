package com.cyrus.service;

import com.cyrus.config.APIResponse;
import com.cyrus.config.FaSystemException;
import com.cyrus.config.MinUnit_Constants;
import com.cyrus.models.DTO.UserModelDTO;
import com.cyrus.models.DTO.UserRequestDTO;
import com.cyrus.models.MyUserDetails;
import com.cyrus.models.RedisDataDTO;
import com.cyrus.repository.RoleMapper;
import com.cyrus.repository.UserModelRepository;
import com.cyrus.util.MyJWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServices {
    @Autowired
    private final MyJWTUtil jwtUtils;

    @Qualifier("redis_template")
    @Autowired
    private final RedisTemplate<String, Object> redisClient = new RedisTemplate<String, Object>();
    @Autowired
    private final MyUserDetailService userDetailService;
    @Autowired
    private final UserModelRepository repositoryMapper;
    @Autowired
    private final RoleMapper roleMapper;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public APIResponse<?> registerNewUSer(UserModelDTO userRequestDTO) {
        return APIResponse.success(repositoryMapper.insert(userRequestDTO.mapToPo()));
    }

    public APIResponse<?> verifyToken(String token) {
        //ADD A REDIS OPERATION HERE TO REPLACE NAME
        RedisDataDTO redisDataDTO = (RedisDataDTO) redisClient.opsForValue().get("admin");
        System.out.println("USER FROM REDIS" + redisDataDTO);
        assert redisDataDTO != null;
        return APIResponse.success(jwtUtils.verifyToken(token, redisDataDTO)
        );
    }

    public APIResponse<?> login(UserRequestDTO userRequestDTO) {
        UserModelDTO responseEntity = new UserModelDTO();

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequestDTO.getUsername(),
                            userRequestDTO.getPassword()
                    )
            );
            System.out.println("AUTHENTICATED : -------------- " + authenticate.isAuthenticated());
            if (authenticate.isAuthenticated()) {
                MyUserDetails userDetails = (MyUserDetails) authenticate.getPrincipal();
                System.out.println("USERDETAILS_________________" + userDetails);
                String generateToken = jwtUtils.createToken(userRequestDTO.getUsername());
//                System.out.println("JWT_________________"+generateToken);
                List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) userDetails.getAuthorities();
//                System.out.println(authorities);
                GrantedAuthority authority = ((List<? extends GrantedAuthority>) userDetails.getAuthorities()).get(0);
                switch (authority.getAuthority()) {
                    case "111111":
                        responseEntity.setRolename("超级管理员");
                        break;
                    case "222222":
                        responseEntity.setRolename("教师");
                        break;
                    case "333333":
                        responseEntity.setRolename("学生");
                        break;
                    case "444444":
                        responseEntity.setRolename("本科学生");
                        break;
                    case "555555":
                        responseEntity.setRolename("555555");
                        break;
                }

                responseEntity.setUsername(userRequestDTO.getUsername());
                responseEntity.setRole(authority.getAuthority());
                responseEntity.setToken(generateToken);

                //NO NEED REDIS FOR THE USER DUE TO THE JWT
//                redisClient.opsForValue().set(userRequestDTO.getUsername(), JSONObject.from(responseEntity));

                return APIResponse.success(responseEntity);
            }
        } catch (BadCredentialsException e) {
            throw new FaSystemException(MinUnit_Constants.WARNING_CODE, "Bad Credentials");
        } catch (Exception e) {
            System.out.println(e.getCause());
            throw new FaSystemException(MinUnit_Constants.WARNING_CODE, e.getMessage());
        }

        return APIResponse.warning().message("Login Failed");
    }
}
