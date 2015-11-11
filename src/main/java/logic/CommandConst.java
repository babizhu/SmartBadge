package logic;

/**
 * Created by   liu_k
 * Time         2015/8/17 16:40
 */
public class CommandConst{
    //开门
    public static final int OPEN_DOOR = 0x157D;//5501


    public static final int RECEIVE_MSG_FROM_BASE_STATION = 0x1389;//5001;最基础的定位信息(rssi信息)
    public static final int RECEIVE_OPEN_DOOR_FROM_BASE_STATION = 0x157D;//接收到基站的开门应答指令
    public static final int RECEIVE_OTHER_CMD_FROM_BASE_STATION = 0x157E;//接收到基站的其他功能的应答指令
    public static final int CONSUME = 0x157F;//由胸牌传入的用户消费指令

}
