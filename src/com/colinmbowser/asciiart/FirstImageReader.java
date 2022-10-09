package com.colinmbowser.asciiart;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FirstImageReader
{
    public static BufferedImage copyFirstImage(String[] args)
    {
        BufferedImage firstImage;
        try
        {
            firstImage = ImageIO.read(new File(args[0]));
            System.out.println("Width: " + firstImage.getWidth() + "\nHeight: " + firstImage.getHeight());
            return firstImage;
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("Error: Failed to open image \"" + args[0] + "\"");
            System.exit(0);
            return null;
        }
    }
}
