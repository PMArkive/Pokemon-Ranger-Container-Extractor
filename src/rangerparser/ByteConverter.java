package rangerparser;

import static rangerparser.ByteOrder.*;

public class ByteConverter {
    public static byte[] reverse(byte[] arr) {
        if (arr == null)
            throw new NullPointerException();
        byte[] reversed = new byte[arr.length];
        for (int l = 0, r = arr.length - 1 ; l <= arr.length && r >= 0 ; l++, r--)
            reversed[l] = arr[r];
        return reversed;
    }
    
    public static byte[] getBytes(short val, ByteOrder endian) {
        byte[] bytes = new byte[2];
        
        bytes[0] = (byte) ((val >>> 8) & 0xFF);
        bytes[1] = (byte) (val & 0xFF);
        
        return (endian == LITTLE_ENDIAN) ? reverse(bytes) : bytes;
    }
    
    public static byte[] getBytes(int val, ByteOrder endian) {
        byte[] bytes = new byte[4];
        
        bytes[0] = (byte) ((val >>> 24) & 0xFF);
        bytes[1] = (byte) ((val >>> 16) & 0xFF);
        bytes[2] = (byte) ((val >>> 8) & 0xFF);
        bytes[3] = (byte) (val & 0xFF);

        return (endian == LITTLE_ENDIAN) ? reverse(bytes) : bytes;
    }
    
    public static byte[] getBytes(long val, ByteOrder endian) {
        byte[] bytes = new byte[8];
        
        bytes[0] = (byte) ((val >>> 56) & 0xFF);
        bytes[1] = (byte) ((val >>> 48) & 0xFF);
        bytes[2] = (byte) ((val >>> 40) & 0xFF);
        bytes[3] = (byte) ((val >>> 32) & 0xFF);
        bytes[4] = (byte) ((val >>> 24) & 0xFF);
        bytes[5] = (byte) ((val >>> 16) & 0xFF);
        bytes[6] = (byte) ((val >>> 8) & 0xFF);
        bytes[7] = (byte) (val & 0xFF);
        
        return (endian == LITTLE_ENDIAN) ? reverse(bytes) : bytes;
    }
    
    public static byte[] getBytes(char val, ByteOrder endian) {
        return getBytes((short) val, endian);
    }
    
    public static byte[] getBytes(float val, ByteOrder endian) {
        return getBytes(Float.floatToRawIntBits(val), endian);
    }
    
    public static byte[] getBytes(double val, ByteOrder endian) {
        return getBytes(Double.doubleToRawLongBits(val), endian);
    }
    
    public static short toShort(byte[] data, ByteOrder endian) {
        data = endian == LITTLE_ENDIAN ? reverse(data) : data;
        
        return (short) (((data[0] & 0xFF) << 8)
                | (data[1] & 0xFF));
    }
    
    public static int toInt(byte[] data, ByteOrder endian) {
        data = endian == LITTLE_ENDIAN ? reverse(data) : data;
        
        return (((data[0] & 0xFF) << 24)
                | ((data[1] & 0xFF) << 16)
                | ((data[2] & 0xFF) << 8)
                | (data[3] & 0xFF));
    }
    
    public static long toLong(byte[] data, ByteOrder endian) {
        data = endian == LITTLE_ENDIAN ? reverse(data) : data;
        
        return (((long) (data[0] & 0xFF) << 56)
                    | ((long) (data[1] & 0xFF) << 48)
                    | ((long) (data[2] & 0xFF) << 40)
                    | ((long) (data[3] & 0xFF) << 32)
                    | ((long) (data[4] & 0xFF) << 24)
                    | ((long) (data[5] & 0xFF) << 16)
                    | ((long) (data[6] & 0xFF) << 8)
                    | (long) (data[7] & 0xFF));
    }
    
    public static char toChar(byte[] data, ByteOrder endian) {
        return (char) toShort(data, endian);
    }
    
    public static float toFloat(byte[] data, ByteOrder endian) {
        return Float.intBitsToFloat(toInt(data, endian));
    }
    
    public static double toDouble(byte[] data, ByteOrder endian) {
        return Double.longBitsToDouble(toLong(data, endian));
    }
}