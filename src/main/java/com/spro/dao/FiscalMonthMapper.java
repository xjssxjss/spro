package com.spro.dao;

import com.spro.entity.finance.FiscalMonth;

public interface FiscalMonthMapper extends BaseMapper<FiscalMonth>{
    /**
     * 查询当前财务月对象
     * @return
     */
    FiscalMonth queryCurrentFiscalMonth();
}