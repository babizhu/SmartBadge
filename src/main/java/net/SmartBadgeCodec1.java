package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import net.handler.HandlerContain;

import java.util.List;

/**
 * Created by liu_k on 2015/8/7.
 *
 */
public class SmartBadgeCodec1 extends ByteToMessageCodec<HandlerContain>{
    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception{
        System.out.println( ctx.channel().remoteAddress() + "接入了");
    }
    @Override
    protected void encode( ChannelHandlerContext ctx, HandlerContain commandBase, ByteBuf byteBuf ) throws Exception{

    }

    @Override
    protected void decode( ChannelHandlerContext ctx, ByteBuf in, List<Object> out ) throws Exception{

        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.readBytes( arr );
        for( byte b : arr ) {
            System.out.print( b + " " );
        }
        System.out.println();

    }

    private boolean checkToken( short token ){
        return false;
    }


    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception{
//        super.exceptionCaught( ctx, cause );
        System.err.println( "EXCEPTION:" + ctx.channel().remoteAddress() + cause.toString() );
    }

}
