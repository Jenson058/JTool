package com.jenson.tool.io;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

/**
 * 文件工具类
 */
public class FileUtil {

    /**
     * 创建目录
     *
     * @param uploadPath 文件上传的路径
     * @return 创建目录是否成功
     */
    public static boolean checkDirExists(String uploadPath) {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            return uploadDir.mkdir();
        }
        return true;
    }

    /**
     * 文件合并
     * @param filePath 合并的地址
     * @param files 合并的小文件
     * @return 是否合并成功
     * @throws IOException 异常
     */
    public static Boolean mergeFile(String filePath, List<String> files) throws IOException {
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath, true));
        //输入流，读文件
        Vector<InputStream> vector = new Vector<InputStream>();
        files.forEach(item -> {
            File file = new File(item);
            try {
                vector.add(new BufferedInputStream(Files.newInputStream(Paths.get(item))));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        SequenceInputStream sequenceInputStream = new SequenceInputStream(vector.elements());
        //10字节的缓存
        byte[] cache = new byte[1024 * 10];
        int len = -1;
        while ((len = sequenceInputStream.read(cache)) != -1) {
            //分段写
            outputStream.write(cache, 0, len);
        }
        //强制将所有缓冲的输出字节被写入磁盘，更可靠
        outputStream.flush();
        outputStream.close();
        sequenceInputStream.close();
        outputStream.close();
        files.forEach(item ->
                new File(item).delete()
        );
        return true;
    }
}
