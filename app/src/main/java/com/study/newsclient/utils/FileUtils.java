package com.study.newsclient.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    /**
     * 获取文件操作对象
     *
     * @param path 文件路径
     * @return
     */
    public static File getFile(String path) {
        File file = new File(path);
        return file;
    }

    /**
     * 获取目录,如果目录不存在会创建
     *
     * @return 目录路径：./
     */
    public static String getDirectory(String path) {
        if (!path.endsWith("/")) {
            path += "/";
        }
        File f = new File(path);
        try {
            if (!f.exists()) {
                if (!f.mkdirs()) {
                    throw new Exception("创建目录" + path + "失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 文件是否存在
     *
     * @param fpath 文件完整路径
     */
    public static boolean isFileExists(String fpath) {
        File f = new File(fpath);
        return f.exists();
    }

    /**
     * 删除文件
     *
     * @param fpath 文件完整路径
     */
    public static void deleteFile(String fpath) {
        File f = new File(fpath);
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * 功能描述:获取文件大小
     *
     * @param fpath
     * @return
     */
    public static long getFileLength(String fpath) {
        long lng = 0;
        File f = new File(fpath);
        lng = f.length();
        return lng;
    }

    /**
     * 功能描述:获取扩展名
     *
     * @param fpath
     * @return 扩展名
     */
    public static String getFileExtension(String fpath) {
        String extension = "";
        File f = new File(fpath);
        if (f.isFile()) {
            int dotIdx = fpath.lastIndexOf(".");
            extension = fpath.substring(dotIdx + 1, fpath.length());
        }
        return extension;
    }

    /**
     * 功能描述:获取文件名
     *
     * @param fpath
     * @return 文件名
     */
    public static String getFileName(String fpath) {
        String fileName = "";
        File f = new File(fpath);
        if (f.isFile()) {
            fileName = f.getName();
        }
        return fileName;
    }

    /**
     * 创建新文件，如果文件已经存在将删除后重新创建
     *
     * @param fpath 文件完整路径
     * @return 文件是否创建成功
     */
    public static boolean createNewFile(String fpath) {
        boolean brtn = false;
        File f = new File(fpath);
        if (f.exists()) {
            f.delete();
        }
        try {
            int nend = fpath.lastIndexOf("/");
            String strdir = fpath.substring(0, nend);
            getDirectory(strdir);
            brtn = f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brtn;
    }

    /**
     * 功能描述:获取文件的字节数组
     *
     * @param filePath 文件完整路徑
     * @return 字节数组
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 功能描述:字节数组转换为文件
     *
     * @param bfile    字节数组
     * @param filePath 文件保存目录
     * @param fileName 文件名
     */
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取保存语音文件的目录
     *
     * @param mContext
     * @return
     */
    public static String getSaveDir(Context mContext) {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            file = new File(Environment.getExternalStorageDirectory().toString());
        } else {
            file = new File(mContext.getFilesDir().toString());
        }
        if (file != null) {
            file = new File(file + File.separator + "yazhidao" + File.separator + "speech");
            if (!file.exists()) {
                try {
                    file.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getPath();
    }

    /**
     * 获取文件的MD5
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }


    /**
     * 获取字符串的md5值
     * @param key
     * @return
     */
    public static String getMD5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 是否存在该文件
     * @param mContext
     * @param mUrl
     * @return
     */
    public static boolean isExistFile(Context mContext, String mUrl) {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            file = new File(Environment.getExternalStorageDirectory().toString());
        } else {
            file = new File(mContext.getFilesDir().toString());
        }
        if (file != null) {
            file = new File(file + File.separator + "yazhidao" + File.separator + "speech" + File.separator + FileUtils.getMD5(mUrl) + ".mp3");
            return file.exists();
        }
        return false;
    }

    /**
     * 生成保存文件的路径
     * @param mContext
     * @param mUrl
     * @return
     */
    public static String getFilePath(Context mContext, String mUrl) {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            file = new File(Environment.getExternalStorageDirectory().toString());
        } else {
            file = new File(mContext.getFilesDir().toString());
        }
        if (file != null) {
            file = new File(file + File.separator + "yazhidao" + File.separator + "speech" + File.separator + FileUtils.getMD5(mUrl) + ".mp3");
        }
        return file.getPath();
    }

    /**
     * 获取要保存文件的File对象
     * @param mContext
     * @param name
     * @return
     */
    public static File getSavePath(Context mContext, String name) {
        File file = null;
        if(!name.endsWith(".amr")){
            name+=".amr";
        }
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            file = new File(Environment.getExternalStorageDirectory().toString());
        } else {
            file = new File(mContext.getFilesDir().toString());
        }
        if (file != null) {
            file = new File(file + File.separator + "yazhidao" + File.separator + "speech");
            if (!file.exists()) {
                try {
                    file.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new File(file, name);
    }

    /**
     * 把内容写入到内存卡中
     * @param name
     * @param bytes
     * @return
     */
    public static File writeFile2SDCard(Context mContext, String name, byte[] bytes) {
        File path = null;
        BufferedOutputStream out=null;
        try {
            OutputStream o = new FileOutputStream(FileUtils.getSavePath(mContext, name));
             out = new BufferedOutputStream(o);
            out.write(bytes);
            path = FileUtils.getSavePath(mContext, name);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out != null) {
                try  {
                    out.flush();
                    out.close();
                } catch(Exception e){}
            }
        }
        return path;
    }

    /**
     * 创建推送文件
     */
    public static File getSavePushInfoPath(Context mContext, String fileName) {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            file = new File(Environment.getExternalStorageDirectory().toString());
        } else {
            file = new File(mContext.getFilesDir().toString());
        }
        if(file!=null){
            file=new File(file+ File.separator+"yazhidao");
            if(!file.exists()){
                try {
                    file.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                file.delete();
            }
        }
        return new File(file,fileName);
    }

    public void saveSelNewsIdInFile(){

    }

    /**
     * 读文件
     * @param file 这个是SDCard 下的txt文件
     * @return
     * @throws IOException
     */
    public static String readSDFile(File file) throws IOException {

//        File file = new File(fileName);

        FileInputStream fis = new FileInputStream(file);

        int length = fis.available();

        byte [] buffer = new byte[length];
        fis.read(buffer);

//       String res = EncodingUtils.getString(buffer, "UTF-8");
        String res="";
        fis.close();
        return res;
    }

    /**
     * 写文件
     * @param file 这个是SDCard 下的txt文件
     * @param write_str
     * @throws IOException
     */
    public static void writeSDFile(File file, String write_str) throws IOException {

//        File file = new File(fileName);

        FileOutputStream fos = new FileOutputStream(file);

        byte [] bytes = write_str.getBytes();

        fos.write(bytes);

        fos.close();
    }

    public static void clear(File file) {
        if (file.exists()) { //指定文件是否存在
            if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）
                File[] files = file.listFiles(); //列出当前文件夹下的所有文件
                for (File f : files) {
                   f.delete();
                    //Log.d("fileName", f.getName()); //打印文件名
                }
            }
        }
    }

    public static void savePhoto(final Context context, final Bitmap bmp, final SaveResultCallback saveResultCallback) {
        final File sdDir = getSDPath();
        if (sdDir == null) {
            Toast.makeText(context,"设备自带的存储不可用",Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                File appDir = new File(sdDir,"/sdk/photo");
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置以当前时间格式为图片名称
                String fileName = df.format(new Date()) + ".png";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    saveResultCallback.onSavedSuccess();
                } catch (FileNotFoundException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                } catch (IOException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                }

                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        }).start();
    }


    public static String [] returnImageUrlsFromHtml(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        if (imageSrcList == null || imageSrcList.size() == 0) {
            Log.e("imageSrcList","资讯中未匹配到图片链接");
            return null;
        }
        return imageSrcList.toArray(new String[imageSrcList.size()]);
    }

    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir =Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir;
    }

    public interface SaveResultCallback {
        void onSavedSuccess();

        void onSavedFailed();
    }
}
