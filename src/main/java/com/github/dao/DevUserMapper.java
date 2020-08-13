package com.github.dao;

import com.github.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

public interface DevUserMapper {
    DevUser doLogin(@Param("devCode") String devCode,@Param("devPassword") String devPassword);
}
