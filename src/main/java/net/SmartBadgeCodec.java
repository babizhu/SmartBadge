package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import logic.Const;
import net.handler.HandlerContain;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by liu_kun on 2015/8/7.
 */
public class SmartBadgeCodec extends ByteToMessageCodec<HandlerContain>{
    private static org.slf4j.Logger logger = LoggerFactory.getLogger( SmartBadgeCodec.class );
    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception{
        logger.debug( ctx.channel().remoteAddress() + "接入了" );
    }

    @Override
    protected void encode( ChannelHandlerContext ctx, HandlerContain commandBase, ByteBuf byteBuf ) throws Exception{

    }

    @Override
    protected void decode( ChannelHandlerContext ctx, ByteBuf in, List<Object> out ) throws Exception{

//        System.out.println( ByteBufUtil.hexDump( in ));
        if( in.readableBytes() < 1 + 2 ) {  //包头+长度

            return;
        }
        in.markReaderIndex();
        byte head = in.readByte(); //-56
        if( head != Const.NET_HEAD ){
            logger.debug( "收到了错误的head信息：" + head );
            return;
        }
        int dataLen = in.readShort();

        logger.debug( ctx.channel().remoteAddress() + " 需要长度:" + dataLen + ",实际长度：" + in.readableBytes() );

        if( in.readableBytes() < dataLen ) {
            in.resetReaderIndex();
            return;
        }

        out.add( new HandlerContain( head, dataLen, in ) );

//        out.add( HandlerManager.INSTANCE.getHandler( ctx, head, in ) );
    }


    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception{
//        super.exceptionCaught( ctx, cause );
        logger.error( "EXCEPTION:" + ctx.channel().remoteAddress() + cause.toString() );
        ctx.close();
    }
}
