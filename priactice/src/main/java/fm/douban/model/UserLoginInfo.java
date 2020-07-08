package fm.douban.model;

import java.io.Serializable;

/**
 * @ClassName UserLoginInfo
 * @Author 刘正星
 * @Date 2020/7/8 9:41
 **/
public class UserLoginInfo implements Serializable {
    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
