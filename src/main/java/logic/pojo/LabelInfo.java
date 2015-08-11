package logic.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by liu_k on 2015/8/6.
 * 单个的标签数据
 */

@Data
@AllArgsConstructor
public class LabelInfo{
    /**
     * 标签的唯一id，此处为mac地址
     */
    private long    id;
    /**
     * 信号强度
     */
    private byte    rssi;


    /**
     * 电量
     */
    private byte    power;

}
