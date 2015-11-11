package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import logic.NetConst;
import net.handler.ReceiveContain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by liu_kun on 2015/8/7.
 * 协议解码器
 */
public class SmartBadgeDecoder extends ByteToMessageDecoder{
    private static Logger logger = LoggerFactory.getLogger( SmartBadgeDecoder.class );

    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception{
        logger.debug( ctx.channel().remoteAddress() + "接入了" );
    }

    protected void decode( ChannelHandlerContext ctx, ByteBuf in, List<Object> out ) throws Exception{

//        System.out.println( ByteBufUtil.hexDump( in ));
        if( in.readableBytes() < 1 + 2 ) {  //1字节包头+2字节长度

            return;
        }
        in.markReaderIndex();
        byte head = in.readByte(); //-56
        if( head != NetConst.NET_HEAD ) {
            logger.debug( "收到了错误的head信息：" + head );
            return;
        }
        int dataLen = in.readShort();

        logger.debug( ctx.channel().remoteAddress() + " 需要长度:" + dataLen + ",实际长度：" + in.readableBytes() );
//        if( dataLen > 30 ){
//            in.resetReaderIndex();
//            logger.debug( ctx.channel().remoteAddress() + " " + ByteBufUtil.hexDump( in ) );
//            //恢复游标，方便下面继续处理
//            //in.readerIndex( 3 );
//            in.readByte();
//            in.readShort();
//        }

        if( in.readableBytes() < dataLen ) {
            in.resetReaderIndex();
            return;
        }

        out.add( new ReceiveContain( head, dataLen, in ) );

//        out.add( HandlerManager.INSTANCE.getHandler( ctx, head, in ) );
    }


    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception{
//        super.exceptionCaught( ctx, cause );
        logger.error( "EXCEPTION:" + ctx.channel().remoteAddress() + cause.toString() );
        ctx.close();
    }
}
