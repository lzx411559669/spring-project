package fm.douban.param;

import fm.douban.model.User;

/**
 * @ClassName UserQueryParam
 * @Author 刘正星
 * @Date 2020/7/6 8:59
 **/
public class UserQueryParam extends User {
    // 页码号，从 1 开始计数。值为 1 表示第一页。默认第一页。
    private int pageNum = 1;
    // 每页记录数，默认 10 条。
    private int pageSize = 10;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
