package com.cyrus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyrus.config.PageUtils;
import com.cyrus.config.R;
import com.cyrus.mapper.MajorsMapper;
import com.cyrus.models.CollegeInfo;
import com.cyrus.models.Majors;
import com.cyrus.models.Vo.CollegeQueryVo;
import com.cyrus.service.CollegeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-02
 */
@Api("学院管理")
@RestController
@RequestMapping("/api/school/college")
@RequiredArgsConstructor
public class CollegeInfoController {

    private final MajorsMapper majorsMapper;
    @Autowired
    private CollegeInfoService collegeInfoService;

    @ApiOperation(value = "所有学院列表")
    @GetMapping("findAll")
    public R findAll() {
        List<CollegeInfo> list = collegeInfoService.list(null);

        list.forEach(collegeInfo -> {
            List<Majors> majorsList = new ArrayList<>();

            majorsList = majorsMapper.selectList(new QueryWrapper<Majors>()
                    .eq("college_id", collegeInfo.getCollegeNo())
                    .select("id", "major_name")
            );
            collegeInfo.setMajorsList(majorsList);
        });
        return R.ok().data("items", list);

    }

    //删除(利用swagger测试)
    @ApiOperation(value = "逻辑删除学院")
    @DeleteMapping("delete/{id}")
    public R logicDeleteTest(@PathVariable String id) {
        boolean b = collegeInfoService.removeById(Long.valueOf(id));
        if (b) {
            return R.ok().message("删除成功");
        } else {
            return R.ok().message("删除失败");
        }
    }

    //分页查询
    @ApiOperation("分页查询")
    @PostMapping("pageCollege")
    public R pageTest(@RequestBody CollegeQueryVo queryVo) {
        if (PageUtils.checkCurrentAndSize(queryVo)) {
            Page<CollegeInfo> page = new Page<CollegeInfo>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));
            collegeInfoService.page(page, null);
            //返回结果封装在page中 ，
            long total = page.getTotal();
            List<CollegeInfo> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }
    }

    //条件查询带分页
    @ApiOperation("条件查询带分页")
    @PostMapping("pageCollegeCondition")
    public R pageCollegeCondition(@RequestBody CollegeQueryVo queryVo) {
        if (PageUtils.checkCurrentAndSize(queryVo)) {
            //创建page
            Page<CollegeInfo> page = new Page<CollegeInfo>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));

            //条件构造器
            QueryWrapper<CollegeInfo> queryWrapper = new QueryWrapper();

            //动态sql拼接(可以写utIl处理)
            String collegeName = queryVo.getCollegeName();
            String collegeNo = queryVo.getCollegeNo();
            if (!StringUtils.isEmpty(collegeName)) {
                queryWrapper.ge("collegeName", collegeName);
            }
            if (!StringUtils.isEmpty(collegeNo)) {
                queryWrapper.le("collegeNo", collegeNo);
            }
            //返回结果封装在page中
            collegeInfoService.page(page, queryWrapper);
            long total = page.getTotal();
            List<CollegeInfo> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }
    }

    //查询学院
    @RequestMapping("getById/{id}")
    @ApiOperation("根据id查询学院")
    public R getCollegeById(@PathVariable String id) {
        CollegeInfo byId = collegeInfoService.getById(id);
        if (byId != null) {
            return R.ok().data("college", byId);
        }
        return R.ok().message("无");

    }

    @PostMapping("search")
    public R search(@RequestBody Map<String, Object> data) {
        List<CollegeInfo> list = new ArrayList<>();
        list = collegeInfoService.list(new QueryWrapper<CollegeInfo>().like("collegeName", data.get("collegeName")));
        list.forEach(collegeInfo -> {
            List<Majors> majorsList = new ArrayList<>();

            majorsList = majorsMapper.selectList(new QueryWrapper<Majors>()
                    .eq("college_id", collegeInfo.getCollegeNo())
                    .select("id", "major_name")
            );
            collegeInfo.setMajorsList(majorsList);
        });
        return R.ok().data("items", list);
    }

    //添加学院的方法
    @PostMapping("addCollege")
    @ApiOperation("添加学院")
    public R addCollege(@RequestBody CollegeInfo collegeInfo) {
        boolean save = collegeInfoService.save(collegeInfo);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改学院
    @PostMapping("updateCollege")
    @ApiOperation("更新学院")
    public R updateCollege(@RequestBody CollegeInfo collegeInfo) {
        QueryWrapper<CollegeInfo> collegeInfoQueryWrapper = new QueryWrapper<>();
        collegeInfoQueryWrapper.eq("id", collegeInfo.getId());
        boolean update = collegeInfoService.update(collegeInfo, collegeInfoQueryWrapper);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //无条件查询学院
    @GetMapping("getCollegeList")
    public R getCollegeList() {
        List<CollegeInfo> list = collegeInfoService.list(null);
        return R.ok().data("items", list);
    }
}

