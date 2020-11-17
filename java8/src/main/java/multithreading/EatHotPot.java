package multithreading;

/**
 * @ClassName EatHotPot
 * @Author 刘正星
 * @Date 2020/7/24 下午4:59
 */
public class EatHotPot {
    public static void main(String[] args) {
    TakeNumber takeNumber = new TakeNumber();
    TakeNumber takeNumber1 = new TakeNumber();
    takeNumber.setName("林妙妙");
    takeNumber1.setName("钱三一");
    Thread thread = new Thread(takeNumber);
    Thread thread2 = new Thread(takeNumber1);
    thread.start();
    thread2.start();

    }
}
