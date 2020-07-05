package com.lzx.service.impl;

import com.lzx.model.Student;
import com.lzx.model.Vacation;
import com.lzx.repository.VacationRepository;
import com.lzx.service.VacationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationServiceImpl implements VacationService {
    @Autowired
    private VacationRepository vacationRepository;
    private static final Logger LOG = LoggerFactory.getLogger(VacationServiceImpl.class);
    public Vacation save(Vacation vacation){
        if (vacation == null){
            LOG.error("input vacation data is null");
            return null;
        }
        return vacationRepository.save(vacation);
    }

    @Override
    public List<Vacation> findByStudent(Student student) {
        if (student == null){
            LOG.error("input student is null");
            return null;
        }
        return vacationRepository.findByStudent(student);
    }

    @Override
    public void deleteById(long vacationId) {
         vacationRepository.deleteById(vacationId);
    }

    @Override
    public Vacation findById(long vacationId) {
        return vacationRepository.findById(vacationId);
    }
    public List<Vacation> findAll(){
        return vacationRepository.findAll();
    }

    @Override
    public long count() {
        return vacationRepository.count();
    }

}
