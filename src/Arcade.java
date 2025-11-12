
import java.util.ArrayList;
import java.util.Collections;

public class Arcade {
    private String arcadeName;
    private int revenue;
    ArrayList<ArcadeGame> arcadeGamesCollection = new ArrayList<>();
    ArrayList<Customer> customers = new ArrayList<>();

    public Arcade (String arcadeName)
    {
        this.arcadeName = arcadeName;
    }

    public void addCustomer(Customer customer)
    {
        customers.add(customer);
    }

    public void addArcadeGame(ArcadeGame game)
    {
        arcadeGamesCollection.add(game);
    }

    public String getArcadeName()
    {
        return arcadeName;
    }

    public int getRevenue()
    {
        return revenue;
    }


    public Customer getCustomer(String customerID) throws InvalidCustomerException
    //This method iterates through all clients and, if the provided id matches, returns the correct customer
    {
        if(customerID.length() !=6 )
        {
            throw new InvalidCustomerException("Id should have 6 characters");
        }
        for(int i = 0; i<customers.size();i++)
        {
            if(customers.get(i).getCustomerID().equals(customerID))
            {
                    return customers.get(i);
            }
        }
        System.out.println("There is no customer with provided id");
       return null;

    }

    public ArcadeGame getArcadeGame(String gameID) throws IncorrectIdException
    //This method iterates through all games and, if the provided id matches, returns the correct game
    {
        boolean exist = false;
        for(int i=0; i<arcadeGamesCollection.size(); i++)
        {
            if(arcadeGamesCollection.get(i).getID().equals(gameID))
            {
                exist = true;
                return arcadeGamesCollection.get(i);

            }
            if(gameID.length() !=10 && (!gameID.startsWith("C") || !gameID.startsWith("A")))
            {

                throw new IncorrectIdException("id should contain 10 characters and start from letter A or C depends on the game!");
            }
        }
        if(!exist)
        {
            System.out.println("There is no game with that id");
        }

        return null;
    }

