package cn.tekin.mybatis.mapper;

import cn.tekin.mybatis.po.Orders;
import cn.tekin.mybatis.po.OrdersCustom;
import cn.tekin.mybatis.po.User;

import java.util.List;


public interface IOrdersMapperCustom {
    //查询订单关联查询用户信息
    public List<OrdersCustom> findOrdersUser() throws Exception;

    //查询订单关联查询用户使用resultMap
    public List<Orders> findOrdersUserResultMap() throws Exception;

    //查询订单(关联用户)及订单明细
    public List<Orders> findOrdersAndOrderDetailResultMap() throws Exception;

    //查询用户购买商品信息
    public List<User> findUserAndItemsResultMap() throws Exception;

    //查询订单关联查询用户，用户信息是延迟加载
    public List<Orders> findOrdersUserLazyLoading() throws Exception;

}
