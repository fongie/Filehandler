package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientWriter extends Remote {
   void write(String string) throws RemoteException;
}
