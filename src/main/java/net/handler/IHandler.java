package net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * user         LIUKUN
 * time         2014-6-6 19:05
 */
public interface IHandler{
    ByteBuf run( ChannelHandlerContext ctx );

}
