package multithreading.demo;

/**
 * @ClassName OfferSet
 * @Author 刘正星
 * @Date 2020/7/25 上午10:25
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class OfferTest {
    public static void main(String[] args) {
        List<Offer> offers = getOffers();


       /* offers.forEach(offer -> {

            Publisher publisher = new Publisher(offer);
            Thread thread = new Thread(publisher);
            thread.start();

        });*/
        //异步重构
        Publisher publisher = new Publisher();

        List<CompletableFuture> cfs = new ArrayList<>();
        offers.forEach(offer -> {
            CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> publisher.save(offer))
                    .thenApply((offer2) -> publisher.examine(offer2))
                    .thenAccept(offer1 -> publisher.publish(offer1));
            cfs.add(cf);
        });

        try {
            //等待所有线程执行完毕，allof（）只支持数组，不支持集合
            CompletableFuture.allOf(cfs.toArray(new CompletableFuture[]{})).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    // 获取一批商品数据
  /*  private static List<Offer> getOffers() {
        List<Offer> offers = new ArrayList<>();

        Offer offer1 = new Offer();
        offer1.setTitle("Nike 黑白宇航员泡椒3代战靴");
        offer1.setImages(Arrays.asList(
                "https://gw.alicdn.com/tfscom/https://img.alicdn.com/imgextra/i4/2689779491/O1CN01eCUUdR2JyvbHi2lKz_!!2689779491-0-daren.jpg"));
        offers.add(offer1);

        Offer offer2 = new Offer();
        offer2.setTitle("Karrimor 重型户外载重包架");
        offer2.setImages(Arrays.asList(
                "https://gw.alicdn.com/tfscom/https://img.alicdn.com/imgextra/i1/2925577988/O1CN0128sYF7Clch08w3v_!!2925577988-0-daren.jpg"));
        offers.add(offer2);

        Offer offer3 = new Offer();
        offer3.setTitle("Nike Air Max彩色气垫跑鞋");
        offer3.setImages(Arrays.asList(
                "https://gw.alicdn.com/tfscom/https://img.alicdn.com/imgextra/i4/2913435316/O1CN01sLCKT81p8m0ALR3oq_!!2913435316-0-daren.jpg"));
        offers.add(offer3);

        Offer offer4 = new Offer();
        offer4.setTitle("kappa 复古串标夹克外套");
        offer4.setImages(Arrays.asList(
                "https://gw.alicdn.com/tfscom/img.alicdn.com/imgextra/i1/675114980/O1CN011messEKH0dCuDYK_!!675114980-0-daren.jpg"));
        offers.add(offer4);

        return offers;
    }*/

    // 获取一批商品数据
    private static List<Offer> getOffers() {
        List<Offer> offers = new ArrayList<>();

        for (int i = 1; i <= 3000; i++) {
            Offer offer = new Offer();
            offer.setTitle("Nike 黑白宇航员泡椒3代战靴" + i);

            offer.setImages(Arrays.asList(
                    "https://gw.alicdn.com/tfscom/https://img.alicdn.com/imgextra/i4/2689779491/O1CN01eCUUdR2JyvbHi2lKz_!!2689779491-0-daren.jpg"));

            offers.add(offer);
        }

        return offers;
    }


}

