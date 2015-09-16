/**
 * Lim Chen Gim A0113645L 
 *
 * This program TextBuddy manipulates text in a file.
 * Users can input commands such as 'ADD', 'DELETE', 'DISPLAY', 'CLEAR', 
 * 'SORT', 'SEARCH', 'EXIT' to edit contents in a text file. This program will auto-save
 * after every change user makes.
 *
 * Assumptions made: 
 * 1. Inputs entered are always valid.
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

	//Messages to user after user operations
	private static final String COMMAND = "command: ";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use\n";
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
	//The possible command types
	private static final String ADD = "add";
	private static final String CLEAR = "clear";
	private static final String DELETE = "delete";
	private static final String DISPLAY = "display";
	private static final String SORT = "sort";
	private static final String SEARCH = "search";
	private static final String EXIT = "exit";
	//Messages when error encountered during user operations
	private static final String ERROR_CLEAR = "IOException encountered when clearing file.";
	private static final String ERROR_DELETE = "IOException encountered when deleting file.";
	private static final String ERROR_OPENFILE = "System exits due to error in opening file.";
	//Messages of invalid input
	private static final String INVALID_COMMAND = "Invalid command\n";
	private static final String INVALID_INPUT = "System exits due to invalid input.";
	private static final String INVALID_USAGE_OF_COMMAND = "Invalid usage of command.\n";
	//Variables
	private static final int BEGIN_INDEX = 0;
	private static final String BLANK_SPACE = " ";
	private static final String EMPTY_STRING = "";
	
	private static Scanner sc = new Scanner(System.in);
	private static ArrayList<String> listOfContents = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {
		checkForInvalidArguments(args);
		String fileName = args[0];
		File file = openFile(fileName);
		showToUser(welcomeMessage(fileName));
		executeCommand(fileName, file);
	}
	
	private static void checkForInvalidArguments(String[] args) {
		if (args.length != 1) {
			System.out.print(INVALID_INPUT);
			System.exit(0);
		}
	}
	
	/**
	 * This method opens to read file if the file exists.
	 * If the file does not exist, it will create a file with the given file name.
	 * @param fileName
	 * @return the file
	 */
	private static File openFile(String fileName) {
		File file = new File(fileName);
		
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			showToUser(ERROR_OPENFILE);
			e.printStackTrace();
			System.exit(1);
		}
		
		return file;
	}
	
	private static void showToUser(String input) {
		System.out.print(input);
	}
	
	private static String welcomeMessage(String fileName) {
		return (String.format(MESSAGE_WELCOME, fileName));
	}
	
	/**
	 * This method execute the command by passing the command to another function to determine the command type.
	 * @param fileName
	 * @param file
	 * @return result of the command
	 */
	private static String executeCommand(String fileName, File file) {
		while (true) {
			showToUser(COMMAND);
			String userCommand = sc.nextLine();
			System.out.print(determineCommandType(userCommand, file));
		}
	}
	
	/**
	 * This method determine the command type and pass the command to the actual command.
	 * @param commandLine
	 * @param file
	 * @return result of the command
	 */
	public static String determineCommandType(String commandLine, File file) {
		if (commandLine.equals(DISPLAY) || commandLine.equals(CLEAR) || commandLine.equals(SORT) || commandLine.equals(EXIT)) {
			if (commandLine.equals(DISPLAY)) {
				return display(file);
			} else if (commandLine.equals(CLEAR)) {
				return clear(file);
			} else if (commandLine.equals(SORT)) {
				return sort(file);
			} else if (commandLine.equals(EXIT)) {
				System.exit(0);
			}
		} else if (commandLine.equals(ADD) || commandLine.equals(DELETE) || commandLine.equals(SEARCH)) {
				return INVALID_USAGE_OF_COMMAND;
		} else {	
			String command = getFirstWord(commandLine);
			String text = removeFirstWord(commandLine);
			
			if (command.equals(ADD)) {
				return add(text, file);
			} else if (command.equals(DELETE)) {
				return delete(text, file);
			} else if (command.equals(SEARCH)) {
				return search(text, file);
			}
		}
		
		return INVALID_COMMAND;
	}
	
	private static String getFirstWord(String commandLine) {
		return (commandLine.substring(BEGIN_INDEX, commandLine.indexOf(BLANK_SPACE)));
	}
	
	private static String removeFirstWord(String commandLine) {
		return (commandLine.substring(commandLine.indexOf(BLANK_SPACE)+1));
	}

	private static String add(String text, File file) {
		listOfContents.add(text);
		writeToFile(file);
		return (String.format(MESSAGE_ADD, file.getName(), text));
	}
	
	private static boolean isEmpty(File file) {
		if (file.length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private static String display(File file) {
		if (isEmpty(file)) {
			return(String.format(MESSAGE_EMPTYFILE, file.getName()));
		} else {
			for (int i = 0; i < listOfContents.size(); i++ ) {
				System.out.println((i+1) + ". " + listOfContents.get(i));
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
				deleteText = listOfContents.get(index-1);
				listOfContents.remove(index-1);
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
			
			for (int i = 0; i < listOfContents.size(); i++ ) {
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
		}
		catch (IOException e) {
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
			return String.format(MESSAGE_SORTED, file.getName());
		}
	}
	
	private static String search(String text, File file) {
		if (isEmpty(file)) {
			return MESSSAGE_SEARCH_EMPTYFILE;
		} else if (!listOfContents.contains(text)) {
			return String.format(MESSAGE_SEARCH_WORD_NOT_FOUND, text, file.getName());
		} else {
			for (int i = 0; i < listOfContents.size(); i++) {
				String line = listOfContents.get(i);
				
				if (line.contains(text)) {
					System.out.println((i+1) + ". " + listOfContents.get(i));
				}
			}
			
			return EMPTY_STRING;
		}
	}
}
