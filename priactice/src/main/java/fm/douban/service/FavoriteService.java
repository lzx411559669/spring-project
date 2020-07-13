package fm.douban.service;

import fm.douban.model.Favorite;

import java.util.List;

/**
 * @InterfaceName FavoriteService
 * @Author 刘正星
 * @Date 2020/7/10 18:32
 **/
public interface FavoriteService {
    //新增一个喜欢
    Favorite add(Favorite favorite);
    //计算喜欢数，如果大于0表示已经喜欢
    List<Favorite> list(Favorite favParam);
    //删除一个喜欢
    boolean delete(Favorite favParam);
}
