package by.bsuir.csan.command.manager.impl;

import by.bsuir.csan.command.manager.api.FileManagerCommand;
import by.bsuir.csan.constant.CommandType;
import by.bsuir.csan.constant.Mode;
import by.bsuir.csan.controller.request.ServletInfo;
import by.bsuir.csan.logic.FileManager;

import java.io.File;

public class CopyCommand implements FileManagerCommand {

    private final String param = CommandType.COPY_FILE;

    @Override
    public CommandResult execute(ServletInfo requestContext, FileManager fileManager) {
        String fileName = requestContext.getRequest().getParameter(param);
        if (!fileManager.isFileInDir(fileName)) {
            return new CommandResult("Enable to copy file");
        }
        fileManager.setSavedFile(fileName);
        fileManager.setMode(Mode.COPY);
        return new CommandResult(null);
    }

    public String getParam() {
        return param;
    }
}
