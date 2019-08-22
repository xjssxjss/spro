package com.spro.dao;

import com.spro.entity.au.User;

import java.util.Map;

public interface UserMapper extends BaseMapper<User>{
    Integer queryUsersCountByParams(Map<String,Object> paramMap);
}