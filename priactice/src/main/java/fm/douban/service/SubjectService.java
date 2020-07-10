package fm.douban.service;

import fm.douban.model.Subject;

import java.util.List;

public interface SubjectService {
    //添加一个主题
    Subject addSubject(Subject subject);
    //查询单个主题
    Subject get(String subjectId);
    //查询一组主题
    List<Subject> getSubjects(String type);
    //查询一组主题
    List<Subject> getSubjects(String type, String subType);
    //删除一个主题
    boolean delete(String subjectId);

    List<Subject> getSubjects(Subject subjectParam);
}
