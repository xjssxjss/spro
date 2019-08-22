package com.spro.enums;

public enum ResultCode {
    SUCCESS(200,"成功"),               //成功
    FAIL(400,"失败"),                  //失败
    UNAUTHORIZED(401,"未认证"),          //未认证（签名错误）
    NOT_FOUND(404,"接口不存在"),             //接口不存在
    SEND_EMAIL_SUCCESS(201,"发送邮件成功!!"),
    SEND_EMAIL_FAIL(202,"发送邮件失败!!"),
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"); //服务器内部错误

    private Integer state;
    private String descript;

    ResultCode(Integer state, String descript) {
        this.state = state;
        this.descript = descript;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    /**
     * 将操作码转换为具体的操作名称
     *
     * @param code 操作码
     * @return 返回操作名称
     */
    public static String getNameByToothBrushEnum(Integer code) {
        for (ResultCode opcode : ResultCode.values()) {
            if (opcode.getState().equals(code)) {
                return opcode.getDescript();
            }
        }
        return null;
    }
}