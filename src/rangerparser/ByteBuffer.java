package rangerparser;

import java.util.HashMap;
import java.util.zip.CRC32;

public class ByteBuffer {
    public ByteBuffer() {
        endianness = ByteOrder.BIG_ENDIAN;
        marks = new HashMap();
    }
    
    public ByteBuffer(int length, ByteOrder endian) {
        buffer = new byte[length];
        endianness = endian;
        marks = new HashMap();
    }
    
    public ByteBuffer(byte[] bytes, ByteOrder endian) {
        buffer = bytes;
        endianness = endian;
        marks = new HashMap();
    }
    
    @Override
    public String toString() {
        return "Size: " + buffer.length + ", Position: " + position;
    }
    
    @Override
    public int hashCode() {
        CRC32 crc = new CRC32() {{ update(buffer); }};
        return (int) crc.getValue();
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ByteBuffer) ? hashCode() == obj.hashCode() : false;
    }
    
    public byte[] getContent() {
        return buffer;
    }
    
    public void setContent(byte[] bytes) {
        buffer = bytes;
        position = 0;
        marks.clear();
    }
    
    public ByteOrder getEndianness() {
        return endianness;
    }
    
    public void setEndianness(ByteOrder endian) {
        endianness = endian;
    }
    
    public int length() {
        return buffer.length;
    }
    
    public int remaining() {
        return (position < buffer.length) ? buffer.length - position : 0;
    }
    
    public boolean hasRemaining() {
        return remaining() > 0;
    }
    
    public int position() {
        return position;
    }
    
    public void position(int newpos) {
        if (newpos < 0)
            throw new IllegalArgumentException();
        position = newpos;
    }
    
    public void position(String key) {
        position = marks.containsKey(key) ? marks.get(key) : position;
    }
    
    public void skip(int val) {
        position += val;
    }
    
    public int getMark(String name) {
        return marks.containsKey(name) ? marks.get(name) : 0;
    }
    
    public int setMark(String name) {
        return setMark(name, position);
    }
    
    public int setMark(String name, int pos) {
        if (marks.containsKey(name))
            marks.replace(name, pos);
        else
            marks.put(name, pos);
        return pos;
    }
    
    public void deleteMark(String name) {
        if (marks.containsKey(name))
            marks.remove(name);
    }
    
    public void clearMarks() {
        marks.clear();
    }
    
    public void allocate(int size) {
        if (size < 0)
            throw new IllegalArgumentException();
        buffer = new byte[size];
        position = 0;
        marks.clear();
    }
    
    public void clear() {
        buffer = new byte[buffer.length];
        marks.clear();
    }
    
    public void clear(int start, int end) {
        if (start >= buffer.length || end >= buffer.length)
            throw new IllegalArgumentException();
        for ( ; start <= end ; start++)
            buffer[start] = (byte) 0x0;
    }
    
    public void extend(int addsize) {
        if (addsize < 0)
            throw new IllegalArgumentException();
        byte[] extended = new byte[buffer.length + addsize];
        System.arraycopy(buffer, 0, extended, 0, buffer.length);
        buffer = extended;
    }
    
    public byte[] readBytes(int l) {
        if (l <= 0)
            throw new IllegalArgumentException();
        byte[] b = new byte[l];
        if (remaining() >= l) {
            for (int c = 0 ; c < l ; c++)
                b[c] = buffer[position++];
        }
        return b;
    }
    
    public byte[] readRemaining() {
        return readBytes(remaining());
    }
    
    public byte readByte() {
        return (remaining() >= 1) ? buffer[position++] : 0;
    }
    
    public short readShort() {
        return (remaining() >= 2) ? ByteConverter.toShort(readBytes(2), endianness) : 0;
    }
    
    public int readInt() {
        return (remaining() >= 4) ? ByteConverter.toInt(readBytes(4), endianness) : 0;
    }
    
    public long readLong() {
        return (remaining() >= 8) ? ByteConverter.toLong(readBytes(8), endianness): 0;
    }
    
    public char readChar() {
        return (remaining() >= 2) ? ByteConverter.toChar(readBytes(2), endianness): 0;
    }
    
    public float readFloat() {
        return (remaining() >= 4) ? ByteConverter.toFloat(readBytes(4), endianness) : 0;
    }
    
    public double readDouble() {
        return (remaining() >= 8) ? ByteConverter.toDouble(readBytes(8), endianness) : 0;
    }
    
    public boolean readBool() {
        return (remaining() >= 1) ? buffer[position++] != 0x0 : false;
    }
    
    public void writeBytes(byte[] val) {
        if (position + val.length > buffer.length)
            extend(val.length);
        for (byte b : val)
            buffer[position++] = b;
    }
    
    public void writeByte(byte val) {
        if (position + 1 > buffer.length)
            extend(1);
        buffer[position++] = val;
    }
    
    public void writeShort(short val) {
        writeBytes(ByteConverter.getBytes(val, endianness));
    }
    
    public void writeInt(int val) {
        writeBytes(ByteConverter.getBytes(val, endianness));
    }
    
    public void writeLong(long val) {
        writeBytes(ByteConverter.getBytes(val, endianness));
    }
    
    public void writeChar(char val) {
        writeBytes(ByteConverter.getBytes(val, endianness));
    }
    
    public void writeFloat(float val) {
        writeBytes(ByteConverter.getBytes(val, endianness));
    }
    
    public void writeDouble(double val) {
        writeBytes(ByteConverter.getBytes(val, endianness));
    }
    
    public void writeBool(boolean val) {
        writeByte((byte) (val ? 1 : 0));
    }
    
    public int writeString(String val) {
        byte[] bytes = val.getBytes();
        int pos = position();
        writeByte((byte) bytes.length);
        writeBytes(bytes);
        return pos;
    }
    
    protected byte[] buffer;
    private int position;
    private ByteOrder endianness;
    private HashMap<String, Integer> marks;
}