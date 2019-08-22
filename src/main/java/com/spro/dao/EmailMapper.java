package com.spro.dao;

import com.spro.entity.sys.Email;

import java.util.List;
import java.util.Map;

public interface EmailMapper extends BaseMapper<Email>{
    List<Map> countEmail();
}