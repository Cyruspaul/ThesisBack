package com.cyrus.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cyrus.config.APIResponse;
import com.cyrus.config.R;
import com.cyrus.mapper.ApplicationMapper;
import com.cyrus.models.Application;
import com.cyrus.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school/applicant")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService service;
    private final ApplicationMapper applicationMapper;

    @GetMapping("findAll")
    public R findApplication() {
        List<Application> list = applicationMapper.selectList(null);
        return R.ok().data("items", list);
    }

    @PostMapping("getOne/{id}")
    public APIResponse<?> getApplication(@PathVariable("id") String id) {
        return service.getInfo(id);
    }

    @PostMapping("accept/{id}")
    public R acceptApplication(@PathVariable("id") String id) {
        Application application = applicationMapper.selectById(id);
        if (application == null)
            return R.error().data(null).message("没有找到 ！");
        return R.ok().data("items", application);
    }

    @PostMapping("refuse/{id}")
    public R refuseApplication(@PathVariable("id") String id) {
        Application application = new Application();
        application.setRegNum(id);
        application.setStatus(1);
        int result = applicationMapper.update(
                application, new UpdateWrapper<Application>().eq("application_status", 1)
        );
        if (result != 1)
            return R.error().data(null).message("更新操作失败 ！");
        return R.ok().data(null).message("更新成功 ！");
    }

    @DeleteMapping("logicDelete/{id}")
    public R ApplicationDelete(@PathVariable String id) {
        int b = applicationMapper.deleteById(id);
        if (b == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * Save change
     *
     * @return APIResponse： Operation status
     */
    @RequestMapping("saveChange")
    public APIResponse<?> saveChange(@RequestBody Application applicationInfo) {
        return service.saveChange(applicationInfo);
    }


    /**
     * A new student try to register
     *
     * @return APIResponse： Operation status
     */
    @RequestMapping("registerNew")
    public APIResponse<?> registerNew(@RequestBody Application applicantRegisterDTO) {
        return service.registerNew(applicantRegisterDTO);
    }


}
