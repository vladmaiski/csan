package by.bsuir.csan.command.manager.impl;

import by.bsuir.csan.command.manager.api.FileManagerCommand;
import by.bsuir.csan.controller.request.ServletInfo;
import by.bsuir.csan.logic.FileManager;

import java.io.IOException;

public class PasteCommand implements FileManagerCommand {

    @Override
    public CommandResult execute(ServletInfo requestContext, FileManager fileManager) {
        try {
            if (fileManager.realizeMode()) {
                return new CommandResult(null);
            }
        } catch (IOException e ) {
            return new CommandResult("Enable to " + fileManager.getMode().name());
        }
        return new CommandResult("Enable to " + fileManager.getMode().name());
    }

}
