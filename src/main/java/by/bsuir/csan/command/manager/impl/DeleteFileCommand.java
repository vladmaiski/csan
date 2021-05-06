package by.bsuir.csan.command.manager.impl;

import by.bsuir.csan.command.manager.api.FileManagerCommand;
import by.bsuir.csan.constant.CommandType;
import by.bsuir.csan.controller.request.ServletInfo;
import by.bsuir.csan.logic.FileManager;

public class DeleteFileCommand implements FileManagerCommand {

    private final static String param = CommandType.DELETE_FILE;

    @Override
    public CommandResult execute(ServletInfo servletInfo, FileManager fileManager) {
        String filename = servletInfo.getRequest().getParameter(param);
        if (filename == null)
            return new CommandResult("Invalid param for deleting file");
        String msg = null;
        if (!fileManager.deleteFile(filename))
            msg = "Can't create new dir";
        return new CommandResult(msg);
    }

    public static String getParam() {
        return param;
    }
}
