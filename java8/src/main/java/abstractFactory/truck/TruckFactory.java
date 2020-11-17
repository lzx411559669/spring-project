package abstractFactory.truck;

import abstractFactory.VehicleFactory;
import abstractFactory.car.Car;

/**
 * @ClassName TruckFactory
 * @Author 刘正星
 * @Date 2020/7/26 下午1:06
 */
public class TruckFactory implements VehicleFactory {

    @Override
    public Car makeCar(String brand) {
        return null;
    }

    @Override
    public Truck makeTruck(String brand) {
        Truck truck = null;
        if (brand.equals("cargo")){
            CargoTruck cargoTruck = new CargoTruck();
            cargoTruck.setFunction("载货卡车");
            truck = cargoTruck;
        }
        if (brand.equals("off-road")){
            OffRoadTruck offRoadTruck= new OffRoadTruck();
            offRoadTruck.setFunction("越野卡车");
            truck = offRoadTruck;
        }
        return truck;
    }
}
