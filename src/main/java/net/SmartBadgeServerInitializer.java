package net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by liu_k on 2015/8/6.
 */
public class SmartBadgeServerInitializer extends ChannelInitializer<SocketChannel>{
        //    private static final ProtobufVarint32FrameDecoder VARINT32_FRAME_DECODER = new ProtobufVarint32FrameDecoder();


            @Override
    protected void initChannel( SocketChannel ch ) throws Exception{
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast( "codec", new ServerCodec() );
                pipeline.addLast( "handler", new SmartBadgeDispatcher() );

            }
        }
