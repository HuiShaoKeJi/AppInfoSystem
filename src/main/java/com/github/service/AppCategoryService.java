package com.github.service;

import com.github.pojo.AppCategory;

import java.util.List;

public interface AppCategoryService {
    List<AppCategory> getAppCategoryListByParentiId(Integer pid);
}
