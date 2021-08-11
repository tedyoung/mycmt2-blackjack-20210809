package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private final Game game;

    @Autowired
    public BlackjackController(Game game) {
        this.game = game;
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        populateGameViewInto(model);
        return "blackjack";
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        populateGameViewInto(model);
        model.addAttribute("outcome", game.determineOutcome().displayValue());
        return "done";
    }

    @PostMapping("/start-game")
    public String startGame() {
        game.initialDeal();
        return redirectPage();
    }

    @PostMapping("/hit")
    public String hitCommand() {
        game.playerHits();
        return redirectPage();
    }

    @PostMapping("/stand")
    public String standCommand() {
        game.playerStands();
        return redirectPage();
    }

    private String redirectPage() {
        if (game.isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

    private void populateGameViewInto(Model model) {
        GameView gameView = GameView.of(game);
        model.addAttribute("gameView", gameView);
    }

}
