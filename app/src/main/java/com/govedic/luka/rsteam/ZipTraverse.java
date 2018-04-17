package com.govedic.luka.rsteam;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipTraverse {

    public static Map<String, Integer> numberOfFiles(ZipInputStream zin) {
        Map<String, Integer> numFiles = new HashMap<>();
        try {
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                System.out.println(ze.getName());

                if (!ze.isDirectory()) {
                    //save to map

                    //get only the file name not the path
                    String[] path = ze.getName().split("/");
                    String name = path[path.length-1];

                    String[] ext = name.split("\\.");
                    String extension;

                    //if there is only one element in the array, it means that there is no dot and hence the file has no extension
                    if (ext.length > 1)
                        extension = ext[ext.length - 1];
                    else
                        extension = "NO EXTENSION";

                    System.out.println(extension);
                    int current = 0;
                    if (numFiles.containsKey(extension)) {
                        current = numFiles.get(extension);
                    }
                    numFiles.put(extension, current + 1);
                    zin.closeEntry();
                }
            }
            zin.close();
            System.out.println("Finished unzip");
        } catch (Exception e) {
            System.err.println("Unzip Error:");
            System.err.println(e);
        }

        return numFiles;
    }
}
