package com.internship.supercoders.superchat.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Max on 23.02.2017.
 */

public class FileManager {
    private Context mContext;
    private File currentDir;
    private File currentFile;

    FileManager(Context context) {
        this.mContext = context;
    }

    public void saveToInternalStorage(String dir, String filename, byte[] bytes) throws IOException {
        File currentDir = mContext.getDir(dir, Context.MODE_PRIVATE);
        File currentFile = new File(currentDir, filename); //Getting a file within the dir.
        FileOutputStream outputStream = new FileOutputStream(currentFile); //Use the stream as usual to write into the file.
        outputStream.write(bytes);
        outputStream.close();
    }

}
