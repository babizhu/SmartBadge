package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import logic.Const;

/**
 * Created by   liu_k
 * Time         2015/8/18 17:36
 */
public class SmartBadgeEncoder extends MessageToByteEncoder<ByteBuf>{
    @Override
    protected void encode( ChannelHandlerContext channelHandlerContext, ByteBuf responseBuf, ByteBuf fullResponse ) throws Exception{


        fullResponse.writeByte( Const.NET_HEAD1 );//头
        fullResponse.writeShort( responseBuf.readableBytes() + 3 );//2 for 包号 | 3 for token ，foot
        fullResponse.writeBytes( responseBuf );

        fullResponse.writeShort( Const.NET_TOKEN );
        fullResponse.writeByte( Const.NET_FOOT );

    }
}
