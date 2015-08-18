package net.http;

import cfg.ServerCfg;
import org.nutz.http.Http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by   liu_k
 * Time         2015/8/17 18:14
 */
public class Test{
    public static void main( String[] args ) throws InterruptedException{
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put( "Data", "192.168.1.98,2950513331973914624,-87,100" );
        //parms.put( "website", Nutz.version() );
        for( int i = 0; i < 10000000; i++ ) {


//            Thread.currentThread().sleep( 1000 );
        String response = Http.post( ServerCfg.WEB_IP,
                parms,
                5 * 1000 ); // 可以同时设置超时时间

        // 该post的返回值是"version: #{params[:version]}, website: #{params[:website]}"
        System.out.println( i + " " + response );
        }
    }
}
