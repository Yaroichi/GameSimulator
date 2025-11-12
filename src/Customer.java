public class Customer implements Comparable<Customer>{
    private String customerID;
    private String customerName;
    private int age;
    private LevelOfDiscount levelOfDiscount;
    private int accBalance;

    public Customer(String customerID,String customerName,int age,LevelOfDiscount levelOfDiscount)
    {
        this.customerID = customerID;
        if(customerID.length() != 6)
        {
            throw  new InvalidCustomerException("Id should contain 6 characters!");
        }
        this.customerName = customerName;
        this.age = age;
        this.levelOfDiscount = levelOfDiscount;
        accBalance = 0;
    }

    public Customer (String customerID,String customerName,int accBalance,int age,LevelOfDiscount levelOfDiscount)
    {
        this(customerID,customerName,age,levelOfDiscount);
        if(levelOfDiscount != LevelOfDiscount.STUDENT && accBalance < 0)//throws exception if balance goes less than zero if it is not student
        {
            throw new InvalidCustomerException("Account balance can't be zero!");
        }
        else if(customerID.length() != 6)//checks whether id has 6 characters
        {
            throw  new InvalidCustomerException("Id should contain 6 characters!");
        }
        else {
            this.accBalance = accBalance;
        }
        if(levelOfDiscount == LevelOfDiscount.STUDENT)//allows balance to go lower than zero if it is a student
        {
            if(accBalance <-500)
            {
                throw new InvalidCustomerException("Student's balance cannot be lower than -500!");
            }
            else
            {
                this.accBalance = accBalance;
            }
            this.accBalance = accBalance;
        }

    }

    public String getCustomerID()
    {
        return customerID;
    }
    public String getCustomerName()
    {
        return customerName;
    }
    public int getAge()
    {
        return age;
    }
    public LevelOfDiscount getLevelOfDiscount()
    {
        return levelOfDiscount;
    }
    public int getAccBalance()
    {
        return accBalance;
    }


    public int addFunds(int amount)//adds money only if positive amount is entered
    {
        if(amount<=0)
        {
            System.out.println("Please enter the positive amount!");

        }
        else
        {
            return accBalance = accBalance + (amount);
        }
        return accBalance;
    }

    public int chargeAccount(ArcadeGame game,boolean isPeak)//checked exception is thrown if balance doesn't have enough credits
    {
        double totalCharge;

       if(accBalance < game.getPrice())//
       {
           try
           {
               throw new InsufficientBalanceException("Your balance does not have enough credits");
           }
           catch (InsufficientBalanceException e)
           {
               System.out.println( e.getMessage());
               return 0;
           }

       }
       if(game instanceof ActiveGame)//Also checks and throws checked exception if customer tries to play ActiveGame but they are too young
       {
           if(age < ((ActiveGame) game).minAge)
           {
               try
               {
                   throw new AgeLimitException("You are too young!");
               }
               catch (AgeLimitException e)
               {
                   System.out.println( e.getMessage());
                   return 0;
               }

           }

       }
       if(levelOfDiscount == LevelOfDiscount.STUDENT)//Gives discount based ong the level of discount
       {
           totalCharge = game.calculatePrice(isPeak) * 0.95;
           accBalance = accBalance - (int)totalCharge;
           return (int)totalCharge;
       }
       else if(levelOfDiscount == LevelOfDiscount.STAFF)
       {
           totalCharge = game.calculatePrice(isPeak) * 0.90;
           accBalance = accBalance - (int)totalCharge;
           return (int)totalCharge;
       }
       else
       {
           totalCharge = game.calculatePrice(isPeak);
           accBalance = accBalance - (int)totalCharge;
           return (int)totalCharge;
       }


    }

    public int compareTo(Customer other)
    {
        return Integer.compare(this.accBalance,other.accBalance);//Used to sort ArrayList of customers to determoine the richest
    }


    public String toString()
    {

          return  "\n"+ "\n" + "Name: " + customerName + "\n" + "Id: " + customerID + "\n" + "Age: " + age + "\n" + "Level of discount: " + levelOfDiscount + "\n" + "Current balance: " + accBalance;
    }
//    public static void main(String[] args)
//    {
////        Customer customer = new Customer("123456","Alex",14,LevelOfDiscount.NONE,5);
//        //chargeAccount methd works well when the levevel of the discount is none
////        Customer customer = new Customer("123456","Alex",14,LevelOfDiscount.STUDENT,5);
//        //chargeAccount works well when level of the discount is STUDENT
////        Customer customer = new Customer("123456","Alex",14,LevelOfDiscount.CMP_STAFF,5);
//        //The same with CMP_STAFF
////        Customer customer = new Customer("123456","Alex",8,LevelOfDiscount.CMP_STAFF,5);
//        //Method has successfully given AgeLimitException
////        Customer customer = new Customer("123456","Alex",14,LevelOfDiscount.CMP_STAFF,1);
//        //Method has successfully  given InsuficientBalanceException
//        ActiveGame actGame = new ActiveGame("Game","A002211345",2,10);
//        System.out.println(customer.chargeAccount(actGame,false));
//
// //       addFunds method succesfully adds funds
////        System.out.println(customer);
////        int newBalance = customer.addFunds(1);
////        System.out.println(newBalance);

//    Customer cusstomer = new Customer("C45678","Adam",12,LevelOfDiscount.STUDENT); toString testing
//    System.out.println(cusstomer.toString());
//
//    }
//
}
