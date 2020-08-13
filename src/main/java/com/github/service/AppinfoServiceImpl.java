package com.github.service;

import com.github.dao.AppInfoMapper;
import com.github.pojo.AppInfo;
import com.github.pojo.DataDictionary;
import com.github.pojo.QueryAppInfoVO;
import com.github.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class AppinfoServiceImpl implements AppinfoService {

    @Resource
    private AppInfoMapper appInfoMapper;
    @Override
    public PageBean<AppInfo> findAppList(QueryAppInfoVO queryAppInfoVO) {
        //1.创建PageBean对象
        PageBean<AppInfo> pageBean = new PageBean<AppInfo>();
        pageBean.setCurrentPage(queryAppInfoVO.getPageIndex());
        pageBean.setPageSize(queryAppInfoVO.getPageSize());
        queryAppInfoVO.setStartIndex(pageBean.getStartIndex());
        //2.查询总记录数
        int count = appInfoMapper.getTotalCount(queryAppInfoVO);
        pageBean.setTotalCount(count);
        //3.查询结果集
        List<AppInfo> appInfoList = appInfoMapper.findAppInfo(queryAppInfoVO);
        pageBean.setResult(appInfoList);
        return pageBean;
    }

    @Override
    public List<DataDictionary> findDictionaryList(String param) {
        return appInfoMapper.findDictionaryList(param);
    }
}
