package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import logic.ClientException;
import logic.Const;
import logic.ErrorCode;
import net.handler.HandlerManager;
import net.handler.IHandler;
import net.handler.ReceiveContain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liu_k on 2015/8/6.
 */
public class SmartBadgeDispatcher extends SimpleChannelInboundHandler<ReceiveContain>{
    private static final Logger logger = LoggerFactory.getLogger( SmartBadgeDispatcher.class );

    @Override
    protected void messageReceived( ChannelHandlerContext ctx, ReceiveContain contain ) throws Exception{

        IHandler handler = HandlerManager.INSTANCE.getHandler( contain );
        if( handler == null ){
            throw new ClientException( ErrorCode.HANDLER_NOT_FOUND, "handler未找到:" + contain.getHandlerId() );
        }
        try {

            ByteBuf responseBuf = handler.run( ctx );
            if( responseBuf != null ){
                ctx.writeAndFlush( buildFullResponse( ctx, responseBuf ) );
            }
        }
        catch( Exception e ){
            logger.error( e.getMessage() );
        }
        finally {
            contain.getData().release();
        }


    }

    /**
     * 把业务逻辑返回的数据内容，包装到完整的包中
     * 返回的数据内容包括 包命令 + 包内容，不包括token以及foot
     *
     * @param ctx
     * @param responseBuf
     * @return
     */
    private ByteBuf buildFullResponse( ChannelHandlerContext ctx, ByteBuf responseBuf ){

        ByteBuf fullResponse = ctx.alloc().buffer();
        fullResponse.writeByte( Const.NET_HEAD1 );//头
        fullResponse.writeShort( responseBuf.readableBytes() - 2 + 3 );//2 for 包号 | 3 for token ，foot
        fullResponse.writeBytes( responseBuf );
        //fullResponse.writeShort( CommandConst.OPEN_DOOR );
        fullResponse.writeShort( Const.NET_TOKEN );
        fullResponse.writeByte( Const.NET_FOOT );
        return fullResponse;
    }


}
