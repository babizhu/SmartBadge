package net.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * user         LIUKUN
 * time         2014-6-6 19:05
 */
public interface IHandler{
    void run( ChannelHandlerContext ctx );

}
