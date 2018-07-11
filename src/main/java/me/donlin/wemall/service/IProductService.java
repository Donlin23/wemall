package me.donlin.wemall.service;

import com.github.pagehelper.PageInfo;
import me.donlin.wemall.common.ServerResponse;
import me.donlin.wemall.pojo.Product;
import me.donlin.wemall.vo.ProductDetailVo;

/**
 * @Author: Donlin
 * @Date: Created in 16:45 2018/7/10
 * @Version: 1.0
 * @Description:
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> productSearch(String productName, Integer productId, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
