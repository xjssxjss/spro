package com.spro.dao;

import com.spro.entity.sys.SequenceCounter;

import java.util.List;
import java.util.Map;

public interface SequenceCounterMapper extends BaseMapper<SequenceCounter>{
    List<SequenceCounter> querySequenceCounterByName(Map<String,Object> paramMap);
}