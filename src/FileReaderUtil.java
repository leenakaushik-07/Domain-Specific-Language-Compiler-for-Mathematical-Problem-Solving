import java.io.*;

public class FileReaderUtil {

    public static String readSourceFile(String path) {
        try {
            return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return null;
        }
    }
}