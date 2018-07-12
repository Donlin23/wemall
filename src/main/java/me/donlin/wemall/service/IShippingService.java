package me.donlin.wemall.service;

import com.github.pagehelper.PageInfo;
import me.donlin.wemall.common.ServerResponse;
import me.donlin.wemall.pojo.Shipping;

/**
 * @Author: Donlin
 * @Date: Created in 10:29 2018/7/12
 * @Version: 1.0
 * @Description:
 */
public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> del(Integer userId, Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
