package com.lzx.controller;

import com.lzx.model.*;
import com.lzx.repository.UserRepository;
import com.lzx.service.ClasssService;
import com.lzx.service.StudentService;
import com.lzx.service.TeacherService;
import com.lzx.service.VacationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @ClassName MainController
 * @Author 刘正星
 * @Date 2020/6/2 17:37
 **/
@Controller
public class MainController {
    //日志系统
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    private Student student = null;
    private Teacher teacher = null;
    private User user = null;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClasssService classsService;
    @Autowired
    private VacationService vacationService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserRepository userRepository;

    /**
     * 跳转主页
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    /**
     * 跳转登录页
     *
     * @return
     */
    @GetMapping("/login.html")
    public String loginHtml() {
        return "login";
    }

    /**
     * 跳转注册页
     *
     * @return
     */
    @GetMapping("/add.html")
    public String registerHtml() {
        return "add";
    }

    /**
     * 注册
     *
     * @param student
     * @param grade
     * @param name
     * @return
     */
    @PostMapping("/register")
    public String register(Student student, @RequestParam("grade") String grade, @RequestParam("classsname") String name) {
        student.setGmtCreated(LocalDateTime.now());
        student.setGetModified(LocalDateTime.now());
        Classs classs = classsService.findByNameAndGrade(name, grade);
        student.setClasss(classs);
        studentService.save(student);
        return "login";
    }

    /**
     * 请假页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/vacate")
    public String vacate(Model model) {
        model.addAttribute("user", student);
        return "vacate";
    }

    /**
     * 登录验证
     *
     * @param userId
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String loginStu(String userId, String password,String role) {
        if (role==null){
            student = studentService.validated(userId, password);
            if (student != null) {
                return "redirect:/vacate";
            }else {
                LOG.error("账号/密码不正确/或用户不存在");
                return "error";
            }
        }else if (role.equals("teacher")){
            teacher = teacherService.validated(userId,password);
            if (teacher!=null){
                return "redirect:/dealing";
            }else {
                LOG.error("账号/密码不正确/或用户不存在");
                return "error";
            }
        }else if (role.equals("admin")){
            user = userRepository.findByUsernameAndAndPassword(userId,password);
            if (user!=null){
                return "redirect:/findAllTeacher";
            }else {
                LOG.error("账号/密码不正确/或用户不存在");
                return "error";
            }
        }
        return "error";
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        return "login";
    }

    /**
     * 个人信息页
     *
     * @param model
     * @return
     */
    @RequestMapping("/userinfo")
    public String userInfo(Model model) {
        model.addAttribute("userinfo", studentService.findByStudentId(student.getStudentId()));
        return "userinfo";
    }
    @RequestMapping("/modify")
    public String modifyStuinfo(Student newStu){
        newStu.setId(student.getId());
        newStu.setGmtCreated(student.getGmtCreated());
        newStu.setGetModified(LocalDateTime.now());
        newStu.setClasss(student.getClasss());
        newStu.setStudentId(student.getStudentId());
        newStu.setVacationList(student.getVacationList());
        studentService.save(newStu);
        return "redirect:/userinfo";
    }

    /**
     * 请假
     * @param vacation
     * @return
     */
    @RequestMapping("/addVacation")
    public String addVacation(Vacation vacation) {
        vacation.setStudent(student);
        vacation.setGmtCreated(LocalDateTime.now());
        vacation.setGetModified(LocalDateTime.now());
        vacationService.save(vacation);
        return "redirect:/vacationlist";
    }
    /**
     * 假条记录
     */
    @RequestMapping("/vacationlist")
    public String vacationList(Model model){
        List<Vacation> vacations = new ArrayList<>();
        if (vacationService.findByStudent(student)!=null){
            vacations = vacationService.findByStudent(student);
            Date date  =  new Date();
            //是否预期
            vacations.forEach(vacation -> {
                if (vacation.getEndTime().before(date)){
                    vacation.setRemark(true);
                }
            });
            model.addAttribute("vacationlist",vacations);
        }
       return "vacationlist";
    }

    /**
     * 删除假条
     * @param vacationId
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public String delete(@RequestParam("vacationid") long vacationId){
        vacationService.deleteById(vacationId);
        return "true";
    }

    /**
     * 待处理假条
     * @param model
     * @return
     */
    @RequestMapping("/dealing")
    public String dealingVacaion(Model model){
        model.addAttribute("classlist",classsService.findByTeacher(teacher));
        return "pendingVacation";
    }

    /**
     * 班级列表
     * @param model
     * @return
     */
    @RequestMapping("/classlist")
    public String classList(Model model){
        model.addAttribute("classlist",classsService.findByTeacher(teacher));
        return "classlist";
    }

    /**
     * 批阅假条——同意
     * @param vacationId
     * @return
     */
    @RequestMapping("/approve")
    @ResponseBody
    public String approve(@RequestParam("vacationId") long vacationId){
       Vacation vacation = vacationService.findById(vacationId);
       vacation.setApproval(true);
       vacationService.save(vacation);
       return "true";
    }
    /**
     * 批阅假条——不同意
     * @param vacationId
     * @return
     */
    @RequestMapping("/notapprove")
    @ResponseBody
    public String notApprove(@RequestParam("vacationId") long vacationId){
        Vacation vacation = vacationService.findById(vacationId);
        vacation.setRemark(true);
        vacationService.save(vacation);
        return "true";
    }

    /**
     * 返回所有学生列表
     * @param model
     * @return
     */
    @RequestMapping("/findAllStudent")
    public String findAllStudent(Model model){
        model.addAttribute("studentlist",studentService.findAll());
        return "studentinfomge";
    }

    /**
     *所有老师页面
     * @param model
     * @return
     */
    @RequestMapping("/findAllTeacher")
    public String findAllTeacher(Model model){
        model.addAttribute("teacherlist",teacherService.findAll());
        return "teacherinfomge";
    }

    /**
     *所有班级页面
     * @param model
     * @return
     */
    @RequestMapping("/findAllClass")
    public String findAllClass(Model model){
        model.addAttribute("classlist",classsService.findAll());
        return "classsinfomge";
    }

    /**
     *添加老师
     * @param teacher
     * @return
     */
    @RequestMapping("addteacher")
    public String addTeacher(Teacher teacher){
        teacher.setGetModified(LocalDateTime.now());
        teacher.setGmtCreated(LocalDateTime.now());
        teacherService.save(teacher);
        return "redirect:/findAllTeacher";
    }

    /**
     * 添加班级
     * @param classs
     * @return
     */
    @RequestMapping("addClass")
    public String addClass(Classs classs){
        classs.setGmtCreated(LocalDateTime.now());
        classs.setGetModified(LocalDateTime.now());
        LocalDate now = LocalDate.now();
        long i = now.getDayOfMonth()<=6?now.getYear()-1:now.getYear();
        for (long j=i;j>i-4;j--){
            classs.setGrade(String.valueOf(j));
            classsService.save(classs);
        }
        return "redirect:/findAllClass";
    }

    /**
     * 信息统计
     * @return
     */
    @GetMapping("/getinfo")
    @ResponseBody
    public List<Long> getinfo(){
        List<Long> listinfo = new ArrayList<>();
        listinfo.add(vacationService.count());
        listinfo.add(studentService.count());
        listinfo.add(classsService.count());
        listinfo.add(teacherService.count());
        return listinfo;
    }

    /**
     * 站点信息统计页面
     * @return
     */
  @RequestMapping("/siteinfo")
  public String siteInfo(){
        return "siteinfo";
  }


    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

}
