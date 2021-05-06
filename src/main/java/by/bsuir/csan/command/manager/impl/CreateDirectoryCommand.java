package by.bsuir.csan.command.manager.impl;

import by.bsuir.csan.command.manager.api.FileManagerCommand;
import by.bsuir.csan.constant.CommandType;
import by.bsuir.csan.controller.request.ServletInfo;
import by.bsuir.csan.logic.FileManager;

public class CreateDirectoryCommand implements FileManagerCommand {

    private final static String param = CommandType.CREATE_FILE;

    @Override
    public CommandResult execute(ServletInfo servletInfo, FileManager fileManager) {
        String dirName = servletInfo.getRequest().getParameter(param);
        String msg = null;
        if (!fileManager.createDir(dirName))
            msg = "Can't create new dir";
        return new CommandResult(msg);
    }

}
