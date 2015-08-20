package client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import logic.CommandConst;
import logic.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by   liu_k
 * Time         2015/8/18 18:09
 */
public class SmartBadgeClientHandler extends ChannelHandlerAdapter{

    public static final Logger logger = LoggerFactory.getLogger( SmartBadgeClientHandler.class );
    private final ByteBuf msg;

    private ChannelHandlerContext ctx;
    /**
     * Creates a client-side handler.
     */
    public SmartBadgeClientHandler() {
        msg = Unpooled.buffer(  );
        buildMsg();



    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        sendMsg1();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //ctx.write(msg);
        ByteBuf buf = (ByteBuf) msg;
        logger.debug( ByteBufUtil.hexDump( buf ) );

        try {
            Thread.sleep( 1000 );
            //msg = ctx.alloc().buffer();
            sendMsg1();


        } catch( InterruptedException e ) {
            e.printStackTrace();
        }finally {
            buf.release();
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    private void buildMsg(){
        msg.writeByte( Const.NET_HEAD );//head
        msg.writeShort( 16 );//长度
        msg.writeShort( CommandConst.RECEIVE_BASESTATION_MSG );
        msg.writeByte( 1 );//循环数量
//        msg.writeLong( 0X28F2543E315C0000 );
//        msg.writeLong( 2950513331973914624 );
        msg.writeLong( 1001 );
        msg.writeByte( -49 );
        msg.writeByte( 100 );
        msg.writeShort( 0XA7A7 );
        msg.writeByte( 0XFF );


    }
    private void sendMsg1(){
        msg.retain();
        msg.markWriterIndex();
        ctx.writeAndFlush( msg );
        msg.resetWriterIndex();

    }
    private void sendMsg(){
        msg.retain();
        //msg.markWriterIndex();
        logger.debug( String.valueOf( msg.writerIndex() ) );
        msg.writeByte( Const.NET_HEAD );//head
        msg.writeShort( 16 );//长度
        msg.writeShort( CommandConst.RECEIVE_BASESTATION_MSG );
        msg.writeByte( 1 );//循环数量
//        msg.writeLong( 0X28F2543E315C0000 );
//        msg.writeLong( 2950513331973914624 );
        msg.writeLong( 1001 );
        msg.writeByte( -49 );
        msg.writeByte( 100 );
        msg.writeShort( 0XA7A7 );
        msg.writeByte( 0XFF );

        logger.debug( String.valueOf( msg.writerIndex() ) );

        ctx.writeAndFlush( msg );
        logger.debug( String.valueOf( msg.writerIndex() ) );

        //msg.resetWriterIndex();

    }
}
