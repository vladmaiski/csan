package by.bsuir.csan.command.manager.impl;

import by.bsuir.csan.command.manager.api.FileManagerCommand;
import by.bsuir.csan.constant.CommandType;
import by.bsuir.csan.controller.request.ServletInfo;
import by.bsuir.csan.logic.FileManager;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class FileUploadCommand implements FileManagerCommand {

    private final String param = CommandType.UPLOAD_FILE;

    @Override
    public CommandResult execute(ServletInfo requestContext, FileManager fileManager) {
        Part part = null;
        try {
            part = requestContext.getRequest().getPart(param);
        } catch (IOException | ServletException e) {
            return new CommandResult("Can't get part of file");
        }
        if (part == null)
            return new CommandResult("Can't get part of file");
        String fileName = part.getSubmittedFileName();
        InputStream fileStream = null;
        try {
            fileStream = part.getInputStream();
        } catch (IOException e) {
            new CommandResult("Can't get input stream");
        }
        String fileContent = new BufferedReader(new InputStreamReader(fileStream)).lines().collect(Collectors.joining("\n"));
        fileManager.addNewFile(fileName, fileContent);
        return new CommandResult(null);
    }

    public String getParam() {
        return param;
    }
}
