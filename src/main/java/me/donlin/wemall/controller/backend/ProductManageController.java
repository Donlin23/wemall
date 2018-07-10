package me.donlin.wemall.controller.backend;

import me.donlin.wemall.common.Const;
import me.donlin.wemall.common.ResponseCode;
import me.donlin.wemall.common.ServerResponse;
import me.donlin.wemall.pojo.Product;
import me.donlin.wemall.pojo.User;
import me.donlin.wemall.service.IProductService;
import me.donlin.wemall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: Donlin
 * @Date: Created in 16:39 2018/7/10
 * @Version: 1.0
 * @Description:
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    /**
     * 新增或修改产品
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("product_save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByError("无权限操作");
        }
    }

    /**
     * 修改产品的销售状态
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.setSaleStatus(productId, status);
        }else {
            return ServerResponse.createByError("无权限操作");
        }
    }

    /**
     * 获取商品详情模块
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("get_detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByError("无权限操作");
        }
    }

    /**
     * 后台查询商品列表模块
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("get_list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.getProductList(pageNum, pageSize);
        }else {
            return ServerResponse.createByError("无权限操作");
        }
    }

    /**
     * 后台商品搜索模块
     * @param session
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("product_search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session, String productName, Integer productId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return iProductService.productSearch(productName, productId, pageNum, pageSize);
        }else {
            return ServerResponse.createByError("无权限操作");
        }
    }
}
