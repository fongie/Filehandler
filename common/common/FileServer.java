package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileServer extends Remote {

   boolean register(String username, String password) throws RemoteException;

   boolean login(String username, String password, ClientWriter writer) throws RemoteException;

   void logout(String username) throws RemoteException;

   void upload(FileData fileData) throws RemoteException, AuthenticationException, FilenameNotUniqueException;

   ReadableFile download(String path, String requestedBy) throws RemoteException, AuthenticationException, NoSuchFileException;

   List<? extends ReadableFile> ls() throws RemoteException;

   void delete(String path, String requestedBy) throws AuthenticationException, NoSuchFileException, RemoteException, NoPermissionException;
}
