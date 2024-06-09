package project;
import project.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private int wage;
    private String name;

    public Player(int wage, String name) {
        this.wage = wage;
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public int calculateBestValue() {
        int totalValue = 0;
        int aceCount = 0;

        for (Card card : hand) {
            if (card.isAce()) {
                aceCount++;
            }
            totalValue += card.getValue();
        }

        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }

    public int getBjCardValues() {
        return calculateBestValue();
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public String getName() {
        return name;
    }
}