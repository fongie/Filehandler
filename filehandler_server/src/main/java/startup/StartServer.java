package startup;

import controller.ServerController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {
   public static void main(String[] args) {
      System.out.println("Starting Filehandler server.");
      EntityManagerFactory ef = Persistence.createEntityManagerFactory("FilehandlerPersistence");

      try {
         LocateRegistry.getRegistry().list(); //hack to see if any registry is running (lists them)
      } catch (RemoteException notYetStarted) {
         try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT); //1099
         } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("Could not start server (on creating registry)");
         }
      }

      try {
         ServerController cntr = new ServerController(ef);
         Naming.rebind("fileserver", cntr); //naming performs lookup of registry for us
      } catch (RemoteException e) {
         e.printStackTrace();
      } catch (MalformedURLException e) {
         e.printStackTrace();
         System.err.println("Could not start server (malformed URL)");
      }
   }
}
