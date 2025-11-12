
 abstract public class ArcadeGame implements Comparable<ArcadeGame>
{
    protected String name;
    protected String id;
    protected int price;
    protected double newPrice;

    public ArcadeGame(String name,String id, int price)
    {
        this.name = name;
        this.id = id;
        this.price = price;
        if(id.length() != 10) //Checking whether id is 10 characters long
        {
            throw new IncorrectIdException("Id should be 10 characters long!");
        }
    }

    public String getName()
    {
        return name;
    }
    public String getID()
    {
        return id;
    }
    public int getPrice()
    {
        return price;
    }
    public int compareTo(ArcadeGame other)
    {
        return Integer.compare(this.price,other.price);//The compareTo method is used to perform sorting on an ArrayList of games
    }


    public abstract int calculatePrice(boolean peak);

//    public String toString()
//    {
//        return "The " + name + " has id: " + id + " and its price is " + price;
//    }

//    public static void main(String[] args)
//    {
//        ArcadeGame obj = new ArcadeGame("race","1003238488",1);
//        System.out.println(obj);
//        ArcadeGame obj1 = new ArcadeGame("race","100323848",1);
//
//    }
    //The main method is commented out because i used it for testing to check whether the exception is thrown
    // and whether the price is shown in pence
}
