package com.controller; /**
 * Created by Kris Peng on 09:10 2019/5/2 .
 * All right reserved.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entity.Score;
import com.entity.Student;
import com.service.ScoreService;
import com.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/api/v1")
@ResponseBody

public class MainController {

    @Autowired
    StudentService studentService;

    @Autowired
    ScoreService scoreService;

    @PutMapping(path="student/add")
    public String addStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    @DeleteMapping(path="student/delete/{stuId}")
    public String deleteStudent(@PathVariable("stuId") Integer stuId){
        return studentService.deleteStudent(stuId);
    }

    @PutMapping(path="student/score/{stuId}")
    public String addScore(@PathVariable("stuId") Integer stuId,
                           @RequestBody JSONObject score_json){
        return scoreService.saveScore(score_json, stuId);
    }

    @GetMapping(path="avgscore/{gradeId}")
    public HashMap<String,Integer> getGrade(@PathVariable("gradeId") Integer gradeId){
        return scoreService.getGrade(gradeId);
    }

}