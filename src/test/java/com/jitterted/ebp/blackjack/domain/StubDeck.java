package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class StubDeck extends Deck {
    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    private final ListIterator<Card> iterator;

    public StubDeck(List<Card> cards) {
        this.iterator = cards.listIterator();
    }

    public StubDeck(Rank... ranks) {
        List<Card> cards = new ArrayList<>();
        for (Rank rank : ranks) {
            cards.add(new Card(DUMMY_SUIT, rank));
        }
        this.iterator = cards.listIterator();
    }

    public static Deck playerHitsAndGoesBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK,
                            Rank.THREE);
    }

    public static StubDeck playerHitsAndDoesNotGoBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.SEVEN, Rank.JACK,
                            Rank.THREE);
    }

    public static Deck playerStandsAndBeatsDealer() {
        return new StubDeck(Rank.TEN, Rank.NINE,
                            Rank.QUEEN, Rank.EIGHT);
    }

    public static Deck playerNotDealtBlackjack() {
        return new StubDeck(Rank.TEN, Rank.NINE,
                            Rank.FIVE, Rank.EIGHT);
    }

    public static Deck playerDealtBlackjack() {
        return new StubDeck(Rank.ACE, Rank.NINE,
                            Rank.JACK, Rank.EIGHT);
    }

    @Override
    public Card draw() {
        return iterator.next();
    }
}
