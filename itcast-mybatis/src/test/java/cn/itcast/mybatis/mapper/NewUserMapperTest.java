package cn.itcast.mybatis.mapper;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.mybatis.pojo.User;

public class NewUserMapperTest {

    private NewUserMapper newUserMapper;

    @Before
    public void setUp() throws Exception {
        // 完成mybatis的初始化
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        this.newUserMapper = sqlSession.getMapper(NewUserMapper.class);
    }

    @Test
    public void testSelectOne() {
        User record = new User();
        record.setUserName("zhangsan");
        record.setPassword("45678");
        User queryAll = newUserMapper.selectOne(record);
        System.out.println(queryAll);
    }

    @Test
    public void testSelect() {
        User record = new User();
        record.setSex(1);
        List<User> list = this.newUserMapper.select(record);
        for (User user : list) {
            System.out.println(user);
        }
    }
    
    /**
     * 测试分页助手
     */
    @Test
    public void testPageHelper() {
        
        //设置分页参数，第一个参数是：当前页数，第二个参数是：数据条数
        PageHelper.startPage(1, 3);
        
        List<User> list = this.newUserMapper.queryAll();
        
        //获取分页后的数据
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        
        System.out.println("数据总条数：" + pageInfo.getTotal());
        System.out.println("数据总页数：" + pageInfo.getPages());
        
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelectCount() {
        // 查询数据总条数
        System.out.println(this.newUserMapper.selectCount(null));
    }

    @Test
    public void testSelectByPrimaryKey() {
        User user = this.newUserMapper.selectByPrimaryKey(1L);
        System.out.println(user);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setAge(20);
        user.setBirthday(new Date());
        //user.setName("test_name_5");
       // user.setPassword("20");
        //user.setSex(1);
        user.setUserName("test_username_5");
        // 使用所有的属性作为字段使用
        this.newUserMapper.insert(user);
        
        System.out.println("id = " + user.getId());
    }

    @Test
    public void testInsertSelective() {
        User user = new User();
        user.setUserName("test_username_4");
        //插入数据，使用不为null的属性作为字段使用
        this.newUserMapper.insertSelective(user);
    }

    @Test
    public void testDelete() {
        // TODO 作业
       // this.newUserMapper.delete(null);
    }

    @Test
    public void testDeleteByPrimaryKey() {
       this.newUserMapper.deleteByPrimaryKey(11L);
    }

    @Test
    public void testUpdateByPrimaryKey() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        //修改用户的密码
        User user = new User();
        user.setId(1L);
        user.setPassword("123456");
        user.setUpdated(new Date());
        this.newUserMapper.updateByPrimaryKeySelective(user);
    }
    
    /**
     * 需求：
     * 1、查询用户列表并且按照更新时间倒序排序
     * 2、根据多个id查询用户数据
     * 3、批量删除数据
     * 
     */

    @Test
    public void testSelectCountByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteByExample() {
        //3、批量删除数据
        Example example = new Example(User.class);
        List<Object> ids = new ArrayList<Object>();
        ids.add(9L);
        ids.add(10L);
        ids.add(50L);
        ids.add(60L);
        example.createCriteria().andIn("id", ids);
        System.out.println(this.newUserMapper.deleteByExample(example));
    }

    @Test
    public void testSelectByExample() {
        //1、查询用户列表并且按照更新时间倒序排序
        Example example = new Example(User.class);
        example.setOrderByClause("updated DESC");
        List<User> list = this.newUserMapper.selectByExample(example);
        for (User user : list) {
            System.out.println(user);
        }
    }
    
    @Test
    public void testSelectByExample2() {
        //2、根据多个id查询用户数据
        Example example = new Example(User.class);
        List<Object> ids = new ArrayList<Object>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        example.createCriteria().andIn("id", ids);
        List<User> list = this.newUserMapper.selectByExample(example);
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdateByExampleSelective() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByExample() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testQueryPageList() {
        List<User> list = this.newUserMapper.queryPageList(3, 2);
        for (User user : list) {
            System.out.println(user);
        }
    }

}
