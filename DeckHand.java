import java.util.ArrayList;

public class DeckHand extends DosDeck {
    private ArrayList<DosCard> hand;
    public DeckHand()
    {
        hand = new ArrayList<>();
    }

    public void add(DosCard card)
    {
        hand.add(card);
    }

    public void remove(DosCard card)
    {
        hand.remove(card);
    }

    public int size()
    {
        return hand.size();
    }

    public DosCard getCard(int index)
    {
        return hand.get(index);
    }

    public ArrayList<DosCard> getSingleNumberMatches(DosCard center)
    {
        ArrayList<DosCard> matches = new ArrayList<>();
        for(DosCard card : hand)
        {
            if(card.getFace() == center.getFace()){
                matches.add(card);
            }else if(card.getFace() == 11){
                matches.add(card);
            }else if(center.getFace() == 11){
                matches.add(card);
            }
        }
        return matches;
    }


    public ArrayList<DeckHand> getDoubleNumberMatches(DosCard center)
    {
        ArrayList<DeckHand> matches = new ArrayList<>();
        for(int x = 0 ; x < hand.size() ; x ++){
            for(int y = x+1 ; y < hand.size() ; y ++){
                int card1 = hand.get(x).getFace();
                int card2 = hand.get(y).getFace();
                int sum = card1 + card2;
                if(sum == center.getFace()){
                    DeckHand matchMade = new DeckHand();
                    matchMade.add(hand.get(x));
                    matchMade.add(hand.get(y));
                    matches.add(matchMade);
                }
            }
        }
        return matches;
    }


    public int getSum(DosCard one, DosCard two)
    {
        return one.getFace() + two.getFace();
    }

    public String toString()
    {
        String result = "";
        for(int i=0; i<hand.size(); i++)
        {
            result+="["+hand.get(i).toString()+"] ";
        }
        return result;
    }

}
