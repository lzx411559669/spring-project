package multithreading;

import java.util.Random;

/**
 * @ClassName TakeNumber
 * @Author 刘正星
 * @Date 2020/7/24 下午5:00
 */
public class TakeNumber implements Runnable{
    private String name;
    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }



    @Override
    public void run() {
        Random random = new Random();
        int r = random.nextInt(11);
        System.out.println(getName()+" 拿到的排队号码是："+r);
    }
}
