import java.io.File;

public class FileNode {
    private final File file;

    public FileNode(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return file.getName().isEmpty() ? file.getPath() : file.getName();
    }

    public boolean isFolder() {
        return this.file.listFiles() == null;
    }

}