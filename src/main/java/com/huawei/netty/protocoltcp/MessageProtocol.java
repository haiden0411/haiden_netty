package com.huawei.netty.protocoltcp;
import lombok.Data;
/**
 * Author：胡灯
 * Date：2020-08-01 9:57
 * Description：<描述>
 */
@Data
public class MessageProtocol
{
    private int len;
    private byte[] content;

}
