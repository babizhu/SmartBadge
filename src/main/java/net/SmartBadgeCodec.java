package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import logic.pojo.CommandBase;

import java.util.List;

/**
 * Created by liu_k on 2015/8/7.
 */
public class SmartBadgeCodec extends ByteToMessageCodec<CommandBase>{
    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception{
        System.out.println( ctx.channel().remoteAddress() + "接入了" );
    }

    @Override
    protected void encode( ChannelHandlerContext ctx, CommandBase commandBase, ByteBuf byteBuf ) throws Exception{

    }

    @Override
    protected void decode( ChannelHandlerContext ctx, ByteBuf in, List<Object> out ) throws Exception{

//        System.out.println(ByteBufUtil.hexDump( in ));
        if( in.readableBytes() < 1 + 2 ) {  //包头+长度

            return;
        }
        in.markReaderIndex();
        byte head = in.readByte();
        int dataLen = in.readShort();
        System.out.println( ctx.channel().remoteAddress() + " 需要长度:" + dataLen + ",实际长度：" + in.readableBytes() );
        if( in.readableBytes() < dataLen ) {
            in.resetReaderIndex();
            return;
        }

        out.add( new CommandBase( head, in ) );

    }


    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception{
//        super.exceptionCaught( ctx, cause );
        System.err.println( "EXCEPTION:" + ctx.channel().remoteAddress() + cause.toString() );
        ctx.close();
    }
}
