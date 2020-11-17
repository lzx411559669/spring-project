package abstractFactory.truck;

/**
 * @ClassName AbstractTruck
 * @Author 刘正星
 * @Date 2020/7/26 下午1:05
 */
public abstract class AbstractTruck implements Truck{

    private String function;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function){
        this.function = function;
    }
}
