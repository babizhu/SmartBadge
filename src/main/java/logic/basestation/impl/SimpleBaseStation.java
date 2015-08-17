package logic.basestation.impl;

import com.google.common.collect.Maps;
import logic.pojo.LabelInfo;

import java.util.Map;

/**
 * Created by liu_k on 2015/8/10.
 * 比较简单的基站算法
 */
public class SimpleBaseStation extends AbstractBaseStation{
    /**
     * long  标签的唯一标识，
     * labelInfo 标签信息，滤波由基站完成
     */
    Map<Long,LabelInfo> labels = Maps.newHashMap();

//    @Override
//    public void refresh( StationFrameData frameData ){
//
//        //labels.clear();
//        for( LabelInfo label : frameData.getLabelInfos() ) {
//            List<LabelInfo> labelQueue = labels.get( label.getId() );
//            if( labelQueue != null ){
//                if( labelQueue.size() >= 10 ) {
//                    labelQueue.remove( 9 );
//                }
//                labelQueue.add( 0,label );
//            }else {
//                labelQueue = new ArrayList<>(  );
//                labelQueue.add( label );
//                labels.put( label.getId(),labelQueue );
//            }
//        }
//        //Queue<LabelInfo> labelQueue = labels.get( labelId );
//    }



    @Override
    public byte getRssiById( long labelId ){

        return labels.containsKey( labelId )? labels.get( labelId ).getRssi() : 0;
    }

    @Override
    public void refresh( LabelInfo[] labelInfos ){
        labels.clear();
        for( LabelInfo labelInfo : labelInfos ) {
            labels.put( labelInfo.getId(), labelInfo );
        }
    }
}
