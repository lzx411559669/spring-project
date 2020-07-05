package com.lzx.service;


import com.lzx.model.Student;
import com.lzx.model.Vacation;

import java.util.List;

public interface VacationService {
    /**
     * 保存or更新
     * @param vacation
     * @return
     */
    Vacation save(Vacation vacation);

    /**
     * 根据学生查找假条
     * @param student
     * @return
     */
    List<Vacation> findByStudent(Student student);
    void deleteById(long vacationId);

    /**
     * 根据id查找
     * @param vacationId
     * @return
     */
    Vacation findById(long vacationId);

    /**
     * 返回所有假条实例
     * @return
     */
    List<Vacation> findAll();
    /**
     * 统计
     * @return
     */
    long count();
}
