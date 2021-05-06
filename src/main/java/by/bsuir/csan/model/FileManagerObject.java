package by.bsuir.csan.model;

import by.bsuir.csan.constant.FileType;
import by.bsuir.csan.logic.FileManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileManagerObject {

    private FileType fileType;
    private String name;
    private String path;

    public FileManagerObject(FileType fileType, String name, String path) {
        this.fileType = fileType;
        this.name = name;
        this.path = path;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsolutePath() {
        return path;
    }

    public String getPath() {
        return path.replace(FileManager.getRoot(), "");
    }

    public void setPath(String path) {
        this.path = path;
    }
}
