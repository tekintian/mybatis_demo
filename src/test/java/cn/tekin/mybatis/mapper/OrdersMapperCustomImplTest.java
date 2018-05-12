package cn.tekin.mybatis.mapper;

import cn.tekin.mybatis.po.Orders;
import cn.tekin.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OrdersMapperCustomImplTest {
    private InputStream inputStream=null;
    private SqlSessionFactory sqlSessionFactory=null;
    private SqlSession sqlSession=null;

    //注解Before是在执行本类所有测试方法之前先调用这个方法
    //在每个单元测试之前运行
    @Before
    public void setUp(){
        // 得到mybatis配置文件流: 注意 SqlMapConfig.xml文件位于 resources 文件的根目录
        try {
            //处理异常
            this.inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建会话工厂，传入mybatis配置文件的信息
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 通过工厂得到SqlSession
        this.sqlSession = sqlSessionFactory.openSession();
    }

    //在测试完成后执行
    @After
    public void tearDown(){
        // 释放资源
        sqlSession.close();
    }

    @Test
    public void testFindOrdersUserResultMap() throws Exception {

        // 创建代理对象
        OrdersMapperCustomImpl ordersMapperCustom = sqlSession
                .getMapper(OrdersMapperCustomImpl.class);

        // 调用maper的方法
        List<Orders> list = ordersMapperCustom.findOrdersUserResultMap();

        System.out.println(list);

//        sqlSession.close();
    }

    // 查询订单关联查询用户，用户信息使用延迟加载
    @Test
    public void testFindOrdersUserLazyLoading() throws Exception {

        OrdersMapperCustomImpl ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustomImpl.class);
        // 查询订单信息（单表）
        List<Orders> list = ordersMapperCustom.findOrdersUserLazyLoading();

        // 遍历上边的订单列表
        for (Orders orders : list) {
            // 执行getUser()去查询用户信息，这里实现按需加载
            User user = orders.getUser();
            System.out.println(user);
        }
    }

    // 一级缓存测试
    @Test
    public void testCache1() throws Exception {
//        SqlSession sqlSession = sqlSessionFactory.openSession();// 创建代理对象
        UserMapperImpl userMapper = sqlSession.getMapper(UserMapperImpl.class);

        // 下边查询使用一个SqlSession
        // 第一次发起请求，查询id为1的用户
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

        // 如果sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

        // 更新user1的信息
        // user1.setUsername("测试用户22");
        // userMapper.updateUser(user1);
        // //执行commit操作去清空缓存
        // sqlSession.commit();

        // 第二次发起请求，查询id为1的用户
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);

//        sqlSession.close();

    }

    // 二级缓存测试
    @Test
    public void testCache2() throws Exception {

        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();
        // 创建代理对象
        UserMapperImpl userMapper1 = sqlSession1.getMapper(UserMapperImpl.class);
        // 第一次发起请求，查询id为1的用户
        User user1 = userMapper1.findUserById(1);
        System.out.println(user1);

        //这里执行关闭操作，将sqlsession中的数据写到二级缓存区域
        sqlSession1.close();


        //		//使用sqlSession3执行commit()操作
        //		UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
        //		User user  = userMapper3.findUserById(1);
        //		user.setUsername("张明明");
        //		userMapper3.updateUser(user);
        //		//执行提交，清空UserMapper下边的二级缓存
        //		sqlSession3.commit();
        //		sqlSession3.close();



        UserMapperImpl userMapper2 = sqlSession2.getMapper(UserMapperImpl.class);
        // 第二次发起请求，查询id为1的用户
        User user2 = userMapper2.findUserById(1);
        System.out.println(user2);

        sqlSession2.close();
    }

}
