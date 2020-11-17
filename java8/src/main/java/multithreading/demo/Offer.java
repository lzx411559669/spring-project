package multithreading.demo;
import java.util.List;
/**
 * @ClassName Offer
 * @Author 刘正星
 * @Date 2020/7/25 上午10:24
 */
/**
 * 商品
 */
public class Offer {
    private int id;
    private String title;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
