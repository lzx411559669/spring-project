package com.lzx.service.impl;

import com.lzx.model.Student;
import com.lzx.repository.StudentRepository;
import com.lzx.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Autowired
    StudentRepository studentRepository;
    @Override
    public Student save(Student student) {
        if (student==null){
            LOG.error("input student data is null!");
            return null;
        }
            return studentRepository.save(student);
    }

    @Override
    public Student validated(String studentId, String password) {
        if (!StringUtils.hasText(studentId)||!StringUtils.hasText(password)){
            LOG.error("input studentId or password is null!");
            return null;
        }
        return studentRepository.findByStudentIdAndPassword(studentId,password);
    }

    @Override
    public Student findByStudentId(String studentId) {
        if (!StringUtils.hasText(studentId)){
            LOG.error("input studentId is blank!");
            return null;
        }
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public long count() {
        return studentRepository.count();
    }
}
