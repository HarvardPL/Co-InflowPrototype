import lbs.harvard.coinflow.CoInFlowUserAPI;

public class Webstore {
    public int low;
    private int high;

    private static int h;
    private static int l;

    private int[] transaction;
  
    
   public static void main(String[] args){

      Webstore w = new Webstore();
      w.setBillingAdr(l,l);
      w.setDeliveryAdr(h,h);
      w.getBillAdr();
      w.getDeliverAdr();
   }

   private Adress bill;
   private DAdress delivery;

   public void setBillingAdr(int name, int street) {
      bill = new Adress();
      bill.name = name;
      bill.street = street;
   }

   public void setDeliveryAdr(int name, int street) {
      delivery = new DAdress();
      CoInFlowUserAPI.raiseObjLabel(delivery, 
      		CoInFlowUserAPI.getLattice().getLabelByName("high"));
      delivery.name = name;
      delivery.street = street;
   }

   public int getBillAdr() {
     return this.bill.street;
   }

   public int getDeliverAdr() {
     return this.delivery.street;
   }

   public static class Adress {
     public int name;
     public int street;
   }

   public static class DAdress extends Adress{
     public int name;
	 public int street;
   }
}
