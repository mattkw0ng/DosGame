import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class DosGame {

    public static DosDeck deck;
    public static DeckHand discardPile;
    public static DeckHand centerRow;

    public static void main(String[] args)
    {
        //-------------------------- SETUP ------------------------------------
        //creates the deck, discard pile, center row, player array, and scanner
        deck = new DosDeck();
        discardPile = new DeckHand();
        centerRow = new DeckHand();
        ArrayList<DosPlayer> players = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        //shuffles deck
        System.out.println("Here is the deck: \n" + deck);
        System.out.println("Deck size: " + deck.getSize());
        deck.shuffle();
        System.out.println("Here is the shufled deck: \n" + deck + "\n");
        System.out.println("---------------------- START GAME ----------------------");



        //Creates Players
        for(int x = 1 ; x<4 ; x++)
        {
            System.out.println("Player " + x + " what is your name? ");
            String name = scanner.nextLine();
            players.add(new DosPlayer(name));
        }

        //deals 7 cards to players
        dealCards(players,deck);

        //Creates center row and prints
        for(int i = 0 ; i < 2 ; i++)
        {
            centerRow.add(deck.getCard(0));
            deck.removeCard(deck.getCard(0));
        }

        //-------------------------------------------- GAME ----------------------------------------------------
        //------------------------------------------------------------------------------------------------------
        boolean gameOver = false;

        int index = 3;
        while(!gameOver) {
            int playerIndex = index % 3;
            DosPlayer currentPlayer = players.get(playerIndex);
            System.out.println("------------ " + currentPlayer.getName() + "'s turn ------------");
            System.out.println("Center Row: \n" + centerRow);
            System.out.println("Hand: " + currentPlayer.getHand() + "\n");

            if(!canMatch(currentPlayer)){
                System.out.println("You must draw a card");
                currentPlayer.getHand().add(deck.getCard(0));
                deck.removeCard(deck.getCard(0));
                Random rand = new Random();
                int pick = rand.nextInt(currentPlayer.getHand().size());
                DosCard pickedCard = currentPlayer.getHand().getCard(pick);
                centerRow.add(pickedCard);
                currentPlayer.getHand().remove(pickedCard);
            }
            while(canMatch(currentPlayer)){
                turn(currentPlayer); //Displays choices and processes user input
            }


            //checks it player has 5 points and prints the players points
            int pointsThisRound = currentPlayer.getPoints();
            System.out.println("Points earned this round: " + pointsThisRound);
            System.out.println("Total Points: " + currentPlayer.getTotalPoints());
            if (currentPlayer.getTotalPoints() >=5){
                currentPlayer.won();
                gameOver = true;
                System.out.println(currentPlayer.getName() + " won the game!!");
            }

            //adds cards to center row based on the bonus points and checks if they run out of cards
            if (currentPlayer.getHand().size() > pointsThisRound) {
                for (int points = 0; points < pointsThisRound; points++) {

                    centerRow.add(pickRandom(currentPlayer));
                }
            } else {
                currentPlayer.won();
                System.out.println(currentPlayer.getName() + " won the game!!");
                gameOver = true;
            }
            currentPlayer.setPoints(0); //resets the points for the turn
            index ++;

            //refills the center row if it is has no cards
            while(centerRow.size() < 2){
                centerRow.add(deck.getCard(0));
                deck.removeCard(deck.getCard(0));
            }
        }
        //-------------------------------------------- GAME ----------------------------------------------------
        //------------------------------------------------------------------------------------------------------

    }



    //-------------------- METHOD DECLARATION ------------------------------

    public static void dealCards(ArrayList<DosPlayer> players, DosDeck deck)
    {
        for(int x = 0 ; x < 7 ; x++)
        {
            for (DosPlayer player : players) {
                player.deal(deck.getCard(0));
                deck.removeCard(deck.getCard(0));
            }
        }
    }

    public static int getBonusPoints(ArrayList<DosCard> list)
    {
        int color = list.get(0).getSuit();
        boolean sameColor = true;
        if(color == 5){
            int tempColor = list.get(1).getSuit();
            for(int index = 1 ; index < list.size() ; index++){
                if(list.get(index).getSuit() == tempColor){
                    sameColor = true;
                }else if (list.get(index).getSuit() == 5){
                    sameColor = true;
                }else{
                    sameColor = false;
                    break;
                }
            }
        }else{
            for (DosCard card : list) {
                if (card.getSuit() == color) {
                    sameColor = true;
                } else if (card.getSuit() == 5) {
                    sameColor = true;
                } else {
                    sameColor = false;
                    break;
                }
            }
        }
        int bonusPoints;
        if(sameColor){
            if(list.size()== 2){
                bonusPoints = 1;
            }else{
                bonusPoints = 2;
            }
        }else{
            bonusPoints = 0;
        }
        return bonusPoints;
    }

    public static DosCard pickRandom(DosPlayer player)
    {
        Random rand = new Random();
        int randomIndex = rand.nextInt(player.getHand().size());
        return player.getHand().getCard(randomIndex);
    }

    public static boolean canMatch(DosPlayer player)
    {
        boolean isMatch = false;
        for(int index = 0 ; index < centerRow.size() ; index ++){
            boolean tempDoubleMatch = player.checkDoubleMatches(centerRow.getCard(index));
            boolean tempSingleMatch = player.checkSingleMatches(centerRow.getCard(index));
            if(tempDoubleMatch){
                isMatch = true;
                break;
            }else if(tempSingleMatch){
                isMatch = true;
                break;
            }
        }
        return isMatch;
    }

    public static int getValidInput(int limit)
    {
        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("-- Choose one: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice <= 0 || choice > limit);
        return choice;
    }

    public static void turn(DosPlayer player)
    {
        //--------------------------DOUBLE MATCH---------------------------
        //matchIndex stores the index of the pair and the corresponding center row card (stores [centerRow index][matchesMade index][type (single or double)]
        ArrayList<int[]> matchIndex = new ArrayList<>();
        ArrayList<DeckHand> doubleMatchesMade = new ArrayList<>();
        //for loop cycles through the center row and finds matches for each card
        for(int index = 0 ; index < centerRow.size() ; index ++){
            boolean hasDoubleMatch = player.checkDoubleMatches(centerRow.getCard(index));
            if(hasDoubleMatch){

                ArrayList<DeckHand> doubleMatches;
                doubleMatches = player.getDoubleMatches(centerRow.getCard(index));
                int pairIndex = 0;

                for(DeckHand pairs : doubleMatches){

                    //numbers is an array that stores the indexes to matchIndex
                    int[] tempIndexes = new int[3];
                    tempIndexes[0] = index;
                    tempIndexes[1] = pairIndex;
                    tempIndexes[2] = 2; //2 ==> double match
                    pairIndex++;

                    //adds the indexes to the matchIndex and the pair to the matchesMade array list
                    matchIndex.add(tempIndexes);
                    doubleMatchesMade.add(pairs);
                }
            }
        }


        //------------------------SINGLE MATCH--------------------------------------
        ArrayList<DosCard> singleMatches = new ArrayList<>();
        for(int index = 0 ; index < centerRow.size() ; index++) {
            boolean hasSingleMatch = player.checkSingleMatches(centerRow.getCard(index));
            if (hasSingleMatch) {
                ArrayList<DosCard> tempMatches = new ArrayList<>();
                tempMatches = player.getSingleMatches(centerRow.getCard(index));
                int tempNum = 0;
                for (DosCard card : tempMatches) {
                    singleMatches.add(card);
                    int[] tempIndexes = new int[3];
                    tempIndexes[0] = index; //centerRow index
                    tempIndexes[1] = tempNum; //index for singleMatches
                    tempIndexes[2] = 1; //1==>single match
                    tempNum++;
                    matchIndex.add(tempIndexes);
                }

            }
        }

        if(singleMatches.size() + doubleMatchesMade.size() == 0 ){
            player.getHand().add(deck.getCard(0));
            deck.removeCard(deck.getCard(0));
        }

        //-------------------------------Selection process-----------------------------------------

        if(matchIndex.size()>0) {
            System.out.println("-- Here are your options: ");
            if(doubleMatchesMade.size()>0) {
                System.out.println("> Double Matches: ");
                for (int num = 0; num < doubleMatchesMade.size(); num++) {
                    int numPrint = num + 1;
                    int[] tempIndexes = matchIndex.get(num);
                    System.out.println(numPrint + ". " + doubleMatchesMade.get(num) + " --> " + centerRow.getCard(tempIndexes[0]));
                }
            }else{
                System.out.println("** No Double Matches **");
            }
            if(singleMatches.size()>0) {
                System.out.println("> Single Matches: ");
                for (int num = 0; num < singleMatches.size(); num++) {
                    int numPrint = doubleMatchesMade.size() + 1 + num;
                    int[] tempIndexes = matchIndex.get(numPrint - 1);
                    System.out.println(numPrint + ". " + singleMatches.get(num) + " --> " + centerRow.getCard(tempIndexes[0]));
                }
            }else{
                System.out.println("** No Single Matches **");
            }

            //System.out.println("-- Choose one: ");
            //Scanner scanner = new Scanner(System.in);
            int choice = getValidInput(matchIndex.size());
            choice--; //subtracts one so it works as an index
            int[] chosenIndex = matchIndex.get(choice);
            int centerIndex = chosenIndex[0];
            int cardIndex = chosenIndex[1];
            ArrayList<DosCard> checkColor = new ArrayList<>();
            DosCard chosenCenterCard = centerRow.getCard(centerIndex);

            if(chosenIndex[2] == 1){
                //Double matches bonus point calculation
                choice = choice - doubleMatchesMade.size();
                DosCard pickedCard = singleMatches.get(choice);
                checkColor.add(pickedCard);
                checkColor.add(chosenCenterCard);

                //checks for bonus points
                int bonusPoints = getBonusPoints(checkColor);
                player.addPoints(bonusPoints);

                //adds the used cards to the discard pile
                discardPile.add(chosenCenterCard);
                discardPile.add(pickedCard);
                centerRow.remove(chosenCenterCard);
                player.removeCard(pickedCard);

            }else{
                DeckHand chosenPair = doubleMatchesMade.get(cardIndex);
                DosCard card1 = chosenPair.getCard(0);
                DosCard card2 = chosenPair.getCard(1);

                //creates an array of the used cards to input into the getBonusPoints method
                checkColor.add(chosenCenterCard);
                checkColor.add(card1);
                checkColor.add(card2);

                //checks for bonus points
                int bonusPoints = getBonusPoints(checkColor);
                player.addPoints(bonusPoints);

                //adds the used cards to the discard pile
                discardPile.add(chosenCenterCard);
                discardPile.add(card1);
                discardPile.add(card2);
                centerRow.remove(chosenCenterCard);
                player.removeCard(card1);
                player.removeCard(card2);
            }
        }
    }
}
