package com.service;

import com.alibaba.fastjson.JSONObject;
import com.entity.Score;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kris Peng on 00:01 2019/5/4 .
 * All right reserved.
 */
public interface ScoreService {

    String saveScore(JSONObject score_json, Integer stuId);
    HashMap<String,Integer> getGrade(Integer gradeId);
}
