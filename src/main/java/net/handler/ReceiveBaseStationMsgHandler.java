package net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import logic.ClientException;
import logic.CommandConst;
import logic.ErrorCode;
import logic.pojo.LabelInfo;
import net.http.PostRssiToWebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by   liu_k
 * Time         2015/8/12 10:58
 */
public class ReceiveBaseStationMsgHandler implements IHandler{ //extends AbstractHandler{

    private static Logger logger = LoggerFactory.getLogger( ReceiveBaseStationMsgHandler.class );
    private LabelInfo[] labelInfos;

    public ReceiveBaseStationMsgHandler( ByteBuf data ){

        parse( data );
    }

    private void parse( ByteBuf data ){
        byte labelCount = data.readByte();
        if( labelCount <= 0 ) {
            throw new ClientException( ErrorCode.INVALID_REQUEST, "收到的标签数量为负数：" + labelCount );
        }

        labelInfos = new LabelInfo[labelCount];
        for( int i = 0; i < labelCount; i++ ) {

            long labelId = data.readLong();
            byte rssi = data.readByte();
            byte power = data.readByte();

            LabelInfo labelInfo = new LabelInfo( labelId, rssi, power );
            labelInfos[i] = labelInfo;
        }
    }

    @Override
    public ByteBuf run( ChannelHandlerContext ctx ){

        // BaseStationManager.INSTANCE.receiveBaseStationMsg( ctx.channel().remoteAddress(), labelInfos );
        StringBuilder sb = new StringBuilder();
        for( LabelInfo labelInfo : labelInfos ) {
            sb.append( getRemoteIp( ctx ) ).append( "," )
                    .append( labelInfo.getId() ).append( "," )
                    .append( labelInfo.getRssi() ).append( "," )
                    .append( labelInfo.getPower() ).append( " " );
        }
        String responseBody = PostRssiToWebServer.INSTANCE.sendToWebServer( sb.toString() );

        //buildResponse( ctx );
        return buildResponse(  );
    }


    /**
     * 构建业务内容返回的数据内容，包括包id，以及实际内容
     *
     * @return
     *  包括包id以及包内容的一个bytebuf
     */
    private ByteBuf buildResponse( ){
        ByteBuf buf = Unpooled.buffer();

        buf.writeShort( CommandConst.OPEN_DOOR );

//        logger.debug( ByteBufUtil.hexDump( buf ) );
        return buf;
    }

    private String getRemoteIp( ChannelHandlerContext ctx ){
        String ip = ctx.channel().remoteAddress().toString();
        ip = ip.substring( 1, ip.indexOf( ":" ) );
        return ip;
    }

}
