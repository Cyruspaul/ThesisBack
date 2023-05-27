package com.cyrus.applicant.controller;


import com.cyrus.applicant.feign.SchoolModuleFeignClient;
import com.cyrus.applicant.models.ApplicationInfo;
import com.cyrus.common.config.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/applicant")
@RequiredArgsConstructor
public class ApplicantController {

    private final SchoolModuleFeignClient feignClient;

    /**
     * A new student try to register
     *
     * @return APIResponse： Operation status
     */
    @RequestMapping("register_new")
    public APIResponse<?> registerNew(@RequestBody ApplicationInfo applicantRegisterDTO) {
        return feignClient.registerNew(applicantRegisterDTO);
    }

    /**
     * A new student try to register
     *
     * @return APIResponse： Operation status
     */
    @PostMapping("getInfo/{id}")
    public APIResponse<?> getInfo(@PathVariable("id") String id) {
        return feignClient.getOneApplication(id);
    }

    /**
     * A new student try to register
     *
     * @return APIResponse： Operation status
     */
    @RequestMapping("saveChange")
    public APIResponse<?> saveChange(@RequestBody ApplicationInfo applicationInfo) {
        return feignClient.saveChange(applicationInfo);
    }
}
