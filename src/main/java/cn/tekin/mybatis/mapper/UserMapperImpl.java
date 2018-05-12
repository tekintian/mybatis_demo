package cn.tekin.mybatis.mapper;

import cn.tekin.mybatis.po.User;
import cn.tekin.mybatis.po.UserCustom;
import cn.tekin.mybatis.po.UserQueryVo;

import java.util.List;

public interface UserMapperImpl {

    // 输出单个pojo对象，方法返回值是单个对象类型
    //根据id查询用户信息
    public User findUserById(int id) throws Exception;

    //输出pojo对象list，方法返回值是List
    //根据用户名列查询用户列表
    public List<User> findUserByName(String name) throws Exception;

    //添加用户信息
    public void insertUser(User user) throws Exception;

    //删除用户信息
    public void deleteUser(int id) throws Exception;

    //更新用户
    public void updateUser(User user)throws Exception;

    //输入映射  -- 用户信息综合查询
    public List<UserCustom> findUserList(UserQueryVo userQueryVo) throws Exception;

    // 输出简单类型演示 -- 用户信息查询数量
    public Integer findUserCount(UserQueryVo userQueryVo) throws Exception;

    //根据id查询用户信息，使用resultMap输出
    public User findUserByIdResultMap(int id) throws Exception;
}