package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHelper {

    private static final String SOURCE_FOLDER = System.getProperty("user.dir") + "/src/test/resources/test-data/files/";
    private static final String TEMP_FOLDER = System.getProperty("user.dir")
            + "/src/test/resources/test-data/tempFiles/";

    public static String copyAndRenameFileWithTimestamp(String originalFileName) {
        File sourceFile = new File(SOURCE_FOLDER + originalFileName);

        if (!sourceFile.exists()) {
            throw new RuntimeException("Source file does not exist: " + sourceFile.getAbsolutePath());
        }

        // Ensure temp folder exists
        File tempDir = new File(TEMP_FOLDER);
        if (!tempDir.exists())
            tempDir.mkdirs();

        // Generate unique name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));

        String newFileName = baseName + "_" + timestamp + extension;
        File newFile = new File(TEMP_FOLDER + newFileName);

        try {
            Files.copy(sourceFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy file to temp folder: " + e.getMessage(), e);
        }

        return newFile.getAbsolutePath(); // return full path
    }

    public static void cleanUpTempFiles() {
        File tempDir = new File(TEMP_FOLDER);
        if (!tempDir.exists())
            return;

        File[] files = tempDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.delete()) {
                    System.out.println("Deleted temp file: " + file.getName());
                }
            }
        }
    }
}
