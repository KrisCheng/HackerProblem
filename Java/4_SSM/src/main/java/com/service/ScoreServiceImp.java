package com.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entity.Score;
import com.mapper.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Kris Peng on 00:02 2019/5/4 .
 * All right reserved.
 */

@Service

public class ScoreServiceImp implements ScoreService {

    @Autowired
    ScoreRepository scoreRepository;

    public String saveScore(JSONObject score_json, Integer stuId) {
        JSONArray json = score_json.getJSONArray("score");
        Iterator<Object> iterator = json.iterator();
        while(iterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            for (String key : jsonObject.keySet()) {
                Score score = new Score();
                score.setStudent_id(stuId);
                score.setSubject(key);
                score.setValue(Integer.parseInt(jsonObject.get(key).toString()));
                scoreRepository.save(score);
            }
        }
        return "Score Added.";
    }

    public HashMap<String,Integer> getGrade(Integer gradeId){
        List<Score> all_score = scoreRepository.getGrade(gradeId);
        HashMap<String, Integer> result = new HashMap<>();
        HashMap<String, Integer> count = new HashMap<>();
        for(Score item : all_score){
            if(result.containsKey(item.getSubject())){
                int avg = (count.get(item.getSubject()) * result.get(item.getSubject()) + item.getValue()) /(count.get(item.getSubject()) + 1);
                count.put(item.getSubject(), count.get(item.getSubject()) + 1);
                result.put(item.getSubject(), avg);
            }
            else{
                result.put(item.getSubject(), item.getValue());
                count.put(item.getSubject(), 1);
            }
        }
        System.out.println(result);
        return result;
    }
}
