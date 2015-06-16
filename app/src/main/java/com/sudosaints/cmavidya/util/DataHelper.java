package com.sudosaints.cmavidya.util;

import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataHelper {

    public static final String DATA_PARENT_DIR = ".CMAVIDYA";
    public static final String DATA_DIR = ".PDF";

    public static boolean isExtStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static void createDataDirIfNotExists() {
        if (isExtStorageAvailable()) {
            File root = Environment.getExternalStorageDirectory();
            String path = root.getAbsolutePath() + File.separatorChar + DATA_PARENT_DIR + File.separatorChar + DATA_DIR;
            File dataDir = new File(path);
            if (!(dataDir.exists() && dataDir.isDirectory())) {
                dataDir.mkdirs();
            }
        }
    }

    public static String getDataDir() {
        File root = Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + File.separatorChar + DATA_PARENT_DIR + File.separatorChar + DATA_DIR;
        return path;
    }

    public static String getMimeType(File file) {
        String mimeType = null;
        try {
            String extension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
            if (null != extension) {
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mimeType;
    }

    public static boolean isImageFile(File file) {
        String type = getMimeType(file);
        if (null != type && type.contains("image")) {
            return true;
        }
        return false;
    }

    public static void DownloadPDFFile(String fileURL, File directory) {
        try {

            FileOutputStream f = new FileOutputStream(directory);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            InputStream in = c.getInputStream();

            byte[] buffer = new byte[32768];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}