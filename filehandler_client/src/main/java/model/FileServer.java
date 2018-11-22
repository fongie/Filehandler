package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServer extends Remote {

   public boolean register(String username, String password) throws RemoteException;

   public boolean login(String username, String password) throws RemoteException;
}
