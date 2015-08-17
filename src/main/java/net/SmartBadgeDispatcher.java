package net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import logic.ClientException;
import logic.ErrorCode;
import net.handler.HandlerContain;
import net.handler.HandlerManager;
import net.handler.IHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liu_k on 2015/8/6.
 */
public class SmartBadgeDispatcher extends SimpleChannelInboundHandler<HandlerContain>{
    private static Logger logger = LoggerFactory.getLogger( SmartBadgeDispatcher.class );

    @Override
    protected void messageReceived( ChannelHandlerContext ctx, HandlerContain contain ) throws Exception{

        IHandler handler = HandlerManager.INSTANCE.getHandler( contain );
        if( handler == null ){
            throw new ClientException( ErrorCode.HANDLER_NOT_FOUND, "handler未找到:" + contain.getHandlerId() );
        }
        try {

            handler.run( ctx );
        }
        catch( Exception e ){
            logger.error( e.getMessage() );
        }
        finally {
            contain.getData().release();
        }


    }


}
