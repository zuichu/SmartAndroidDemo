package me.zuichu.sa.utils;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by office on 2017/4/7.
 */
public class FileUtil {

    public static String formatXML(String xml) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception e) {
            e.printStackTrace();
            return xml;
        }
    }

    public static String formatJson(String json) {
        String text = json;
        if (json.startsWith("{")) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                text = jsonObject.toString(2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (json.startsWith("[")) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                text = jsonArray.toString(2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public static boolean hasFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createDictionary(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean copyFile(String srcPath, String destPath) {
        try {
            FileInputStream in = new FileInputStream(srcPath);
            FileOutputStream out = new FileOutputStream(destPath);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean renameFile(String resFilePath, String newFilePath) {
        File resFile = new File(resFilePath);
        File newFile = new File(newFilePath);
        return resFile.renameTo(newFile);
    }

    public static void saveFile(InputStream inputStream, String filePath) {
        try {
            OutputStream outputStream = new FileOutputStream(new File(filePath), false);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergeFiles(Context context, File outFile, List<File> files) {
        FileChannel outChannel = null;
        try {
            outChannel = new FileOutputStream(outFile).getChannel();
            for (File f : files) {
                FileChannel fc = new FileInputStream(f).getChannel();
                ByteBuffer bb = ByteBuffer.allocate(1024 * 8);
                while (fc.read(bb) != -1) {
                    bb.flip();
                    outChannel.write(bb);
                    bb.clear();
                }
                fc.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static String readStringFromRaw(Context context, int rawId) {
        String res = "";
        try {
            InputStream in = context.getResources().openRawResource(rawId);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = new String(buffer, "UTF-8");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String readStringFromAsset(Context context, String fileName) {
        String res = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void writeText(String content, String filePath) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }
        }
    }

    public static String readText(String path) {
        byte Buffer[] = new byte[1024];
        File file = new File(path);
        FileInputStream in = null;
        ByteArrayOutputStream outputStream = null;
        try {
            in = new FileInputStream(file);
            int len = in.read(Buffer);
            outputStream = new ByteArrayOutputStream();
            outputStream.write(Buffer, 0, len);
            return new String(outputStream.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String getFileSize(Context context, String path) {
        File file = new File(path);
        long fileLong = file.length();
        return android.text.format.Formatter.formatFileSize(context, fileLong);
    }

    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    public static boolean isDictionary(String path) {
        return new File(path).isDirectory();
    }

    public static void unzipFile(String input, String output) {
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(input));
            OutputStream outputStream = new FileOutputStream(output);
            byte[] buf = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            gzipInputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createNewFile(String path) {
        String fileName = path.split(File.separator)[path.split(File.separator).length - 1];
        String filePath = path.replace(fileName, "");
        createDictionary(filePath);
        createFile(path);
    }

    public static String getFileName(String path) {
        int index = path.lastIndexOf(File.separator);
        if (index >= 0) {
            return path.substring(index + 1);
        }
        return path;
    }

    public static boolean deleteAllFile(final File path) {
        if (path.isDirectory()) {
            final File[] files = path.listFiles();
            if (files != null) {
                for (final File child : files) {
                    deleteAllFile(child);
                }
            }
        }
        return path.delete();
    }

    public static void cleanDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            int count = children.length;
            for (int i = 0; i < count; i++) {
                File file = new File(dir, children[i]);
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

}
