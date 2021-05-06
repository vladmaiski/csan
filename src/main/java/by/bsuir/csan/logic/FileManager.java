package by.bsuir.csan.logic;

import by.bsuir.csan.constant.Mode;
import by.bsuir.csan.model.FileManagerObject;
import by.bsuir.csan.constant.FileType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {

    private static final File root = new File("/tmp/test/");
    private File currentFile = new File(root.getAbsolutePath());
    private File savedFile;
    private Mode mode;

    public static String getRoot() {
        return root.toString();
    }

    private FileManager() {

    }

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

    public boolean realizeMode() throws IOException {
        if (mode == Mode.MOVE) {
            boolean isCopied = copyFile();
            if (!isCopied)
                return false;
            return deleteFileByAbsPath(savedFile.getAbsolutePath());
        }

        if (mode == Mode.COPY) {
            return copyFile();
        }
        return false;
    }

    private boolean copyFile() throws IOException {
        File copied = new File(currentFile.getPath() + File.separator + savedFile.getName());
        Path path = Paths.get(currentFile.getPath() + File.separator + savedFile.getName());
        Files.createFile(path);
        try (
                InputStream in = new BufferedInputStream(
                        new FileInputStream(savedFile.getAbsolutePath()));
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(copied))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public void setSavedFile(String savedFile) {
        this.savedFile = new File(currentFile.getAbsolutePath() + File.separator + savedFile);
    }

    public File getSavedFile() {
        return savedFile;
    }

    public boolean createDir(String dirName) {
        File folder = new File(currentFile.getAbsolutePath() + "/" + dirName);
        return folder.mkdir();
    }

    public String getPath() {
        if (isRoot())
            return "/";
        return currentFile.getPath().replaceAll(root.getPath(), "");
    }

    public void changeDirectory(String dirName) {
        String newDirPath = currentFile.getAbsolutePath() + "/" + dirName;
        if (isFolder(newDirPath)) {
            currentFile = new File(newDirPath);
        }
    }

    public boolean addNewFile(String name, String content) {
        File newFile = new File(currentFile.getAbsolutePath() + "/" + name);
        try {
            if(!newFile.createNewFile())
                return false;
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile.getAbsolutePath()));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteFileByAbsPath(String path) {
        return deleteFileR(new File(path));
    }

    public boolean deleteFile(String name) {
        File fileToDelete = new File(currentFile.getAbsolutePath() + "/" + name);
        return deleteFileR(fileToDelete);
    }

    private boolean deleteFileR(File fileToDelete) {
        if (fileToDelete.isDirectory()) {
            try {
                deleteDirectoryRecursion(fileToDelete);
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        return fileToDelete.delete();
    }

    private void deleteDirectoryRecursion(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectoryRecursion(entry);
                }
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }

    public boolean changeDirectoryTo(String fullPath) {
        File folder = new File(root.getAbsolutePath() + "/" + fullPath + "/");
        if (!folder.exists() || !folder.isDirectory()) {
            currentFile = new File(root.getAbsolutePath());
            return false;
        }
        boolean related = folder.getAbsolutePath().contains(root.getAbsolutePath());

        if (!related) {
            return false;
        }

        currentFile = folder;
        return true;
    }

    private boolean isFolder(String path) {
        File folder = new File(path);
        return folder.isDirectory();
    }

    public boolean isFileInDir(String name) {
        File file = new File(currentFile.getAbsolutePath() + File.separator + name);
        return file.exists();
    }

    public void dirBack() {
        if (isRoot())
            return;
        currentFile = currentFile.getParentFile();
    }

    private boolean isRoot() {
        return currentFile.equals(root);
    }

    public File getFile(String name) {
        ArrayList<FileManagerObject> allObjectsInDir = new ArrayList<>();
        File[] dirObjects = currentFile.listFiles();
        if (dirObjects == null)
            return null;
        for (final File fileEntry : dirObjects) {
            if (fileEntry.isFile() && fileEntry.getName().equals(name)) {
                return fileEntry;
            }
        }
        return null;
    }

    public ArrayList<FileManagerObject> getAllObjects() {
        ArrayList<FileManagerObject> allObjectsInDir = new ArrayList<>();
        if (!isRoot())
            allObjectsInDir.add(new FileManagerObject(FileType.BACK_LINK, "..", currentFile.getParentFile().getAbsolutePath()));
        File[] dirObjects = currentFile.listFiles();
        if (dirObjects == null)
            return allObjectsInDir;
        for (final File fileEntry : dirObjects) {
            if (fileEntry.isDirectory()) {
                allObjectsInDir.add(new FileManagerObject(FileType.DIR, fileEntry.getName(), currentFile.getAbsolutePath()));
            }
            if (fileEntry.isFile()) {
                allObjectsInDir.add(new FileManagerObject(FileType.FILE, fileEntry.getName(), currentFile.getAbsolutePath()));
            }
        }
        return allObjectsInDir;
    }

}
