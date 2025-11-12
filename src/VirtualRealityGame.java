public class VirtualRealityGame extends ActiveGame{
//    public enum Equipment {HEADSET_ONLY,HEADSET_AND_CONTROLLER,BODY_TRACKING_SUIT};
    private Equipment vrEquipment;

    public VirtualRealityGame (String name,String id,int price,int minAge,Equipment vrEquipment)
    {
        super(name,id,price,minAge);
        this.vrEquipment = vrEquipment;
        if(id.length() != 10 || !id.startsWith("AV"))
        {
            throw new IncorrectIdException("The id should start from the letter AV because it is Active Virtual Game");
        }

    }
    public Equipment getVrEquipment()
    {
        return vrEquipment;
    }

    public int calculatePrice(boolean peak)//checks peak time and type of Equipment
    {
        if(peak == true)
        {
            newPrice = price;
            return (int) newPrice;
        }
        else if (peak == false && vrEquipment == Equipment.HEADSET_ONLY)
        {
            newPrice = price * 0.9;
            return (int) newPrice;
        }
        else if (peak == false && vrEquipment == Equipment.HEADSET_AND_CONTROLLER)
        {
            newPrice = price * 0.95;
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
//        return  "Price of the game " + name + " with id " + id + " is: " + (int)newPrice + "p and equipment needed for this game: " + vrEquipment;
        return  "\n"+ "\n" + "Name: "+ name + "\n" + "Id: " + id + "\n" + "Price: " + price + "p \n" + "Equipment type: " + vrEquipment;
    }

//    public static void main(String[] args)
//    {
//        //Testing whether everything works fine
//        VirtualRealityGame vrgame = new VirtualRealityGame("VR boxing","AV00000000",4,10,Equipment.BODY_TRACKING_SUIT);
//        VirtualRealityGame vrgame = new VirtualRealityGame("VR boxing","AV00000000",4,10,Equipment.HEADSET_ONLY);
//        //Testing whether discounts work fine
//        System.out.println(vrgame.calculatePrice(true));
//        System.out.println(vrgame.calculatePrice(false));
//        System.out.println(vrgame);
//
//      //Testing whether there is sn exception related to the first letter
//      VirtualRealityGame vrgame1 = new VirtualRealityGame("VR boxing","0000000000",4,10,Equipment.BODY_TRACKING_SUIT);
//      //Testing related to the first two letters of the id because of VRGame class
//      VirtualRealityGame vrgame2 = new VirtualRealityGame("VR boxing","A000000000",4,10,Equipment.BODY_TRACKING_SUIT);
//     //Testing related to the number of characters
//      VirtualRealityGame vrgame3 = new VirtualRealityGame("VR boxing","AV000000000",4,10,Equipment.BODY_TRACKING_SUIT);
//    }


}
