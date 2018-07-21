package cn.itcast.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import cn.itcast.mybatis.pojo.User;

public interface UserMapper {

    public User queryUserById(Long id);

    public User queryUserByUserNameAndPassword(@Param("userName") String userName,
            @Param("password") String password);

    public void saveUser(User user);

    public void updateUser(User user);

    public void deleteById(Long id);

}
