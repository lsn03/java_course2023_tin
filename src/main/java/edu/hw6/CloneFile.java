package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CloneFile {
    private CloneFile() {

    }

    public static void cloneFile(Path path) {
        String fileFullName = path.getFileName().toString();
        String ext = fileFullName.substring(fileFullName.lastIndexOf('.'));
        String fileName = fileFullName.substring(0, fileFullName.lastIndexOf('.'));
        Path dir = path.getParent();


        String newFileName = fileName + ext;
        int countOfCopy = 1;
        while (Files.exists(dir.resolve(newFileName))) {

            if (countOfCopy == 1) {
                newFileName = fileName + " - копия" + ext;
            } else {
                newFileName = fileName + " - копия(" + countOfCopy + ")" + ext;
            }
            countOfCopy += 1;
            try {
                if (!Files.exists(dir.resolve(newFileName))) {
                    Files.copy(path, dir.resolve(newFileName));
                    break;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }

}
