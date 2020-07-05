package com.lzx.repository;

import com.lzx.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Teacher findByTeacherIdAndPassword(String teacherId,String password);

    @Override
    <S extends Teacher> S save(S s);

    @Override
    List<Teacher> findAll();

    @Override
    void deleteById(Long aLong);
}
