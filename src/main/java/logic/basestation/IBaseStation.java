package logic.basestation;

import logic.pojo.LabelInfo;

/**
 * Created by liu_kun on 2015/8/10.
 * 基站信息的接口类
 */
public interface IBaseStation{






    /**
     * 根据标签id，获取此标签在此基站的信号强度，如果没有，返回0
     *
     * @param labelId           标签的唯一id
     *
     * @return                  c标签信息，如果不存在返回null
     */
    public byte getRssiById( long labelId );

    /**
     * 刷新此基站的数据
     * @param labelInfos       基站上传的新数据,包含此基站收到的所有标签信息
     */
    void refresh( LabelInfo[] labelInfos );
}
