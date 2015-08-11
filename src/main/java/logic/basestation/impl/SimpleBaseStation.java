package logic.basestation.impl;

import com.google.common.collect.Maps;
import logic.pojo.LabelInfo;
import logic.pojo.StationFrameData;

import java.util.Map;

/**
 * Created by liu_k on 2015/8/10.
 * 比较简单的基站算法
 */
public class SimpleBaseStation extends AbstractBaseStation{
    /**
     *long  标签的唯一标识，用于快速定位
     */
    Map<Long,LabelInfo> labels = Maps.newHashMap();

    @Override
    public void refresh( StationFrameData frameData ){

        labels.clear();
        for( LabelInfo label : frameData.getLabelInfos() ) {
            labels.put( label.getId(), label );
        }
    }

    @Override
    public byte getRssiById( long labelId ){

        LabelInfo labelInfo = labels.get( labelId );
        return labelInfo == null ? 0 : labelInfo.getRssi();
    }
}
