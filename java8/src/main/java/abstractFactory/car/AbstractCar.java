package abstractFactory.car;

/**
 * @ClassName AbstractCar
 * @Author 刘正星
 * @Date 2020/7/26 下午1:04
 */
public abstract class  AbstractCar implements Car{
   private String brand;
   public String  getBrand(){
       return this.brand;
   }

   public void setBrand(String brand){
       this.brand = brand;
   }

}
