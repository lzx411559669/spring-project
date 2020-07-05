package com.lzx.repository;

import com.lzx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName UserRepository
 * @Author 刘正星
 * @Date 2020/6/2 20:06
 **/
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 根据账号密码查找user
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndAndPassword(String username,String password);
}
