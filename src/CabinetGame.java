public class CabinetGame extends ArcadeGame {
    private boolean hasReward;

    public CabinetGame(String name,String id,int price,boolean hasReward)
    {
        super(name,id,price);
        this.hasReward = hasReward;
        if(!id.startsWith("C"))
        {
            throw new IncorrectIdException("The id should start from the letter C");
        }
    }
    public boolean getHasReward()
    {
        return hasReward;
    }
    public int calculatePrice(boolean peak)//overridden calculatePrice method
    {
        if(peak == true)// if it is peak time then price stays the same
        {
            newPrice = price;
            return (int) newPrice;
        }
        else if (peak == false && hasReward == false)//if it is not peak time and there is no reward price gets 50% discount
        {
            newPrice = price * 0.5;
            return (int) newPrice;
        }
        else if (peak == false && hasReward == true)//This time if peak time and there is reward price gets 20% discount
        {
            newPrice = price * 0.8;
            return (int) newPrice;
        }
        else//in other cases price stays the same
        {
            newPrice = price;
            return (int) newPrice;
        }

    }
    public String toString()
    {
        return "\n"+ "\n" + "Name: " + name + "\n" + "Id: " + id + "\n" + "Price: " + price + "p \n" + "Status of the reward: : " + hasReward;
    }

//    public static void main(String[] args)
//    {
////        CabinetGame cabGame = new CabinetGame("Pac-Man","C003322111",6,true);
////        System.out.println(cabGame.calculatePrice(false));
////        System.out.println(cabGame);
//        CabinetGame cabinetGame = new CabinetGame("aafaf","C000111222",500,true);
//        System.out.println(cabinetGame.toString());
//    }

    //Exception works well,if first character of id is not a letter C then exception appears.The same with number of characters.
    // Also,price is returned properly according to the discounts

}
