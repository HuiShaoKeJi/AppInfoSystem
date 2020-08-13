package com.github.service;

import com.github.pojo.AppInfo;
import com.github.pojo.DataDictionary;
import com.github.pojo.QueryAppInfoVO;
import com.github.util.PageBean;

import java.util.List;

public interface AppinfoService {
    PageBean<AppInfo> findAppList(QueryAppInfoVO queryAppInfoVO);

    List<DataDictionary> findDictionaryList(String param);
}
