package client;

import cfg.ServerCfg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Created by   liu_k
 * Time         2015/8/18 18:01
 * 一个测试智能胸牌server的客户端
 */
public class SmartBadgeClient{

    public static void main( String[] args ) throws InterruptedException{
        String host = ServerCfg.IP;
        int port = ServerCfg.PORT;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group( workerGroup ); // (2)
            b.channel( NioSocketChannel.class ); // (3)
            b.option( ChannelOption.SO_KEEPALIVE, true ); // (4)
            b.handler( new ChannelInitializer<SocketChannel>(){
                @Override
                public void initChannel( SocketChannel ch ) throws Exception{
                    ch.pipeline().addLast( new SmartBadgeClientHandler() );
                }
            } );

            // Start the client.
            ChannelFuture f = b.connect( host, port ).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }


}
