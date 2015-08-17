package net;

import cfg.ServerCfg;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liu_kun on 2015/8/6.
 *
 */
public class SmartBadgeServer{

    private static Logger logger = LoggerFactory.getLogger( SmartBadgeServer.class );

    public ChannelFuture run(){


        EventLoopGroup bossGroup = new NioEventLoopGroup( 1 );
        EventLoopGroup workerGroup = new NioEventLoopGroup( 5 );
        ChannelFuture future = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group( bossGroup, workerGroup )
                    .channel( NioServerSocketChannel.class )
                    .childHandler( new SmartBadgeServerInitializer() )
                    .option( ChannelOption.SO_BACKLOG, 128 )


                    .childOption( ChannelOption.SO_KEEPALIVE, false );


            // Bind and start to accept incoming connections.
            future = b.bind( ServerCfg.PORT ).sync(); // (7)



            logger.debug( "服务器开始启动，端口：" + ServerCfg.PORT );
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            future.channel().closeFuture().sync();


        } catch( InterruptedException e ) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        return future;
    }

    public static void main( String[] args ){
        new SmartBadgeServer().run();
    }

}
