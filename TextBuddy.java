/**
  *Lim Chen Gim A0113645L
  *
  * This program TextBuddy manipulates text in a file.
  * Users can input commands such as 'ADD', 'DELETE', 'DISPLAY', 'CLEAR',
  * 'SORT', 'SEARCH', 'EXIT' to edit contents in a text file. This program
  * will auto-save after every change user makes.
  *
  * Assumptions:
  * 1. Inputs entered are always valid. (Otherwise program will terminate due
  * to error as I did not implement error catching.)
  *
  */
  
  import java.io.BufferedWriter;
  import java.io.File;
  import java.io.FileWriter;
  import java.io.IOException;
  import java.io.PrintWriter;
  import java.util.ArrayList;
  import java.util.Collections;
  import java.util.Scanner;
  
  
  public class TextBuddy {
   
   //Messages after user operations
   private static final String MESSAGE_ADD = "added to %s: \"%s\"\n";
   private static final String MESSAGE_CLEAR = "all content deleted from %s\n";
   private static final String MESSAGE_DELETE = "deleted from %s: \"%s\"\n";
   private static final String MESSAGE_DELETE_EMPTYFILE = "File is Empty. There is nothing to delete.\n";
   private static final String MESSAGE_DELETE_LINE_NUMBER_DOES_NOT_EXISTS = "Line does not exists\n";
   private static final String MESSAGE_EMPTYFILE = "%s is empty\n";
   private static final String MESSAGE_SORTED = "The content of %s is sorted in alphabetical order\n";
   private static final String MESSAGE_SORT_EMPTYFILE = "There is nothing to be sorted\n";
   private static final String MESSSAGE_SEARCH_EMPTYFILE = "File is empty. Please add text before searching.\n";
   private static final String MESSAGE_SEARCH_WORD_NOT_FOUND = "Unable to find \"%s\" in %s\n";
   
   //Error messages during user operations
   private static final String ERROR_CLEAR = "IOException encountered when clearing file.";
   private static final String ERROR_DELETE = "IOException encountered when deleting file.";
   
   //Invalid input messages
   private static final String INVALID_INPUT = "System exits due to invalid input.";
   
   //Variables
   private static final String EMPTY_STRING = "";
   
   private static Scanner sc = new Scanner(System.in);
   private static ArrayList<String> listOfContents = new ArrayList<String>();
   
   
   
   public static void main(String[] args) throws IOException {
    checkForInvalidArguments(args);
    String fileName = args[0];
    File file = openFile(fileName);
   }
   
   private static void checkForInvalidArguments(String[] args) {
    if (args.length != 1) {
     System.out.print(INVALID_INPUT);
     System.exit(0);
    }
   }
   
   private static File openFile(String filename) {
    File file = new File(fileName);
    
    try {
     if (!file.exists()) {
      file.createNewFile();
     }
    } catch (IOException e) {
     e.printStackTrace();
     System.exit(1);
    }
    
    return file;
   }
   
   private static boolean isEmpty(File file) {
    if (file.length() = 0) {
     return true;
    } else {
     return false;
    }
   }
   
   private static String add(String text, File file) {
    listOfContents.add(text);
    writeToFile(file);
    return (String.format(MESSAGE_ADD, file.getName(), text));
   }
   
   private static String display(File file) {
    if (isEmpty(file)) {
     return(String.format(MESSAGE_EMPTYFILE, file.getName()));
    } else {
     for (int i = 0; i < listOfContents.size(); i++) {
      System.out.println((i + 1) + ". " + listOfContents.get(i));
     }
     return(EMPTY_STRING);
     
    }
   }
   
   private static String delete(String text, File file) {
    String deleteText;
    
    if (isEmpty(file)) {
     return MESSAGE_DELETE_EMPTYFILE;
    } else {
     int index = Integer.parseInt(text);
     
     if (listOfContents.size() < index) {
      return MESSAGE_DELETE_LINE_NUMBER_DOES_NOT_EXISTS;
     } else {
      deleteText = listOfContents.get(index - 1);
      listOfContents.remove(index - 1);
      listOfContents.trimToSize();
      writeToFile(file);
      
      return String.format(MESSAGE_DELETE, file.getName(), deleteText);
     }
    }
   }
   
   private static void writeToFile(File file) {
    FileWriter writer = null;
    BufferedWriter buffWriter = null;
    
    try {
     writer = new FileWriter(file);
     buffWriter = new BufferedWriter(writer);
     
     for(int i = 0; i < listOfContents.size(); i++) {
      buffWriter.write(listOfContents.get(i));
      buffWriter.newLine();
     }
    } catch (IOException e) {
     System.out.println(ERROR_DELETE);
     e.printStackTrace();
    } finally {
     try {
      if (buffWriter != null) {
       buffWriter.close();
      }
     } catch (IOException e) {
      e.printStackTrace();
     }
    }
   }
   
   private static String clear(File file) {
    PrintWriter clear = null;
    listOfContents.clear();
    
    try {
     clear = new PrintWriter(new FileWriter(file.getName(), false));
     clear.print(EMPTY_STRING);
    } catch (IOException e) {
     System.out.println(ERROR_CLEAR);
     e.printStackTrace();
    } finally {
     if (clear != null) {
      clear.close();
     }
    }
    
    return String.format(MESSAGE_CLEAR, file.getName());
   }
   
   private static String sort(File file) {
    if (isEmpty(file)) {
     return MESSAGE_SORT_EMPTYFILE;
    } else {
     Collections.sort(listOfContents);
     writeToFile(file);
     return String.format(MESSAGE_SORTED), file.getName());
    }
   }
   
   private static String search(String text, File file) {
    if (isEmpty(file)) {
     return MESSAGE_SEARCH_EMPTYFILE;
    } else if (!listOfContents.contains(text)) {
     return String.format(MESSAGE_SEARCH_WORD_NOT_FOUND, text, file.getName());
    } else {
     for (int i = 0; i < listOfContents.size(); i++) {
      String line = listOfContents.get(i);
      
      if (line.contains(text)) {
       System.out.println((i + 1) + ". " + listOfContents.get(i));
      }
      
      return EMPTY_STRING;
     }
    }
   }
   
   
 } 
  
  
  
  
  

