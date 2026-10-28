import java.io.*;

public class FileWriterUtil {

    public static void writeToFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error writing file: " + filename);
        }
    }
}