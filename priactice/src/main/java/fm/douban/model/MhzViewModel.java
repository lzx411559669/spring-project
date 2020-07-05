package fm.douban.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName MhzViewModel
 * @Author 刘正星
 * @Date 2020/6/20 8:56
 **/
@Data
public class MhzViewModel {
    private String title;
    private List<Subject> subjects;
}
