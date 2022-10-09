package com.colinmbowser.asciiart;

import java.awt.image.BufferedImage;

public class ASCIIART
{
    public static void main(String[] args)
    {
        System.out.println("Hello. Welcome to my ASCII Art Program.");
        String imageName = args[0].substring(0, args[0].indexOf("."));

        BufferedImage firstImage = FirstImageReader.copyFirstImage(args);
        GrayScale.makeGrayScale(firstImage, imageName);

    }
}

