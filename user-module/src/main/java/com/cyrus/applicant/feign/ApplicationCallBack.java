package com.cyrus.applicant.feign;


import com.cyrus.applicant.models.ApplicationInfo;
import com.cyrus.common.config.APIResponse;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCallBack implements SchoolModuleFeignClient {

    @Override
    public APIResponse<?> getOneApplication(String id) {
        return APIResponse.warning("getOne FAiled...");
    }

    /**
     * @param applicationInfo
     * @return
     */
    @Override
    public APIResponse<?> saveChange(ApplicationInfo applicationInfo) {
        return APIResponse.warning("Update failed...");
    }

    /**
     * @param applicantRegisterDTO
     * @return
     */
    @Override
    public APIResponse<?> registerNew(ApplicationInfo applicantRegisterDTO) {
        return APIResponse.warning("Registration failed...");
    }

}
