package com.tao.serialliba.tobaco;

public enum ResultType {

//    结果/故障	结果/故障代码	结果/故障名称
//    OK	0x00	执行完成
//    INVALID_CMD	0x01	无效命令
//    INVALID_PARA	0x02	无效参数
//    CHECKSUN_WRONG	0x03	校验和错误
//    FRAMES_TYPE_WRONG	0x04	帧类型出错
//    FRAME_HEAD_ WRONG	0x05	帧头出错
//	          0x06
//            0x07
//            0x08
//    ZERO_ERR	0x09	红外/光耦异常(步进电机)
//    STEP_RST_TIME_OUT	0x0A	步进电机复位超时
//    SELL_ERR	0x0B	出货失败
//（红外无检测到货物）
//    SYSTEM_BUSY	0x0C	系统忙碌
//    CHANNEL_ERR	0x0D	货道异常
//（出货模块检测异常）
//    PN_MOTOR_OVER_CURRENT	0x0E	正反电机转动过流
//    PN_MOTOR_RUN_TIMEOUT	0x0F	正反电机运动超时
//（找不到限位开关）
//    GET_QBAR_CODE_TIMEOUT	0x10	获取二位码超时
//    TEMP_ERR	0x30	温度异常
//    SHAKE_WARNING	0x31	报警器

    OK((byte) 0x00, "执行完成"),
    INVALID_CMD((byte) 0x01, "无效命令"),
    INVALID_PARA((byte) 0x02, "无效参数"),
    CHECKSUN_WRONG((byte) 0x03, "校验和错误"),
    FRAMES_TYPE_WRONG((byte) 0x04, "帧类型出错"),
    FRAME_HEAD_WRONG((byte) 0x05, "帧头出错"),
    CHANNEL_EMPTY((byte) 0x06, "通道无货"),
    GET_QBAR_CODE_TIMEOUT((byte) 0x07, "获取二位码超时"),
    ADD_GOODS_ERR((byte) 0x08, "补货错误(检测该通道已经有货)"),
    
    
    ZERO_ERR((byte) 0x09, "红外/光耦异常(步进电机)"),
    STEP_RST_TIME_OUT((byte) 0x0A, "步进电机复位超时"),
    SELL_ERR((byte) 0x0B, "出货失败"),
    SYSTEM_BUSY((byte) 0x0C, "系统忙碌"),
    CHANNEL_ERR((byte) 0x0D, "货道异常"),
    PN_MOTOR_OVER_CURRENT((byte) 0x0E, "正反电机转动过流"),
    PN_MOTOR_RUN_TIMEOUT((byte) 0x0F, "正反电机运动超时"),
//    GET_QBAR_CODE_TIMEOUT((byte) 0x10, "获取二位码超时"),
    TEMP_ERR((byte) 0x30, "温度异常"),

    SHAKE_WARNING((byte) 0x31, "报警器");

    byte data;
    String detail;

    ResultType(byte data, String detail) {
        this.data = data;
        this.detail = detail;
    }

    static ResultType type(byte data) {
        switch (data) {
            case (byte) 0x00:
                return OK;//((byte) 0x00, "执行完成") ,
            case (byte) 0x01:
                return INVALID_CMD;//((byte) 0x01, "无效命令") ,
            case (byte) 0x02:
                return INVALID_PARA;//((byte) 0x02, "无效参数") ,
            case (byte) 0x03:
                return CHECKSUN_WRONG;//((byte) 0x03, "校验和错误") ,
            case (byte) 0x04:
                return FRAMES_TYPE_WRONG;//((byte) 0x04, "帧类型出错") ,
            case (byte) 0x05:
                return FRAME_HEAD_WRONG;//((byte) 0x05, "帧头出错") ,
             case (byte) 0x06:
                return CHANNEL_EMPTY;//((byte) 0x05, "帧头出错") ,
            case (byte) 0x07:
                return GET_QBAR_CODE_TIMEOUT;//((byte) 0x10, "获取二位码超时") ,
             case (byte) 0x08:
                return ADD_GOODS_ERR;//((byte) 0x05, "帧头出错") ,
            
            case (byte) 0x09:
                return ZERO_ERR;//((byte) 0x09, "红外/光耦异常(步进电机)") ,
            case (byte) 0x0A:
                return STEP_RST_TIME_OUT;//((byte) 0x0A, "步进电机复位超时") ,
            case (byte) 0x0B:
                return SELL_ERR;//((byte) 0x0B, "出货失败") ,
            case (byte) 0x0C:
                return SYSTEM_BUSY;//((byte) 0x0C, "系统忙碌") ,
            case (byte) 0x0D:
                return CHANNEL_ERR;//((byte) 0x0D, "货道异常") ,
            case (byte) 0x0E:
                return PN_MOTOR_OVER_CURRENT;//((byte) 0x0E, "正反电机转动过流") ,
            case (byte) 0x0F:
                return PN_MOTOR_RUN_TIMEOUT;//((byte) 0x0F, "正反电机运动超时") ,
            case (byte) 0x30:
                return TEMP_ERR;//((byte) 0x30, "温度异常") ,
            case (byte) 0x31:
                return SHAKE_WARNING;//((byte) 0x31, "报警器");
            default:
                return null;
        }
    }


}
