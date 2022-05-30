package com.merure.checkers;

import com.merure.checkers.game.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.awt.*;

@Configuration
public class CheckersConfiguration {

    private final Game game;

    public CheckersConfiguration(Game game) {
        this.game = game;
    }

    /**
     * The window where checkers game will be shown.
     *
     * @return @JFrame window
     */
    @Bean
    JFrame frame() {
        JFrame window = new JFrame("Checkers");

        window.setContentPane(this.game);
        window.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screenSize.width - window.getWidth()) / 2, (screenSize.height - window.getHeight()) / 2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        window.setResizable(false);
        window.setVisible(true);

        return window;
    }
}
