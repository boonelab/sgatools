package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
 
public class Zipper
{
 
    Zipper(){}
 
    public static void zipDir(String zipFileName, String dir) throws Exception {
        File dirObj = new File(dir);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        System.out.println("Creating : " + zipFileName);
        addDir(dirObj, out);
        out.close();
    }

    static void addDir(File dirObj, ZipOutputStream out) throws IOException {
        File[] files = dirObj.listFiles();
        byte[] tmpBuf = new byte[1024];

        for (int i = 0; i < files.length; i++) {
        	
            if (files[i].isDirectory()) {
                addDir(files[i], out);
                continue;
            }else if(files[i].getName().startsWith(".")){
            	//Hidden file in osx, ignore
            	continue;
            }
            FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
            System.out.println(" Adding: " + files[i].getAbsolutePath());
            out.putNextEntry(new ZipEntry(files[i].getName()));
            int len;
            while ((len = in.read(tmpBuf)) > 0) {
                out.write(tmpBuf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
    }
}