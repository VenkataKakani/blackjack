package blackjack;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class BlackJack {

    public BlackJack() {
    }
    public void startGame(int noOfPlayers) {
        System.out.println("Starting game with "+ noOfPlayers + " players");
        System.out.println("Shuffling... ");

        //Start first set
        Map<Integer, List<RandomCardCombination>> map = new HashMap<>();

        String CONST1 = "Dealing to  player ";
        String CONST2 = "Dealing to  computer, card :";
        String CONST3 = "Dealing to  computer, cards :";
        for ( int i = 0; i < noOfPlayers + 1 ; i++) {
            RandomCardCombination randomCardComb = new RandomCardCombination();
            List<RandomCardCombination> rCardCombList = new ArrayList<>();
            String str = (i == 3) ? CONST2+" facedown" : CONST1+ (i+1)+"  card: "+randomCardComb.getCount()+" "+randomCardComb.getName();
            rCardCombList.add(randomCardComb);
            map.put(i,rCardCombList);
            System.out.println(str);
        }
        Map<Integer, PlayerCountTracker> playerTotalCountMap = new HashMap<>();
        map.forEach((key, value) -> {
            while(true) {
                int totalCardCount = getTotalCardCount(value);
                String str;
                str = key == 3?returnString(value, key, CONST3):returnString(value, key, CONST1);
                System.out.println(str);
                System.out.print("Hit or Stand? > ");
                Scanner ob1 = new Scanner(System.in);
                if(ob1.nextLine().equals("hit")){
                    value.add(new RandomCardCombination());
                    totalCardCount = getTotalCardCount(value);
                    playerTotalCountMap.put(key, new PlayerCountTracker(totalCardCount, false));
                    if(totalCardCount > 21){
                        str = key == 3?returnString(value, key, CONST3):returnString(value, key, CONST1);
                        System.out.println(str + " Busted over 21.");
                        playerTotalCountMap.put(key, new PlayerCountTracker(totalCardCount, true));
                        break;
                    }
                } else {
                    totalCardCount = getTotalCardCount(value);
                    playerTotalCountMap.put(key, new PlayerCountTracker(totalCardCount, false));
                    break;
                }
            }
        });
        displayFinalResult(playerTotalCountMap);
    }

    public void displayFinalResult(Map<Integer, PlayerCountTracker> playerTotalCountMap) {
        PlayerCountTracker playerCountTracker = playerTotalCountMap.get(playerTotalCountMap.size()-1);
        int dealerFinalCount = playerCountTracker.totalCount;
        String str = "";
        playerTotalCountMap.forEach((key, value) -> {
            if(dealerFinalCount > value.totalCount) {
                System.out.println("Scoring player "+key+" has "+value.totalCount+", dealer has "+dealerFinalCount+".  Dealer wins.");
            } else if(dealerFinalCount < value.totalCount && !value.isBusted){
                System.out.println("Scoring player "+key+" has "+value.totalCount+", dealer has "+dealerFinalCount+".  Player"+key+" wins.");
            } else if (value.isBusted) {
                System.out.println("Scoring player "+key+" has busted.  Dealer wins.");
            }
        });
    }

    private static int getTotalCardCount(List<RandomCardCombination> list){
        return list.stream().map(RandomCardCombination::getCount).collect(Collectors.summingInt(i->i));
    }
    private static String returnString(List<RandomCardCombination> list, int key, String con){
        String str = "";
        for (RandomCardCombination rm: list) {
            str = str + (rm.count == 1 ? ": Ace " + rm.getName() + ", " :  rm.count + " " + rm.getName() + ", ");
        }
        return key == 3 ? con + str: con + (key+1) + " cards :" +str;
    }
    @Data
    public static class PlayerCountTracker {
        int totalCount;
        boolean isBusted;

        public PlayerCountTracker(int count, boolean isBusted){
            this.totalCount = count;
            this.isBusted = isBusted;
        }
    }
    @Data
    public static class RandomCardCombination {
        int count;
        String name;

        public RandomCardCombination() {
            this.count = new Random().ints(1,10).findFirst().getAsInt();
            this.name = new String[] {"Clubs", "Hearts","Spades", "Diamonds"}[new Random().nextInt(4)];
        }
    }
}
