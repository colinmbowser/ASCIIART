package com.colinmbowser.asciiart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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

        //HashMap<Character, BufferedImage> chars = new HashMap<>();

        char[] chararacters = characterSet.toCharArray();
        for (char c : chararacters)
        {
            BufferedImage charImage = getCharToImage(c);// get the image
            chars.put(c, charImage);
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
        TextFileManip(newEdgeImage);
    }

    public BufferedImage getCharToImage(char CharText)
    {
        BufferedImage charImage2 = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graph2d2 = charImage2.createGraphics();
        Font font = new Font("monospaced", Font.BOLD, 32); //move this to the upper class and pass it in
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

        desiredWidth = Integer.parseInt(desiredWidthString);
        oldImageWidth = edgeImage.getWidth();
        oldImageHeight = edgeImage.getHeight();

        widthTextSquaresNum = (int)Math.round(desiredWidth / textWidth);
        newImageWidth = widthTextSquaresNum * textWidth;
        factoringScale = (double)newImageWidth / oldImageWidth;
        //System.out.println(factoringScale);

        desiredHeight = factoringScale * oldImageHeight;
        heightTextSquaresNum = (int)desiredHeight / textHeight;
        newImageHeight = heightTextSquaresNum * textHeight;

        BufferedImage distortedImage = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = distortedImage.createGraphics();
        g.drawImage(edgeImage, 0, 0, newImageWidth, newImageHeight, null);
        g.dispose();

        try
        {
            //ImageIO.write(grayScale, "png", new File(imageName + "_gray_scale.png"));
            ImageIO.write(distortedImage, "png", new File( "distorted_edge.png"));
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("Error: Failed to save edge image distorted_edge.png\"");
        }

        return distortedImage;
    }

    public void TextFileManip(BufferedImage newEdgeImage)
    {



    }
}