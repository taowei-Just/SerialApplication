package com.tao.serialliba.tobaco;

public enum CommandContentType {
//    STEP_MOTOR_RESET	0x50	复位
//    SET_PARA	0x51	设置参数
//    GET_PARA	0x52	获取参数
//    GET_CHANNEL_STATE	0x53	获取货道状态
//    OPEN	0x54	打开(LED、报警器、条码扫描器等)
//    CLOSE	0x55	关闭
//    STEP_MOTOR_MOVE	0x56	步进电机运动至指定坐标
//    HD_MOTOR_TIME_CAL	0x57	出货电机时间校准
//    PN_MOTOR_MOVE	0x58	正反转电机转动
//    SELL_PROCESS	0x59	出货命令
//   LED_BAR_CTRL 	0x5A	灯条控制亮度
//    GET_TEMPERATURE	0x5B	获取温度
//    GET_BARCODER	0x5C	获取付款二维码
//    CHANGE_TEMPERATURE_CTRL	0x5D	更改控制的温度
//    GET_DOOR_STATE	0x5E	获取大门状态
//	            0x5F
//    RESET_COM	0x60	复位通信


    STEP_MOTOR_RESET((byte) 0x50, "复位"),

    SET_PARA((byte) 0x51, "设置参数"),

    GET_PARA((byte) 0x52, "获取参数"),

    GET_CHANNEL_STATE((byte) 0x53, "获取货道状态"),

    OPEN((byte) 0x54, "打开(LED、报警器、条码扫描器等)"),

    CLOSE((byte) 0x55, "关闭"),   

    STEP_MOTOR_MOVE((byte) 0x56, "步进电机运动至指定坐标"),

    HD_MOTOR_TIME_CAL((byte) 0x57, "出货电机时间校准"),

    PN_MOTOR_MOVE((byte) 0x58, "正反转电机转动(" + "导烟帘\\推烟\\升货仓\\取货门帘" + ")"),

    SELL_PROCESS((byte) 0x59, "出货命令"),

    LED_BAR_CTRL((byte) 0x5A, "灯条控制亮度"),

    GET_TEMPERATURE((byte) 0x5B, "获取温度"),

    STEO_MOTOR_MOVE_POS((byte) 0x5C, "步进电机运动至自定义位置点"),

    CHANGE_TEMPERATURE_CTRL((byte) 0x5D, "更改控制的温度"),

    GET_DOOR_STATE((byte) 0x5E, "获取大门状态"),

    BOOT_READY((byte) 0x5F, "握手协议（主板开机准备工作已完成）"),

    RESET_COM((byte) 0x60, "复位通信"),

    ADD_GOODS_READY((byte) 0x61, "补货前命令" + "（补货模块运动至加货口）"),

    ADD_GOODS((byte) 0x62, "到对应的通道补货"),

    GET_BARCODER((byte) 0x63, "获取付款二维码"),


    GET_VERSIONS((byte) 0x99, "获取版本号");


    byte data;
    String detail;

    public byte getData() {
        return data;
    }

    CommandContentType(byte data, String detail) {
        this.data = data;
        this.detail = detail;
    }

    static CommandContentType type(byte data) {
        switch (data) {
            case (byte) 0x50:
                return STEP_MOTOR_RESET;
            case (byte) 0x51:
                return SET_PARA;
            case (byte) 0x52:
                return GET_PARA;
            case (byte) 0x53:
                return GET_CHANNEL_STATE;
            case (byte) 0x54:
                return OPEN;//((byte) 0x54, "打开(LED、报警器、条码扫描器等)"),
            case (byte) 0x55:
                return CLOSE;//((byte) 0x55, "关闭"),
            case (byte) 0x56:
                return STEP_MOTOR_MOVE;//((byte) 0x56, "步进电机运动至指定坐标"),
            case (byte) 0x57:
                return HD_MOTOR_TIME_CAL;//((byte) 0x57, "出货电机时间校准"),
            case (byte) 0x58:
                return PN_MOTOR_MOVE;//((byte) 0x58, "正反转电机转动"),
            case (byte) 0x59:
                return SELL_PROCESS;//((byte) 0x59, "出货命令"),
            case (byte) 0x5A:
                return LED_BAR_CTRL;//((byte) 0x5A, "灯条控制亮度"),
            case (byte) 0x5B:
                return GET_TEMPERATURE;//((byte) 0x5B, "获取温度"),
            case (byte) 0x5C:
                return STEO_MOTOR_MOVE_POS;//((byte) 0x5C, "获取付款二维码"),
            case (byte) 0x5D:
                return CHANGE_TEMPERATURE_CTRL;//((byte) 0x5D, "更改控制的温度"),
            case (byte) 0x5E:
                return GET_DOOR_STATE;//((byte) 0x5D, "更改控制的温度"),

            case (byte) 0x5F:
                return BOOT_READY;
            case (byte) 0x60:
                return RESET_COM;

            case (byte) 0x61:
                return ADD_GOODS_READY;
            case (byte) 0x62:
                return ADD_GOODS;
            case (byte) 0x63:
                return GET_BARCODER;


            case (byte) 0x99:
                return GET_VERSIONS;//((byte) 0x99, "获取版本号");
            default:
                return null;
        }
    }

    static byte type(CommandContentType type) {
        switch (type) {
            case STEP_MOTOR_RESET:
                return (byte) 0x50;
            case SET_PARA:
                return (byte) 0x51;
            case GET_PARA:
                return (byte) 0x52;
            case GET_CHANNEL_STATE:
                return (byte) 0x53;
            case OPEN:
                return (byte) 0x54;//((byte) 0x54, "打开(LED、报警器、条码扫描器等)"),
            case CLOSE:
                return (byte) 0x55;//((byte) 0x55, "关闭"),
            case STEP_MOTOR_MOVE:
                return (byte) 0x56;//((byte) 0x56, "步进电机运动至指定坐标"),
            case HD_MOTOR_TIME_CAL:
                return (byte) 0x57;//((byte) 0x57, "出货电机时间校准"),
            case PN_MOTOR_MOVE:
                return (byte) 0x58;//((byte) 0x58, "正反转电机转动"),
            case SELL_PROCESS:
                return (byte) 0x59;//((byte) 0x59, "出货命令"),
            case LED_BAR_CTRL:
                return (byte) 0x5A;//((byte) 0x5A, "灯条控制亮度"),
            case GET_TEMPERATURE:
                return (byte) 0x5B;//((byte) 0x5B, "获取温度"),
            case STEO_MOTOR_MOVE_POS:
                return (byte) 0x5C;//((byte) 0x5C, "获取付款二维码"),
            case CHANGE_TEMPERATURE_CTRL:
                return (byte) 0x5D;//((byte) 0x5D, "更改控制的温度"),
            case GET_DOOR_STATE:
                return (byte) 0x5E;//((byte) 0x5D, "更改控制的温度"),

            case BOOT_READY:
                return (byte) 0x5F;
            case RESET_COM:
                return (byte) 0x60;

            case ADD_GOODS_READY:
                return (byte) 0x61;
            case ADD_GOODS:
                return (byte) 0x62;
            case GET_BARCODER:
                return (byte) 0x63;


            case GET_VERSIONS://((byte) 0x99, "获取版本号"):
                return (byte) 0x99;
            default:
                return 0;
        }
    }

    }
