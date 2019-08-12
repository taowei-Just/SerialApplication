package com.tao.serialliba.tobaco;

import com.tao.utilslib.encrypt.ParseSystemUtil;

public class CommandResult {
    
    int flag =0 ;
    // 指令类型
    CmdType cmdType ;
    // 结果类型
    ResultType resultType ;
    // 具体指令
    CommandContentType contentType ;
    // 指令数据
    byte[] data ;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public CmdType getCmdType() {
        return cmdType;
    }

    public void setCmdType(CmdType cmdType) {
        this.cmdType = cmdType;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public CommandContentType getContentType() {
        return contentType;
    }

    public void setContentType(CommandContentType contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "flag=" + flag +
                ", cmdType=" + cmdType +
                ", resultType=" + resultType +
                ", contentType=" + contentType +
                ", data=" + ParseSystemUtil.parseByte2HexStr( data) +
                '}';
    }
}
