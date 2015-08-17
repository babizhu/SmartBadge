package net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import logic.ClientException;
import logic.ErrorCode;
import logic.pojo.LabelInfo;
import net.http.PostRssiToWebServer;

/**
 * Created by   liu_k
 * Time         2015/8/12 10:58
 */
public class ReceiveBaseStationMsgHandler implements IHandler{ //extends AbstractHandler{


    private LabelInfo[] labelInfos;

    public ReceiveBaseStationMsgHandler( ByteBuf data ){

        parse( data );
    }

    private void parse( ByteBuf data ){
        byte labelCount = data.readByte();
        if( labelCount <= 0 ){
            throw new ClientException( ErrorCode.INVALID_REQUEST, "收到的标签数量为负数：" + labelCount );
        }

        labelInfos = new LabelInfo[labelCount];
        for( int i = 0; i < labelCount; i++ ) {

            long labelId = data.readLong();
            byte rssi = data.readByte();
            byte power = data.readByte();

            LabelInfo labelInfo = new LabelInfo( labelId, rssi, power);
            labelInfos[i] = labelInfo;
        }
    }

    @Override
    public void run( ChannelHandlerContext ctx ){

       // BaseStationManager.INSTANCE.receiveBaseStationMsg( ctx.channel().remoteAddress(), labelInfos );
        StringBuilder sb = new StringBuilder(  );
        for( LabelInfo labelInfo : labelInfos ) {
            sb.append( getRemoteIp( ctx ) ).append( "," )
                    .append( labelInfo.getId() ).append( ",")
                    .append( labelInfo.getRssi() ).append( "," )
                    .append( labelInfo.getPower() ).append( " " );
        }
        PostRssiToWebServer.INSTANCE.sendToWebServer( sb.toString() );
    }

    private String getRemoteIp( ChannelHandlerContext ctx ){
        String ip = ctx.channel().remoteAddress().toString();
        ip = ip.substring( 1, ip.indexOf( ":" ) );
        return ip;
    }

}
