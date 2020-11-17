package abstractFactory.car;

import abstractFactory.VehicleFactory;
import abstractFactory.truck.Truck;

/**
 * @ClassName CarFactory
 * @Author 刘正星
 * @Date 2020/7/26 下午1:05
 */
public class CarFactory implements VehicleFactory {
    Car car = null;
    @Override
    public Car makeCar(String brand) {
        if (brand.equals("fukang")){
            FuKang fuKang = new FuKang();
            fuKang.setBrand("富康");
            car = fuKang;
        }
        if (brand.equals("elysee")){
            Elysee elysee = new Elysee();
            elysee.setBrand("爱丽舍");
            car = elysee;
        }
        return car;
    }

    @Override
    public Truck makeTruck(String brand) {
        return null;
    }
}
