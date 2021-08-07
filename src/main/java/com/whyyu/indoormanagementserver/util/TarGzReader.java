package com.whyyu.indoormanagementserver.util;

import org.codehaus.plexus.archiver.tar.TarEntry;
import org.codehaus.plexus.archiver.tar.TarInputStream;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

/**
 * @author WhyYu
 * @Description 解压遥感影像的.tar.gz压缩文件, 只提取其中一个波段作为缩略图数据来源，并提取元数据文件
 * 默认把红光波段(Band4)的图像作为缩略图的数据来源
 * @Date 2021/8/6 22:21
 */
public class TarGzReader {
    /**
     * description: readTarGz <br>
     * date: 2021/8/7 17:57 <br>
     * author: WhyYu <br>
     * @param file .tar.gz的遥感影像压缩文件
     * @param outputDir 需要输出的根目录
     * @return 提取的2个我们所需文件的类别和存储到的路径
     */ 
    public static HashMap<String, String> readTarGz(File file, String outputDir) {
        TarInputStream tarIn = null;
        HashMap<String, String> resultMap = new HashMap<>();
        try {
            tarIn = new TarInputStream(new GZIPInputStream(
                    new BufferedInputStream(new FileInputStream(file))),
                    1024 * 10);

            createDirectory(outputDir, null);
            //解压后每个组成文件的实体对象
            TarEntry entry = null;
            while ((entry = tarIn.getNextEntry()) != null) {
                String entryName = entry.getName();
                if (entry.isDirectory()) {
                    //是目录,创建子目录
                    createDirectory(outputDir, entryName);
                } else {
                    //是文件，检查一下是否是我们需要的Band4或者元数据
                    String storingPath = outputDir + "/" + entryName;
                    if (entryName.contains("B4.")) {
                        resultMap.put("Thumbnail", storingPath);
                        try {
                            storeTarFile(tarIn, storingPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (entryName.contains("MTL.")) {
                        resultMap.put("Meta", storingPath);
                        try {
                            storeTarFile(tarIn, storingPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (tarIn != null) {
                    tarIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    /**
     * description: 调整输出图像的大小,遥感图像一般都是几千乘几千像素，我们使用缩略图不需要传输这么大的图像 <br>
     * date: 2021/8/7 18:04 <br>
     * author: WhyYu <br>
     * @param source tif的bufferedImage
     * @param zoom 缩放比例如0.1代表原来的十分之一
     * @return 缩小后的图像
     */
    public static BufferedImage resize(BufferedImage source, double zoom) {
        int type = source.getType();
        BufferedImage target = null;
        // handmade
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster((int) (source.getWidth() * zoom), (int) (source.getHeight() * zoom));
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage((int) (source.getWidth() * zoom), (int) (source.getHeight() * zoom), type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(zoom, zoom));
        g.dispose();
        return target;

    }

    private static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        // 子目录不为空
        if (!(subDir == null || subDir.trim().equals(""))) {
            file = new File(outputDir + "/" + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.mkdirs();
        }
    }

    /**
     * description: 把tar文件中挑出来的文件进行存储 <br>
     * date: 2021/8/7 19:42 <br>
     * author: WhyYu <br>
     * @param tarIn  TarInputStream
     * @param storingPath 存储路径
     */
    private static void storeTarFile(TarInputStream tarIn, String storingPath) throws IOException {
        File tmpFile = new File(storingPath);
        OutputStream out = new FileOutputStream(tmpFile);
        int length = 0;
        byte[] b = new byte[2048];
        //读取的时候只会读当前的entry对象，不是全读
        while ((length = tarIn.read(b)) != -1) {
            out.write(b, 0, length);
        }
        out.flush();
        out.close();
    }
}
