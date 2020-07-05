package com.lzx.service;


import com.lzx.model.Classs;
import com.lzx.model.Teacher;

import java.util.List;

public interface ClasssService {
    /**
     * 保存或更新
     * @param classs
     * @return
     */
    Classs save(Classs classs);

    /**
     * 根据年级跟专业
     * @param name
     * @param grade
     * @return
     */
    Classs findByNameAndGrade(String name,String grade);

    /**
     * 根据老师查找
     * @param teacher
     * @return
     */
    List<Classs> findByTeacher(Teacher teacher);

    /**
     * 获取所有classs
     * @return
     */
    List<Classs> findAll();

    /**
     * 统计
     * @return
     */
    long count();
}
