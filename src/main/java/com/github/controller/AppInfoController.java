package com.github.controller;

import com.alibaba.fastjson.JSON;
import com.github.pojo.*;
import com.github.service.AppCategoryService;
import com.github.service.AppinfoService;
import com.github.util.PageBean;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dev/flatform/app")
public class AppInfoController {

    @Resource
    private AppinfoService appinfoService;

    @Resource
    private AppCategoryService appCategoryService;


    @ResponseBody
    @RequestMapping("/categorylevellist.json")
    public String getCategoryList(Integer pid){
        List<AppCategory> appCategoryList = appCategoryService.getAppCategoryListByParentiId(pid);
        return JSON.toJSONString(appCategoryList);
    }

    /**
     * 查询app列表
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

        // 查询一级分类信息
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

    /**
     * AJAX动态加载所属平台
     */
    @ResponseBody
    @RequestMapping("/datadictionarylist.json")
    public String getFlatFormList(@RequestParam String tcode){
        List<DataDictionary> flatFormList = appinfoService.findDictionaryflatFormList(tcode);
        return JSON.toJSONString(flatFormList);
    }

    /**
     * 跳转新增
     */
    @RequestMapping("/appinfoadd")
    public  String appInfoAdd(){
        return "developer/appinfoadd";
    }


    @ResponseBody
    @RequestMapping("/apkexist.json")
    public String checkAPKName(@RequestParam String APKName){
        Map<Object,String> map = new HashMap<>( );
        if (APKName.isEmpty()){
            map.put("APKName","empty");
        }else if(appinfoService.apkNameExist(APKName)){
            map.put("APKName","exist");
        }else{
            map.put("APKName","noexist");
        }
        return JSON .toJSONString(map);
    }

    @RequestMapping("/appinfoaddsave")
    public String appInfoAddSave(HttpServletRequest request, HttpSession session, @ModelAttribute AppInfo appInfo, @RequestParam("a_logoPicPath")MultipartFile multipartFile){
        String logoLocPath = null;
        String logoPicPath = null;
        // 判断是否是文件上传
        if (!multipartFile.isEmpty()){
            // 1.指定上传的目录
            String realPath = session.getServletContext().getRealPath("statics/uploadfiles");
            // 2.定义上传文件的大小
            int fileSize = 2097152;
            //3.定义上传文件的类型
            List<String> fileNameList = Arrays.asList("jpg","png");
            // 获取文件的大小
            long size = multipartFile.getSize();
            // 获取文件名
            String fileName = multipartFile.getOriginalFilename();
            // 获取文件的扩展名
            String extension = FilenameUtils.getExtension(fileName);
            // 判断是否符合你文件的要求
            if (fileSize < size){
                request.setAttribute("fileUploadError","上传文件超过大小限制!");
                return "developer/appinfoadd";
            }else if(!fileNameList.contains(extension)){//文件格式不符合
                request.setAttribute("fileUploadError","不支持此种文件的格式!");
                return "developer/appinfoadd";
            }else{
                String newFileName = appInfo.getAPKName()+"_"+System.currentTimeMillis()+"."+extension;
                File dest = new File(realPath+File.separator+newFileName);
                try {
                    // 进行文件上传
                    multipartFile.transferTo(dest);
                    // 获取文件上传的地址
                    logoPicPath = File.separator+"statics"+File.separator+"uploadfiles"+File.separator+newFileName;
                    // 获取绝对路径
                    logoLocPath = realPath+File.separator+newFileName;
                }catch (IllegalStateException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
                //设置相对路径
                appInfo.setLogoPicPath(logoPicPath);
                //设置绝对路径
                appInfo.setLogoLocPath(logoLocPath);
                DevUser devUser = (DevUser)session.getAttribute("devUserSession");
                appInfo.setCreatedBy(devUser.getId());
                appInfo.setCreationDate(new Date());
                appInfo.setDevId(devUser.getId());

                boolean add = appinfoService.appInfoAdd(appInfo);
                if (!add){
                    return "developer/appinfoadd";
                }
            }
        }
        return "redirect:/dev/flatform/app/list";
    }
}
