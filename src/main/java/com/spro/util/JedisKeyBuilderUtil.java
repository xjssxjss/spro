package com.spro.util;

import com.spro.common.GlobalConstant;

public class JedisKeyBuilderUtil {
    public static String keyBuilder(String module,String func,String... args){
        StringBuffer key = new StringBuffer(GlobalConstant.KEY_PREFIX);//KEY_PREFIX 为项目前缀
        key.append(GlobalConstant.KEY_SPLIT_CHAR).append(module).append(GlobalConstant.KEY_SPLIT_CHAR).append(func);
        for (String arg : args) {// KEY_SPLIT_CHAR 为分割字符
            key.append(GlobalConstant.KEY_SPLIT_CHAR).append(arg);
        }
        return key.toString();
    }

    public static void main(String args[]){
        System.out.println(new JedisKeyBuilderUtil().keyBuilder("com.spro","entity","DicEntries"));;
    }
}
