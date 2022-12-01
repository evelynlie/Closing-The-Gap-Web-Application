package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class LGA {
   // LGA 2016 Code
   private int code16;

   // LGA 2016 Name
   private String name16;

   // LGA 2021 Code
   private int code21;

   // LGA 2021 Name
   private String name21;

   /**
    * Create an LGA and set the fields
    */
   public LGA(int code16, String name16, int code21, String name21) {
      this.code16 = code16;
      this.name16 = name16;
      this.code21 = code21;
      this.name21 = name21;
   }

   public int getCode16() {
      return code16;
   }

   public String getName16() {
      return name16;
   }

   public int getCode21() {
      return code21;
   }

   public String getName21() {
      return name21;
   }
}
