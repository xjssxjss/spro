package com.spro.service.finance;

import com.spro.dao.FiscalMonthMapper;
import com.spro.entity.finance.FiscalMonth;
import com.spro.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FiscalMonthService extends BaseService<FiscalMonth>{
    private Logger logger = LoggerFactory.getLogger(FiscalMonthService.class);

    @Autowired
    private FiscalMonthMapper fiscalMonthMapper;
    /**
     * 查询当前财务月
     * @return
     */
    public FiscalMonth queryCurrentFiscalMonth(){
        FiscalMonth fiscalMonth = fiscalMonthMapper.queryCurrentFiscalMonth();
        return fiscalMonth;
    }
}
