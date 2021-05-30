public class Webstore {
    public int low;
    private int high;

    private static int h;
    private static int l;

    private int[] transaction;
  
    
   public static void main(String[] args){

      Webstore w = new Webstore();
      w.reinit(true);
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
    
   public void reinit(boolean h) {
     if (h) {
       if (vids != null && vids.length > 0 && vids[0] != null) {
         VideoSet v = new VideoSet();
         v.vFree = vids[0].vFree;
         v.vPrime = vids[0].vPrime;
         vids[0] = v;
       }
     }
   }      

   public static class VideoSet {
     public Video vFree;
     public Video vPrime;
   }

   public static class Video{}
}
