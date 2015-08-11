package logic.basestation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import logic.pojo.LabelInfo;
import logic.pojo.StationFrameData;

import java.util.List;
import java.util.Map;

/**
 * Created by liu_k on 2015/8/10.
 */
public enum  BaseStationManager{
    INSTANCE;

    private Map<Integer,IBaseStation> stationMap = Maps.newHashMap();
    private List<LabelInfo> labelInfos = Lists.newArrayList();

    public void recordLabelPosition( LabelInfo label ){

        for( IBaseStation station : stationMap.values() ) {
            List<Byte> list = Lists.newArrayList();
            byte rssi = station.getRssiById( label.getId() );
            if( rssi != 0 ){
                list.add( rssi );
            }


        }


    }

    public void receive( ChannelHandlerContext ctx, ByteBuf data ){

        StationFrameData frameData = new StationFrameData( 100, data );
//        byte foot = data.readByte();
        System.out.println( "来自" + ctx.channel().remoteAddress() + "收到的数据：");
        System.out.println( frameData );

    }
}
