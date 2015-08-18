package net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by   liu_k
 * Time         2015/8/18 9:54
 */
public class PrintRawNetData extends ChannelHandlerAdapter{
    public static final Logger logger = LoggerFactory.getLogger( PrintRawNetData.class );
    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception{

        ByteBuf buf = (ByteBuf) msg;
        //buf.markReaderIndex();
        logger.debug( ctx.channel().remoteAddress() + "(" + buf.readableBytes() + ")" + ByteBufUtil.hexDump( buf ) );
        //buf.resetReaderIndex();
        //byte[] bytes = new byte[buf.readableBytes()];
        //buf.readBytes( bytes );
        //buf.resetReaderIndex();
        //buf.readByte();
        super.channelRead( ctx, msg );
    }
}
