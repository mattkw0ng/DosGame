//********************************************************************
//  CardStack.java       
//
//  
//********************************************************************

import java.util.*;

public class CardStack
{
    protected ArrayList<DosCard> stack;

    //-----------------------------------------------------------------
    //  Creates a full deck with the cards initially "in order".
    //-----------------------------------------------------------------
    public CardStack()
    {
        stack = new ArrayList<DosCard>();
    }

    //-----------------------------------------------------------------
    //  Adds a card to the collection.
    //-----------------------------------------------------------------
    public void addCard(DosCard c)
    {
        stack.add(c);
    }
    //-----------------------------------------------------------------
    //  Deals the first card from the collection.
    //-----------------------------------------------------------------
    public DosCard deal()
    {
        if (stack.size() > 0) {
            return stack.remove(0);
        } else {
            return null;
        }
    }

    //-----------------------------------------------------------------
    //  Deals a random card from the collection.
    //-----------------------------------------------------------------
    public DosCard randomDeal()
    {
        Random generator = new Random();
        int index = generator.nextInt(stack.size());

        if (stack.size() > 0) {
            return stack.remove(index);
        } else {
            return null;
        }       
    }

    //-----------------------------------------------------------------
    //  Removes a card from the stack of cards.
    //-----------------------------------------------------------------
    public boolean removeCard(DosCard c)
    {
        return stack.remove(c);
    }

    //-----------------------------------------------------------------
    //  Returns the ith card from the stack of cards.
    //-----------------------------------------------------------------
    public DosCard getCard(int i)
    {
        if(stack.size()>i) {
            return stack.get(i);
        }
        return null;
    }

    //-----------------------------------------------------------------
    //  Returns the number of cards left in the deck.
    //-----------------------------------------------------------------
    public int getSize()
    {
        return stack.size();
    }

    //-----------------------------------------------------------------
    //  Returns true is the deck has cards in it, else false.
    //-----------------------------------------------------------------
    public boolean hasMoreCards()
    {
        return (stack.size() > 0);
    }

    //-----------------------------------------------------------------
    //  Swaps two cards in the Stack of Cards
    //-----------------------------------------------------------------
    public void swap(int position1, int position2)
    {
        DosCard temp;
        temp = stack.get(position1);
        stack.set(position1, stack.get(position2));
        stack.set(position2, temp);
    }


    //-----------------------------------------------------------------
    //  String representation of the Stack of Cards
    //-----------------------------------------------------------------
    public String toString()
    {
        String result = "";
        for(int i=0; i<stack.size(); i++)
        {
            result+="["+stack.get(i).toString()+"] ";
        }
        return result;
    }

}
