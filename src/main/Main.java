package main;

import javax.swing.JFrame;
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Snake");

        GamePanel gP = new GamePanel();
        window.add(gP);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gP.startGameThread();
    }
}