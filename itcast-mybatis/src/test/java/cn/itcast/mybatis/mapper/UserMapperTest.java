package cn.itcast.mybatis.mapper;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.mybatis.pojo.User;

public class UserMapperTest {

    private UserMapper userMapper;

    @Before
    public void setUp() throws Exception {
        // 完成mybatis的初始化
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        this.userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void testQueryUserById() {
        User user = this.userMapper.queryUserById(10L);
        System.out.println(user);
    }

    @Test
    public void testQueryUserByUserNameAndPassword() {
        User user = this.userMapper.queryUserByUserNameAndPassword("zhangsan", "123456");
        System.out.println(user);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setAge(20);
        user.setBirthday(new Date());
        //user.setName("test_name_2");
        user.setPassword("20");
        user.setSex(1);
        user.setUserName("test_username_2");
        this.userMapper.saveUser(user);

        System.out.println("id : " + user.getId());
    }

    @Test
    public void testUpdateUser() {
        User user = this.userMapper.queryUserById(1L);
        user.setPassword("45678");
        this.userMapper.updateUser(user);
    }

    @Test
    public void testDeleteById() {
        this.userMapper.deleteById(8L);
    }

}
