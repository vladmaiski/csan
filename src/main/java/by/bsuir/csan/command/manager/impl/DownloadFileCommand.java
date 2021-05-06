package by.bsuir.csan.command.manager.impl;

import by.bsuir.csan.command.manager.api.FileManagerCommand;
import by.bsuir.csan.constant.CommandType;
import by.bsuir.csan.controller.request.ServletInfo;
import by.bsuir.csan.logic.FileManager;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

public class DownloadFileCommand implements FileManagerCommand {

    private final String param = CommandType.DOWNLOAD_FILE;

    @Override
    public CommandResult execute(ServletInfo servletInfo, FileManager fileManager) {
        String filename = null;
        try {
            filename = servletInfo.getRequest().getParameter(param);

            if (filename == null || filename.equals("")) {
                return new CommandResult("Invalid filename");
            }

            File file = fileManager.getFile(filename);
            if (file == null) {
                return new CommandResult("Can't find such file");
            }

            HttpServletResponse response = servletInfo.getResponse();

            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            FileInputStream fileInputStream = new java.io.FileInputStream(file.getAbsolutePath());

            int i;
            while ((i = fileInputStream.read()) != -1) {
                response.getWriter().write(i);
            }
            response.getWriter().flush();
            fileInputStream.close();
        } catch (Exception e) {
            return new CommandResult("Error while downloading file[" + filename + "]");
        }
        return new CommandResult(null);
    }

    public String getParam() {
        return param;
    }
}
