/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web_constants;

import javax.servlet.ServletContext;
/**
 *
 * @author dell
 */
public class PathConstants {

    public static final String CONTEXT_PATH = "/HanaShop";
    private final String projectDirPath;
    private final String projectLogPath;
    private final String projectWebDirPath;
    private final String projectPublicImgDirPath;

    public PathConstants(ServletContext context) {
        projectDirPath = context.getRealPath("/").replace("\\build\\web", "") + "src\\java";
        projectLogPath = projectDirPath + "\\logs\\myweb.log";
        projectWebDirPath = context.getRealPath("/").replace("\\build\\web", "") + "web";
        projectPublicImgDirPath = projectWebDirPath + "\\images";
    }

    public String getProjectDirPath() {
        return projectDirPath;
    }

    public String getProjectLogPath() {
        return projectLogPath;
    }

    public String getProjectWebDirPath() {
        return projectWebDirPath;
    }

    public String getProjectPublicImgDirPath() {
        return projectPublicImgDirPath;
    }

}
