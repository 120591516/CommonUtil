package com.wwt.commonutil.util.image;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ImageUtils {

    public static Map<String, Object> getExif(InputStream inputStream) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
            map = printExif(metadata);
        } catch (ImageProcessingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    // 获取exif信息，将旋转角度信息拿到
    private static Map<String, Object> printExif(Metadata metadata) {
        Map<String, Object> map = new HashMap<String, Object>();
        String tagName = null;
        String desc = null;
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                tagName = tag.getTagName();
                desc = tag.getDescription();
                if (tagName.equals("Orientation")) {
                    map.put("Orientation", desc);
                }
            }
        }
        return map;
    }

    public static int getAngle(Map<String, Object> map) {
        int ro = 0;
        if(map.get("Orientation")!=null) {
            String ori = map.get("Orientation").toString();
            if (ori.indexOf("90") >= 0) {
                ro = 1;
            } else if (ori.indexOf("180") >= 0) {
                ro = 2;
            } else if (ori.indexOf("270") >= 0) {
                ro = 3;
            }
        }
        return ro;
    }

    public static BufferedImage getBufferedImg(BufferedImage src,int width, int height, int ro) {
        int angle = (int) (90 * ro);
        int type = src.getColorModel().getTransparency();
        int wid = width;
        int hei = height;
        if (ro % 2 != 0) {
            int temp = width;
            width = height;
            height = temp;
        }
        Rectangle re = new Rectangle(new Dimension(width, height));
        BufferedImage BfImg = null;
        BfImg = new BufferedImage(re.width, re.height, type);
        Graphics2D g2 = BfImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.rotate(Math.toRadians(angle), re.width / 2,re.height / 2);
        g2.drawImage(src, (re.width - wid) / 2, (re.height - hei) / 2, null);
        g2.dispose();
        return BfImg;
    }

    //获得图片的高
    public static int getHeight(InputStream is) {
        BufferedImage src = null;
        int height = -1;
        try {
            src = ImageIO.read(is);
            height = src.getHeight();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return height;
    }

    //获得图片的宽
    public static int getWidth(InputStream is) {
        BufferedImage src = null;
        int width = -1;
        try {
            src = ImageIO.read(is);
            width = src.getWidth();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return width;
    }

    public static void main(String[] args) throws Exception{

        File file = new File("D:/测试照片/c652b0ad-9ad3-4ca9-9556-cd44dcf131bf.png");
        File localFile = new File("D:/测试照片/localFile.jpeg");
        String substring = file.getName().substring(file.getName().lastIndexOf("."));
        System.out.println(substring);
        int angle =  ImageUtils.getAngle(ImageUtils.getExif(new FileInputStream(file)));
        System.out.println(angle);
        BufferedImage bf =ImageUtils.getBufferedImg(ImageIO.read(new FileInputStream(file)), ImageUtils.getWidth(new FileInputStream(file)), ImageUtils.getHeight(new FileInputStream(file)), angle);
        ImageIO.write(bf, substring.substring(1), localFile);
        //度问题

    }

    public void  test(){

    }
}