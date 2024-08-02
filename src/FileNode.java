import java.io.File;

public class FileNode {
    private final File file;

    public FileNode(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}