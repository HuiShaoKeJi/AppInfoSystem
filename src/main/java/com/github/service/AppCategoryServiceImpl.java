package com.github.service;

import com.github.dao.AppCategoryMapper;
import com.github.pojo.AppCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class AppCategoryServiceImpl implements AppCategoryService {

    @Resource
    private AppCategoryMapper appCategoryMapper;
    @Override
    public List<AppCategory> getAppCategoryListByParentiId(Integer pid) {
        return appCategoryMapper.getAppCategoryListByParentiId(pid);
    }
}
