package by.bsuir.csan.logic;

import by.bsuir.csan.model.FileObject;
import by.bsuir.csan.model.FileType;

import java.io.File;
import java.util.ArrayList;

public class FileManager {

    private final File root = new File("/tmp/test");
    private File currentFile = new File(root.getAbsolutePath());

    private static volatile FileManager instance;

    public static FileManager getInstance() {
        FileManager localInstance = instance;
        if (localInstance == null) {
            synchronized (FileManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FileManager();
                }
            }
        }
        return localInstance;
    }

    public void changeDirectory(String dirName) {
        String newDirPath = currentFile.getAbsolutePath() + "/" + dirName;
        if (isFolder(newDirPath)) {
            currentFile = new File(newDirPath);
        }
    }

    private boolean isFolder(String path) {
        File folder = new File(path);
        return folder.isDirectory();
    }

    public void dirBack() {
        if (isRoot())
            return;
        currentFile = currentFile.getParentFile();
    }

    private boolean isRoot() {
        return currentFile.equals(root);
    }

    public ArrayList<FileObject> getAllObjects() {
        ArrayList<FileObject> allObjectsInDir = new ArrayList<>();
        if (!isRoot())
            allObjectsInDir.add(new FileObject(FileType.BACK_LINK, "..", currentFile.getParentFile().getAbsolutePath()));
        File[] dirObjects = currentFile.listFiles();
        if (dirObjects == null)
            return allObjectsInDir;
        for (final File fileEntry : dirObjects) {
            if (fileEntry.isDirectory()) {
                allObjectsInDir.add(new FileObject(FileType.DIR, fileEntry.getName(), currentFile.getAbsolutePath()));
            }
            if (fileEntry.isFile()) {
                allObjectsInDir.add(new FileObject(FileType.FILE, fileEntry.getName(), currentFile.getAbsolutePath()));
            }
        }
        return allObjectsInDir;
    }

}
