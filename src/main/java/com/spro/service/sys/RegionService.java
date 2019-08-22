package com.spro.service.sys;

import com.spro.common.GlobalConstant;
import com.spro.entity.sys.Region;
import com.spro.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RegionService extends BaseService<Region> {

    private Logger logger = LoggerFactory.getLogger(RegionService.class);
    /**
     * 获取所有region
     * @return
     */
    public Map<String,Object> queryAllRegions(){
        List<Region> resultList = new ArrayList();
        //首先查询所有区域信息
        try{
           List<Region> listRegion = queryByParams(paramMap);
           //说明有region信息
           if(null != listRegion && listRegion.size() > 0){
               for (Region region : listRegion) {
                   //说明是父节点
                   if("-1".equals(region.getRegionParentId())){
                       //获取一级region下的子region
                       List<Region> childRegion = getChildRegion(listRegion,region);
                       if(null == childRegion || childRegion.size()<=0){
                           //说明没有region
                           resultList.add(region);
                       } else {
                           region.setChildren(childRegion);
                           resultList.add(region);
                       }
                   }
               }
           }

           data = resultList;
           message = GlobalConstant.SUCCESS_MESSAGE;
           success = true;
        }catch (Exception e){
            success = false;
            message = e.getMessage();
            e.printStackTrace();
        }
        return result();
    }

    /**
     * 递归获取region地区信息
     * @return
     */
    public List<Region> getChildRegion(List<Region> childListRegion , Region region){
        //声明子region集合对象
        List<Region> listRegion = new ArrayList<>();
        for (Region childRegion : childListRegion) {
            //说明子region Id等于其父region Id
            if(!"-1".equals(childRegion)
                    && !childRegion.getRegionId().equals(region.getRegionId())
                    && childRegion.getRegionParentId().equals(region.getRegionId())){
                List<Region> liRegion = getChildRegion(childListRegion,childRegion);
                if(null != liRegion && liRegion.size()>0){
                    //根据子region查询,是否还有子region
                    List<Region> tempRegion = new ArrayList<>();
                    //说明还有子region
                    for (Region region1 : liRegion) {
                        if(!"-1".equals(childRegion) && !region1.getRegionId().equals(childRegion.getRegionId()) && region1.getRegionParentId().equals(childRegion.getRegionId())){
                            tempRegion.add(region1);
                        }
                    }
                    childRegion.setChildren(tempRegion);
                } else {
                    childRegion.setChildren(null);
                }

                listRegion.add(childRegion);
            }
        }
        return listRegion;
    }
}
