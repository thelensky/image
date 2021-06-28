package ru.thelensky.vr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VRImage {

    private BufferedImage img;

    public VRImage(String path) {
        try {
            var input = new File(path);
            this.img = ImageIO.read(input);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static String makeImg(String path) {
        var vrImage = new VRImage(path);
        var imgWidth = vrImage.getImg().getWidth();
        var oneForthOfWidth = imgWidth / 4;
        var threeForthOfWidth = imgWidth - oneForthOfWidth;

        var leftSide = vrImage.slice(0, threeForthOfWidth);
        var rightSide = vrImage.slice(oneForthOfWidth, threeForthOfWidth);
        var vrImg = VRImage.mergeImages(leftSide, rightSide);
        var name = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());

        var result = new File(name + "." + ImgExtension.JPG);
        try {
            ImageIO.write(vrImg, ImgExtension.JPG.toString(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.getPath();
    }

    public static BufferedImage mergeImages(BufferedImage leftImg, BufferedImage rightImg) {
        var width = leftImg.getWidth() + rightImg.getWidth();
        var height = leftImg.getHeight();
        var result = new BufferedImage(width, height, leftImg.getType());
        var graphics2D = result.createGraphics();
        var color = graphics2D.getColor();

        graphics2D.setPaint(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(color);
        graphics2D.drawImage(leftImg, null, 0, 0);
        graphics2D.drawImage(rightImg, null, leftImg.getWidth(), 0);
        graphics2D.dispose();
        return result;
    }

    public BufferedImage getImg() {
        return img;
    }

    public BufferedImage slice(int startSlice, int endSlice) {
        return img.getSubimage(startSlice, 0, endSlice, img.getHeight());
    }

    private enum ImgExtension {
        JPG("jpg");

        private final String extension;

        ImgExtension(String extension) {
            this.extension = extension;
        }

        @Override
        public String toString() {
            return extension;
        }
    }

}
