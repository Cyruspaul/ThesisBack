package com.cyrus.models.Vo;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeachPlanDTO implements Serializable {

    private String majorNo;
    private String studyLevel;
    private List<JSONObject> posts;
}
