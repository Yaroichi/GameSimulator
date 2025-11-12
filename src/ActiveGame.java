public class ActiveGame extends ArcadeGame {
    protected int minAge;

    public ActiveGame(String name,String id,int price,int minAge)
    {
        super(name,id,price);
        this.minAge = minAge;
        if(id.length() != 10 || !id.startsWith("A"))
        {
            throw new IncorrectIdException("The id should start from the letter A because it is Active Game");
        }
    }
    public int getMinAge()
    {
        return minAge;
    }
    public int calculatePrice(boolean peak) //same principle used as in Cabin game but without 50% discount and without rewards regardless age limit
    {
        if(peak == true)
        {
            newPrice = price;
            return (int) newPrice;
        }
        else if (peak == false)
        {
            newPrice = price * 0.8;
            return (int) newPrice;
        }
        else
        {
            newPrice = price;
            return (int) newPrice;
        }

    }
    public String toString()
    {
//        return "Price of the game " + name + " with id " + id + " is: " + (int)newPrice + "p and minimum age to play the game is: " + minAge;
        return  "\n"+ "\n" + "Name: " + name + "\n" + "Id: " + id + "\n" + "Price: " + price + "p \n" + "Minimum age:" + minAge;
    }

//    public static void main(String[] args)
//    {
//        ActiveGame actGame = new ActiveGame("Air Hockey","A003322111",5,12);
//        System.out.println(actGame.calculatePrice(true));
//        System.out.println(actGame);
//    }

    //I have tested the ActiveGame class the same way as cabinetGame class
}
