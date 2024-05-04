import java.lang.reflect.Array;
import java.util.*;


public class Poker {

    public static class StartSettings {

        //Here you choose 1. Player and ai pot, 2. Amount of rounds until the game ends(unless capital reaches 0).

        public static int[] startCapitalForPlayerAndAI() {
            Scanner scan = new Scanner(System.in);

            System.out.println("Input the amount of start capital you want the player to have: ");
            int playerCapital = scan.nextInt();
            System.out.println("Input the amount of start capital you want the AI to have: ");
            int aiCapital = scan.nextInt();
            while ((aiCapital >= 1000000 || playerCapital >= 1000000) && (playerCapital < 1 || aiCapital < 1)) {
                System.out.println("The values need to exceed $1 and be less than 1 000 000. Please input again.");
                System.out.println("Input Player capital: ");
                playerCapital = scan.nextInt();
                System.out.println("Input AI capital: ");
                aiCapital = scan.nextInt();
            }

            System.out.println("You will have " + playerCapital + "$");
            System.out.println("The AI will have " + aiCapital + "$");

            return new int[]{playerCapital, aiCapital};

        }

        public static int amountOfRounds() {
            Scanner scan = new Scanner(System.in);
            System.out.println("How many rounds do you want? ");
            int rounds = scan.nextInt();
            while(!(rounds > 1 )){
                System.out.println("Input a number greater than 1");
                rounds = scan.nextInt();
            }
            return rounds;
        }

    }


    public static class Deck {
        public static ArrayList<String> deckOfCards() {
            String[] suits = {"HEARTS", "SPADES", "DIAMONDS", "CLUBS"};
            String[] ranks = {"TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING", "ACE"};

            //The reason im using arrayLists is because I do not have to keep making new arrays for every update in the deck.(since they update the size of the array automatically when something is removed).
            ArrayList<String> deck = new ArrayList<>();
            for (String suit : suits) {
                for (String rank : ranks) {
                    deck.add(rank + "-" + suit);
                }
            }
            return deck;

        }

        public static ArrayList<String> shuffleDeck(ArrayList<String> deck) {
            Collections.shuffle(deck);
            return deck;
        }

        public static ArrayList<String> removeCardsAfterDraw(ArrayList<String> shuffledDeck, String[] drawnCards) {
            for (int i = 0; i < 4; i++) {
                shuffledDeck.remove(String.valueOf(drawnCards[i]));
            } //Removes the cards drawn from the deck.
            return shuffledDeck;
        }
    }

    public static class Card {
        public static String[] drawPlayerAndAICards(ArrayList<String> shuffledDeck) {
            String[] cardsInPlayerAndEnemyHand = new String[4];
            for (int i = 0; i < cardsInPlayerAndEnemyHand.length; i++) {
                cardsInPlayerAndEnemyHand[i] = shuffledDeck.get(i);

            }
            return cardsInPlayerAndEnemyHand;
        }

        public static String[] aiHand(String[] cardsInHand) {
            String[] cardsInAIHand = new String[2];
            cardsInAIHand[0] = cardsInHand[2];
            cardsInAIHand[1] = cardsInHand[3];
            return cardsInAIHand;
        }

        public static String[] playerHand(String[] cardsInHand) {
            String[] cardsInPlayerHand = new String[2];
            cardsInPlayerHand[0] = cardsInHand[0];
            cardsInPlayerHand[1] = cardsInHand[1];
            return cardsInPlayerHand;
        }

        //Map Suit and rank to their respective integers.
        //Add onto this by making wincons later

