package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHelper {

    private static final String FILES_FOLDER = System.getProperty("user.dir") + "/src/test/resources/test-data/files/";
    private static final long DELETE_THRESHOLD_MILLIS = 24 * 60 * 60 * 1000; // 24 hours

    public static String copyAndRenameFileWithTimestamp(String originalFileName) {

        File originalFile = new File(FILES_FOLDER + originalFileName);

        if (!originalFile.exists()) {
            throw new RuntimeException("Original file does not exist: " + originalFile.getAbsolutePath());
        }

        // Generate timestamp with milliseconds
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());

        // Split base name and extension
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));

        String newFileName = baseName + "_" + timestamp + extension;

        File newFile = new File(FILES_FOLDER + newFileName);

        try {
            Files.copy(originalFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + newFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy and rename file: " + e.getMessage(), e);
        }

        // Clean up old files
        cleanUpOldFiles(baseName, extension);

        return newFileName;
    }

    private static void cleanUpOldFiles(String baseName, String extension) {
        File folder = new File(FILES_FOLDER);
        File[] files = folder.listFiles((dir, name) -> name.startsWith(baseName + "_") && name.endsWith(extension));

        if (files != null) {
            long now = System.currentTimeMillis();
            for (File file : files) {
                if (now - file.lastModified() > DELETE_THRESHOLD_MILLIS) {
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("Deleted old file: " + file.getName());
                    }
                }
            }
        }
    }
}
