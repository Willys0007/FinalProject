import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;


public class Main {


    public static void main(String[] args) throws ScriptException {

        Random random = new Random();



        System.out.println("Do you want to play a game of your choice [Y/N] ");
        Scanner scan = new Scanner(System.in);


        while(true) {
            String startGameOrNot = scan.next();
            if (startGameOrNot.equalsIgnoreCase("y")) {

                int correctgame = gameSelection();
                //Calculator game
                if (correctgame == 1) {
                    Main.runCalculator(scan);
                    break;
                }else if(correctgame == 2) {
                    boolean pokerIsRunning = true;
                    int turnCounter = 1;
                    boolean isPlayerTurn = true;
                    int roundCounter = 0;
                    int amountOfCards = 3;

                    int[] startCapital = Poker.StartSettings.startCapitalForPlayerAndAI(); //important to store the start capital, and update later.
                    int TOTAL_ROUNDS = Poker.StartSettings.amountOfRounds();
                    Main.runPoker(isPlayerTurn, pokerIsRunning, startCapital, amountOfCards, turnCounter, TOTAL_ROUNDS, roundCounter);

                    break;
                }


            } else {
                System.out.println("Welp Cya");
                break;
            }
        }

    }

    public static int gameSelection() {
        Scanner scan = new Scanner(System.in);

        int whichgame;
        do {
            System.out.println("Input a value for a game, '1' is a calculator, '2' is poker: ");
            whichgame = scan.nextInt();

        } while (whichgame != 1 && whichgame != 2);
        return whichgame;
    }

