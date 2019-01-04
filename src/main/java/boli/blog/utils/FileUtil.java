package boli.blog.utils;

import java.io.File;

public class FileUtil {

    public static boolean mkdir(String path){
        File file = new File(path);
        if(!file.exists()){
            return file.mkdir();
        }
        return true;
    }
}
