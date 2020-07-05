package com.lzx.service;


import com.lzx.model.Teacher;

import java.util.List;

public interface TeacherService {
    /**
     * 登录验证
     * @param teacherId
     * @param password
     * @return
     */
    Teacher validated(String teacherId, String password);

    /**
     * 插入或更新
     * @param teacher
     * @return
     */
    Teacher save(Teacher teacher);

    /**
     * 返回所有老师
     * @return
     */
    List<Teacher> findAll();

    /**
     * 根据id删除
     * @param teacherId
     */
    void deleteById(long teacherId);
    /**
     * 统计
     * @return
     */
    long count();

}
