package rangerparser;

public enum ByteOrder {
    BIG_ENDIAN      (new byte[] { (byte) 0xFE, (byte) 0xFF }),  // Left to right
    LITTLE_ENDIAN   (new byte[] { (byte) 0xFF, (byte) 0xFE });  // Right to left
    
    private final byte[] identifier;
    private ByteOrder(byte[] identifier) {
        this.identifier = identifier;
    }
    
    public byte[] getIdentifier() {
        return identifier;
    }
    
    public static ByteOrder getByteOrder(byte[] e) {
        return (e == BIG_ENDIAN.identifier) ? BIG_ENDIAN :
                (e == LITTLE_ENDIAN.identifier) ? LITTLE_ENDIAN : null;
    }
}