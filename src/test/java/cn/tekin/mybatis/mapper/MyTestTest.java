package cn.tekin.mybatis.mapper;

import cn.tekin.mybatis.dao.MyTestDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyTestTest {
    InputStream inputStream;
    SqlSessionFactory sqlSessionFactory;

    @Test
    public void getMaxValueFromColumnTest(){


        // 得到mybatis配置文件流: 注意 SqlMapConfig.xml文件位于 resources 文件的根目录
        try {
            //处理异常
            this.inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }


        //创建会话工厂，传入mybatis配置文件的信息
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        try {
            MyTestDao myTestDao=new MyTestDao(sqlSessionFactory);

            Integer maxId = myTestDao.getMaxValueFromColumn("id","my_test");

            System.out.println(maxId);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
