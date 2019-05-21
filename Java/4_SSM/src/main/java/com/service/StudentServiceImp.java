package com.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.entity.Score;
import com.entity.Student;
import com.mapper.ScoreRepository;
import com.mapper.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.HashMapChangeSet;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Kris Peng on 10:30 2019/5/2 .
 * All right reserved.
 */

@Service

public class StudentServiceImp implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    public String saveStudent(Student student) {
        studentRepository.save(student);
        System.out.println(student.getName() + " Saved.");
        return "Saved";
    }

    public String deleteStudent(Integer stuId) {
        studentRepository.deleteById(stuId);
        System.out.println(stuId + " Deleted.");
        return "Deleted";
    }
}