        public static ArrayList<Integer> convertSuitToNum(ArrayList<String> cardsInPlayerAndOnBoard) {
            //Take input array and convert it to number to compare.
            //Will be 5 cards long, 1 for AI and 1 for Player.
            //'1' is hearts, '2' is clubs, '3' is spades, '4' is diamonds.
            //It will return an arraylist like [1,2,1,3,4]. This means they have 2 hearts and no win-conditions met through suits.

            int[] numbersArray = new int[cardsInPlayerAndOnBoard.size()];
            for (int i = 0; i < cardsInPlayerAndOnBoard.size(); i++) {
                String[] splitArray = cardsInPlayerAndOnBoard.get(i).split("-");
                if (splitArray[1].equals("HEARTS")) {
                    numbersArray[i] = 1;
                } else if (splitArray[1].equals("CLUBS")) {
                    numbersArray[i] = 2;
                } else if (splitArray[1].equals("SPADES")) {
                    numbersArray[i] = 3;
                } else {
                    numbersArray[i] = 4;
                }
            }
            ArrayList<Integer> numbersArrayAsArrayList = new ArrayList<>();
            for (int i = 0; i < numbersArray.length; i++) {
                numbersArrayAsArrayList.add(numbersArray[i]);
            }
            return numbersArrayAsArrayList;
        }


        public static ArrayList<Integer> convertRankToNum(ArrayList<String> progressionBoard) {


            int[] numbersArray = new int[progressionBoard.size()];
            for (int i = 0; i < progressionBoard.size(); i++) {
                String[] splitArray = progressionBoard.get(i).split("-");
                //Could use a hashmap to assign each value pair to a "key" number. But i think this is fine for now.
                switch (splitArray[0]) {
                    case "TWO":
                        numbersArray[i] = 2;
                        break;
                    case "THREE":
                        numbersArray[i] = 3;
                        break;
                    case "FOUR":
                        numbersArray[i] = 4;
                        break;
                    case "FIVE":
                        numbersArray[i] = 5;
                        break;
                    case "SIX":
                        numbersArray[i] = 6;
                        break;
                    case "SEVEN":
                        numbersArray[i] = 7;
                        break;
                    case "EIGHT":
                        numbersArray[i] = 8;
                        break;
                    case "NINE":
                        numbersArray[i] = 9;
                        break;
                    case "TEN":
                        numbersArray[i] = 10;
                        break;
                    case "JACK":
                        numbersArray[i] = 11;
                        break;
                    case "QUEEN":
                        numbersArray[i] = 12;
                        break;
                    case "KING":
                        numbersArray[i] = 13;
                        break;
                    case "ACE":
                        numbersArray[i] = 14;
                        break;
                }
            }
            ArrayList<Integer> numbersArrayAsArrayList = new ArrayList<>();
            for (int item : numbersArray) {
                numbersArrayAsArrayList.add((item));
            }
            return numbersArrayAsArrayList;
        }

        public static ArrayList<String> cardsOnBoardProgression(ArrayList<String> cardsInPlayerAndOnBoard, int turnCounter, ArrayList<String> storeValuesRemoved, boolean playerTurn) {


            if (turnCounter == 3 && playerTurn) {
                //Store the last 2 elements in the array.
                storeValuesRemoved.add(cardsInPlayerAndOnBoard.get(5));
                storeValuesRemoved.add(cardsInPlayerAndOnBoard.get(6));

                cardsInPlayerAndOnBoard.remove(6);
                cardsInPlayerAndOnBoard.remove(5);

                return cardsInPlayerAndOnBoard;
            } else if (turnCounter == 4) {
                cardsInPlayerAndOnBoard.add(storeValuesRemoved.get(0));
                return cardsInPlayerAndOnBoard;

            } else if (turnCounter == 5) {
                cardsInPlayerAndOnBoard.add(storeValuesRemoved.get(1));
                return cardsInPlayerAndOnBoard;
            }
            return cardsInPlayerAndOnBoard;
        }
    }
    public static class GameState {
        public static double smallAndBigBlindPot(int[] amountOfCapitalInAIAndPlayer) {
            //take 10 % of big and 5 % of small blind to put into pot.
            int count = 0;
            double[] arrayWithBigAndSmallBlind = new double[2];
            for (int i = 0; i < amountOfCapitalInAIAndPlayer.length; i++) {
                count++;
                if (count == 1) {
                    arrayWithBigAndSmallBlind[i] = amountOfCapitalInAIAndPlayer[i] * 0.05;

                } else {
                    arrayWithBigAndSmallBlind[i] = amountOfCapitalInAIAndPlayer[i] * 0.10;
                }

            }

            return Arrays.stream(arrayWithBigAndSmallBlind).sum();
        }

