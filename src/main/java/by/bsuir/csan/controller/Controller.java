package by.bsuir.csan.controller;

import by.bsuir.csan.logic.FileManager;
import by.bsuir.csan.model.FileObject;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "manager-servlet", value = "/manager")
public class Controller extends HttpServlet {
    FileManager fileManager = FileManager.getInstance();
    private String message;

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        FileManager fileManager = FileManager.getInstance();
        ArrayList<FileObject> fileObjects = fileManager.getAllObjects();
        PrintWriter responseWriter = response.getWriter();
        for (FileObject fileObject : fileObjects) {
            responseWriter.write(fileObject.getName() + "<br/>");
        }
        responseWriter.write("<br/>");
        fileManager.changeDirectory("test3");
        fileObjects = fileManager.getAllObjects();
        for (FileObject fileObject : fileObjects) {
            responseWriter.write(fileObject.getName() + "<br/>");
        }
        responseWriter.flush();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void destroy() {

    }
}