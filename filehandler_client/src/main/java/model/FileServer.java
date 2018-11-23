package model;

import integration.FileData;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServer extends Remote {

   boolean register(String username, String password) throws RemoteException;

   boolean login(String username, String password, ClientWriter writer) throws RemoteException;

   void upload(FileData fileData) throws RemoteException;

}
