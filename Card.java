package project;
public class Card {
    private int value;
    private boolean isAce;

    public Card(int value) {
        if(value%13 == 0){
            this.value = 10;
        }else {
            this.value = value % 13;
        }
        this.isAce = (this.value == 1);
    }

    public int getValue() {
        if (isAce) {
            return 11; // Default to 11 for simplicity
        }
        return Math.min(this.value, 10); // Face cards are worth 10
    }

    public boolean isAce() {
        return isAce;
    }
}