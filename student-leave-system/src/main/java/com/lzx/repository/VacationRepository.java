package com.lzx.repository;

import com.lzx.model.Student;
import com.lzx.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation,Long> {
    @Override
    <S extends Vacation> S save(S s);
    List<Vacation> findByStudent(Student student);
    Vacation findById(long vacationId);
}
