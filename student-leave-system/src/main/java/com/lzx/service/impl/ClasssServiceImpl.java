package com.lzx.service.impl;

import com.lzx.model.Classs;
import com.lzx.model.Teacher;
import com.lzx.repository.ClasssRepository;
import com.lzx.service.ClasssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClasssServiceImpl implements ClasssService {
    @Autowired
  private  ClasssRepository classsRepository;
    private static final Logger LOG = LoggerFactory.getLogger(ClasssServiceImpl.class);

    @Override
    public Classs save(Classs classs) {
        //验证classs是否为空
        if (classs == null){
            //错误日志输出错误信息
            LOG.error("input classsData is null");
            return null;
        }
        return classsRepository.save(classs);
    }
    public Classs findByNameAndGrade(String name,String grade){
        if (!StringUtils.hasText(grade)||!StringUtils.hasText(name)){
            LOG.error("input grade or name is blank");
            return null;
        }
        return classsRepository.findByNameAndGrade(name,grade);
    }

    @Override
    public List<Classs> findByTeacher(Teacher teacher) {
        if (teacher == null){
            LOG.error("input teacher is null !");
            return null;
        }
        return classsRepository.findByTeacher(teacher);
    }

    @Override
    public List<Classs> findAll() {
        return classsRepository.findAll();
    }

    @Override
    public long count() {
        return classsRepository.count();
    }

}
