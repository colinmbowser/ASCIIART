package com.colinmbowser.asciiart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AsciiFinder
{
    int textWidth;
    int textHeight;
    int widthTextSquaresNum;
    int heightTextSquaresNum;
    HashMap<Character, BufferedImage> chars = new HashMap<>();
    public void AsciiBrain(BufferedImage edgeImage, String imageName, String[] args)
    {
        //int i = 0; used to make directory to check all the characters are outputting correctly
        String characterSet = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

        char[] chararacters = characterSet.toCharArray();
        for (char masterKey : chararacters)
        {
            BufferedImage charImage = getCharToImage(masterKey);// get the image
            chars.put(masterKey, charImage);
            // used to make directory to check all the characters are outputting correctly
/*            try
            {
                ImageIO.write(charImage, "png", new File("Text_Pictures/" + "text_picture_" + i + ".png"));
            }
            catch (IOException e)
            {
                //e.printStackTrace();
                System.out.println("Error: Failed to save text image" + i + "_text.png\"");
                System.exit(0);
            }
            i++;*/
        }
        BufferedImage newEdgeImage = distortImage(edgeImage, args[2]);
        TextFileManip(newEdgeImage, imageName);
    }

    public BufferedImage getCharToImage(char CharText)
    {
        BufferedImage charImage2 = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graph2d2 = charImage2.createGraphics();
        Font font = new Font("monospaced", Font.BOLD, 32);
        graph2d2.setFont(font);
        FontMetrics fontm = graph2d2.getFontMetrics();
        textWidth = fontm.stringWidth(String.valueOf(CharText));
        textHeight = fontm.getHeight();
        graph2d2.dispose();
        charImage2 = new BufferedImage(textWidth, textHeight, BufferedImage.TYPE_BYTE_GRAY);
        graph2d2 = charImage2.createGraphics();

        graph2d2.setFont(font);
        fontm = graph2d2.getFontMetrics();
        graph2d2.setColor(Color.WHITE);
        graph2d2.fillRect(0, 0, textWidth, textHeight);
        graph2d2.setColor(Color.BLACK);
        graph2d2.drawString(String.valueOf(CharText), 0, fontm.getAscent());
        graph2d2.dispose();
        //System.out.println(textWidth + " " + textHeight);

        return charImage2;
    }

    public BufferedImage distortImage(BufferedImage edgeImage, String desiredWidthString)
    {
        // int textWidth Global Variable
        // int textHeight Global Variable
        int oldImageWidth;
        int oldImageHeight;
        //int widthTextSquaresNum Global Variable
        //int heightTextSquaresNum Global Variable
        int newImageWidth;
        int newImageHeight;
        double desiredWidth;
        double desiredHeight;
        double factoringScale;

        // i have a headache
        desiredWidth = Integer.parseInt(desiredWidthString);
        oldImageWidth = edgeImage.getWidth();
        oldImageHeight = edgeImage.getHeight();

        widthTextSquaresNum = (int)Math.round(desiredWidth / textWidth);
        newImageWidth = widthTextSquaresNum * textWidth;
        factoringScale = (double)newImageWidth / oldImageWidth;
        //System.out.println(factoringScale); // to verify factoringscale is decimal

        desiredHeight = factoringScale * oldImageHeight;
        heightTextSquaresNum = (int)desiredHeight / textHeight;
        newImageHeight = heightTextSquaresNum * textHeight;

        BufferedImage distortedImage = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = distortedImage.createGraphics();
        g.drawImage(edgeImage, 0, 0, newImageWidth, newImageHeight, null);
        g.dispose();

/*        try
        {
            //ImageIO.write(grayScale, "png", new File(imageName + "_gray_scale.png"));
            ImageIO.write(distortedImage, "png", new File( "distorted_edge.png"));
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("Error: Failed to save edge image distorted_edge.png\"");
        }*/

        return distortedImage;
    }

    public void TextFileManip(BufferedImage newEdgeImage, String imageName)
    {
        String asciiTxtImage ="";


        for (int h = 0; h < heightTextSquaresNum; h++)
        {
            System.out.println("Written " + h + " line out of " + heightTextSquaresNum);
            for (int w = 0; w < widthTextSquaresNum; w++)
            {
                int bestMatch = 0;
                char bestChar = ' ';

                int posW = w * textWidth;
                int posH = h * textHeight;
                for (Map.Entry<Character, BufferedImage> entry : chars.entrySet())
                {
                    char changeC = entry.getKey();
                    BufferedImage charImage = entry.getValue();
                    char charVal = changeC;
                    int matchVal = 0;

                    int textH = 0;
                    for (int smallRectH = posH; smallRectH < posH + textHeight; smallRectH++)
                    {
                        int textW = 0;
                        for (int smallRectW = posW; smallRectW < posW + textWidth; smallRectW++)
                        {
                            if (newEdgeImage.getRGB(smallRectW, smallRectH) == charImage.getRGB(textW, textH))
                            {
                                matchVal++;
                            }
                            textW++;
                        }
                        textH++;
                    }
                    if (matchVal > bestMatch)
                    {
                        bestMatch = matchVal;
                        bestChar = charVal;
                    }
                }
                asciiTxtImage = asciiTxtImage + bestChar;
            }
            asciiTxtImage = asciiTxtImage + "\n";
        }
        System.out.println("Written " + heightTextSquaresNum + " line out of " + heightTextSquaresNum);
        System.out.println("100% Written");
        try
        {
            //saves text image to new text file
            BufferedWriter TextArtDrawer = new BufferedWriter(new FileWriter(imageName + ".txt"));
            TextArtDrawer.write(asciiTxtImage);
            TextArtDrawer.flush();
            TextArtDrawer.close();
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("Error: Failed to save text file \"" + imageName + "_asciiArt.txt\"");
        }
        //System.out.println(asciiTxtImage); // will output image in terminal
    }
}