package com.mapper;

import com.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kris Peng on 10:31 2019/5/2 .
 * All right reserved.
 */

public interface StudentRepository extends JpaRepository<Student, Integer> {

}