    public boolean processTransaction(String customerID,String gameID,boolean peak)
    //this method gets game andd customer by their id and checks what type of the game is that
    {
        boolean wasUpdated = false;
        Customer customer = getCustomer(customerID);
        ArcadeGame game = getArcadeGame(gameID);
        int balance = customer.getAccBalance();
        int price = game.getPrice();
        if(game instanceof ActiveGame)//if it is ActiveGame then it checks minimum age and balance
        {
            if(balance >= price && customer.getAge() >= ((ActiveGame) game).minAge)
            {

                int charge = customer.chargeAccount(game,peak);
                revenue = revenue + charge;
                wasUpdated = true;

            }
            else
            {
                if(customer.getAge() < ((ActiveGame) game).minAge)
                {
                    try
                    {
                        throw new AgeLimitException("You are too young");
                    }
                    catch (AgeLimitException e)
                    {
                        System.out.println(e.getMessage());

                    }
                }
                if(balance < price)
                {
                    try
                    {
                        String getName = getCustomer(customerID).getCustomerName();
                        throw new InsufficientBalanceException(getName + ", you don't have enough credits");
                    }
                    catch (InsufficientBalanceException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }


            }
        }
        else//If it is Cabinet game then it checks just a balance
        {
            if(balance >= price)
            {
                int charge = customer.chargeAccount(game,peak);
                revenue = revenue + charge;
                wasUpdated = true;
            }
            else
            {
                try
                {
                    String getName = getCustomer(customerID).getCustomerName();
                    throw new InsufficientBalanceException(getName + ", you don't have enough credits");
                }
                catch (InsufficientBalanceException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }

        return wasUpdated;
    }

    public Customer findRichestCustomer()
    {
        Collections.sort(customers);
//        for(int i =0;i<customers.size();i++)
//        {
//            System.out.println(customers.get(i)); Testing whether ArrayList is sorted
//        }

        Customer richest = customers.get(0);//checks and compares each customer with the first one and assigns to the variable richest until the end of the ArrayList
        for(int i = 1;i<customers.size() ;i++)
        {
            if(customers.get(i).getAccBalance() > richest.getAccBalance())
            {
                richest = customers.get(i);

            }

        }
        return richest;

    }


    public int getMedianGamePrice()
    {
        Collections.sort(arcadeGamesCollection);

        if(arcadeGamesCollection.size() % 2 == 0)//checks whether ArrayList has even number of games if it is even then it finds two median ones and divides them by 2
        {
            int numberOfElemnts = arcadeGamesCollection.size();
            ArcadeGame median1 = arcadeGamesCollection.get(numberOfElemnts/2);
            ArcadeGame median2 = arcadeGamesCollection.get(numberOfElemnts/2 -1);
            int meddian = (median1.getPrice() + median2.getPrice()) / 2;
            return meddian;
        }
        else//if it odd number of games then just find median
        {
            ArcadeGame medianElement = arcadeGamesCollection.get(arcadeGamesCollection.size() / 2);
            int median = medianElement.getPrice();
            return median;
        }
    }


    public  int[] countArcadeGames()
    {//This method simply checks the current type of each game and increments the count for that type until the end of the ArrayList is reached.
        int[] arcadeGames = new int[3];
        int cabinetG = 0;
        int activeG = 0;
        int vrG = 0;
        for(int i=0;i<arcadeGamesCollection.size(); i++)
        {
            if(arcadeGamesCollection.get(i) instanceof CabinetGame)
            {
                cabinetG++;
                arcadeGames[0] = cabinetG;
            }
            else if(arcadeGamesCollection.get(i) instanceof VirtualRealityGame)
            {
                vrG++;
                arcadeGames[2] = vrG;
            }
            else
            {
                activeG++;
                arcadeGames[1] = activeG;
            }
            if(i == arcadeGamesCollection.size() -1)
            {
//                System.out.println(arcadeGames[2]); used for testing to check how many games are there
//                System.out.println(arcadeGames[0]);

                return arcadeGames;
            }

        }

        return arcadeGames;
    }

    public static void printCorporateJargon()
    {
        System.out.println("GamesCo does not take responsibility for any accidents or fits of rage that occur on the premises");
    }

    public String toString()
    {
        return "Arcade name: " + arcadeName + "\n" + "Revenue:  "+ revenue +"\n"+ "\n" + "Games list: " + arcadeGamesCollection + "\n" + "\n" + "List of customers: " + customers;
    }



//    public static void main(String[] args)
//    {
//////
//            Customer customer = new Customer("100000","Bob",600,10, LevelOfDiscount.STAFF);
//            ActiveGame game = new ActiveGame("great","A100222333",100,10);
//            CabinetGame game1 = new CabinetGame("sss","C100200300",500,true);
//            VirtualRealityGame game2 = new VirtualRealityGame("aaa","AV10020033",400,10, Equipment.BODY_TRACKING_SUIT);
//////        VirtualRealityGame game3 = new VirtualRealityGame("aava","AV10020033",6,12, VirtualRealityGame.Equipment.BODY_TRACKING_SUIT);
//////        VirtualRealityGame game4 = new VirtualRealityGame("aaab","AV10020033",9,12, VirtualRealityGame.Equipment.BODY_TRACKING_SUIT);
//////        VirtualRealityGame game5 = new VirtualRealityGame("aadab","AV10020033",10,12, VirtualRealityGame.Equipment.BODY_TRACKING_SUIT);
//            Arcade arcade = new Arcade("Tennis");
//            arcade.addArcadeGame(game);
//            arcade.addArcadeGame(game1);
//            arcade.addArcadeGame(game2);
//////        arcade.addArcadeGame(game3);
//////        arcade.addArcadeGame(game4);
//////        arcade.addArcadeGame(game5);
//////        arcade.getMedianGamePrice(); // Testing of the getMedianGamePrice succesfully sorts array base omn the price and returns median
////
//////        arcade.countArcadeGames();//Testing of the countArcadeGames ,it counts precise number of games and separate ActiveGames and VirtualRealityGames
////
////
//            arcade.addCustomer(customer);
//////        arcade.addArcadeGame(game); Testing of the addGame
////
//////        Testing of the proccessTransaction.succesfully returns true if tracnsaction completed,false if not and stops transaction based on the exceptions
//            Boolean transactionStatus = arcade.processTransaction(customer.getCustomerID(),game1.getID(),true);
//            System.out.println("Transaction status is : " + transactionStatus +" and current balance of the customer is " + customer.getAccBalance());
////////        arcade.customers.add(customer);
////////        Customer str = arcade.getCustomer("100001");
////////        System.out.println(str);
////
////
//////       Testing whether findRichestCustomer displays actually the richest and it does
//////        Arcade arcade = new Arcade("Tennis");
//////        Customer c1 = new Customer("123456","Alex",1,20, Customer.LevelOfDiscount.CMP_STAFF);
//////        Customer c2 = new Customer("123458","John",2,21, Customer.LevelOfDiscount.CMP_STAFF);
//////        Customer c3 = new Customer("123457","Bob",3,22, Customer.LevelOfDiscount.CMP_STAFF);
//////        Customer c4= new Customer("123457","Sara",3,22, Customer.LevelOfDiscount.CMP_STAFF);
//////        Customer c5 = new Customer("123457","Jess",10,22, Customer.LevelOfDiscount.CMP_STAFF);
//////        arcade.addCustomer(c1);
//////        arcade.addCustomer(c2);
//////        arcade.addCustomer(c3);
//////        arcade.addCustomer(c4);
//////        arcade.addCustomer(c5);
//////        arcade.findRichestCustomer();
//
//
//    }
}