        public static double[] removePot(int[] startCapital) {
            double startCapitalPlayer = startCapital[0];
            double startCapitalAI = startCapital[1];
            double playerMinusPot = startCapitalPlayer - startCapitalPlayer * 0.05;
            double AIMinusPot = startCapitalAI - startCapitalAI * 0.10;

            return new double[]{playerMinusPot, AIMinusPot};


        }

        public static void isTurn(int Turn) {
            System.out.println();
            System.out.println("Turn " + Turn + ":");
        }

        public static double[] updateCapital(double returnedPlayerBet, double returnedAIBet, double[] playerAndAiCapital) {
            double[] capital = new double[2];

            capital[0] = playerAndAiCapital[0] - returnedPlayerBet; // Subtract AI's bet first
            capital[1] = playerAndAiCapital[1] - returnedAIBet; // Subtract player's bet second
            return capital;

        }

        public static double updatePot(double returnedPlayerBet, double returnedAIBet, double totalPot) {
            return totalPot + returnedPlayerBet + returnedAIBet;
        }

        public static void printCapitalAndPot(double[] playerAndAICapital, double totalPot) {
            System.out.println("The pot is: " + totalPot);
            System.out.println("Player Capital: " + playerAndAICapital[0]);
            System.out.println("AI Capital: " + playerAndAICapital[1]);
        }

        public static ArrayList<String> getCardsOnBoard(ArrayList<String> deck) {
            ArrayList<String> cardsOnBoard = new ArrayList<String>();

                for (int i = 0; i < 5; i++) {
                String cards = deck.get(i);
                cardsOnBoard.add(cards);
            }
            return cardsOnBoard;
        }

        public static void showCardsOnBoard(ArrayList<String> deck, int turnCounter, int amountOfCards) {
            System.out.println("Card(s) on board: ");
            //FIX FOR LOOP


            for (int i = 0; i < amountOfCards; i++) {
                for (int j = 0; j <= turnCounter; j++) {
                    if (i == j) {
                        System.out.print(deck.get(j));

                    }
                }
                System.out.print(" ");
            }



        }


