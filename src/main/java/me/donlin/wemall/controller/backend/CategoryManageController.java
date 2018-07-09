package me.donlin.wemall.controller.backend;

import me.donlin.wemall.common.Const;
import me.donlin.wemall.common.ResponseCode;
import me.donlin.wemall.common.ServerResponse;
import me.donlin.wemall.pojo.User;
import me.donlin.wemall.service.ICategoryService;
import me.donlin.wemall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: Donlin
 * @Date: Created in 15:02 2018/7/9
 * @Version: 1.0
 * @Description:
 */
@Controller
@RequestMapping(value = "/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加品类模块
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName,@RequestParam(value = "parentId", defaultValue = "0") int parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //是管理员
            //增加我们处理分类的逻辑
            return iCategoryService.addCategory(categoryName, parentId);
        }else {
            return ServerResponse.createByError("无权限操作，需要管理员权限");
        }
    }

    /**
     * 更新品类名字模块
     * @param session
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //更新categoryName
            return iCategoryService.updateCategoryName(categoryId, categoryName);
        }else {
            return ServerResponse.createByError("无权限操作，需要管理员权限");
        }
    }

    /**
     * 查询子节点的category信息
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_children_parallel_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //查询子节点的category信息，并且不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else {
            return ServerResponse.createByError("无权限操作，需要管理员权限");
        }
    }

    /**
     * 递归查询本节点的Id及孩子节点的Id
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category_and_deep_children_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id和递归子节点的id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else {
            return ServerResponse.createByError("无权限操作，需要管理员权限");
        }
    }



}
