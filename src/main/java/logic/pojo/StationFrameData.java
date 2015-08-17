package logic.pojo;

import com.bbz.tool.time.TimeUtil;
import io.netty.buffer.ByteBuf;
import logic.ClientException;
import logic.ErrorCode;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Created by liu_k on 2015/8/6.
 * 基站(网络)上传的一帧数据
 *
 */

@Data
public class StationFrameData{
    private String          ip;
    private LabelInfo[]     labelInfos;
    private long            time;



    public StationFrameData( String ip, ByteBuf data ){
        this.ip = ip;
        byte labelCount = data.readByte();
        if( labelCount <= 0 ){
            throw new ClientException( ErrorCode.INVALID_REQUEST, "收到的标签数量为负数：" + labelCount );
        }

        labelInfos = new LabelInfo[labelCount];
        for( int i = 0; i < labelCount; i++ ) {

            long labelId = data.readLong();
            byte rssi = data.readByte();
            byte power = data.readByte();
            LabelInfo labelInfo = new LabelInfo( labelId, rssi, power);
            labelInfos[i] = labelInfo;
        }

        time = System.currentTimeMillis();


    }

    @Override
    public String toString(){
        return "StationFrameData{" +
                "id=" + ip +
                ", labelInfos=" + Arrays.toString( labelInfos ) +
                ", time=" + new DateTime( time ).toString( TimeUtil.DATA_FORMAT_STR ) +
                '}';
    }


}
