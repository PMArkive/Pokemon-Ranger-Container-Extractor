package rangerparser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Header (0x10 bytes):
 *  0x0 - int: signature / type
 *  0x4 - int: entry length
 *  0x8 - int: entry size
 * Types:
 *  CHPT
 *  CPDT
 *  EEDT
 *  ELDT
 *  ETDT
 *  FPKD - Pokémon data
 *  LPDT
 *  MPCP
 *  MPPS
 *  NPCD - NPC data
 *  OBJD - Objects data
 *  PLST
 *  ZKND - Pokédex list(?)
 *  ZKNE - Pokédex entries(?)
 * @author Suna
 */
public class RangerFile {
    public static void open(InputStream in, String destpath) throws IOException {
        // Create buffer
        ByteBuffer buf = new ByteBuffer(in.available(), ByteOrder.LITTLE_ENDIAN);
        in.read(buf.buffer);
        
        // Create the destination folder
        File dest = new File(destpath + " entries");
        dest.mkdir();
        
        // Get important file information
        byte[] signature = buf.readBytes(0x4);
        int entryLength = buf.readInt();
        int entryCount = buf.readInt();
        buf.position(0x10);
        
        // Write header file
        ByteBuffer headerbuf = new ByteBuffer(0x8, ByteOrder.LITTLE_ENDIAN);
        headerbuf.writeBytes(signature);
        headerbuf.writeInt(entryLength);
        File headerfile = new File(dest.getAbsolutePath() + "/main.bin");
        headerfile.createNewFile();
        try (FileOutputStream hout = new FileOutputStream(headerfile)) {
            hout.write(headerbuf.buffer);
            hout.flush();
        }
        
        // Write entry files
        for (int i = 0 ; i < entryCount ; i++) {
            File entryfile = new File(dest.getAbsolutePath() + "/" + i + ".bin");
            entryfile.createNewFile();
            try (FileOutputStream eout = new FileOutputStream(entryfile)) {
                eout.write(buf.readBytes(entryLength));
                eout.flush();
            }
        }
    }
    
    public static void save(OutputStream out, List<File> files) throws IOException  {
        // nope...
    }
}