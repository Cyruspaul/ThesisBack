package com.cyrus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cyrus.config.R;
import com.cyrus.mapper.AccountMapper;
import com.cyrus.mapper.ApplicationMapper;
import com.cyrus.models.Account;
import com.cyrus.models.Application;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/school")
@RequiredArgsConstructor
public class AccountTaskController {

    private final AccountMapper accountMapper;
    private final ApplicationMapper applicationMapper;


    @ApiOperation(value = "所有列表")
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
    public R finD() {
        List<Account> list = accountMapper.selectList(null);
        return R.ok().data("items", list);
    }

    @PostMapping("task/search")
    public R getOne(@RequestBody Map<String, Object> data) {
        List<Account> list = new ArrayList<>();
        list = accountMapper.selectList(new QueryWrapper<Account>()
                .like("teacherName", data.get("teacherName") != null ? data.get("teacherName") : "")
                .eq("title", data.get("title"))
        );

        return R.ok().data("items", list);
    }

    @DeleteMapping("task/logicDelete/{id}")
    public R taskDelete(@PathVariable String id) {
        int b = accountMapper.deleteById(id);
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

    @PostMapping("application/accept/{id}")
    public R acceptApplication(@PathVariable("id") String id) {
        Application application = applicationMapper.selectById(id);
        if (application == null)
            return R.error().data(null).message("没有找到 ！");
        return R.ok().data("items", application);
    }

    @PostMapping("application/refuse/{id}")
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

    @DeleteMapping("application/logicDelete/{id}")
    public R ApplicationDelete(@PathVariable String id) {
        int b = accountMapper.deleteById(id);
        if (b == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}
