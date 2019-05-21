package com.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kris Peng on 15:18 2019/5/2 .
 * All right reserved.
 */

@Repository

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    @Query(value = "select sc.* from \n" +
            "score as sc, student as st where \n" +
            "st.grade = :gradeId and sc.student_id = st.id\n",nativeQuery = true)
    List<Score> getGrade(@Param("gradeId") Integer gradeId);

}
