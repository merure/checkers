package com.merure.checkers.game;

import javax.swing.*;
import java.awt.*;

/**
 * Prepares JPanel where Checkers game will be shown.
 */
public class Game extends JPanel {

    public Game() {

        // Configure JPanel
        setLayout(null);  // I will do the layout myself.
        setPreferredSize(new Dimension(350, 250));
        setBackground(new Color(255, 255, 255));  // White background.

        // Assign UI components to JPanel
        UI ui = new UI();
        add(ui);
        add(ui.newGameButton);
        add(ui.resignButton);
        add(ui.message);
        //        this.opts = new OptionPanel(this); // TODO refactor buttons as part of optionPanel

        // Configure UI components
        ui.setBounds(20, 20, 164, 164); // Note:  size MUST be 164-by-164 !
        ui.newGameButton.setBounds(210, 60, 120, 30);
        ui.resignButton.setBounds(210, 120, 120, 30);
        ui.message.setBounds(0, 200, 350, 30);
    }


}
