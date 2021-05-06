package by.bsuir.csan.service;

import by.bsuir.csan.command.manager.impl.CommandResult;
import by.bsuir.csan.model.FileManagerObject;

import java.util.ArrayList;

public class ManagerResponseCreator {

    public StringBuilder getHeaderLinks() {
        return headerLinks;
    }

    public void setHeaderLinks(StringBuilder headerLinks) {
        this.headerLinks = headerLinks;
    }

    private StringBuilder headerLinks;

    public ManagerResponseCreator(StringBuilder headerLinks) {
        this.headerLinks = headerLinks;
    }

    public static StringBuilder createResponse(ArrayList<FileManagerObject> objects,
                                               String pathPattern,
                                               String currentPath,
                                               CommandResult commandResult) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html>");
        htmlContent.append("<head>");
        htmlContent.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/styles.css\" />");
        htmlContent.append("          <style type=\"text/css\">\n" +
                "          .item-name {\n" +
                "            width: 300px;\n" +
                "        }     \n" +
                "       p {\n" +
                "    display: flex;\n" +
                "}" +
                " p a {\n" +
                "    margin-right: 30px;\n" +
                "}" +
                "  </style>");
        htmlContent.append("</head>");
        htmlContent.append("<body>");
        htmlContent.append("<form method=\"post\" action=\"\" enctype=\"multipart/form-data\">");
        htmlContent.append("<input type=\"file\" name=\"upload-file\"><br>");
        htmlContent.append("<input type=\"submit\" value=\"Create\">");
        htmlContent.append("</form>");
        htmlContent.append("<form method=\"post\" action=\"\">\n" +
                "                <input type=\"text\" name=\"create-file\"/><br/>\n" +
                "               <input type=\"submit\" value=\"Create\" />\n" +
                "           </form>");
        htmlContent.append("<p><a href=\"" + pathPattern + currentPath + "\">UPDATE</a></p>");
        htmlContent.append("<p><a href=\"" + pathPattern + "\">ROOT</a></p>");
        htmlContent.append("<p><a href=\"" + pathPattern + currentPath + "?mode=paste\"\">PASTE</a></p>");
        htmlContent.append("<p>Current path: " + currentPath + "</p>");
        htmlContent.append(createDirectionWebContent(objects, pathPattern));
        htmlContent.append("<script>");
        if (commandResult.getMessage() != null)
            htmlContent.append("alert(\"" + commandResult.getMessage() + "\");");
        htmlContent.append("</script>");
        htmlContent.append("</body>");
        htmlContent.append("</html>");
        return htmlContent;
    }

    private static String createDirectionWebContent(ArrayList<FileManagerObject> objects, String pathPattern) {
        StringBuilder content = new StringBuilder();
        for (FileManagerObject object : objects) {
            switch (object.getFileType()) {
                case DIR:
                    content.append("<p><span class=\"item-name\">[D]<a href=\"" + pathPattern + object.getPath() + "/" + object.getName() + "\">" + object.getName() + "</a></span><a href='" + pathPattern + object.getPath() + "?delete-file=" + object.getName() + "'>DELETE</a></p>");
                    break;
                case BACK_LINK:
                    content.append("<p><a href=\"" + pathPattern + object.getPath() + "\">" + object.getName() + "</a></p>");
                    break;
                case FILE:
                    content.append("<p><span class=\"item-name\">[F]<a href='" + pathPattern + object.getPath() + "?download-file=" + object.getName() + "'>" + object.getName() + "</a></span><a href='" + pathPattern + object.getPath() + "?delete-file=" + object.getName() + "'>DELETE</a><a href='" + pathPattern + object.getPath() + "?copy=" + object.getName() + "'>COPY</a><a href='" + pathPattern + object.getPath() + "?move=" + object.getName() + "'>MOVE</a></p>");
                    break;
            }
        }
        return content.toString();
    }

}
