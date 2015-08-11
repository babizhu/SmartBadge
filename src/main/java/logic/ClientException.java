package logic;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by   liu_k
 * Time         2015/8/10 17:25
 * 需要通知到到客户端的异常
 */



@EqualsAndHashCode(callSuper = true)

@Data
public class ClientException extends RuntimeException{

    private final ErrorCode code;
    private String desc;

    public ClientException( ErrorCode code, String desc ){
        this.code = code;
        this.desc = desc;
    }

    public ClientException( ErrorCode code ){
        this.code = code;

    }

}