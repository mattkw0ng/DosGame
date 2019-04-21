import java.util.ArrayList;
public class DosPlayer extends Player {
    private DeckHand hand;
    public DosPlayer(String name)
    {
        super(name);
        hand = new DeckHand();
    }
    public void deal(DosCard card)
    {
        hand.add(card);
    }
    public void removeCard(DosCard card)
    {
        hand.remove(card);
    }
    public DeckHand getHand()
    {
        return hand;
    }
    public boolean checkSingleMatches(DosCard center)
    {
        ArrayList<DosCard> singleMatches = new ArrayList<>(hand.getSingleNumberMatches(center));
        if(singleMatches.size()>0) {
            //System.out.println("For " + center + ": " + singleMatches);
            return true;
        } else {
            //System.out.println("No matches for " + center);
            return false;
        }
    }
    public boolean checkDoubleMatches(DosCard center)
    {
        ArrayList<DeckHand> doubleMatches = new ArrayList<>(hand.getDoubleNumberMatches(center));
        if(doubleMatches.size()>0) {
            //System.out.println("For " + center + ": " + doubleMatches);
            return true;
        } else {
            //System.out.println("No double matches for " + center);
            return false;
        }
    }

    public ArrayList<DosCard> getSingleMatches(DosCard center)
    {
        ArrayList<DosCard> singleMatches = new ArrayList<>(hand.getSingleNumberMatches(center));
        return singleMatches;
    }

    public ArrayList<DeckHand> getDoubleMatches(DosCard center)
    {
        ArrayList<DeckHand> doubleMatches = new ArrayList<>(hand.getDoubleNumberMatches(center));
        return doubleMatches;
    }


}
