package me.donlin.wemall.service;

import me.donlin.wemall.common.ServerResponse;
import me.donlin.wemall.pojo.Category;

import java.util.List;

/**
 * @Author: Donlin
 * @Date: Created in 17:33 2018/7/9
 * @Version: 1.0
 * @Description:
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
