package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface to send messages to the client (from server)
 */
public interface ClientWriter extends Remote {
   void write(String string) throws RemoteException;
}
