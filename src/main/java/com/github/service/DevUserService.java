package com.github.service;

import com.github.pojo.DevUser;

public interface DevUserService {
    DevUser doLogin(String devCode, String devPassword);
}
