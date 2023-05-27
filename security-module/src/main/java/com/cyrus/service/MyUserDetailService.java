package com.cyrus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.FaSystemException;
import com.cyrus.config.MinUnit_Constants;
import com.cyrus.config.MyPasswordEncoder;
import com.cyrus.models.MyUserDetails;
import com.cyrus.models.PO.UserModelPO;
import com.cyrus.models.Role;
import com.cyrus.repository.RoleMapper;
import com.cyrus.repository.UserModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final MyPasswordEncoder passwordEncoder;
    private final UserModelRepository repository;
    private final RoleMapper roleMapper;

    //    AuthenticationManager authenticationManager(){
//        return  ;
//    }
    @Override
    public UserDetails loadUserByUsername(String payload) throws FaSystemException {


        UserModelPO user = repository.selectOne(new QueryWrapper<UserModelPO>().eq("account", payload));
        System.out.println("USER FROM DTB ----- " + user);

        if (user == null) {
            System.out.println("USER NOT FOUND  EXCEPTION");
            throw new FaSystemException(MinUnit_Constants.WARNING_CODE, "User Informations Invalid...");
        }

        Role current_role = roleMapper.selectOne(new QueryWrapper<Role>().eq("role_code", user.getRole()));

        if (current_role == null) {
            System.out.println("ROLE NOT FOUND  EXCEPTION");
            throw new FaSystemException(MinUnit_Constants.WARNING_CODE, "You don't have credentials. Contact Admin !");
        }
//        user.setRole(current_role.getRoleName());
        return new MyUserDetails().fromUserModelPO(user);
    }

    public int saveUser() {
        return repository.insert(UserModelPO.builder()
//                .email("admin2@mail.com")
                .role("ADMIN")
                .password(passwordEncoder.encode("123456"))
                .username("admin123")
                .build());
    }

    //    @Override
    public UserDetails loadUserByUsername2(String payload) throws UsernameNotFoundException {

        String username = payload.split("/")[0];
        String password = payload.split("/")[1];

        UserModelPO user = repository.selectOne(new QueryWrapper<UserModelPO>().eq("username", payload));
        System.out.println(user.getPassword());
        System.out.println(password);
        System.out.println(passwordEncoder.matches(password, user.getPassword()));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("USER PASSWORD ERROR EXCEPTION");
            throw new UsernameNotFoundException("USER PASSWORD ERROR FOUND");
        } else {
            MyUserDetails myUserDetails = new MyUserDetails().fromUserModelPO(user);
            System.out.println("username = " + myUserDetails);
            return myUserDetails;
        }

    }

}
