package com.internship.supercoders.superchat.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by Max on 23.02.2017.
 */

public class FileManager {
    private Context mContext;
    private File currentDir;
    private File currentFile;

    public FileManager(Context context) {
        this.mContext = context;
    }

    public boolean saveToInternalStorage(String dir, String filename, ResponseBody body) {
        Log.i("FileManager", "START WRITING");
        File currentDir = mContext.getDir(dir, Context.MODE_PRIVATE);
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            File currentFile = new File(currentDir, filename); //Getting a file within the dir.
            if (!currentFile.exists()) {
                currentFile.createNewFile();
            }
            outputStream = new FileOutputStream(currentFile); //Use the stream as usual to write into the file.
            inputStream = body.byteStream();
            byte[] fileReader = new byte[4096];
            while (true) {
                int read = inputStream.read(fileReader);

                if (read == -1) {
                    break;
                }

                outputStream.write(fileReader, 0, read);
            }

            outputStream.flush();
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                return false;
            }
            //TODO: NullPointerException
        }

    }

    public boolean saveToFolder(String path, ResponseBody body, String name) {
        try {
            // todo change the file location/name according to your needs
            String root = Environment.getExternalStorageDirectory().toString();
            File dir = new File(root + path);
            if (!dir.exists()) dir.mkdirs();
            File fTo = new File(root + path + name + ".jpg");
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(fTo);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("stas", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
