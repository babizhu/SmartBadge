package util;

import org.junit.Test;

/**
 * Created by   liu_k
 * Time         2015/8/11 12:20
 */
public class DataTypeChangeHelperTest{

    @Test
    public void testUnsignedByteToInt() throws Exception{
        int i = DataTypeChangeHelper.unsignedByteToInt( (byte) -64 );
        System.out.println( i );
    }

    @Test
    public void testByteToHex() throws Exception{
        byte b = -20;
        System.out.println( DataTypeChangeHelper.byteToHex( b ));
    }

    @Test
    public void testUnsigned4BytesToInt() throws Exception{

    }

    @Test
    public void testShortToByteArray() throws Exception{

    }

    @Test
    public void testIntToByteArray() throws Exception{

    }

    @Test
    public void testLongToByteArray() throws Exception{

    }

    @Test
    public void testInt2byte() throws Exception{

        System.out.println(DataTypeChangeHelper.ip2Int( "192.168.0.1" ));
    }

    @Test
    public void testByte2int() throws Exception{
        byte[] b = new byte[2];
        b[0] = -20;
        b[1] = 0;
        System.out.println(DataTypeChangeHelper.byte2int( b ));
    }

}