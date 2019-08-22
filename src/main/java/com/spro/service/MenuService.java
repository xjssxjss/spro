package com.spro.service;

import com.spro.entity.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService extends BaseService<Menu>{

    Logger logger = LoggerFactory.getLogger(MenuService.class);

    public Map<String,Object> getMenus(){
        List<Menu> listMenu = new ArrayList<>();
        List<Menu> dictionary = null;
        try{
            dictionary = queryByParams(new HashMap());
            for (Menu menuObj : dictionary) {
                //说明是一级菜单
                if(menuObj.getType().equals("M")){
                    menuObj.setChildren(getMenu(menuObj,dictionary));
                    listMenu.add(menuObj);
                }
            }
            success = true;
        } catch (Exception e){
            e.printStackTrace();
            success = false;
        }

        data = listMenu;
        return result();
    }

    /**
     * 获取子菜单
     * @param menuObj
     * @param menuList
     * @return
     */
    public List<Menu> getMenu(Menu menuObj,List<Menu> menuList){
        List<Menu> childMenu = new ArrayList<>();
        for (Menu menu : menuList) {
            //说明有下级菜单
            if(menuObj.getId() != menu.getId() && menuObj.getId() == menu.getParentId()){
                if(null != menu.getParentId()){
                    List<Menu> menuList1 = new ArrayList<>();
                    List<Menu> menuList2 = getMenu(menu,menuList);
                    for (Menu menu1 : menuList2) {
                        if(menu.getId() != menu1.getId() && menu.getId() == menu1.getParentId()){
                            //是否设置children为null
                            //如果获取主菜单ID，在parentId中没有映射，则可以设置为null
                            menuList1.add(menu1);
                        }
                    }
                    menu.setChildren(menuList1);
                }
                if(menu.getChildren().size() == 0 ){
                    menu.setChildren(null);
                }
                childMenu.add(menu);
            }
        }
        return childMenu;
    }
}
