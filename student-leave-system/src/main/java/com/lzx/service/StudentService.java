package com.lzx.service;


import com.lzx.model.Student;

import java.util.List;

public interface StudentService {
    /**
     * 插入或者更新
     * @param student
     * @return
     */
    Student save(Student student);

    /**
     * 登录验证
     * @param studentId
     * @param password
     * @return
     */
    Student validated(String studentId,String password);
    /**
     * 根据学号查找
     */
    Student findByStudentId(String studentId);
    /**
     * 获取所有学生
     */
    List<Student> findAll();
    /**
     * 统计
     * @return
     */
    long count();
}
