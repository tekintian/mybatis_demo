package cn.tekin.mybatis.mapper;

import cn.tekin.mybatis.po.User;
import cn.tekin.mybatis.po.UserCustom;
import cn.tekin.mybatis.po.UserQueryVo;
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

public class userMapperImplTest {
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
    public void FindUserById() throws Exception{

        //创建UserMapperImpl代理对象
        UserMapperImpl userMapperImpl=sqlSession.getMapper(UserMapperImpl.class);

        //调用userMapperImpl的方法
        User user=userMapperImpl.findUserById(1);

        System.out.println("id="+ user.getId() + " Username="+user.getUsername()+" Address="+ user.getAddress());
    }

    //用户信息的综合 查询
    @Test
    public void testFindUserList() throws Exception {

        //创建UserMapperImpl对象，mybatis自动生成mapper代理对象
        UserMapperImpl userMapper = sqlSession.getMapper(UserMapperImpl.class);

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
        userCustom.setSex("男");
        userCustom.setUsername("小明");
        userCustom.setAddress("昆明");
        userQueryVo.setUserCustom(userCustom);
        //调用userMapper的方法

        List<UserCustom> list = userMapper.findUserList(userQueryVo);

        System.out.println(list);

    }

    //用户信息综合查询总数
    @Test
    public void testFindUserCount() throws Exception {

        //创建UserMapper对象，mybatis自动生成mapper代理对象
        UserMapperImpl userMapper = sqlSession.getMapper(UserMapperImpl.class);

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
        userCustom.setSex("男");
        userCustom.setUsername("小");
        userQueryVo.setUserCustom(userCustom);
        //调用userMapper的方法

        int count = userMapper.findUserCount(userQueryVo);

        System.out.println("用户数量："+count);

    }

}