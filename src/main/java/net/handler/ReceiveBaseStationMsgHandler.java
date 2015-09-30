package net.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import logic.ClientException;
import logic.ErrorCode;
import logic.pojo.LabelInfo;
import net.http.PostRssiToWebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by   liu_k
 * Time         2015/8/12 10:58
 *
 * 处理基站回送的应答信息，
 * 例如：发送给基站开门指令之后，基站回送一个开门指令的应答信息，则在此类中进行处理
 *
 */
public class ReceiveBaseStationMsgHandler implements IHandler{ //extends AbstractHandler{
    private static Logger logger = LoggerFactory.getLogger( ReceiveBaseStationCmdHandler.class );
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
                    .append( Long.toHexString( labelInfo.getId() ) ).append( "," )
                    .append( labelInfo.getRssi() ).append( "," )
                    .append( labelInfo.getPower() ).append( " " );
        }
        String responseBody = PostRssiToWebServer.INSTANCE.sendToWebServer( sb.toString() );
        logger.debug( "从web服务器收到的关于基站"+ getRemoteIp( ctx ) +"的反馈数据为：" + responseBody );

        JSONObject jsonObject  = (JSONObject) JSON.parse( responseBody );
        if( jsonObject != null ){
            String  command = jsonObject.getString( "Command" );
            if( command != null ){
                return buildResponse( command );
            }
        }



        //buildResponse( ctx );
        return null;
    }


    /**
     * 构建业务内容返回的数据内容，包括包id，以及实际内容
     *
     * @return
     *  包括包id以及包内容的一个bytebuf
     */
    private ByteBuf buildResponse( String command ){
        ByteBuf buf = Unpooled.buffer();

        buf.writeShort( Short.parseShort( command ) );

//        logger.debug( ByteBufUtil.hexDump( buf ) );
        return buf;
    }

    private String getRemoteIp( ChannelHandlerContext ctx ){
        String ip = ctx.channel().remoteAddress().toString();
        ip = ip.substring( 1, ip.indexOf( ":" ) );
        return ip;
    }


}
