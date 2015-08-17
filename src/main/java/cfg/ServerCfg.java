package cfg;

/**
 * Created by liu_k on 2015/8/6.
 */

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * user         LIUKUN
 * time         2014-4-7 13:38
 *
 * 服务器的整体配置文件
 *
 */

public class ServerCfg{

    /**
     * 监听ip
     */
    public static final String          IP;

    /**
     * 监听端口
     */
    public static final int             PORT;

    /**
     * 管理端口
     */
    public static final int             GM_PORT;

    /**
     * 上传web地址
     */
    public static final String          WEB_IP;

    static {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream( new FileInputStream( "resources/server.properties" ) );
            prop.load( in );

        } catch( IOException e ) {
            e.printStackTrace();
        }
        IP = prop.getProperty( "ip" ).trim();
        PORT = Integer.parseInt( prop.getProperty( "port" ).trim() );
        GM_PORT = Integer.parseInt( prop.getProperty( "gmPort" ).trim() );
        WEB_IP = prop.getProperty( "webIp" ).trim();

    }

    public static void main( String[] args ){
        System.out.println( ServerCfg.GM_PORT );
    }
}
