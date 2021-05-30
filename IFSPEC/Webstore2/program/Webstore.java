public class Webstore {
    public int low;
    private int high;

    private static int h;
    private static int l;

    private int[] transaction;
  
    
   public static void main(String[] args){

      Webstore w = new Webstore();
      w.seePreview(l);
      w.seePrime(l);
   }

   private VideoSet[] vids;

   public Video seePreview(int i) {
     if (vids != null && 0 <= i && i < vids.length) {
       return vids[i].vFree;
     } else {
       return null;
     }
   }

  public Video seePrime(int i) {
     if (vids != null && 0 <= i && i < vids.length) {
       return vids[i].vPrime;
     } else {
       return null;
     }
   }
          

   public static class VideoSet {
     public Video vFree;
     public Video vPrime;
   }

   public static class Video{}
}
