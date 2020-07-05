package com.lzx.repository;

import com.lzx.model.Classs;
import com.lzx.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasssRepository extends JpaRepository<Classs,Long> {
    @Override
    <S extends Classs> S save(S s);
   // @Query(nativeQuery = true,value = "select * from classs where name=:name and grade=:grade")
   Classs findByNameAndGrade(@Param("name") String name,@Param("grade") String grade);
   List<Classs> findByTeacher(Teacher teacher);

    @Override
    List<Classs> findAll();

    @Override
    void deleteById(Long aLong);

    @Override
    long count();
}