        public static ArrayList<String> getPlayerHandAndBoard(String[] playerHand, ArrayList<String> cardsOnBoard) {


            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(playerHand));
            arrayList.addAll(cardsOnBoard);
            return arrayList;
        }
    }


    public static class WinCons {




        //This class will have a method which takes two arrays - AI and Player cards in hand + cards on board. - after every turn and checks if you have any of the win conditions (such as) pair or three of a kind.
        public static int highCard(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> ranksAI, ArrayList<Integer> ranksWithBoardAI,int turnCounter){
            //1 is win for player
            //0 is win for ai
            //2 is same sum
            if(turnCounter == 2){
                //What the function does is that it converts it into a stream where it then maps each elements value to its integer representation. and then sums it.
                int playerSum = ranks.stream().mapToInt(Integer::intValue).sum();
                int AISum = ranksAI.stream().mapToInt(Integer::intValue).sum();
                if(playerSum > AISum){
                    return 1;
                } else if (AISum > playerSum) {
                    return 0;
                }else{
                    return 2;
                }
            }else if(turnCounter >= 3 ) {
                int playerSum = ranksWithBoard.stream().mapToInt(Integer::intValue).sum();
                int AISum = ranksWithBoardAI.stream().mapToInt(Integer::intValue).sum();
                if(playerSum > AISum){
                    return 1;
                }else if(AISum > playerSum){
                    return 0;
                }else{
                    return 2;
                }
            }else{
                return 0;
            }

        }
        public static ArrayList<Integer> pair(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> ranksAI, ArrayList<Integer> ranksWithBoardAI,int turnCounter){
            //Check their rank and if they are the same.
            ArrayList<Integer> ar = new ArrayList<>();
            int num;
            int count = 0;
            switch(turnCounter){
                case 2:
                    for (int i = 2; i <= 13; i++) {

                        for (int j = 0; j < ranks.size(); j++) {
                             num = ranks.get(j);
                             if(num == i){
                                 count++;
                             }
                        }
                        System.out.println(count);
                    }
                    break;
                case 3,4,5:

            }
            return ranks;

        }
        /*
        public static boolean twoPair(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> ranksAI, ArrayList<Integer> ranksWithBoardAI, boolean isPlayerTurn){
            //*2
        }

        public static boolean straight(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> ranksAI, ArrayList<Integer> ranksWithBoardAI, boolean isPlayerTurn){
        }

        public static boolean flush(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> ranksAI, ArrayList<Integer> ranksWithBoardAI, boolean isPlayerTurn){

        }

        public static boolean fullHouse(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> ranksAI, ArrayList<Integer> ranksWithBoardAI, boolean isPlayerTurn){

        }
        public static boolean straightFlush(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> ranksAI, ArrayList<Integer> ranksWithBoardAI, ArrayList<Integer> suits, ArrayList<Integer> suitsAI, ArrayList<Integer> suitsWithBoardAI, boolean isPlayerTurn){

        }
        public static boolean royalFlush(ArrayList<Integer> ranks, ArrayList<Integer> ranksWithBoard, ArrayList<Integer> ranksAI, ArrayList<Integer> ranksWithBoardAI, ArrayList<Integer> suits, ArrayList<Integer> suitsAI, ArrayList<Integer> suitsWithBoardAI, boolean isPlayerTurn){

        }
        public static boolean whoWon(){

        }

         */


    }


    public static class Player {


        public static void showPlayerHand(String[] cardsInPlayerHand) {
            System.out.println("You drew: " + Arrays.toString(cardsInPlayerHand).replace('[', '(').replace(']', ')'));
        }

        public static int chosenPlayerOperation() {
            //if you act first you can either check or bet, or fold.
            //If you are second to act you can either check, raise or fold or call.
            //Also since the player is always first, there is no need to add a raise or call function for the player.

            Scanner scan = new Scanner(System.in);
            System.out.println("'1' is CHECK, '2' is BET, '3' is FOLD ");
            int action = scan.nextInt();
            while ((action != 1) && (action != 2) && (action != 3)) {
                System.out.println("Invalid input");
                System.out.println("'1' is CHECK, '2' is BET, '3' is FOLD ");
                action = scan.nextInt();
            }

            return action;
        }

        public static double runPlayerOperation(double[] playerAndAICapital, boolean isCurrentlyPlayerTurn, double totalPot, int playerOperation, boolean pokerIsRunning) {
            //initilize double to access it outside the if-statements.
            double betToAddToPot = 0.0;
            if (playerOperation == 1) {
                //Check
                Poker.Player.check();
            } else if (playerOperation == 2) {
                //Bet
                betToAddToPot = Poker.Player.returnTotalBet(playerAndAICapital, isCurrentlyPlayerTurn, totalPot);
                return betToAddToPot;
            } else if (playerOperation == 3) {
                //Fold
                Poker.Player.fold(totalPot, pokerIsRunning);
            }

            return betToAddToPot;
        }


        public static double returnTotalBet(double[] playerAndAICapitalMinusPot, boolean isPlayerTurn, double totalPot) {
            Scanner scan = new Scanner(System.in);

            if (isPlayerTurn) {
                System.out.println("You have chosen BET");
                System.out.println("Choose what to BET");
                System.out.println("'1' is 1/4-OF-POT, '2' is 1/2-OF-POT, '3' is ALL-IN, '4' is a personal amount: ");
                System.out.println();
                int betAction = scan.nextInt();
                while (betAction != 1 && betAction != 2 && betAction != 3 && betAction != 4) {
                    System.out.println("Invalid input");
                    betAction = scan.nextInt();
                }
                switch (betAction) {
                    case 1:
                        if (playerAndAICapitalMinusPot[0] > totalPot * 0.25) {

                            return totalPot * 0.25;

                        }
                    case 2:
                        if (playerAndAICapitalMinusPot[0] > totalPot * 0.5) {
                            return totalPot * 0.5;

                        }
                    case 3:
                        return playerAndAICapitalMinusPot[0];


                    case 4:
                        System.out.println("How much? ");
                        double personalBet = scan.nextDouble();
                        while (personalBet > playerAndAICapitalMinusPot[0]) {
                            System.out.println("The inputed value exceeds your total capital: ");
                            System.out.println("Your current pot is: " + playerAndAICapitalMinusPot[0]+"$");
                            personalBet = scan.nextDouble();
                        }
                        return personalBet;
                }

            } else {
                System.out.println("The AI chose bet");
                System.out.println("The ai went all in");
                System.out.println(playerAndAICapitalMinusPot[1]+"$ has been added to the pot");
                //Add more functionality so the ai does not always choose ALL-IN

                return playerAndAICapitalMinusPot[1];

            }

            return 0;
        }

        public static void check() {
            //Add logic later
            System.out.println("CHECK");
        }

        public static void fold(double totalPot, boolean pokerIsRunning) {
            System.out.println("You folded");
            System.out.println("AI wins the total pot: " + totalPot+"$");
            pokerIsRunning = false;
            //Add a statement that restarts the game
        }

    }
    public static class AI {
        public static int chosenAIOperation() { // Will improve with more advanced decision-making later.
            Random random = new Random();
            //since 0 is the lowest number i dont need a minimum value.
            return random.nextInt((5) + 1);
        }

        public static double runAIOperation(int playerOperation, double[] playerAndAICapital, boolean isCurrentlyPlayerTurn, double totalPot, double returnedBet, boolean pokerIsRunning) {
            int aiOperation = Poker.AI.chosenAIOperation();
            double aiBet = 0.0;

            switch (aiOperation) {
                case 1:
                    if (playerOperation == 1) {
                        Poker.AI.checkAI();
                    }
                    break;
                case 2:
                    if (playerOperation != 2) {
                        aiBet = Poker.Player.returnTotalBet(playerAndAICapital, isCurrentlyPlayerTurn, totalPot);
                        return aiBet;
                    }
                    break;
                case 3:
                    return AI.AIFold(playerAndAICapital, totalPot, pokerIsRunning);
                case 4:
                    if (playerOperation == 2) {
                        return Poker.AI.raiseAI(playerAndAICapital, totalPot, returnedBet);
                    }
                    break;
                case 5:
                    if (playerOperation == 2) {
                        return Poker.AI.callAI(playerAndAICapital, returnedBet);
                    }
                    break;
            }

            return 0.0;
        }

        public static double raiseAI(double[] playerAndAICapital, double totalPot, double returnedBet) { //It's a raise if someone has already made a bet.
            if (playerAndAICapital[1] > returnedBet) {
                System.out.println("The ai chose: RAISE");
                System.out.println("The ai has raised by "+playerAndAICapital[1] * 0.5);
                return playerAndAICapital[1] * 0.5;
                //Remember to add a method that updates both the pot and the capital
            } else {
                System.out.println("Bug with RAISE AI");
                return 0.0;
            }
        }

        public static double callAI(double[] playerAndAICapital, double returnedBet) { //Can only be done if the prior player has made a bet. (is to match the bet).

            if (playerAndAICapital[1] >= returnedBet) {
                System.out.println("The AI chose: CALL");
                return returnedBet;
            } else {
                System.out.println("Bug with callAI");
                return 0.0;
            }
        }

        public static void checkAI() {
            System.out.println("The AI chose: CHECK");
            System.out.println("Moving onto next turn");//This literally does nothing besides move the turn forward.
        }

        public static double AIFold(double[] playerAndAICapital, double totalPot, boolean pokerIsRunning) {
            System.out.println("The AI chose: FOLD");
            System.out.println(playerAndAICapital[1]+"$ has been added to pot");
            pokerIsRunning = false;
            return playerAndAICapital[1] + totalPot;
        }
    }


}






