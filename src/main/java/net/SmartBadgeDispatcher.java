package net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import logic.basestation.BaseStationManager;
import logic.pojo.CommandBase;

/**
 * Created by liu_k on 2015/8/6.
 */
public class SmartBadgeDispatcher extends SimpleChannelInboundHandler<CommandBase>{
    @Override
    protected void messageReceived( ChannelHandlerContext ctx, CommandBase commandBase ) throws Exception{

//        System.out.println(commandBase);

        switch( commandBase.getCommandId() ){
            case 5001:
                BaseStationManager.INSTANCE.receive( ctx, commandBase.getData() );
        }
    }
}
