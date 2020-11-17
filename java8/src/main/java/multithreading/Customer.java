package multithreading;

/**
 * @ClassName Customer
 * @Author 刘正星
 * @Date 2020/7/24 下午5:40
 */
public class Customer implements Runnable{
  private Computer computer;

    public Customer(Computer computer) {
        this.computer = computer;
    }

    @Override
    public void run() {
     while (computer.getCount()>0){
         try {
             Thread.sleep(100);
             computer.sell();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

     }
    }
}
