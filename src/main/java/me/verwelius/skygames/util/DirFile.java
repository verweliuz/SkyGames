package me.verwelius.skygames.util;

import java.io.File;

public class DirFile extends File {

    private static File folder = new File(System.getProperty("user.dir"));

    public static void setFolder(File folder) {
        DirFile.folder = folder;
    }

    private final String relativePath;

    public DirFile(String path) {
        super(folder + File.separator + path);
        this.relativePath = path;
    }

    public String getRelativePath() {
        return this.relativePath;
    }

}