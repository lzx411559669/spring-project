package abstractFactory;

import abstractFactory.car.CarFactory;
import abstractFactory.truck.OffRoadTruck;
import abstractFactory.truck.Truck;
import abstractFactory.truck.TruckFactory;

/**
 * @ClassName VehicleFactoryProducer
 * @Author 刘正星
 * @Date 2020/7/26 下午1:01
 */
public class VehicleFactoryProducer {

  public   VehicleFactory buildFactory(String type){
        VehicleFactory vehicleFactory = null;
        if (type.equals("car")){
            CarFactory carFactory = new CarFactory();
            vehicleFactory = carFactory;
        }
        if (type.equals("truck")){
            TruckFactory truckFactory = new TruckFactory();
            vehicleFactory = truckFactory;
        }
        return vehicleFactory;
    }

}
