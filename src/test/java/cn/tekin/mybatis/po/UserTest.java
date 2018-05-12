package cn.tekin.mybatis.po;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTest {
    //根据id查询用户信息，得到一条记录结果

    @Test
    public void findUserByIdTest() throws IOException {
        // 得到mybatis配置文件流: 注意 SqlMapConfig.xml文件位于 resources 文件的根目录
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        //创建会话工厂，传入mybatis配置文件的信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 通过工厂得到SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 通过SqlSession操作数据库
        // 第一个参数：映射文件中statement的id，等于=namespace+"."+statement的id
        // 第二个参数：指定和映射文件中所匹配的parameterType类型的参数
        // sqlSession.selectOne结果 是与映射文件中所匹配的resultType类型的对象
        // selectOne查询出一条记录
        User user = sqlSession.selectOne("test.mybatis.po.user.findUserById", 1);

        System.out.println("User对象："+user);
        System.out.println("用户ID:"+ user.getId()+" 用户名："+user.getUsername() +" 用户地址："+user.getAddress());

        // 释放资源
        sqlSession.close();

    }

    // 根据用户名称模糊查询用户列表
    @Test
    public void findUserByNameTest() throws IOException {

        // 得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 创建会话工厂，传入mybatis的配置文件信息
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);

        //通过工厂得到sqlsession
        SqlSession sqlSession=sqlSessionFactory.openSession();

        //list 中的 user 和映射文件中的 resultType所指定的类型必须一致
        List<User> list =sqlSession.selectList("test.mybatis.po.user.searchUserByName","王五");

        //控制台输出
        System.out.println("list等于："+list);


        //关闭资源
        sqlSession.close();
    }

    //根据用户名或者地址模糊查询用户列表，使用 HashMap传递多个参数
    @Test
    public void findUserByKeywordTest() throws IOException {
        //直接获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        //获取sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //使用HashMap传递多个搜索参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username","昆明"); // AND搜索
        map.put("address","四川");// AND搜索
        map.put("id",1);//OR搜索

        //根据关键词获取数据列表
        List<User> list=sqlSession.selectList("test.mybatis.po.user.findUserByKeyword",map);

        System.out.println(list);
//        for (int i = 0; i < list.size(); i++) {
//            Object[] ob=(Object[]) list[i];
//            System.out.println(ob.toString());
//
////            System.out.println("用户ID:"+ list[i].getId()+" 用户名："+list[i].getUsername() +" 用户地址："+list[i].getAddress());
//        }

        //关闭资源
        sqlSession.close();

    }
}
