package com.colinmbowser.asciiart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GrayScale
{
    public static void makeGrayScale(BufferedImage firstImage, String imageName)
    {
        BufferedImage grayScaleImage = new BufferedImage(firstImage.getWidth(), firstImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayScaleImage.getGraphics();
        g.drawImage(firstImage,0,0,null);
        g.dispose();
        try
        {
            ImageIO.write(grayScaleImage, "png", new File(imageName + "_gray_scale.png"));
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("Error: Failed to save grayscale image \"" + imageName + "_gray_scale.png\"");
        }
    }
}
