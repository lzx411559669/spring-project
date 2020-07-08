package fm.douban.service;

import fm.douban.model.User;
import fm.douban.param.UserQueryParam;
import org.springframework.data.domain.Page;

/**
 * @ClassName UserService
 * @Author 刘正星
 * @Date 2020/7/6 9:08
 **/
public interface UserService {
    /**
     * 新增
     */
    User add(User user);

    /**
     * id 查询
     */
    User get(String id);

    /**
     * 条件查询，支持分页
     */
    Page<User> list(UserQueryParam param);

    /**
     * 修改
     */
    boolean modify(User user);

    /**
     * 删除
     */
    boolean delete(String id);

    /**
     * 根据用户名查找
     * @param loginName
     * @return
     */
    User getByLoginName(String loginName);
}
