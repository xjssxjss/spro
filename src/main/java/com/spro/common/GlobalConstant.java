package com.spro.common;

/**
 * 常量配置
 */
public class GlobalConstant {
    //项目前缀
    public static String KEY_PREFIX = "spro";
    //redis key分割符
    public static String KEY_SPLIT_CHAR = ":";

    //入库单号前缀
    public static String DN_SUFFIX = "SLCCK";

    //文件常用后缀
    public static String PROP_SUFFIX = ".properties";

    //所有配置文件名称,多个中间以,逗号进行分隔
    public static final String BASE_FILE_NAMES = "spro,email,bonita,wx";

    //
    public static final String CONTENT_TYPE = "application/vnd.ms-excel;charset=UTF-8";

    //数据访问成功提示
    public static final String SUCCESS_MESSAGE = "数据访问成功!!";

    public static final String FAIL_PRICE_NOT_COMPLATE = "价格表数据不完整，请维护相关表数据!!";

    public static final String SUCCESS_UPLOAD_MESSAGE = "数据上传成功!!";

    public static final String UPLOAD_ERROR_MESSAGE = "请上传正确的模板文件!!";

    public static final String SUCCESS_INSERT_MESSAGE = "数据新增成功!!";

    public static final String FAIL_INV_IS_NOT_ENOUGNN = "库存不足!!";

    public static final String SUCCESS_BUILD_JOB_MESSAGE = "单据创建成功!!";

    public static final String SUCCESS_UPDATE_MESSAGE = "数据更新成功!!";

    public static final String FAIL_USER_REPEAR = "用户信息已存在";

    //数据访问失败提示

    public static final String ERROR_MESSAGE = "数据访问失败!!";

    //邮件发送状态
    public static final String EMAIL_WAIT_SEND = "emailWaitSend";       //邮件待发送
    public static final String EMAIL_ALREADY_SEND = "emailAlreadySend";       //邮件已发送

    //附件类型
    public static final String FILE_TYPE = "FILE_TYPE";         //人物
}
