package logic.pojo;

import lombok.Data;

/**
 * Created by liu_kun on 2015/8/10.
 * 基站的位置信息
 */

@Data


public class Position{

    /**
     * 基站所处的地理位置坐标
     * x，y代表平面所处的横纵坐标
     * z，一般代表楼层
     *
     */

    private float x;
    private float y;
    private float z;
}

