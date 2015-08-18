package net.http;

import cfg.ServerCfg;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.nutz.http.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by   liu_kun
 * Time         2015/8/14 10:51
 */
public enum PostRssiToWebServer{
    INSTANCE;
    private static Logger logger = LoggerFactory.getLogger( PostRssiToWebServer.class );

    public String sendToWebServer( String content ){
        logger.info( content );
//        CloseableHttpClient httpclient = HttpClients.createDefault();
////        String url = "http://192.168.1.217/IndoorLocation/PostData.action";
//
//        String responseBody = null;
//
//        try {
//            HttpUriRequest request = RequestBuilder.post()
//                    .setUri( new URI( ServerCfg.WEB_IP ) )
//                    .addParameter( "Data", content )
//                    .build();
//
//            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
//
//                @Override
//                public String handleResponse(
//                        final HttpResponse response) throws ClientProtocolException, IOException {
//                    int status = response.getStatusLine().getStatusCode();
//                    if (status >= 200 && status < 300) {
//                        HttpEntity entity = response.getEntity();
//                        return entity != null ? EntityUtils.toString( entity ) : null;
//                    } else {
//                        throw new ClientProtocolException("Unexpected response status: " + status);
//                    }
//                }
//
//            };
//            responseBody = httpclient.execute(request, responseHandler);
//            logger.info( responseBody );
//        } catch( URISyntaxException e ) {
//            logger.error( e.getMessage() );
//        } catch( ClientProtocolException e ) {
//            logger.error( e.getMessage() );
//        } catch( IOException e ) {
//            logger.error( e.getMessage() );
//        }
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put( "Data", content );
        String responseBody = null;
        //parms.put( "website", Nutz.version() );
        try {

            responseBody = Http.post( ServerCfg.WEB_IP,
                    parms,
                    5 * 1000 ); // 可以同时设置超时时间

        } catch( Exception e ) {
            logger.error( "发送内容 " + content + "到" + ServerCfg.WEB_IP + "的时候出现了异常 " + e.toString() );
            throw e;
        }

        return responseBody;


    }


    public static void main( String[] args ) throws IOException, URISyntaxException{

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://192.168.1.217/IndoorLocation/PostData.action";

        HttpUriRequest request = RequestBuilder.post()
                .setUri( new URI( url ) )
                .addParameter( "Data", "111,222,333 444,555,666" )
                .build();

        ResponseHandler<String> responseHandler = new ResponseHandler<String>(){

            @Override
            public String handleResponse(
                    final HttpResponse response ) throws ClientProtocolException, IOException{
                int status = response.getStatusLine().getStatusCode();
                if( status >= 200 && status < 300 ) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString( entity ) : null;
                } else {
                    throw new ClientProtocolException( "Unexpected response status: " + status );
                }
            }

        };
        String responseBody = httpclient.execute( request, responseHandler );
        System.out.println( responseBody );
    }
}
