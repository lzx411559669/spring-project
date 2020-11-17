package multithreading;

/**
 * @ClassName Computer
 * @Author 刘正星
 * @Date 2020/7/24 下午5:40
 */
public class Computer {

    private int count;

    private static Computer instance = new Computer();
    private Computer() {
      this.count = 30;
    }

    public static Computer getInstance(){
        return instance;
    }
    public synchronized void sell(){
        if (this.count>0){
            count--;
            System.out.println(Thread.currentThread().getName()+" 买走一台，剩余库存 "+count+" 台");
        }else {
            System.out.println(Thread.currentThread().getName()+" 秒杀失败，剩余库存 "+count+" 台");
        }


    }

    public int getCount(){
        return count;
    }
}
