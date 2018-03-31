package cn.itcast.userinfo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.userinfo.bean.EasyUIResult;
import cn.itcast.userinfo.mapper.UserMapper;
import cn.itcast.userinfo.pojo.User;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id查询用户数据
     * 
     * @param id
     * @return
     */
    public User queryUserById(Long id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询
     * 
     * @param page
     * @param rows
     * @return
     */
    public EasyUIResult queryUserPageList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<User> list = this.userMapper.select(null);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }

    public void saveUser(User user) {
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        this.userMapper.insert(user);
    }

    public Integer updateUser(User user) {
        user.setUpdated(new Date());
        //更新不为null的字段
        return this.userMapper.updateByPrimaryKeySelective(user);
    }

    public Integer deleteById(Long id) {
        return this.userMapper.deleteByPrimaryKey(id);
    }

}
