package by.bsuir.csan.command.manager.api;

import by.bsuir.csan.command.manager.impl.CommandResult;
import by.bsuir.csan.controller.request.ServletInfo;
import by.bsuir.csan.logic.FileManager;

public interface FileManagerCommand {

    CommandResult execute(ServletInfo requestContext, FileManager fileManager);

}
