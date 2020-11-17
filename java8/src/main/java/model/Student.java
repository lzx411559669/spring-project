package model;

import lombok.Data;

/**
 * @ClassName Student
 * @Author 刘正星
 * @Date 2020/7/21 下午5:01
 */
@Data
public class Student {
    private int id;
    private String name;
    private String adress;
    private int age;

    public Student(int id, String name, String adress, int age) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
