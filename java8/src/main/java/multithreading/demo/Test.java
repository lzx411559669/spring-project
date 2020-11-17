package multithreading.demo;

import java.math.BigDecimal;

/**
 * @ClassName Test
 * @Author 刘正星
 * @Date 2020/8/6 下午10:16
 * @Description
 */
public class Test {
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10000;
    public static void main(String[] args) {
        System.out.println(getSum(1.0000));
        System.out.println(div(7,60));
    }
    public static double div(double v1, double v2)
    {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static double getSum(double n){
        if (n==1){
            return div(1,10);
        }
        return getSum(n-1)+(div(div(1,2*(n-1)-div(1,2*n)),5));
    }
}
