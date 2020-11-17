package multithreading.seat;

/**
 * @ClassName ReadingRoom
 * @Author 刘正星
 * @Date 2020/7/26 下午6:32
 * 教室
 */
import java.util.concurrent.atomic.AtomicBoolean;

public class ReadingRoom {
    private static AtomicBoolean[] PLACES = {new AtomicBoolean(false), new AtomicBoolean(false), new AtomicBoolean(
            false), new AtomicBoolean(false), new AtomicBoolean(false)};


    // 坐下。返回 成功/失败，败表示没有空位
    public boolean sit(Student student) {
        boolean result = false;
        for (int i = 0;i<PLACES.length;i++){
            if (PLACES[i].compareAndSet(false,true)){
                student.setPlaceIndex(i);
                result = true;
                break;
            }
        }
        return result;
    }

    // 离开
    public void leave(Student student) {

        if (student.getPlaceIndex()<0 || student.getPlaceIndex()>4){
            return;

        }
        PLACES[student.getPlaceIndex()].compareAndSet(true,false) ;
        student.setPlaceIndex(-1);

    }
}
