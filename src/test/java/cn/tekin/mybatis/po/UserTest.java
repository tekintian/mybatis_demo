package cn.tekin.mybatis.po;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTest {
    //把测试用到的对象先在这里声明了，减少冗余代码
    // SqlSession最佳应用场合在方法体内，定义成局部变量使用, 此处仅为介绍冗余代码而用，因为每次测试 Junit都会关闭或者开启 SqlSession
    private InputStream inputStream=null;
    private SqlSessionFactory sqlSessionFactory=null;
    private SqlSession sqlSession=null;

    //java sqldate 获取; 直接使用 java.util.Date() 获取会报错【Cannot resolve constructor 'Date()'】
    java.sql.Date sqlDate=new java.sql.Date(new Date().getTime());

    // 另外一种直接获取sql.Date的方法，直接转换系统时间戳为sql.Date
    // java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());

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

    //根据id查询用户信息，得到一条记录结果
    @Test
    public void findUserByIdTest()  {

        // 通过SqlSession操作数据库
        // 第一个参数：映射文件中statement的id，等于=namespace+"."+statement的id
        // 第二个参数：指定和映射文件中所匹配的parameterType类型的参数
        // sqlSession.selectOne结果 是与映射文件中所匹配的resultType类型的对象
        // selectOne查询出一条记录
        User user = sqlSession.selectOne("cn.tekin.mybatis.po.User.findUserById", 1);

        System.out.println("User对象："+user);
        System.out.println("用户ID:"+ user.getId()+" 用户名："+user.getUsername() +" 用户地址："+user.getAddress());


    }

    // 根据用户名称模糊查询用户列表
    @Test
    public void findUserByNameTest() {

//        // 得到配置文件流
//        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
//
//        // 创建会话工厂，传入mybatis的配置文件信息
//        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
//
//        //通过工厂得到sqlsession
//        SqlSession sqlSession=sqlSessionFactory.openSession();

        //list 中的 user 和映射文件中的 resultType所指定的类型必须一致
        List<User> list =sqlSession.selectList("cn.tekin.mybatis.po.User.findUserByName","王五");

        //控制台输出
        System.out.println("list等于："+list);


        //关闭资源
//        sqlSession.close();
    }

    //根据用户名或者地址模糊查询用户列表，使用 HashMap传递多个参数
    @Test
    public void findUserByKeywordTest()  {
        //直接获取SqlSessionFactory
//        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
//        //获取sqlsession
//        SqlSession sqlSession = sqlSessionFactory.openSession();

        //使用HashMap传递多个搜索参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username","昆明"); // AND搜索
        map.put("address","四川");// AND搜索
        map.put("id",1);//OR搜索

        //根据关键词获取数据列表
        List<User> list=sqlSession.selectList("cn.tekin.mybatis.po.User.findUserByKeyword",map);

        System.out.println(list);
        //关闭资源
        //sqlSession.close();
    }

    // 添加用户信息
    @Test
    public void insertUserTest() throws IOException {

        // 插入用户对象
        User user = new User();
        user.setUsername("张媛媛");
        user.setBirthday(sqlDate);
        user.setSex("女");
        user.setAddress("云南昆明市五华区");

        sqlSession.insert("cn.tekin.mybatis.po.User.insertUser", user);

        // 提交事务
        sqlSession.commit();

        // 获取用户信息主键
        System.out.println("刚刚插入的用户ID = "+user.getId());
    }


    // 根据id删除 用户信息
    @Test
    public void deleteUserTest() throws IOException {
        // 传入id删除 用户
        sqlSession.delete("cn.tekin.mybatis.po.User.deleteUser", 29);
        // 提交事务
        sqlSession.commit();
    }

    // 更新用户信息
    @Test
    public void updateUserTest() throws IOException {

        java.util.Date date=new java.util.Date();
        long l = date.getTime();
        java.sql.Date sqlDate=new java.sql.Date(l);

        // 更新用户信息
        User user = new User();
        //必须设置id
        user.setId(27);
        user.setUsername("王子");
        user.setBirthday(sqlDate);
        user.setSex("男");
        user.setAddress("云南曲靖");

        sqlSession.update("cn.tekin.mybatis.po.User.updateUser", user);

        // 提交事务
        sqlSession.commit();

    }


}
