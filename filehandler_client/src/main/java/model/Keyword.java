package model;

public enum Keyword {

   /**
    * Show available commands
    */
   HELP,
   /**
    * Register new account
    */
   REGISTER,

   /**
    * Login
    */
   LOGIN,

   /**
    * Logout
    */
   LOGOUT,

   /**
    * List server directory
    */
   LS,

   /**
    * List local directory
    */
   PS,

   /** Download file from server
    *
    */
   DOWNLOAD,

   /** Upload file to server
    *
    */
   UPLOAD,
   /**
    * Delete file from server
    */
   DELETE,
   /**
    * Quit
    */
   QUIT
}
