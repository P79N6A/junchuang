package com.teachingplan.console.common;

import java.io.*;

/**
 * Created by v_yanfzhang on 2018/2/2.
 */
public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName);
            out.write(file);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String fileRead(String filePath){
        String proClassPath = System.getProperty("user.dir");

        File file = new File(proClassPath + filePath);//定义一个file对象，用来初始化FileReader
        FileReader reader = null;//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = null;
        StringBuilder sb = null;
        try {
            reader = new FileReader(file);
            bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
            sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
            String s = "";
            while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
                System.out.println(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != bReader) {
                try {
                    bReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
