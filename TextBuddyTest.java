import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;


public class TextBuddyTest {

	@Test
	public void testDetermineCommandType() {
		String fileName = "mytextfile.txt";
		File file = new File(fileName);
		
		//Test display function before adding any text into file
		testDetermineCommandType("display before adding", "", "display", file);
		//Test add function by adding a line of text
		testDetermineCommandType("add", "added to mytextfile.txt: \"little brown fox\"\n", "add little brown fox", file);
		//Test display function after adding a line of text
		testDetermineCommandType("display after first add", "", "display", file);
		//Test add function by adding a line of text
		testDetermineCommandType("add", "added to mytextfile.txt: \"jumped over the moon\"\n", "add jumped over the moon", file);
		//Test display function after adding two lines of text
		testDetermineCommandType("display", "", "display", file);
		//Test add function by adding a line of text
		testDetermineCommandType("add", "added to mytextfile.txt: \"to meet little grey fox\"\n", "add to meet little grey fox", file);
		//Test display function after adding three lines of text
		testDetermineCommandType("display", "", "display", file);
		//Test delete function by deleting the second line of the text
		testDetermineCommandType("delete", "deleted from mytextfile.txt: \"jumped over the moon\"\n", "delete 2", file);
		//Test display function after deleting the second line of text
		testDetermineCommandType("display", "", "display", file);
		//Test clear function
		testDetermineCommandType("clear", "all content deleted from mytextfile.txt\n", "clear", file);
		//Test the determineCommandType function by entering an invalid command
		testDetermineCommandType("invalid command", "Invalid command\n", "little brown fox", file);
		//Test sort function by first adding new lines of text
		testDetermineCommandType("add", "added to mytextfile.txt: \"all fruits\"\n", "add all fruits", file);
		testDetermineCommandType("add", "added to mytextfile.txt: \"orange\"\n", "add orange", file);
		testDetermineCommandType("add", "added to mytextfile.txt: \"kiwi\"\n", "add kiwi", file);
		testDetermineCommandType("add", "added to mytextfile.txt: \"apple\"\n", "add apple", file);
		//Test display function to see the original display of the newly added lines of text before sort function
		testDetermineCommandType("display", "", "display", file);
		//Test sort function after adding lines of text
		testDetermineCommandType("sort", "The content of mytextfile.txt is sorted in alphabetical order\n", "sort", file);
		//Display the result of the sort functions to see if the lines are sorted in alphabetical order
		testDetermineCommandType("display", "", "display", file);
		//Test search function by adding new lines of text
		testDetermineCommandType("add", "added to mytextfile.txt: \"apple and apricot\"\n", "add apple and apricot", file);
		testDetermineCommandType("add", "added to mytextfile.txt: \"apricot\"\n", "add apricot", file);
		testDetermineCommandType("add", "added to mytextfile.txt: \"pear and apricot\"\n", "add pear and apricot", file);
		testDetermineCommandType("add", "added to mytextfile.txt: \"apple, apricot, banana\"\n", "add apple, apricot, banana", file);
		//Test sort function after adding new lines of text
		testDetermineCommandType("sort", "The content of mytextfile.txt is sorted in alphabetical order\n", "sort", file);
		//Test display function to show the sorted display of the lines of text
		testDetermineCommandType("display", "", "display", file);
		//Test search function of a word where the word appeared once in the file
		testDetermineCommandType("search for orange", "", "search orange", file);
		//Test search function of a word where the word appeared twice in the file
		testDetermineCommandType("search for apple", "", "search apple", file);
		//Test search function of a word where the word appeared more than twice in the file
		testDetermineCommandType("search for apricot", "", "search apricot", file);
		//Test search function of a word where the word is not in the file
		testDetermineCommandType("search for mango", "Unable to find \"mango\" in mytextfile.txt\n", "search mango", file);
	}
	
	private void testDetermineCommandType(String description, String expected, String commandLine, File file) {
		assertEquals(description, expected, TextBuddy.determineCommandType(commandLine, file));
	}
}
