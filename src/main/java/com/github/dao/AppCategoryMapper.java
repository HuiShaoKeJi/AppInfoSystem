package com.github.dao;

import com.github.pojo.AppCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppCategoryMapper {
    List<AppCategory> getAppCategoryListByParentiId(@Param("pid") Integer pid);
}