    public static void runPoker(boolean isPlayerTurn, boolean pokerIsRunning, int[] startCapital, int amountOfCards, int turnCounter, int TOTAL_ROUNDS, int roundCounter){

        double[] playerAndAICapital = Poker.GameState.removePot(startCapital);

        //Fix the deck so it updates accordingly
        ArrayList<String> deckOfCard = Poker.Deck.deckOfCards();
        ArrayList<String> shuffledDeck = Poker.Deck.shuffleDeck(deckOfCard);

        //draw cards and get cards on board and in hand.
        String[] cardsInHand = Poker.Card.drawPlayerAndAICards(shuffledDeck);
        ArrayList<String> deck = Poker.Deck.removeCardsAfterDraw(shuffledDeck, cardsInHand);
        String[] cardsInPlayerHand = Poker.Card.playerHand(cardsInHand);
        String[] cardsInAIHand = Poker.Card.aiHand(cardsInHand);
        ArrayList<String> cardsOnBoard = Poker.GameState.getCardsOnBoard(deckOfCard);

        ArrayList<String> cardsInPlayerHandArrayList = new ArrayList<>(Arrays.asList(cardsInPlayerHand));
        ArrayList<String> cardsInAIHandArrayList = new ArrayList<>(Arrays.asList(cardsInAIHand));

        ArrayList<String> cardsInPlayerAndOnBoard = Poker.GameState.getPlayerHandAndBoard(cardsInPlayerHand, cardsOnBoard);
        ArrayList<String> cardsInAIAndOnBoard = Poker.GameState.getPlayerHandAndBoard(cardsInAIHand, cardsOnBoard);
        double totalPot = 0;
        ArrayList<String> storeValuesRemoved = new ArrayList<>();


        while(pokerIsRunning && TOTAL_ROUNDS >= roundCounter){
            switch(turnCounter){
                //Fix all problems related to access between variables
                //This turn the players only put in the big and small blinds into the pot
                case 1: //Turn 1
                    roundCounter++;
                    System.out.println("Round "+ roundCounter + " is starting!");
                    Poker.GameState.isTurn(turnCounter);
                    totalPot = Poker.GameState.smallAndBigBlindPot(startCapital);
                    System.out.println("POT: "+totalPot+"$");
                    System.out.println("-----------------------------------------");
                    turnCounter++;
                    break;


                case 2: //Turn 2

                    //Fix the access of variables
                    Poker.GameState.isTurn(turnCounter);
                    System.out.println("Shuffling cards... ");

                    Poker.Player.showPlayerHand(cardsInPlayerHand);
                    int playerOperation = Poker.Player.chosenPlayerOperation();

                    //Execute chosen operation:
                    double playerBet = Poker.Player.runPlayerOperation(playerAndAICapital,isPlayerTurn, totalPot, playerOperation, pokerIsRunning);
                    isPlayerTurn = false;
                    double AIBetOrRaise = Poker.AI.runAIOperation(playerOperation,playerAndAICapital,isPlayerTurn,totalPot, playerBet, pokerIsRunning);
                    //Update the capital of pot and ai and player
                    playerAndAICapital = Poker.GameState.updateCapital(playerBet, AIBetOrRaise, playerAndAICapital);
                    totalPot = Poker.GameState.updatePot(playerBet, AIBetOrRaise, totalPot);
                    ArrayList<Integer> suits = Poker.Card.convertSuitToNum(cardsInPlayerHandArrayList);
                    ArrayList<Integer> ranks = Poker.Card.convertRankToNum(cardsInPlayerHandArrayList);
                    ArrayList<Integer> suitsAI = Poker.Card.convertSuitToNum(cardsInAIHandArrayList);
                    ArrayList<Integer> ranksAI = Poker.Card.convertRankToNum(cardsInAIHandArrayList);

                    //Proceed to next turn
                    isPlayerTurn = true;



                    turnCounter++;

                    break;
                case 3, 4, 5:
                    Poker.GameState.isTurn(turnCounter);


                    ArrayList<String> cardsOnBoardProgressionPlayer = Poker.Card.cardsOnBoardProgression(cardsInPlayerAndOnBoard, turnCounter, storeValuesRemoved, isPlayerTurn);



                    Poker.GameState.showCardsOnBoard(deck, turnCounter, amountOfCards);
                    Poker.Player.showPlayerHand(cardsInPlayerHand);

                    Poker.GameState.printCapitalAndPot(playerAndAICapital, totalPot);

                    playerOperation = Poker.Player.chosenPlayerOperation();

                    playerBet = Poker.Player.runPlayerOperation(playerAndAICapital, isPlayerTurn, totalPot, playerOperation, pokerIsRunning);
                    isPlayerTurn = false;
                    ArrayList<String> cardsOnBoardProgressionAI = Poker.Card.cardsOnBoardProgression(cardsInAIAndOnBoard, turnCounter, storeValuesRemoved, isPlayerTurn);
                    AIBetOrRaise = Poker.AI.runAIOperation(playerOperation, playerAndAICapital, isPlayerTurn, totalPot, playerBet, pokerIsRunning);

                    totalPot = Poker.GameState.updatePot(playerBet, AIBetOrRaise, totalPot);
                    playerAndAICapital = Poker.GameState.updateCapital(playerBet, AIBetOrRaise, playerAndAICapital);
                    ArrayList<Integer> suitsWithBoard = Poker.Card.convertSuitToNum(cardsOnBoardProgressionPlayer);
                    ArrayList<Integer> ranksWithBoard= Poker.Card.convertRankToNum(cardsOnBoardProgressionPlayer);
                    ArrayList<Integer> suitsAIWithBoard = Poker.Card.convertSuitToNum(cardsOnBoardProgressionAI);
                    ArrayList<Integer> ranksAIWithBoard = Poker.Card.convertRankToNum(cardsOnBoardProgressionAI);
                    amountOfCards++;



                    turnCounter++;
                    break;

                default:
                    System.out.println("Checking who won: ");
                    turnCounter = 1;
                    break;
                    //make method to evaluate win cons based of all cards.
            }

        }

    }
    public static void runCalculator(Scanner scan) throws ScriptException {
        System.out.println("Starting Calculator! ");

        while (true) {


            String stringToCalculate = Calculator.keepAskingUserForInput();
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Object result = engine.eval(stringToCalculate);
            System.out.println("This is your calculation: ");
            System.out.println(stringToCalculate + "= " + result);
            System.out.println();
            System.out.println("Do you want to input another calculation? [Y/N]");
            String response = scan.next();
            if (response.equalsIgnoreCase("n")) {
                break;
            }
        }

    }
}

