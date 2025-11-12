import java.io.*;
import java.util.*;

public class Simulation {

    public static Arcade initialiseArcade(String arcadeName,File gamesFile,File customerFile) throws FileNotFoundException
    {
        Arcade arcade = new Arcade(arcadeName);
        BufferedReader cReader = new BufferedReader(new FileReader(customerFile));
        BufferedReader gReader = new BufferedReader(new FileReader(gamesFile));
        String str;
        int line = 1;
        try
        {
            while((str = cReader.readLine()) != null)// while loop used to go throuch each row and read them
            {
                String[] customerInfo = str.split("#");//splitting row by using # as regex

                String id = customerInfo[0].trim();
                String name = customerInfo[1].trim();
                int balance = Integer.parseInt(customerInfo[2].trim());
                int age = Integer.parseInt(customerInfo[3].trim());
                int numbOfElements = 4;//this variable is needed to check how many elements does array contain if it is equal to this variable
                // then it means there is level of discount missing which means it is none
                if(numbOfElements == customerInfo.length)
                {
                    LevelOfDiscount discountLVL = LevelOfDiscount.NONE;
                    Customer customer = new Customer(id,name,balance,age,discountLVL);
                    arcade.addCustomer(customer);
                }
                else
                {
                    String discountString = customerInfo[4].trim();
                    LevelOfDiscount discountLVL = LevelOfDiscount.valueOf(discountString);
                    Customer customer = new Customer(id,name,balance,age,discountLVL);
                    arcade.addCustomer(customer);
                }

                line++;
            }

            while ((str = gReader.readLine()) != null)
            {
                String[] gamesInfo = str.split("@");

                String id = gamesInfo[0].trim();
                String name = gamesInfo[1].trim();
                String type = gamesInfo[2].trim();
                int price = Integer.parseInt(gamesInfo[3].trim());
                if(type.equals("cabinet"))
                {
                    String reward = gamesInfo[4].trim();
                    if(reward.equals("yes"))
                    {
                        boolean hasReward = true;
                        CabinetGame game = new CabinetGame(name,id,price,hasReward);
                        arcade.addArcadeGame(game);
                    }
                    else
                    {
                        boolean hasReward = false;
                        CabinetGame game = new CabinetGame(name,id,price,hasReward);
                        arcade.addArcadeGame(game);
                    }

                }
                else if(type.equals("active"))
                {
                    int ageLimit = Integer.parseInt(gamesInfo[4].trim());
                    ActiveGame game = new ActiveGame(name,id,price,ageLimit);
                    arcade.addArcadeGame(game);
                }
                else
                {
                    int ageLimit = Integer.parseInt(gamesInfo[4].trim());
                    String equipment = gamesInfo[5];
                    if(equipment.equals("headsetAndController"))
                    {
                        Equipment equipmentType = Equipment.HEADSET_AND_CONTROLLER;
                        VirtualRealityGame game = new VirtualRealityGame(name,id,price,ageLimit,equipmentType);
                        arcade.addArcadeGame(game);
                    }
                    else if(equipment.equals("headsetOnly"))
                    {
                        Equipment equipmentType = Equipment.HEADSET_ONLY;
                        VirtualRealityGame game =  new VirtualRealityGame(name,id,price,ageLimit,equipmentType);
                        arcade.addArcadeGame(game);
                    }
                    else
                    {
                        Equipment equipmentType = Equipment.BODY_TRACKING_SUIT;
                        VirtualRealityGame game = new VirtualRealityGame(name,id,price,ageLimit,equipmentType);
                        arcade.addArcadeGame(game);
                    }
                }

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return arcade;
    }

    public static void simulateFun(Arcade arcade ,File tranFile) throws FileNotFoundException
    {

        BufferedReader tReader = new BufferedReader(new FileReader(tranFile));
        String str;
        int line = 1;
        try
        {
            while((str = tReader.readLine()) != null)//while loop is used to check every single row and it has been divided on three branching parts where first one checks and performs transaction to allow customer to play
            {


                String[] transactions = str.split(",");//splitting each row on certain amount of elements based on row itself
                String action = transactions[0].trim();
                String custID = transactions[1].trim();
                if(action.equals("PLAY"))
                {
                    try
                    {
                        String gameID = transactions[2].trim();
                        String peakStatus = transactions[3].trim();
                        boolean peak;
                        if(peakStatus.equals("PEAK"))
                        {
                            peak = true;
                        }
                        else
                        {
                            peak = false;
                        }

                        boolean result = arcade.processTransaction(custID,gameID,peak);
                        if(result)
                        {
                            String getName = arcade.getCustomer(custID).getCustomerName();
                            System.out.println(getName + ", you have successfully paid for the game. Have fun!");
                        }


                    }
                    catch (IncorrectIdException e)
                    {
                        System.out.println("Wrong format of id: " + e.getMessage());
                    }


                }
                else if (action.equals("NEW_CUSTOMER"))//"The second part is about identifying the type of row to decide where a new customer should be added to the system."
                {
                    String name = transactions[2].trim();
                    int numbOfElements = 6;//same principle used as in initialiseArcade method
                    if(numbOfElements == transactions.length)
                    {
                        String discountLVL = transactions[3].trim();

                        LevelOfDiscount level = LevelOfDiscount.valueOf(discountLVL);
                        int balance = Integer.parseInt(transactions[4].trim());
                        int age = Integer.parseInt(transactions[5].trim());
                        Customer customer = new Customer(custID,name,balance,age,level);
                        arcade.addCustomer(customer);
                        checkForSuccess(arcade,custID);
                    }
                    else
                    {
                        int balance = Integer.parseInt(transactions[3].trim());
                        int age = Integer.parseInt(transactions[4].trim());
                        LevelOfDiscount discountLVL = LevelOfDiscount.NONE;
                        Customer customer = new Customer(custID,name,balance,age,discountLVL);
                        arcade.addCustomer(customer);
                        checkForSuccess(arcade,custID);
                    }
                }
                else //And if first element of the array starts with ADD_FUNDs this parts performing adding money to the customer balance
                {
                    int fee = Integer.parseInt(transactions[2].trim());
                    Customer customer = arcade.getCustomer(custID);
                    try
                    {
                        int oldBalance = customer.getAccBalance();
                        customer.addFunds(fee);
                        if(oldBalance < customer.getAccBalance() )
                        {
                            System.out.println("You have successfully added money to your balance");
                        }
                        else
                        {
                            System.out.println("Money haven't been added,ask for assistance");
                        }
                    }
                    catch (NullPointerException e)
                    {
                        System.out.println("Failed to add funds");
                    }


                }
                line++;
            }
            System.out.println( "\n" + "The richest customer is " +  arcade.findRichestCustomer());
            System.out.println("\n"+  "The median game price is: " + arcade.getMedianGamePrice());
            System.out.println("\n"+  "Cabinet/Active/Virtual games: " + Arrays.toString(arcade.countArcadeGames()));
            Arcade.printCorporateJargon();
            System.out.println();


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    public static void checkForSuccess(Arcade arcade, String custID) // I have created this method just to avoid repetition of the code.
    // This method checks whether adding customer to the customers ArrayList is successful or not
    {
        boolean existInsystem = false;
        for (int i = 0; i < arcade.customers.size();i++)
        {
            if(arcade.customers.get(i).getCustomerID().equals(custID))//Loop through the array of customers until custID is equal to the id of one of the customers
            {
                existInsystem = true;
            }
        }
        if(existInsystem)
        {
            System.out.println("Customer has successfully been added to the system");
        }
        else
        {
            System.out.println("Customer haven't been added,ask for assistance");
        }
    }

    public static void main(String[] args)
    {
        File custFile = new File("customers.txt");
        File gameFile =new File("games.txt");
        File trFile = new File("transactions.txt");
        try
        {
            Arcade passedArcade = initialiseArcade("Arcade of fun ",gameFile,custFile);
             simulateFun(passedArcade,trFile);
            System.out.println(passedArcade);


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
