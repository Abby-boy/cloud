package com.atguigu.cloud.resp;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class R<T> {

    @Schema(description = "状态码")
    private String code;
    @Schema(description = "消息")
    private String msg;
    @Schema(description = "数据")
    private T data;
    @Schema(description = "时间戳")
    private long timestamp;

    private R(){
        this.timestamp = System.currentTimeMillis();
    }

    public static<T> R<T> success(T data){
        R resultR = new R<>();
        resultR.setCode(ReturnCodeEnum.RC200.getCode());
        resultR.setMsg(ReturnCodeEnum.RC200.getMsg());
        resultR.setData(data);
        return resultR;
    }

    public static<T> R<T> fail(String code,String msg){
        R resultR = new R<>();
        resultR.setCode(code);
        resultR.setMsg(msg);
        return resultR;
    }

    public static<T> R<T> fail(){
        R resultR = new R<>();
        return resultR;
    }

}
