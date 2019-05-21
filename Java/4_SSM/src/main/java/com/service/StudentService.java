package com.service;

import com.alibaba.fastjson.JSONObject;
import com.entity.Student;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Kris Peng on 09:21 2019/5/2 .
 * All right reserved.
 */

public interface StudentService {

    String saveStudent(Student student);
    String deleteStudent(Integer stuId);

}
