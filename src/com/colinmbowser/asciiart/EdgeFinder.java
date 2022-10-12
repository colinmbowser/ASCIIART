package com.colinmbowser.asciiart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EdgeFinder
{
    public static BufferedImage makeEdgePicture(BufferedImage GrayScaleImage, String imageName)
    {
        BufferedImage EdgeImage = GrayScaleImage;

        int[][] edgeColors = new int[GrayScaleImage.getWidth()][GrayScaleImage.getHeight()];
        int maxGradient = -1;

        for (int w = 1; w < GrayScaleImage.getWidth() - 1; w++)
        {
            for (int h = 1; h < GrayScaleImage.getHeight() - 1; h++)
            {

                double topleft = getGrayScale(GrayScaleImage.getRGB(w - 1, h - 1));
                double midleft = getGrayScale(GrayScaleImage.getRGB(w - 1, h));
                double botleft = getGrayScale(GrayScaleImage.getRGB(w - 1, h + 1));

                double topmid = getGrayScale(GrayScaleImage.getRGB(w, h - 1));
                double midmid = getGrayScale(GrayScaleImage.getRGB(w, h));
                double botmid = getGrayScale(GrayScaleImage.getRGB(w, h + 1));

                double topright = getGrayScale(GrayScaleImage.getRGB(w + 1, h - 1));
                double midright = getGrayScale(GrayScaleImage.getRGB(w + 1, h));
                double botright = getGrayScale(GrayScaleImage.getRGB(w + 1, h + 1));

                double grayX =  ((topleft * -1 ) + (midleft * 0 ) + (botleft * 1 )) +
                                ((topmid * -2  ) + (midmid * 0  ) + (botmid * 2  )) +
                                ((topright * -1) + (midright * 0) + (botright * 1));

                double grayY =  ((topleft * -1) + (midleft * -2) + (botleft * -1)) +
                                ((topmid * 0  ) + (midmid * 0  ) + (botmid * 0  )) +
                                ((topright * 1) + (midright * 2) + (botright * 1));

                double gval = Math.sqrt((grayX * grayX) + (grayY * grayY));
                int g = (int) gval;
                edgeColors[w][h] = g;
            }
        }

        for (int w = 1; w < GrayScaleImage.getWidth() - 1; w++)
        {
            for (int h = 1; h < GrayScaleImage.getHeight() - 1; h++)
            {
                int edgeColor = edgeColors[w][h];
                if (edgeColor > 255)
                {
                    edgeColor = 255;
                }
                if (edgeColor < 60)
                {
                    edgeColor = 0;
                }
                edgeColor = 255 - edgeColor;
                int setColor = new Color(edgeColor, edgeColor, edgeColor).getRGB();
                EdgeImage.setRGB(w, h, setColor);
                //EdgeImage.setRGB(w, h, edgeColor);
            }
        }
        try
        {
            //ImageIO.write(grayScale, "png", new File(imageName + "_gray_scale.png"));
            ImageIO.write(EdgeImage, "png", new File(imageName + "_edge.png"));
            return EdgeImage;
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("Error: Failed to save edge image \"" + imageName + "_edge.png\"");
            System.exit(0);
            return null;
        }
    }

    public static int  getGrayScale(int rgb)
    {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        //from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
        int gray = (int) ((0.2126 * r) + (0.7152 * g) + (0.0722 * b));
        return gray;
    }
}
