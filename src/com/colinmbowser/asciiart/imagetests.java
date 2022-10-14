package com.colinmbowser.asciiart;

        import java.awt.Graphics;
        import java.awt.Image;
        import java.awt.image.BufferedImage;
        import javax.swing.JPanel;

public class imagetests extends JPanel {

    public void paint(Graphics g) {
        Image img = createImageWithText();
        g.drawImage(img, 20, 20, this);
    }

    private Image createImageWithText() {
        BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        g.drawString("you can't hurry love", 20, 20);
        g.drawString("my name is empty", 20, 40);
        g.drawString("world by world", 20, 60);
        g.drawString("solider boy up in it yo", 20, 80);
        g.drawString("words yo", 20, 100);

        return bufferedImage;
    }
}
/*    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new imagetests());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }*/