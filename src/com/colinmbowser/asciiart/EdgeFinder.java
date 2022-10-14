package com.colinmbowser.asciiart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EdgeFinder
{
    public static BufferedImage makeEdgePicture(BufferedImage changingImage, String imageName, String[] args)
    {

        int[] nextTo = getMethod(args[1]);

        BufferedImage EdgeImage = changingImage;

        int[][] edgePixelColors = new int[changingImage.getWidth()][changingImage.getHeight()];

        for (int w = 1; w < changingImage.getWidth() - 1; w++)
        {
            for (int h = 1; h < changingImage.getHeight() - 1; h++)
            {

                double topleft = getGrayScaleInt(changingImage.getRGB(w - 1, h - 1));
                double midleft = getGrayScaleInt(changingImage.getRGB(w - 1, h));
                double botleft = getGrayScaleInt(changingImage.getRGB(w - 1, h + 1));

                double topmid = getGrayScaleInt(changingImage.getRGB(w, h - 1));
                double midmid = getGrayScaleInt(changingImage.getRGB(w, h));
                double botmid = getGrayScaleInt(changingImage.getRGB(w, h + 1));

                double topright = getGrayScaleInt(changingImage.getRGB(w + 1, h - 1));
                double midright = getGrayScaleInt(changingImage.getRGB(w + 1, h));
                double botright = getGrayScaleInt(changingImage.getRGB(w + 1, h + 1));

                double grayX =  ((topleft  * -nextTo[1]) + (midleft  * 0) + (botleft  * nextTo[1])) +
                                ((topmid   * -nextTo[0]) + (midmid   * 0) + (botmid   * nextTo[0])) +
                                ((topright * -nextTo[1]) + (midright * 0) + (botright * nextTo[1]));

                double grayY =  ((topleft  * -nextTo[1]) + (midleft  * -nextTo[0]) + (botleft  * -nextTo[1])) +
                                ((topmid   * 0         ) + (midmid   * 0         ) + (botmid   * 0         )) +
                                ((topright * nextTo[1] ) + (midright * nextTo[0] ) + (botright * nextTo[1] ));

                double gradval = Math.sqrt((grayX * grayX) + (grayY * grayY));
                int col = (int) gradval;
                edgePixelColors[w][h] = col;
            }
        }

        for (int w = 1; w < changingImage.getWidth() - 1; w++)
        {
            for (int h = 1; h < changingImage.getHeight() - 1; h++)
            {
                int edgeColor = edgePixelColors[w][h];
                if (edgeColor > 255)
                {
                    edgeColor = 255;
                }
                if (edgeColor < 50)
                {
                    edgeColor = 0;
                }
                edgeColor = 255 - edgeColor;
                int setColor = new Color(edgeColor, edgeColor, edgeColor).getRGB();
                EdgeImage.setRGB(w, h, setColor);
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

    public static int[] getMethod(String method) {
        switch(method.toLowerCase()) {
            case "prewitt": return new int[] { 1,  1};
            case "scharr":  return new int[] {10,  3};
            case "sobel":   return new int[] { 2,  1};

            default:
                System.out.println("Unknown edge method");
                System.exit(0);
                return null;
        }
    }

    public static int getGrayScaleInt(int rgb)
    {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        //from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
        int gray = (int) ((0.2126 * r) + (0.7152 * g) + (0.0722 * b));
        return gray;
    }
}