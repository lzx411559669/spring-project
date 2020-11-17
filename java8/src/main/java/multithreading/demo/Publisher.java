package multithreading.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName Publisher
 * @Author 刘正星
 * @Date 2020/7/25 上午10:23
 */

/*public class Publisher implements Runnable{
    // 使用类变量，保证学号都是从同一个递增序列获取
   public static AtomicInteger count = new AtomicInteger(1000);
   private Offer offer;

    public Publisher(Offer offer) {
        this.offer = offer;
    }

    @Override
    public void run() {
        offer.setId(count.incrementAndGet());
        System.out.println("商品标题："+offer.getTitle()+"，商品ID："+offer.getId());
    }
}*/
//异步重构
public class Publisher {

    private static AtomicInteger count = new AtomicInteger(1000);

    public Offer save(Offer offer) {

        offer.setId(count.incrementAndGet());
        return offer;

    }

    public Offer examine(Offer offer) {
        System.out.println("商品审核成功。标题：" + offer.getTitle() + "，ID：" + offer.getId());
        return offer;
    }

    public void publish(Offer offer) {
        System.out.println("商品发布成功。标题：" + offer.getTitle() + "，商品ID：" + offer.getId());
    }
}
