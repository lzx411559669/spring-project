package com.lzx.repository;

import com.lzx.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByStudentId(String studentId);

    Student findByStudentIdAndPassword(String studentId,String password);

    @Override
    <S extends Student> S save(S s);

    @Override
    List<Student> findAll();

    @Override
    void deleteById(Long aLong);
}
