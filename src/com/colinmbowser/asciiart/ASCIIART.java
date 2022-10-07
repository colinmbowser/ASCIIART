package com.colinmbowser.asciiart;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ASCIIART
{
    public static void main(String[] args)
    {
        System.out.println("Hello, World People! new comment");

        BufferedImage image;
        try
        {
            image = ImageIO.read(new File(args[0]));
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("Error: Failed to open image \"" + args[0] + "\"");
            return;
        }
        System.out.println("Width: " + image.getWidth() + "\nHeight: " + image.getHeight());
    }
}

