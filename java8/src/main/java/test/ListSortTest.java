package test;

import model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName ListSortTest
 * @Author 刘正星
 * @Date 2020/7/21 下午5:04
 */
public class ListSortTest {
    public static void main(String[] args) {
       // Student [] students = new Student[]{new Student(111,"张三","london",20),new Student(131,"李四","nyc",18),new Student(121,"王五","jaipur",19)};
       // List<Student> studentList = Arrays.asList(students);
        //studentList.forEach(s->System.out.println(s));
        List<Student> studentList = new ArrayList();
        studentList.add(new Student(111,"孟子","London",20));
        studentList.add(new Student(131,"陈寿","NYV",18));
        studentList.add(new Student(121,"王维","Jaipur",19));
        studentList.add(new Student(171,"亚里士多德","Greek",24));
        studentList.add(new Student(141,"达芬奇","Italy",22));
        
        String message;
        message = "hellol";
        Collections.sort(studentList, (s1,s2)->{
            System.out.println(message+" : "+s1.getName());
            return s2.getAge()-s1.getAge();
        });
        studentList.forEach(s->System.out.println(s));



    }
}
