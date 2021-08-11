package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    public void uponInitialDealWithNoBlackjackPlayerIsNotDone() throws Exception {
        Game game = new Game(StubDeck.playerNotDealtBlackjack());

        game.initialDeal();

        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    public void playerDealtBetterHandThanDealerWhenStandsThenWins() throws Exception {
        Game game = new Game(StubDeck.playerStandsAndBeatsDealer());
        game.initialDeal();

        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    public void standResultsInDealerDrawingCardOnTheirTurn() throws Exception {
        Deck dealerBeatsPlayerAfterDrawingAdditionalCardDeck =
                new StubDeck(Rank.TEN,   Rank.QUEEN,
                             Rank.NINE,  Rank.FIVE,
                                         Rank.SIX);
        Game game = new Game(dealerBeatsPlayerAfterDrawingAdditionalCardDeck);
        game.initialDeal();

        game.playerStands();

        assertThat(game.dealerHand().cards())
                .hasSize(3);
    }

    @Test
    public void playerHitsAndGoesBustThenPlayerLoses() throws Exception {
        Deck playerHitsAndGoesBustDeck = StubDeck.playerHitsAndGoesBust();
        Game game = new Game(playerHitsAndGoesBustDeck);
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BUSTED);
    }

    @Test
    public void playerDealtBlackjackUponInitialDealThenImmediatelyWinsAndPlayerIsDone() throws Exception {
        Deck playerDealtBlackjack = StubDeck.playerDealtBlackjack();
        Game game = new Game(playerDealtBlackjack);

        game.initialDeal();

        assertThat(game.isPlayerDone())
                .isTrue();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_WINS_BLACKJACK);
    }

}