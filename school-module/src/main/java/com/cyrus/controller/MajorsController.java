package com.cyrus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyrus.config.PageUtils;
import com.cyrus.config.R;
import com.cyrus.mapper.CollegeInfoMapper;
import com.cyrus.models.CollegeInfo;
import com.cyrus.models.Majors;
import com.cyrus.models.Vo.MajorQueryVo;
import com.cyrus.service.MajorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api("专业管理")
@RestController
@RequestMapping("/api/school/majors")
@RequiredArgsConstructor
public class MajorsController {
    private final CollegeInfoMapper collegeInfoMapper;
    @Autowired
    private MajorsService majorsService;

    @ApiOperation(value = "所有专业列表")
    @GetMapping("findAll")
    public R findAll() {
        List<Majors> list = majorsService.list(null);

        list.forEach(majors -> {
            if (!majors.getCollegeId().isEmpty()) {
                CollegeInfo collegeInfo = collegeInfoMapper.selectOne(new QueryWrapper<CollegeInfo>()
                        .eq("collegeNo", majors.getCollegeId())
                        .select("collegeName"));
                majors.setCollegeName(collegeInfo.getCollegeName());
            }
        });

        return R.ok().data("items", list);

    }

    //删除(利用swagger测试)
    @ApiOperation(value = "逻辑删除专业")
    @DeleteMapping("delete/{id}")
    public R logicDeleteTest(@PathVariable String id) {
        boolean b = majorsService.removeById(Long.valueOf(id));
        if (b) {
            return R.ok().message("删除成功");
        } else {
            return R.ok().message("删除失败");
        }
    }

    //分页查询
    @ApiOperation("分页查询")
    @PostMapping("pageMajor")
    public R pageTest(@RequestBody MajorQueryVo queryVo) {
        if (PageUtils.checkCurrentAndSize(queryVo)) {
            Page<Majors> page = new Page<Majors>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));
            majorsService.page(page, null);
            //返回结果封装在page中 ，
            long total = page.getTotal();
            List<Majors> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }
    }

    //条件查询带分页
    @ApiOperation("条件查询带分页")
    @PostMapping("pageMajorCondition")
    public R pageMajorCondition(@RequestBody MajorQueryVo queryVo) {
        if (PageUtils.checkCurrentAndSize(queryVo)) {
            //创建page
            Page<Majors> page = new Page<Majors>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));

            //条件构造器
            QueryWrapper<Majors> queryWrapper = new QueryWrapper();

            //动态sql拼接(可以写utIl处理)
            String majorName = queryVo.getMajorName();
            String collegeId = queryVo.getCollegeId();
            if (!StringUtils.isEmpty(majorName)) {
                queryWrapper.ge("college_name", majorName);
            }
            if (!StringUtils.isEmpty(collegeId)) {
                queryWrapper.le("college_id", collegeId);
            }
            //返回结果封装在page中
            majorsService.page(page, queryWrapper);
            long total = page.getTotal();
            List<Majors> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }
    }

    //查询专业
    @PostMapping("getById/{id}")
    @ApiOperation("根据id查询专业")
    public R getMajorById(@PathVariable String id) {
        Majors byId = majorsService.getById(id);
        if (byId != null) {
            return R.ok().data("college", byId);
        }
        return R.ok().message("无");

    }

    //添加专业的方法
    @PostMapping("addMajor")
    @ApiOperation("添加专业")
    public R addMajor(@RequestBody Majors majors) {
        boolean save = majorsService.save(majors);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改专业
    @PostMapping("updateMajor")
    @ApiOperation("更新专业")
    public R updateMajor(@RequestBody Majors majors) {
        QueryWrapper<Majors> majorsQueryWrapper = new QueryWrapper<>();
        majorsQueryWrapper.eq("id", majors.getId());
        boolean update = majorsService.update(majors, majorsQueryWrapper);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //无条件查询专业
    @GetMapping("getMajorList")
    public R getMajorList() {
        List<Majors> list = majorsService.list(null);
        return R.ok().data("items", list);
    }
}

