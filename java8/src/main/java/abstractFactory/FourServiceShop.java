package abstractFactory;

import abstractFactory.car.Car;
import abstractFactory.truck.Truck;

/**
 * @ClassName FourServiceShop
 * @Author 刘正星
 * @Date 2020/7/26 下午1:00
 */
public class FourServiceShop {
    public static void main(String[] args) {

        VehicleFactoryProducer vehicleFactoryProducer = new VehicleFactoryProducer();
        VehicleFactory carFactory = vehicleFactoryProducer.buildFactory("car");
        Car fukang = carFactory.makeCar("fukang");
        Car elysee = carFactory.makeCar("elysee");
        VehicleFactory truckFactory = vehicleFactoryProducer.buildFactory("truck");
        Truck offRoad = truckFactory.makeTruck("off-road");
        Truck cargo = truckFactory.makeTruck("cargo");
        System.out.println(fukang.getBrand());
        System.out.println(elysee.getBrand());
        System.out.println(offRoad.getFunction());
        System.out.println(cargo.getFunction());


    }
}
