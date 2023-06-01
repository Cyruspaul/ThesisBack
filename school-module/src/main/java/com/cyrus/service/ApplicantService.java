package com.cyrus.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.MD5;
import com.cyrus.config.APIResponse;
import com.cyrus.config.FaSystemException;
import com.cyrus.config.MinUnit_Constants;
import com.cyrus.mapper.AccountMapper;
import com.cyrus.mapper.ApplicationMapper;
import com.cyrus.mapper.StudentInfoMapper;
import com.cyrus.models.Account;
import com.cyrus.models.Application;
import com.cyrus.models.StudentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicationMapper applicantRepositoryMapper;
    private final StudentInfoMapper studentInfoMapper;
    private final AccountMapper accountMapper;
    private final RedisTemplate<String, Object> redisKeyValueTemplate;


    public APIResponse<?> login(Map<String, String> data) {
        if (data.isEmpty() || data.get("username").isEmpty() || data.get("password").isEmpty()) {
            throw new FaSystemException(MinUnit_Constants.ERROR_CODE, "Login Informations Error !!!");
        }
        String username = data.get("username").trim();
        String password = data.get("password").trim();
        if (!applicantRepositoryMapper.exists(new QueryWrapper<Application>()
                .eq("username", username)
                .eq("password", password)
        )) {
            return APIResponse.error(null);
        }
        Map<String, Object> value = applicantRepositoryMapper.selectMaps(new QueryWrapper<Application>()
                .eq("username", username)
                .select("student_registration_number as code", "username as id", "role", "student_en_name as name")
        ).get(0);
        return APIResponse.success(value);
    }

    public APIResponse<?> registerNew(Application applicantRegisterDTO) {
        int insert = 0;
        boolean exists = applicantRepositoryMapper.exists(new QueryWrapper<Application>()
                .eq("student_passport", applicantRegisterDTO.getPassport())
                .or()
                .eq("student_email", applicantRegisterDTO.getEmail())
        );
        if (exists) {
            return APIResponse.error().message("The user has already applied !!");
        } else {
            boolean exists1 = accountMapper.exists(new QueryWrapper<Account>()
                    .eq("account", applicantRegisterDTO.getPassport())
            );
            if (exists1) {
                return APIResponse.error().message("You are a STUDENT !!");
            } else {
                applicantRegisterDTO.setGmtModified(Date.valueOf(LocalDate.now()));
                applicantRegisterDTO.setGmtCreate(Date.valueOf(LocalDate.now()));
                applicantRegisterDTO.setIsDeleted(false);
                insert = applicantRepositoryMapper.insert(applicantRegisterDTO);
                Account account = new Account();

                account.setAccount(applicantRegisterDTO.getPassport());
                account.setPassword(MD5.encrypt(applicantRegisterDTO.getPassword()));
                account.setRole("333333");
                account.setIsDeleted(false);
                account.setGmtModified(Date.valueOf(LocalDate.now()));
                account.setGmtCreate(Date.valueOf(LocalDate.now()));

                accountMapper.insert(account);
            }
        }
        return APIResponse.success(insert).message("Registration Success");
    }

    public APIResponse<?> getInfo(String id) {

        Application applicationInfo = applicantRepositoryMapper.selectOne(new QueryWrapper<Application>().eq("student_passport", id));
//        System.out.println(applicationInfo);
        if (applicationInfo == null) {
            return APIResponse.error(null).message("Invalid application Number ");
        }
        if (applicationInfo.getStatus() == 2) {
            System.out.println("ACCPETEDDDDDDDDD-------------------------------------------------------");
            StudentInfo studentBaseModel = studentInfoMapper.selectById(id);
            return APIResponse.success(studentBaseModel);
        } else {
            return APIResponse.success(applicationInfo);
        }

    }

    /**
     * 更新一个申请的信息
     *
     * @param applicationInfo
     * @return 更新成功或失败的提示
     */
    public APIResponse<?> saveChange(Application applicationInfo) {
        applicationInfo.setStatus(1);
        applicationInfo.setGmtModified(Date.valueOf(LocalDate.now()));
        int i = applicantRepositoryMapper.updateById(applicationInfo);
        return i <= 0 ? APIResponse.warning(null).message("Update error") :
                APIResponse.success(i).message("Update Succeeded");

    }
}
