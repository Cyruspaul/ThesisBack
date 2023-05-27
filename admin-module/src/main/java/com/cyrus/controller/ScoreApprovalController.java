package com.cyrus.controller;

import com.cyrus.config.R;
import com.cyrus.models.ScoreApproval;
import com.cyrus.service.ScoreApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/score")
@RequiredArgsConstructor
public class ScoreApprovalController {
    private final ScoreApprovalService scoreApprovalService;


    @RequestMapping("create")
    public R createScore(@RequestBody ScoreApproval scoreApproval) {
        return scoreApprovalService.createScore(scoreApproval);
    }

    @RequestMapping("getByClass/{classid}")
    public R getScore(@PathVariable("classid") String classid) {
        return scoreApprovalService.getByClass(classid);
    }

}
