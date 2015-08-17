package logic.basestation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import logic.pojo.LabelInfo;

import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by liu_k on 2015/8/10.
 */
public enum  BaseStationManager{
    INSTANCE;

    private Map<String, IBaseStation> stationMap = Maps.newHashMap();
    private List<LabelInfo> labelInfos = Lists.newArrayList();

    public void recordLabelPosition( LabelInfo label ){

        for( IBaseStation station : stationMap.values() ) {
            List<Byte> list = Lists.newArrayList();
            byte rssi = station.getRssiById( label.getId() );
            if( rssi != 0 ) {
                list.add( rssi );
            }


        }


    }


    public void receiveBaseStationMsg( SocketAddress ip, LabelInfo[] labelInfos ){
        System.out.println( "来自" + ip + "的数据：" );
        System.out.println( Arrays.toString(labelInfos) );
        System.out.println( "===================================================================");

        IBaseStation station = stationMap.get( ip );
        if( station != null ){
            station.refresh( labelInfos );
        }


    }
}