package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleGame;
import com.jitterted.ebp.blackjack.domain.Game;

// Application Startup & Assembler
public class Blackjack {

    public static void main(String[] args) {
        Game game = new Game();
        // REMINDER! In general, Entities aren't directly passed in to Adapters
        ConsoleGame consoleGame = new ConsoleGame(game);
        consoleGame.start();
    }

}
