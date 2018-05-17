package cn.tekin.mybatis.dao;

import cn.tekin.mybatis.mapper.MyTestMapper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;

public class MyTestDao implements MyTestMapper {

    //通过构造方法接收传入进来的 sqlSessionFactory;
    SqlSessionFactory sqlSessionFactory;
    public MyTestDao(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory=sqlSessionFactory;
    }

    @Override
    public Integer getMaxValueFromColumn(String maxCol, String tableName) throws Exception {

        HashMap hm=new HashMap();
        hm.put("maxCol", maxCol);
        hm.put("tableName", tableName);

        SqlSession sqlSession=sqlSessionFactory.openSession();
        Integer maxId=sqlSession.selectOne("cn.tekin.mybatis.mapper.MyTestMapper.getMaxValueFromColumn", hm);

        //如果资源没有关闭，则释放资源
        if (null!=sqlSession){
            sqlSession.close();
        }
        //返回
        return maxId;
    }

}
