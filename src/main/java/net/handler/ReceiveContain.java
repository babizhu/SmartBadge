package net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import logic.ClientException;
import logic.Const;
import logic.ErrorCode;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liu_k on 2015/8/7.
 * 包含客户端上传内容数据的一个基础容器，包含所有handler的通用的底层基础信息，例如
 * 包号，token等等
 *
 *
 * 其中data字段是需要进一步关注d内容
 */
@Data
public class ReceiveContain{
        private static Logger logger = LoggerFactory.getLogger( ReceiveContain.class );
    private final byte head;
    private final int handlerId;
    private final ByteBuf data;
    private final short token;
    private final byte foot;


    public ReceiveContain( byte head, int dataLen, ByteBuf buf ){
        this.head = head;

        //System.out.println( ByteBufUtil.hexDump( buf ));
        this.handlerId = buf.readShort();
        data = buf.readSlice( dataLen - 5 );//5 for handlerId（2） token(2) and foot(1)
//        System.out.println( ByteBufUtil.hexDump( buf ));
        token = buf.readShort();//-22617


        foot = buf.readByte();//-1
        if( foot != Const.NET_FOOT ){
            logger.error( "错误的包尾" + foot );
        }

        buf.retain();
        if( !isTokenValid() ) {
            throw new ClientException( ErrorCode.SIGNATURE_ERROR );
        }
    }

    @Override
    public String toString(){
        return "ReceiveContain{" +
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
