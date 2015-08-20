package net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by liu_k on 2015/8/6.
 */
public class SmartBadgeServerInitializer extends ChannelInitializer<SocketChannel>{


            @Override
    protected void initChannel( SocketChannel ch ) throws Exception{
                ChannelPipeline pipeline = ch.pipeline();

//                pipeline.addLast( "codec", new SmartBadgeCodec1() );
                pipeline.addLast( "rawNetData", new PrintRawNetData() );
                pipeline.addLast( "encoder", new SmartBadgeDecoder() );
                pipeline.addLast( "decoder", new SmartBadgeEncoder() );
                pipeline.addLast( "handler", new SmartBadgeDispatcher() );

            }
        }
