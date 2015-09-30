package net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by   liu_k
 * Time         2015/8/12 10:58
 */
public class ReceiveBaseStationCmdHandler implements IHandler{ //extends AbstractHandler{

    private static Logger logger = LoggerFactory.getLogger( ReceiveBaseStationMsgHandler.class );
//    private LabelInfo[] labelInfos;

    public ReceiveBaseStationCmdHandler( ByteBuf data ){

        parse( data );
    }

    private void parse( ByteBuf data ){
        logger.debug( "收到一条应答指令，内容:" + ByteBufUtil.hexDump( data ) );
    }

    @Override
    public ByteBuf run( ChannelHandlerContext ctx ){

//        // BaseStationManager.INSTANCE.receiveBaseStationMsg( ctx.channel().remoteAddress(), labelInfos );
//        StringBuilder sb = new StringBuilder();
//        for( LabelInfo labelInfo : labelInfos ) {
//            sb.append( getRemoteIp( ctx ) ).append( "," )
//                    .append( Long.toHexString( labelInfo.getId() ) ).append( "," )
//                    .append( labelInfo.getRssi() ).append( "," )
//                    .append( labelInfo.getPower() ).append( " " );
//        }
//        String responseBody = PostRssiToWebServer.INSTANCE.sendToWebServer( sb.toString() );
//
//        JSONObject jsonObject  = (JSONObject) JSON.parse( responseBody );
//        String  command = jsonObject.getString( "Command" );
//        //System.out.println( command);
//        logger.debug( responseBody );
//        //buildResponse( ctx );
//        return buildResponse(  );
        return null;
    }


    /**
     * 构建业务内容返回的数据内容，包括包id，以及实际内容
     *
     * @return
     *  包括包id以及包内容的一个bytebuf
     */
    private ByteBuf buildResponse( ){
        return null;
    }




}
