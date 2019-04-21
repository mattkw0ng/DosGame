import java.util.ArrayList;
import java.util.Random;

public class DosDeck extends CardStack {
    private ArrayList<DosCard> deck;
    public DosDeck()
    {
        deck = super.stack;
        for(int color = 1 ; color < 5 ; color ++)
        {
            //numbers 1-5 created three times
            for(int r = 1 ; r < 4 ; r++)
            {
                deck.add(new DosCard(1, color));
                for(int num = 3 ; num < 6 ; num ++)
                {
                    deck.add(new DosCard(num,color));
                }
            }
            //numbers 6-10 created two times
            for(int r = 1 ; r < 3 ; r++)
            {
                for(int num = 6 ; num < 11 ; num ++)
                {
                    deck.add(new DosCard(num,color));
                }
            }
            //two wild cards created
            deck.add(new DosCard(11,color));
            deck.add(new DosCard(11,color));
        }
        for(int num = 0 ; num < 12 ; num ++)
        {
            deck.add(new DosCard(2,5));
        }
    }
    public void shuffle()
    {
        for(int index = 0 ; index<deck.size() ; index++)
        {
            Random generator = new Random();
            int num = generator.nextInt(deck.size());
            super.swap(index,num);
        }
    }


}
