package net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.http.PostRssiToWebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liu_k on 2015/10/14.
 * 用户通过胸牌的消费指令
 */
public class ConsumeHandler implements IHandler{
    private static Logger logger = LoggerFactory.getLogger( ConsumeHandler.class );
    public ConsumeHandler( ByteBuf data ){
    }

    @Override
    public ByteBuf run( ChannelHandlerContext ctx ){
        StringBuilder sb = new StringBuilder();

        PostRssiToWebServer.INSTANCE.sendToWebServer( sb.toString() );

        return null;
    }
}
