package net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import logic.ClientException;
import logic.ErrorCode;
import lombok.Data;

/**
 * Created by liu_k on 2015/8/7.
 * 包含客户端上传内容数据的一个基础容器，包含所有handler的通用的底层基础信息，例如
 * 包号，token等等
 *
 *
 * 其中data字段是需要进一步关注d内容
 */
@Data
public class HandlerContain{
    private final byte head;
    private final int handlerId;
    private final ByteBuf data;
    private final short token;
    private final byte foot;


    public HandlerContain( byte head, int dataLen, ByteBuf buf ){
        this.head = head;

        //System.out.println( ByteBufUtil.hexDump( buf ));
        this.handlerId = buf.readShort();
        data = buf.readSlice( dataLen - 5 );//5 for handlerId（2） token(2) and foot(1)
//        System.out.println( ByteBufUtil.hexDump( buf ));
        token = buf.readShort();//-22617


        foot = buf.readByte();//-1

        buf.retain();
        if( !isTokenValid() ) {
            throw new ClientException( ErrorCode.SIGNATURE_ERROR );
        }
    }

    @Override
    public String toString(){
        return "HandlerContain{" +
                "head=" + head +
                ", handlerId=" + handlerId +
                ", data=" + ByteBufUtil.hexDump( data ) +
                ", token=" + token +
                ", foot=" + foot +
                '}';
    }

    private boolean isTokenValid(){

          return true;
    }
}
