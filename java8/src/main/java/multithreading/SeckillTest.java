package multithreading;

/**
 * @ClassName SeckillTest
 * @Author 刘正星
 * @Date 2020/7/24 下午5:41
 */
public class SeckillTest {
    public static void main(String[] args) {
    Computer computer = Computer.getInstance();
    for (int i=0;i<200;i++){
        Customer customer = new Customer(computer);
        Thread thread = new Thread(customer);
        thread.setName("顾客"+i);
        thread.start();
    }
    }
}
