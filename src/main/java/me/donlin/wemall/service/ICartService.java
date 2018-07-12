package me.donlin.wemall.service;

import me.donlin.wemall.common.ServerResponse;
import me.donlin.wemall.vo.CartVo;

/**
 * @Author: Donlin
 * @Date: Created in 19:37 2018/7/11
 * @Version: 1.0
 * @Description:
 */
public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
