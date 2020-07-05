package fm.douban.app.control;

import fm.douban.model.Subject;
import fm.douban.service.SubjectService;
import fm.douban.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test/subject")
public class SubjectTestControl {
    @Autowired
    private SubjectService subjectService;
    @RequestMapping("/add")
    public Subject testAdd(){
        Subject subject = new Subject();
        subject.setId("0");
        subject.setName("test");
        subject.setSubjectType(SubjectUtil.TYPE_MHZ);
        subject.setSubjectSubType(SubjectUtil.TYPE_SUB_AGE);
        return subjectService.addSubject(subject);
    }
    @RequestMapping("/get")
    public Subject testGet(){
        return subjectService.get("0");
    }
    @RequestMapping("/getByType")
    public List<Subject> testGetByType(){
       return subjectService.getSubjects(SubjectUtil.TYPE_MHZ);
    }
    @RequestMapping("/getBySubType")
    public List<Subject> testGetBySubType(){
        return subjectService.getSubjects(SubjectUtil.TYPE_MHZ,SubjectUtil.TYPE_SUB_AGE);
    }
    @RequestMapping("/del")
    public boolean testDelete(){
        return subjectService.delete("0");
    }
}
