package com.github.service;

import com.github.dao.DevUserMapper;
import com.github.pojo.DevUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DevUserServiceImpl implements DevUserService {

    @Resource
    private DevUserMapper devUserMapper;
    @Override
    public DevUser doLogin(String devCode, String devPassword) {

        return devUserMapper.doLogin(devCode,devPassword);
    }
}
