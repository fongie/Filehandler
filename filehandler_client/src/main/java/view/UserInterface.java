package view;

import controller.Controller;
import controller.ServerController;
import model.*;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.List;

import common.FileServer;
import common.ClientWriter;
import common.ReadableFile;

/**
 * The main point of user interaction, a command line view
 */
public class UserInterface {
   private Controller controller;
   private FileServer fileServer;
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
               default:
                  System.out.println("Command does not exist. Type help");
            }
         } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
         }
      }
   }
   private void download(Command command) {
      controller.download(command.getSecond(), command.getThird());
   }
   private void upload(Command command) {
      String localFile = command.getSecond();
      String remoteName = command.getThird();
      try {
         controller.upload(localFile, remoteName);
      } catch (RemoteException | MalformedURLException | NotBoundException e) {
         e.printStackTrace();
         System.err.println("Upload failed");
      }
   }
   private void enumerateFile(ReadableFile file) {
      StringBuilder sb = new StringBuilder();
      sb.append(file.getName());
      sb.append("\t");
      sb.append(file.getSize());
      sb.append("\t");
      sb.append(file.getOwnerName());
      System.out.println(sb.toString());
   }
   private void ls() {
      try {
         List<? extends ReadableFile> list = controller.ls();
         for (ReadableFile file : list) {
            enumerateFile(file);
         }
      } catch (RemoteException e) {
         e.printStackTrace();
         System.out.println("Server call failed");
      }
   }
   private void ps() {
      controller.ps();
   }

   private void help() {
      System.out.println("quit - quit app");
      System.out.println("help - print this");
      System.out.println("register name password - register new user");
      System.out.println("login name password - login user");
      System.out.println("ls - list server directory");
      System.out.println("ps - list local directory");
      System.out.println("download remotefile localname - download file from server");
      System.out.println("upload localfile remotename - upload file to server");
   }

   private void quit() {
      controller.quit();
      System.exit(0);
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
