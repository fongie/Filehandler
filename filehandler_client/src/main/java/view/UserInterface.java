package view;

import common.*;
import controller.Controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.List;

/**
 * The main point of user interaction, a command line view
 */
public class UserInterface {
   private Controller controller;
   private ClientWriter writer;

   /**
    * Constructor for User Interface
    */
   public UserInterface() throws RemoteException {
      controller = new Controller();
      writer = new Callback();
   }

   /**
    * StartServer the user interface
    */
   public void start() {
      System.out.println("Welcome to the file catalog.");
      System.out.println("Type 'help' for available commands");
      run();
   }

   /**
    * Loop to insert commands
    */
   public void run() {

      while (true) {
         Scanner in = new Scanner(System.in);
         String input = in.nextLine();
         if (input.trim().length() == 0) {
            continue;
         }
         try {
            Command command = new Command(input);
            Keyword keyword = command.getKeyword();
            switch (keyword) {
               case QUIT:
                  quit();
               case HELP:
                  help();
                  break;
               case LOGIN:
                  login(command);
                  break;
               case LOGOUT:
                  logout();
                  break;
               case REGISTER:
                  register(command);
                  break;
               case LS:
                  ls();
                  break;
               case PS:
                  ps();
                  break;
               case DOWNLOAD:
                  download(command);
                  break;
               case UPLOAD:
                  upload(command);
                  break;
               case DELETE:
                  delete(command);
                  break;
               default:
                  System.out.println("Command does not exist. Type help");
            }
         } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
         } catch (NoPermissionException | AuthenticationException | NoSuchFileException | FilenameNotUniqueException e) {
            System.out.println(e.getMessage());
         } catch(RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            System.err.println("Server call failed");
         } catch(IOException e) {
            e.printStackTrace();
            System.err.println("Failed to read local files");
         }
      }
   }
   private void delete(Command command) throws RemoteException, MalformedURLException, NoSuchFileException, AuthenticationException, NotBoundException, NoPermissionException {
      String remoteFile = command.getSecond();
      controller.delete(remoteFile);
      System.out.println("Deletion successful");
   }
   private void download(Command command) throws RemoteException, AuthenticationException, MalformedURLException, NotBoundException, NoSuchFileException {
      String remoteFile = command.getSecond();
      String localName = command.getThird();
      try {
         controller.download(remoteFile, localName);
      } catch (IOException e) {
         System.out.println("Could not persist file on harddrive");
      }
      System.out.println("Download successful");
   }
   private void upload(Command command) throws IOException, NotBoundException, AuthenticationException, FilenameNotUniqueException, NoSuchFileException {
      System.out.println("Do you wish for this file to be read-only for other users? y/n");
      Scanner in = new Scanner(System.in);
      String input = in.nextLine();
      boolean writeable;
      if (!(input.trim().length() == 1)) {
         System.out.println("Please write only 'y' or  'n'");
         upload(command);
         return;
      }
      if (input.trim().toLowerCase().equals("y")) {
         writeable = false;
      } else {
         writeable = true;
      }
      String localFile = command.getSecond();
      String remoteName = command.getThird();
      controller.upload(localFile, remoteName, writeable);
      System.out.println("Upload successful");
   }
   private void ls() {
      try {
         System.out.println("SERVER CATALOG:");
         System.out.println("Filename\t Size (bytes)\t Owner\t GlobalPermission");
         System.out.println("--------------------");
         List<? extends ReadableFile> list = controller.ls();
         /*
         for (ReadableFile file : list) {
            enumerateRemoteFile(file);
         }
         */
         list.forEach(this::enumerateRemoteFile);
      } catch (RemoteException | MalformedURLException | NotBoundException e) {
         e.printStackTrace();
         System.out.println("Server call failed");
      }
   }
   private void enumerateRemoteFile(ReadableFile file) {
      StringBuilder sb = new StringBuilder();
      sb.append(file.getName());
      sb.append("\t");
      sb.append(file.getSize());
      sb.append("\t");
      sb.append(file.getOwnerName());
      sb.append("\t");
      if (file.isWriteable()) {
         sb.append("rw");
      } else {
         sb.append("r");
      }
      System.out.println(sb.toString());
   }
   private void ps() throws IOException {
      System.out.println("LOCAL CATALOG:");
      System.out.println("name - size");
      List<ReadableFile> files = controller.ps();
      files.forEach(this::enumerateLocalFile);
   }
   private void enumerateLocalFile(ReadableFile file) {
      StringBuilder sb = new StringBuilder();
      sb.append(file.getName());
      sb.append(" - ");
      sb.append(file.getSize());
      System.out.println(sb.toString());
   }

   private void help() {
      System.out.println("quit - quit app");
      System.out.println("help - print this");
      System.out.println("register name password - register new user");
      System.out.println("login name password - login user");
      System.out.println("logout - logout user");
      System.out.println("ls - list server directory");
      System.out.println("ps - list local directory");
      System.out.println("download remotefile localname - download file from server");
      System.out.println("upload localfile remotename - upload file to server");
      System.out.println("delete remotefile - delete file from server");
   }

   private void quit() throws RemoteException {
      controller.quit();
      System.exit(0);
   }

   private void logout() throws RemoteException {
      controller.logout();
   }

   private void register(Command command) {
      System.out.println("REGISTER");
      boolean registered = false;
      try {
         registered = controller.register(command.getSecond(), command.getThird());
         if (registered) {
            System.out.println("Registration successful!");
         } else {
            System.out.println("Registration failed! Use a unique username.");
         }
      } catch (RemoteException e) {
         System.err.println("Could not locate server.");
         e.printStackTrace();
      } catch (NotBoundException e) {
         e.printStackTrace();
         System.err.println("Could not locate server.");
      } catch (MalformedURLException e) {
         System.err.println("Wrong server address");
         e.printStackTrace();
      }
   }

   private void login(Command command) {
      boolean loggedin = false;
      try {
         String username = command.getSecond();
         String password = command.getThird();
         loggedin = controller.login(username, password, writer);
         if (loggedin) {
            System.out.println("Login successful!");
         } else {
            System.out.println("Login failed! Did you write your password correctly?");

         }
      } catch (RemoteException e) {
         System.err.println("Could not locate server.");
         e.printStackTrace();
      } catch (NotBoundException e) {
         e.printStackTrace();
         System.err.println("Could not locate server.");
      } catch (MalformedURLException e) {
         System.err.println("Wrong server address");
         e.printStackTrace();
      }
   }

   //remote obj sent to the server, for the server to communicate back to client
   private class Callback extends UnicastRemoteObject implements ClientWriter {

      public Callback() throws RemoteException {
         //exported
      }

      public void write(String string) {
         System.out.println("Server: " + string);
      }
   }
}
