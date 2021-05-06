package by.bsuir.csan.controller;

import by.bsuir.csan.command.manager.FileManagerCommandFactory;
import by.bsuir.csan.command.manager.api.FileManagerCommand;
import by.bsuir.csan.command.manager.impl.CommandResult;
import by.bsuir.csan.command.manager.impl.DownloadFileCommand;
import by.bsuir.csan.command.manager.impl.FileUploadCommand;
import by.bsuir.csan.constant.CommandType;
import by.bsuir.csan.controller.request.ServletInfo;
import by.bsuir.csan.logic.FileManager;
import by.bsuir.csan.service.ManagerResponseCreator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MultipartConfig
@WebServlet(name = "manager-servlet", value = "/manager")
public class Controller extends HttpServlet {
    FileManager fileManager = FileManager.getInstance();
    private String message;

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
        }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();
        String requestURL = request.getRequestURL().toString();
        String filePath = getDirPath(requestURI, servletPath);

        CommandResult commandResult = new CommandResult(null);
        boolean rewrite = true;

        if (!fileManager.changeDirectoryTo(filePath)) {
            commandResult = new CommandResult("Can't find such dir");
        } else {
            Part part = null;
            try {
                part = request.getPart(CommandType.UPLOAD_FILE);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
            ServletInfo servletInfo = new ServletInfo(request, response);
            FileManagerCommand command = null;

            if (part != null) {
                command = new FileUploadCommand();
            }

            if (request.getParameterNames().hasMoreElements()) {
                String parameterName = request.getParameterNames().nextElement();
                command = FileManagerCommandFactory.create(parameterName);
                if (command.getClass().equals(DownloadFileCommand.class))
                    rewrite = false;
            }

            if (command != null) {
                commandResult = command.execute(servletInfo, fileManager);
                if(commandResult.getMessage() != null)
                    rewrite = true;
            }
        }
        if (rewrite) {
            PrintWriter responseWriter = response.getWriter();
            responseWriter.append(ManagerResponseCreator.createResponse(fileManager.getAllObjects(), getPathPattern(requestURL, servletPath), fileManager.getPath(), commandResult));
            responseWriter.flush();
        }
    }

    public void destroy() {

    }

    private String getDirPath(String from, String toEndOf) {
        final Matcher matcher = Pattern.compile(toEndOf).matcher(from);
        if (matcher.find()) {
            return from.substring(matcher.end()).trim();
        }
        return null;
    }

    private String getPathPattern(String from, String toEndOf) {
        final Matcher matcher = Pattern.compile(toEndOf).matcher(from);
        if (matcher.find()) {
            return from.substring(0, matcher.end()).trim();
        }
        return null;
    }
}