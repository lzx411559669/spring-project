package abstractFactory;

import abstractFactory.car.Car;
import abstractFactory.truck.Truck;

/**
 * @ClassName vihicleFactory
 * @Author 刘正星
 * @Date 2020/7/26 下午1:01
 */
public interface VehicleFactory {

    Car makeCar(String brand);
    Truck makeTruck(String brand);
}
