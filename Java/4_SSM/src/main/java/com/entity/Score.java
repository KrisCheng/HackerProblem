package com.entity;

import javax.persistence.*;

/**
 * Created by Kris Peng on 15:15 2019/5/2 .
 * All right reserved.
 */
@Entity
public class Score {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "student_id")
    private Integer student_id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "value")
    private Integer value;

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
