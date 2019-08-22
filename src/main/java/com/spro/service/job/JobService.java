package com.spro.service.job;

import com.spro.common.GlobalConstant;
import com.spro.entity.job.JobInfo;
import com.spro.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JobService extends BaseService<JobInfo> {
    private Logger logger = LoggerFactory.getLogger(JobService.class);

    /**
     * 初始化单据
     * @return
     */
    public Map<String,Object> initJobInfo(){
        JobInfo jobInfo = new JobInfo();

        jobInfo.setCode(generateCode(JobInfo.class.getName(),"ccj"));
        jobInfo.setCreateTime(new Date());
        jobInfo.setFisMon(queryCurrentFiscalMonth());
        jobInfo.setFisMonId(queryCurrentFiscalMonth().getId());
        try{
            insert(jobInfo);
            data = jobInfo;
            message = GlobalConstant.SUCCESS_BUILD_JOB_MESSAGE;
            success = true;
        } catch (Exception e){
            message = e.getMessage();
            success = false;
        }
        return result();
    }
}
