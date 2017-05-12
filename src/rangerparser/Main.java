package rangerparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1)
            return;
        
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("File " + args[0] + " does not exist.");
            return;
        }
        
        try {
            RangerFile.open(new FileInputStream(file), args[0]);
        }
        catch (IOException ex) { System.out.println(ex); }
    }
}