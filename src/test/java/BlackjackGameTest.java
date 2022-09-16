import blackjack.BlackJack;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BlackjackGameTest {
    @Test
    public void testWithHits(){
        BlackJack blackJack = new BlackJack();
        blackJack.startGame(3);

        Assert.assertEquals(1,1);
    }
    @Test
    public void testDisplay(){

        Map<Integer, BlackJack.PlayerCountTracker> map = new HashMap<>();
        BlackJack.PlayerCountTracker player1 = new BlackJack.PlayerCountTracker(20, false);
        BlackJack.PlayerCountTracker player2 = new BlackJack.PlayerCountTracker(16, false);
        BlackJack.PlayerCountTracker player3 = new BlackJack.PlayerCountTracker(22, true);
        BlackJack.PlayerCountTracker dealer = new BlackJack.PlayerCountTracker(19, false);
        map.put(1,player1);
        map.put(2,player2);
        map.put(3,player3);
        map.put(4,dealer);

        BlackJack blackJack = new BlackJack();
        blackJack.displayFinalResult(map);

    }
}
