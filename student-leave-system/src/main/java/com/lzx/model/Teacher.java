package com.lzx.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @ClassName Teacher
 * @Author 刘正星
 * @Date 2020/6/2 20:00
 **/
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //班主任id，作为登陆账号
    private String teacherId;
    //密码
    private String password;
    //姓名
    private String name;
    //电话
    private String mobile;
    //创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreated;
    //修改时间
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime getModified;
    //所辅导班级
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id",referencedColumnName = "id")
    private List<Classs> classsList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGetModified() {
        return getModified;
    }

    public void setGetModified(LocalDateTime getModified) {
        this.getModified = getModified;
    }

    public List<Classs> getClasssList() {
        return classsList;
    }

    public void setClasssList(List<Classs> classsList) {
        this.classsList = classsList;
    }
}
