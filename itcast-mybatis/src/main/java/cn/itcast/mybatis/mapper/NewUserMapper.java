package cn.itcast.mybatis.mapper;

import java.util.List;


import cn.itcast.mybatis.pojo.User;

import com.github.abel533.mapper.Mapper;

public interface NewUserMapper extends Mapper<User> {

    /**
     * 分页查询用户数据
     * @param start 数据条数的偏移量
     * @param rows 获取的数据条数
     * @return
     */
    public List<User> queryPageList(Integer start, Integer rows);
    
    public List<User> queryAll();
    
    public User selectOne();

}
