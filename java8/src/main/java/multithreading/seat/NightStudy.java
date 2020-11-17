package multithreading.seat;

/**
 * @ClassName NightStudy
 * @Author 刘正星
 * @Date 2020/7/26 下午6:37
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 晚自习
 */
public class NightStudy {
    private static ReadingRoom room = new ReadingRoom();
    private static Random r = new Random();

    public static void main(String[] args) {
       List<Student> students = initStudents();
       List<CompletableFuture> cfs = new ArrayList<>();


       students.forEach(student -> {
           CompletableFuture<Void> cf = CompletableFuture.supplyAsync(()->studyBegin(student)).thenApply(student1 -> {
               return study(student1);
           }).thenAccept(student1 ->studyComplete(student1));
           cfs.add(cf);

       });
        try {
            CompletableFuture.allOf(cfs.toArray(new CompletableFuture[]{})).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
     /*   room.sit(s);

        if (s.getPlaceIndex() < 0) {
            throw new RuntimeException("抢座位后，座位号应该大于等于 0");
        }

        room.leave(s);

        if (s.getPlaceIndex() != -1) {
            throw new RuntimeException("离开座位后，座位号应该等于 -1");
        }*/
    }

    private static Student studyBegin(Student s) {
           boolean result = false;
        while (!result) {
            result = room.sit(s);
            if (result) {
                System.out.println(s.getName()+"抢到座位"+s.getPlaceIndex());
                break;
            }else {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }
    private static Student study(Student s) {
       int time = r.nextInt(201)+200;
       System.out.println(s.getName()+"学习了"+time+"ms");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static void studyComplete(Student s) {

          System.out.println(s.getName()+"学习结束离开座位，座位号："+s.getPlaceIndex()+"号");
        room.leave(s);
    }

    private static List<Student> initStudents() {
        List<Student> students = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            Student s = new Student();
            s.setName("同学" + i + "号");
            students.add(s);
        }

        return students;
    }
}