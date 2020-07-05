package com.lzx.service.impl;

import com.lzx.model.Teacher;
import com.lzx.repository.TeacherRepository;
import com.lzx.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    private static final Logger LOG = LoggerFactory.getLogger(TeacherServiceImpl.class);
    @Override
    public Teacher validated(String teacherId, String password) {
        if (!StringUtils.hasText(teacherId) ||!StringUtils.hasText(password)){
            LOG.error("input teacherId or password is blank");
            return null;
        }
        return teacherRepository.findByTeacherIdAndPassword(teacherId,password);
    }

    @Override
    public Teacher save(Teacher teacher) {
        if (teacher==null){
            LOG.error("input teacher data is null");
            return null;
        }
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public void deleteById(long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public long count() {
        return teacherRepository.count();
    }
}
