package com.merure.checkers;

import com.merure.checkers.game.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class CheckersApplication extends JPanel {

    public static void main(String[] args) {
        SpringApplication.run(CheckersApplication.class, args);

        JFrame window = new JFrame("Checkers");
        Game content = new Game();

        window.setContentPane(content);
        window.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screenSize.width -window.getWidth())/2, (screenSize.height -window.getHeight())/2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        window.setResizable(false);
        window.setVisible(true);
    }
}
