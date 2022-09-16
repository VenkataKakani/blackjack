package blackjack;

import java.util.*;

// Black Jack
public class BlackjackGameDemo {
    public static void main(String[] args) {
        System.out.print("Blackjack ");
        Scanner sc = new Scanner(System.in);
        int playerCount = Integer.parseInt(sc.nextLine());
        BlackJack blackJack = new BlackJack();
        blackJack.startGame(playerCount);
    }
}
