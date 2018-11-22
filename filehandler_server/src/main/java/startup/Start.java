package startup;

import controller.Controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Start {
   public static void main(String[] args) {
      System.out.println("Starting Filehandler server.");

      EntityManagerFactory ef = Persistence.createEntityManagerFactory("FilehandlerPersistence");

      Controller cntr = new Controller(ef);

      //cntr.register("Hej", "Max");
      System.out.println(cntr.login("Hej", "Max"));

      //TODO next step make client and rmi call to login and register to get that working



   }
}
