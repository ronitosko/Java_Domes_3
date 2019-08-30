import java.io.PrintStream;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		ST searchTree = new ST();		
		System.out.println("Welcome.This programm reads a txt and finds the frequency of each word of that file!");
		
		//1st menu
		Scanner input = new Scanner(System.in);
		System.out.println("1)Read a txt file."
				+ "\n0)Exit.");
		System.out.print("Enter(0-1): ");
		String user1 = input.nextLine();
		String user;
		while(true){
			
			//user wants to preccess a txt file that he has 
			if(Integer.parseInt(user1) == 1){
				
				System.out.println("Please enter the path of your txt file(Ex. C:/Users/roni3/Desktop/test.txt): ");
				System.out.print("Path: ");
				String path = input.nextLine();
				searchTree.load(path);
				System.out.println("Txt file loaded!");
				
				
				while(true){

					System.out.println("Our program provides the following services");
					System.out.println("============================================");
					System.out.println("1)Print words alphabetically.");
					System.out.println("2)Print words by frequency.");
					System.out.println("3)Print distinct words number.");
					System.out.println("4)Print mean frequency.");
					System.out.println("5)Print maximum frequency.");
					System.out.println("6)Print frequency of a given word.");
					System.out.println("7)Search if a word exists.");
					System.out.println("8)Remove a given word.");
					System.out.println("9)Add stop words.");
					System.out.println("10)Remove stop words.");
					System.out.println("11)Print total words of the file that we loaded(Not include words with length>2 and stop words)");
					System.out.println("0)Exit.");
					System.out.print("Enter(0-10): ");
					user = input.nextLine();
					if(Integer.parseInt(user) == 0 ){
						user1 = "0";
						break;
					}
					
					//case 1
					if(Integer.parseInt(user) == 1 ){
						searchTree.printAlphabetically(new PrintStream(System.out));
					}
					
					//case 2
					if(Integer.parseInt(user) == 2 ){
						searchTree.printByFrequency(new PrintStream(System.out));
					}
					
					//case 3
					if(Integer.parseInt(user) == 3 ){
						System.out.println("Total Distinct words: " + searchTree.getDistinctWords());
					}
					
					//case 4
					if(Integer.parseInt(user) == 4 ){
						System.out.println("Mean Frequency: " + searchTree.getMeanFrequency());
					}
					
					//case 5
					if(Integer.parseInt(user) == 5 ){
						System.out.println("Maximum Frequency: " + searchTree.getMaximumFrequency());
					}
					
					//case 6
					if(Integer.parseInt(user) == 6 ){
						System.out.println("Please enter the word: ");
						String word = input.nextLine();
						System.out.println("Frequency of " + word + " is " + searchTree.getFrequency(word));
					}
					
					//case 7
					if(Integer.parseInt(user) == 7 ){
						System.out.println("Please enter the word: ");
						String word = input.nextLine();
						WordFreq treeWord = searchTree.search(word);
						if(treeWord == null){
							System.out.println(word + " does not exists.");
						}else{
							System.out.println(word + " exists!");
						}
					}
					
					//case 8
					if(Integer.parseInt(user) == 8 ){
						System.out.println("Please enter the word: ");
						String word = input.nextLine();
						int tempSize1 = searchTree.getDistinctWords();
						searchTree.remove(word);
						int tempSize2 = searchTree.getDistinctWords();
						if(tempSize1 > tempSize2)
							System.out.println("Word removed!");
						else
							System.out.println("Error removing word.Please check if the words exists on tree.");
					}
					
					
					//case 9
					if(Integer.parseInt(user) == 9 ){
						System.out.println("Please enter the word: ");
						String word = input.nextLine();
						searchTree.addStopWord(word);
						System.out.println("Stop words added!");
					}
					
					
					//case 10
					if(Integer.parseInt(user) == 10 ){
						System.out.println("Please enter the word: ");
						String word = input.nextLine();
						if(searchTree.getStopWordsList().exists(word)){
							searchTree.remove(word);
							System.out.println(word + " removed!");
						}else
							System.out.println("Word does not exist on stop words list.");
					}
					
					//case 11
					if(Integer.parseInt(user) == 11){
						System.out.println("Total words that load method read are: " + searchTree.getTotalWords());
					}
					
				}//end while
			}//end if
			
			
			
			if(Integer.parseInt(user1) == 0) break;
			System.out.println("Wrong input please enter 0 or 1");
			System.out.println("1)Read a txt file."
					+ "\n0)Exit.");
			System.out.print("Enter(0-1): ");
			user1 = input.nextLine();
		}
		
		
		
		input.close();
		
		
	}//end main

}
