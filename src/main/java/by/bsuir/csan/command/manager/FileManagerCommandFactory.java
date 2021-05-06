package by.bsuir.csan.command.manager;

import by.bsuir.csan.command.manager.api.FileManagerCommand;
import by.bsuir.csan.command.manager.impl.*;
import by.bsuir.csan.constant.CommandType;

public class FileManagerCommandFactory {

    public static FileManagerCommand create(String paramName) {
        if (paramName == null) {
            throw new IllegalArgumentException("There is no command to do.");
        }
        switch (paramName) {
            case CommandType.DOWNLOAD_FILE:
                return new DownloadFileCommand();
            case CommandType.DELETE_FILE:
                return new DeleteFileCommand();
            case CommandType.UPLOAD_FILE:
                return new FileUploadCommand();
            case CommandType.CREATE_FILE:
                return new CreateDirectoryCommand();
            case CommandType.COPY_FILE:
                return new CopyCommand();
            case CommandType.MOVE_FILE:
                return new MoveCommand();
            case CommandType.MODE:
                return new PasteCommand();
            default:
                throw new IllegalArgumentException("Unknown command: " + paramName);
        }
    }

}
