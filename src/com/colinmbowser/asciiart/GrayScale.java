package com.colinmbowser.asciiart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GrayScale
{
    public static BufferedImage makeGrayScale(BufferedImage firstImage, String imageName)
    {
        BufferedImage grayScale = new BufferedImage(firstImage.getWidth(), firstImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayScale.getGraphics();
        g.drawImage(firstImage,0,0,null);
        g.dispose();
        try
        {
            ImageIO.write(grayScale, "png", new File(imageName + "_gray_scale.png"));
            return grayScale;
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("Error: Failed to save grayscale image \"" + imageName + "_gray_scale.png\"");
            System.exit(0);
            return null;
        }
    }
}
