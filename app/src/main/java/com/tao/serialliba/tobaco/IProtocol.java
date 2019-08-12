package com.tao.serialliba.tobaco;


/**
 * 烟草机协议
 */

public interface IProtocol {
    byte[] head = new byte[]{(byte) 0xEE, 0x55};
    byte[] end = new byte[]{(byte) 0x0D, 0x0A};
    // 生成校验帧
//    byte checkFrame(byte[] data);
    //  匹配命令代码
//    CommandContentType matchCmdCode(byte data);
    // 匹配结果代码
    //提取数据
//    byte[] extractData(byte[] data);
    // 生成指令 
    


}
