package com.github.controller;

import com.alibaba.fastjson.JSON;
import com.github.pojo.AppCategory;
import com.github.pojo.AppInfo;
import com.github.pojo.DataDictionary;
import com.github.pojo.QueryAppInfoVO;
import com.github.service.AppCategoryService;
import com.github.service.AppinfoService;
import com.github.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/dev/flatform/app")
public class AppInfoController {

    @Resource
    private AppinfoService appinfoService;

    @Resource
    private AppCategoryService appCategoryService;

    // AJAX请求必须添加注解 @ResponseBody   categorylevellist.json?pid=2
    @ResponseBody
    @RequestMapping("/categorylevellist.json/{pid}")
    public String getCategoryList(@PathVariable Integer pid){
        List<AppCategory> appCategoryList = appCategoryService.getAppCategoryListByParentiId(pid);
        return JSON.toJSONString(appCategoryList);
    }

    /**
     * 查询app列表
     * @param request
     * @param queryAppInfoVO
     * @return
     */
    @RequestMapping("/list")
    public String appList(HttpServletRequest request, @ModelAttribute QueryAppInfoVO queryAppInfoVO){
        // 起始页
        if (queryAppInfoVO.getPageIndex() == null){
            queryAppInfoVO.setPageIndex(1);
        }
        // 每页显示的条数
        queryAppInfoVO.setPageSize(5);
        PageBean<AppInfo> pages = appinfoService.findAppList(queryAppInfoVO);

        //查询app状态
        List<DataDictionary> statusList = appinfoService.findDictionaryList("APP_STATUS");

        /*查询所属平台*/
        List<DataDictionary> flatFormList = appinfoService.findDictionaryList("APP_FLATFORM");

        // 查询一级分类信息 categoryLevel1List
        List<AppCategory> categoryLevel1List = appCategoryService.getAppCategoryListByParentiId(null);

        /*进行数据回显*/
        request.setAttribute("querySoftwareName",queryAppInfoVO.getQuerySoftwareName());
        request.setAttribute("queryStatus",queryAppInfoVO.getQueryStatus());
        request.setAttribute("queryFlatformId",queryAppInfoVO.getQueryFlatformId());
        request.setAttribute("queryCategoryLevel1",queryAppInfoVO.getQueryCategoryLevel1());
        request.setAttribute("queryCategoryLevel2",queryAppInfoVO.getQueryCategoryLevel2());
        request.setAttribute("queryCategoryLevel3",queryAppInfoVO.getQueryCategoryLevel3());

        /*完善分类回显*/
        if (queryAppInfoVO.getQueryCategoryLevel1() != null){
            List<AppCategory> categoryLevel2List = appCategoryService.getAppCategoryListByParentiId(queryAppInfoVO.getQueryCategoryLevel1());
            request.setAttribute("categoryLevel2List",categoryLevel2List);
        }
        if (queryAppInfoVO.getQueryCategoryLevel2() != null){
            List<AppCategory> categoryLevel3List = appCategoryService.getAppCategoryListByParentiId(queryAppInfoVO.getQueryCategoryLevel2());
            request.setAttribute("categoryLevel3List",categoryLevel3List);
        }

        /*存储信息*/
        request.setAttribute("categoryLevel1List",categoryLevel1List);
        request.setAttribute("flatFormList",flatFormList);
        request.setAttribute("statusList",statusList);
        request.setAttribute("pages",pages);
        request.setAttribute("appInfoList",pages.getResult());
        return "developer/appinfolist";
    }
}
