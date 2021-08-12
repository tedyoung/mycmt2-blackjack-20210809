package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;

public class Game {

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();
    private final GameMonitor gameMonitor;

    private boolean playerDone;

    public Game() {
        this(new Deck());
    }

    public Game(Deck deck) {
        this(deck, game -> {});
    }

    public Game(Deck deck, GameMonitor gameMonitor) {
        this.deck = deck;
        this.gameMonitor = gameMonitor;
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
        if (playerHand.isBlackjack()) {
            playerDone = true;
        }
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public GameOutcome determineOutcome() {
        if (playerHand.isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (dealerHand.isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand.isBlackjack()) {
            return GameOutcome.PLAYER_WINS_BLACKJACK;
        } else if (playerHand.beats(dealerHand)) {
            return GameOutcome.PLAYER_BEATS_DEALER;
        } else if (playerHand.pushes(dealerHand)) {
            return GameOutcome.PLAYER_PUSHES;
        } else {
            return GameOutcome.PLAYER_LOSES;
        }
    }

    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    // QUERY
    // Snapshot in time?
    // Is it unmodifiable/immutable/protects Game's state
    // 1. clone it -- copy/snapshot, not immutable
    // 2. HandView -- with only cards() and value() methods, is immutable
    // 3. Hand implements HandView -- "live" view of hand, is immutable
    public Hand playerHand() {
        return playerHand;
    }

    public Hand dealerHand() {
        return dealerHand;
    }

    public void playerHits() {
        // GUARD/PRE-CONDITION: player is NOT done
        playerHand.drawFrom(deck);
        playerDone = playerHand.isBusted();
        gameMonitor.roundCompleted(this);
    }

    public void playerStands() {
        // GUARD/PRE-CONDITION: player is NOT done
        playerDone = true;
        dealerTurn();
        gameMonitor.roundCompleted(this);
    }

    public boolean isPlayerDone() {
        return playerDone;
    }


}
