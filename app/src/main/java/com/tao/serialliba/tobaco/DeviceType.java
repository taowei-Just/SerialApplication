package com.tao.serialliba.tobaco;

public enum DeviceType {


    LED_BAR((byte) 0x00, " LED灯条"),

    LED_REBBON((byte) 0x01, "LED灯带"),

    ALERTOR((byte) 0x02, "报警器"),

    SCAN((byte) 0x03, " 扫描器"),

    DOOR((byte) 0x04, "大门锁控"),

    AISLE_MOTOR((byte) 0x05, "货道电机");

    byte data;
    String detail;

    public byte getData() {
        return data;
    }

    DeviceType(byte data, String detail) {
        this.data = data;
        this.detail = detail;
    }

    DeviceType type(byte data) {
        switch (data) {
            case (byte) 0x00:
                return LED_BAR;
            case (byte) 0x01:
                return LED_REBBON;
            case (byte) 0x02:
                return ALERTOR;
            case (byte) 0x03:
                return SCAN;
            case (byte) 0x04:
                return DOOR;
            case (byte) 0x05:
                return AISLE_MOTOR;

            default:
                return null;
        }
    }

}
