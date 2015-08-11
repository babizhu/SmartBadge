package logic.pojo;

import io.netty.buffer.ByteBuf;
import logic.ClientException;
import logic.ErrorCode;
import lombok.Data;

/**
 * Created by liu_k on 2015/8/7.
 */
@Data
public class CommandBase{
    private final byte head;
    private final int commandId;
    private final ByteBuf data;
    private final short token;
    private final byte foot;


    public CommandBase( byte head, ByteBuf buf ){
        this.head = head;
        this.commandId = buf.readShort();

        //byte[] dataTemp = new byte[buf.readableBytes() - 3];//3 for token(2) and foot(1)
        //buf.readBytes( dataTemp );
        data = buf.readSlice( buf.readableBytes() - 3 );//3 for token(2) and foot(1)
//        System.out.println(ByteBufUtil.hexDump( buf ));
        token = buf.readShort();

        foot = buf.readByte();
        buf.retain();
        if( checkToken() ) {
            throw new ClientException( ErrorCode.SIGNATURE_ERROR );
        }
    }

    private boolean checkToken(){

          return true;
    }
}
