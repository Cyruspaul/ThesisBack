package com.cyrus.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cyrus.config.MD5;
import com.cyrus.config.MinUnit_Constants;
import com.cyrus.config.R;
import com.cyrus.mapper.*;
import com.cyrus.models.Account;
import com.cyrus.models.Application;
import com.cyrus.models.StudentInfo;
import com.cyrus.models.TaskPO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AccountTaskController {

    private final AccountMapper accountMapper;
    private final ApplicationMapper applicationMapper;
    private final TaskMapper taskMapper;
    private final StudentInfoMapper studentInfoMapper;
    private final TeacherInfoMapper teacherInfoMapper;
    private final AnnouncementsMappper announcementsMappper;
    private final MajorsMapper majorsMapper;
    private final CourseInfoMapper courseInfoMapper;
    private final ClassInfoDao classInfoDao;
    private final CollegeInfoMapper collegeInfoMapper;


    //**************************************************STATS****************************************************************************
    @GetMapping("stats/get")
    public R getStats() {

        Long student = studentInfoMapper.selectCount(null);
        Long teacher = teacherInfoMapper.selectCount(null);
        Long application = applicationMapper.selectCount(null);
        Long task = taskMapper.selectCount(null);
        Long notification = announcementsMappper.selectCount(null);
        Long majors = majorsMapper.selectCount(null);
        Long course = courseInfoMapper.selectCount(null);
        Long classc = classInfoDao.selectCount(null);
        Long colleg = collegeInfoMapper.selectCount(null);


        List<TaskPO> taskPOS = taskMapper.selectList(new QueryWrapper<TaskPO>().last("limit 10"));
        JSONObject response = new JSONObject();

        response.put("student", student);
        response.put("teacher", teacher);
        response.put("application", application);
        response.put("task", task);
        response.put("notification", notification);
        response.put("majors", majors);
        response.put("course", course);
        response.put("classc", classc);
        response.put("colleg", colleg);


        return R.ok().data("stats", response);
    }


//**************************************************ACCOUNT****************************************************************************

    @GetMapping("account/findAll")
    public R findAll() {
        List<Account> list = accountMapper.selectList(null);
//        list.forEach(account -> account.setPassword());

        return R.ok().data("items", list);
    }

    @PostMapping("account/search")
    public R search(@RequestBody Map<String, Object> data) {
        List<Account> list = accountMapper.selectList(new QueryWrapper<Account>()
                .like("account", data.get("account") != null ? data.get("account") : "")
        );

        return R.ok().data("items", list);
    }

    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("account/logicDelete/{id}")
    public R logicDeleteTest(@PathVariable String id) {
        int b = accountMapper.deleteById(id);
        if (b == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @PostMapping("account/update")
    public R updateTeacher(@RequestBody Account account) {
        account.setGmtModified(Date.valueOf(LocalDate.now()));
        if (account.getPassword() != null) {
            account.setPassword(MD5.encrypt(account.getPassword()));
        }
        int update = accountMapper.updateById(account);
        if (update == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }
//**************************************************TASKS****************************************************************************

    @ApiOperation(value = "所有列表")
    @GetMapping("task/findAll")
    public R findTask() {
        List<TaskPO> list = taskMapper.selectList(null);

        return R.ok().data("items", list);
    }

    @PostMapping("task/search")
    public R getOne(@RequestBody Map<String, Object> data) {
        List<TaskPO> list = new ArrayList<>();
        list = taskMapper.selectList(new QueryWrapper<TaskPO>()
                .like("sendername", data.get("sendername") != null ? data.get("sendername") : "")
                .eq("senderid", data.get("senderid"))
        );

        return R.ok().data("items", list);
    }

    @DeleteMapping("task/logicDelete/{id}")
    public R taskDelete(@PathVariable String id) {
        int b = taskMapper.deleteById(id);
        if (b == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }


//**************************************************APPLICATIONS****************************************************************************

    @GetMapping("application/findAll")
    public R findApplication() {
        List<Application> list = applicationMapper.selectList(null);
        return R.ok().data("items", list);
    }

    @PostMapping("application/getOne/{id}")
    public R getApplication(@PathVariable("id") String id) {
        Application application = applicationMapper.selectById(id);
        if (application == null)
            return R.error().data(null).message("没有找到 ！");
        return R.ok().data("items", application);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("application/accept/{id}")
    public R acceptApplication(@PathVariable("id") String id) {

        //REGISTER DB CHANGES
        Application application = applicationMapper.selectById(id);
        application.setRegNum(id);
        application.setStatus(2);
        application.setGmtModified(Date.valueOf(LocalDate.now()));
        int result = applicationMapper.updateById(application);
        if (result != 1)
            return R.error().data(null).message("更新操作失败 ！");

        //STUDENT DB CHANGES
        StudentInfo studentInfo = StudentInfo.builder()
                .enrollmentNumber(application.getRegNum())
                .major(application.getMajor())
                .isDeleted(false)
                .stuNumber(String.valueOf(new java.util.Date().getTime()))
                .stuEnName(application.getName())
                .stuName(application.getName())
                .email(application.getEmail())
                .passport(application.getPassport())
                .birthday(application.getBirthday())
                .country(application.getCountry())
                .phone(application.getPhone())
                .gmtCreate(MinUnit_Constants.LOCALDATE)
                .gmtModified(MinUnit_Constants.LOCALDATE)
                .build();

        studentInfoMapper.insert(studentInfo);
        //UCENTER MEMBER DB CHANGES

        Account account = new Account();
        account.setAccount(application.getPassport());
        if (application.getLevel() == 0)
            account.setRole("444444");
        else
            account.setRole("555555");
        account.setGmtModified(MinUnit_Constants.LOCALDATE);
        int update = accountMapper.update(account, new UpdateWrapper<Account>().eq("account", account.getAccount()));


        return R.ok().data(null).message("更新成功 ！");
    }

    @PostMapping("application/refuse/{id}")
    public R refuseApplication(@PathVariable("id") String id) {
        Application application = new Application();
        application.setRegNum(id);
        application.setStatus(3);
        application.setGmtModified(Date.valueOf(LocalDate.now()));
        int result = applicationMapper.updateById(application);

        if (result != 1)
            return R.error().data(null).message("更新操作失败 ！");
        return R.ok().data(null).message("更新成功 ！");
    }

    @DeleteMapping("application/logicDelete/{id}")
    public R ApplicationDelete(@PathVariable String id) {
        int b = accountMapper.deleteById(id);
        if (b == 1) {
            applicationMapper.deleteById(id);
            return R.ok().message("删除成功 ！");
        }
        return R.error().message("删除操作失败 ！");
    }

}
