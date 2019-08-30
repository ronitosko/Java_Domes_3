import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ST {
	
	/**
	 * Each  TreeNode will have these 5 refences: left, right, parent, wordFreq and number
	 */
	
	private class TreeNode{
		
		public TreeNode left, right, parent;
		WordFreq element;
		private int number;
		
		protected TreeNode(WordFreq element){
			if(element == null) throw new IllegalArgumentException();
			this.element = element;
			this.setNumber(0);
		}//end of contructor
		
		
		protected void unlink() {
			element = null;
			parent = left = right = null;
		}//end of unlink


		protected int getNumber() {
			return number;
		}


		protected void setNumber(int number) {
			this.number = number;
		}
		
		
		
	}//end of TreeNode class
	
	private TreeNode root;
	private List stopWords;
	private int size;
	private int totalFileWords;
	protected WordFreqComparator cmp;
	protected FreqComparator cmp1;
	
	public ST(){
		this(new WordFreqComparator());
	}//end of constructor 1
	
	public ST(WordFreqComparator cmp){
		this.stopWords = new List("Stop Words List");
		this.size = 0;
		this.totalFileWords = 0;
		this.cmp = cmp;
		
		
	}//end of constructor2
	
	public ST(FreqComparator cmp1){
		this.stopWords = new List("Stop Words List");
		this.size = 0;
		this.totalFileWords = 0;
		this.cmp1 = cmp1;
		this.cmp = new WordFreqComparator();
	}//end of constructor with FreqComparator
	
	public TreeNode find(WordFreq element){
		
		TreeNode p = root;
		while(p != null){
			int result;
			//choose comparator
			result = cmp.compare(element, p.element);
			
			if(result == 0)
				break;
			p = result < 0? p.left : p.right;
			
		}//end of while
		return p;
		
	}//end of find
	
	private WordFreq searchR(TreeNode p, String w){
		
		if(p == null) return null;
		//int result = w.compareToIgnoreCase(p.element.key());
		WordFreq pWord = new WordFreq(p.element.key(), p.element.getFreq());
		WordFreq wWord = new WordFreq(w);
		
		int result;
		//choose comparator
		result = cmp.compare(wWord, pWord);
		
		if(result == 0) return p.element;
		if(result < 0)
			return searchR(p.left, w);
		else
			return searchR(p.right, w);
		
	}//end of searchR
	
	public WordFreq search(String w){
		
		
		WordFreq word = searchR(root, w);
		if(word == null){
			return null;//word doesn't exist
		}else{
			if(word.getFreq() > getMeanFrequency() && cmp1 == null){ //if current's word frequency is greater than mean frequency then add it on the root
				WordFreq temp = new WordFreq(word.key(), word.getFreq());
				remove(word.key());
				rootInsert(temp);
				return temp;
			}//end if
			return word;
		}//end if/else
	}//end of search
	
	public void update(String w){
		//the word that we are going to update/insert
		WordFreq temp = search(w);
		if(temp == null){ //if it doesn't exist
			WordFreq newWord = new WordFreq(w);
			insert(newWord);
		}else{
			temp.setFreq(temp.getFreq() + 1); //otherwise update that word's frequency
		}//end if/else
		
		
	}//end of update
	
	private TreeNode insertR(TreeNode p, WordFreq element, TreeNode parent){
		
		if(p == null){
			TreeNode node = new TreeNode(element);
			node.parent = parent;
			++size;
			node.setNumber(node.getNumber() + 1);
			return node;
		}//end if
		
		int result;
		//choose comparator
		if(cmp1 != null){
			
			result = cmp.compare(element, p.element);
			int temp = cmp1.compare(element, p.element);
			if(result == 0) return p;
			if(result < 0){
				if(temp < 0)
					p.left = insertR(p.left, element, p);
				else
					p.right = insertR(p.right, element, p);
			}else{
				if(temp < 0)
					p.left = insertR(p.left, element, p);
				else
					p.right = insertR(p.right, element, p);
			}
			
			
		}else{
			
			result = cmp.compare(element, p.element);
			if(result == 0) return p;
			if(result < 0)
				p.left = insertR(p.left, element, p);
			else
				p.right = insertR(p.right, element, p);
		}
		return p;
		
		
		
	}//end of insertR()
	
	public void insert(WordFreq w){
		
		if(search(w.key()) == null){
			root = insertR(root, w, null);
		}//end if
	}//end insert
	
	private TreeNode rootInsert(TreeNode p, WordFreq element, TreeNode parent){
		
		if(p == null){
			TreeNode node = new TreeNode(element);
			node.parent = parent;
			++size;
			return node;
		}//end if
		
		
		int result;
		//choose comparator
		result = cmp.compare(element, p.element);
		
		if(result == 0) return p;
		if(result < 0){
			p.left = rootInsert(p.left, element, p);
			p = rotateRight(p);
		}else{
			p.right = rootInsert(p.right, element, p);
			p = rotateLeft(p);
		}//end if
		return p;
		
	}//end of rootInsert
	
	public void rootInsert(WordFreq element){
		root = rootInsert(root, element, null);
	}//end of rootInsert
	
	/**
	 * Performs a left rotation
	 */
	
	private TreeNode rotateLeft(TreeNode pivot){
		
		TreeNode parent = pivot.parent;
		TreeNode child = pivot.right;
		if(parent == null){
			root = child;
		}else if(parent.left == pivot){
			parent.left = child;
		}else{
			parent.right = child;
		}//end if/else if/else
		
		child.parent = pivot.parent;
		pivot.parent = child;
		pivot.right = child.left;
		if(child.left != null) child.left.parent = pivot;
		child.left = pivot;
		return child;
		
	}//end of rotateLeft
	
	/*
	 * Peerforms a right rotation
	 */
	
	
	private TreeNode rotateRight(TreeNode pivot){
		
		TreeNode parent = pivot.parent;
		TreeNode child = pivot.left;
		if(parent == null){
			root = child;
		}else if(parent.left == pivot){
			parent.left = child;
		}else{
			parent.right = child;
		}//end if/else if/else
		
		child.parent = pivot.parent;
		pivot.parent = child;
		pivot.left = child.right;
		if(child.right != null) child.right.parent = pivot;
		child.right = pivot;
		return child;
		
	}//end of rotateRight
	

	
	public void remove(String w){
		
		WordFreq element = new WordFreq(w);
		TreeNode n = find(element);
		if(n == null) return;
		remove(n);
		
	}//end of remove
	
	private void remove(TreeNode p){
		
		//case 1: we have two chiidren
		if(p.left != null && p.right != null){
			
			TreeNode succ = succ(p);
			p.element = succ.element;
			p = succ;
			
		}//end if
		
		TreeNode parent = p.parent;
		TreeNode child = p.left != null ? p.left : p.right;
		//remove root
		if(parent == null){
			root = child;
		}else if( p == parent.left ){ //bypass p
			parent.left = child;
		}else{
			parent.right = child;
		}//end if / else if/ else
		if(child != null){
			child.parent = parent;
		}//end if
		p.unlink();
		--size;
		
	}//end of remove
	
	private TreeNode succ(TreeNode q){
		if(q.right != null){
			TreeNode p = q.right;
			while(p.left != null) p = p.left;
			return p;
		}else{
			TreeNode p = q.parent;
			TreeNode ch = q;
			while(p != null && ch == p.right){
				ch = p;
				p = p.parent;
			}//end while
			return p;
		}//end if/else
	}//end succ
	
	/*
	 * 
	 *PRINT METHODS 
	 * 
	 */
	
	void print(){
		printTree(root, "->");
	}
	
	void printTree(TreeNode node, String prefix){
	    if(node == null) return;

	    System.out.println(prefix + " + " + node.element.key() + " with freq: " + node.element.getFreq());
	    printTree(node.left , prefix + " left");
	    printTree(node.right , prefix + " right");
	}
	
	
	/*
	 * CALCULATING METHODS
	 */
	
	public int getTotalWords(){
		return totalFileWords;
	}
	
	public int getDistinctWords(){
		return size;
	}//end of size()
	
	
	public double getMeanFrequency(){
		
		return getSumFreqR(root) / getDistinctWords();
		
		
	}//end of getMeanFrequency
	
	
	private double getSumFreqR(TreeNode head){

		//kaleis anadromika ola ta nodes h subtrees
		if(head == null) return 0;
		double sum = ( head.element.getFreq() + getSumFreqR(head.left) + getSumFreqR(head.right) );
		return sum;
		
	}
	
	int getFrequency(String w){
		
		WordFreq word = search(w);
		if(word == null){
			System.out.println(w + " does not exist");
			return 0;
		}
		return word.getFreq();
	}
	
	WordFreq getMaximumFrequency(){
		return root.element;
	}
	
	/*
	 * METHODS OF LIST
	 */
	
	public void removeStopWord(String w){
		stopWords.deleteNode(w);
	}//end of removeStopWords
	 
	public void addStopWord(String w){
		
		stopWords.push(w);
		 
	}//end of addStopWord
	
	public List getStopWordsList(){
		return stopWords;
	}//end of getStopWordsList
	
	
	/*
	 * READING FILE METHOD
	 */
	
	public void load(String fileName){
		
		BufferedReader reader;
		String line;
		String[] tokens;
		
		
		try{
			
			/*
			 *
			 * Creating stop words list first  
			 * 
			 */
			
			reader = new BufferedReader(new InputStreamReader( new FileInputStream(fileName) ) ); //reading txt file with BufferedReader
			
			try{
				
				line = reader.readLine().toLowerCase();
				while(line != null){
					tokens = line.split("[^a-zA-Z0-9]");
					for(String word: tokens){
						if(word.trim().length() > 2 || word.trim().equals("") || word.trim().equals("\n")) //creating stop word list based on length of the words because most stop words are for example a,an,ok,no etc.
							continue;
						String pro = word.trim().replace("[^A-Za-z]", ""); //replace non aplhabetic with ""
						
						if(!pro.equals("")) //if it doesn't exists on stopWords
							addStopWord(pro.trim().toLowerCase());
						
						
					}//end for
					line = reader.readLine();
				}//end while
				
			}catch(IOException e){
				System.out.println("Error Reading File!");
			}//end try/catch
			
			
			/*
			 *
			 * Reading file 
			 * 
			 */
			reader = new BufferedReader(new InputStreamReader( new FileInputStream(fileName) ) ); //reading txt file with BufferedReader
			
			try{
	
				line = reader.readLine().toLowerCase();
				while(line != null){
					tokens = line.split("[^a-zA-Z0-9]");
					for(String word: tokens){
						
						if(word.trim().toLowerCase().equals("") || word.trim().toLowerCase().equals("\n") || stopWords.exists(word)) //if it is useless word or if its on stopWords list continue
							continue;
						String pro = word.trim().toLowerCase().replace("[^A-Za-z]", ""); //replace non aplhabetic with ""
						if(pro != ""){
							totalFileWords++;
							update(pro);
						}
						
					}//end for
					
					
					
					line = reader.readLine();
				}//end while
				
			}catch(IOException e){
				System.out.println("Error Reading File!");
			}
			
			
			
		}catch(FileNotFoundException e){
			System.out.println("File Not Fould!");
		}//end try/catch
		
		
		
		
	}//end of load
	
	public void printAlphabetically(PrintStream stream){
		
		if(root == null)
			stream.println("Tree is empty");
		else{
			stream.println("Sorted Alphabetically");
			stream.println("=====================");
			inOrder(root, stream);
		}
		
	}//end of printByFrequency
	
	public void inOrder(TreeNode focusNode, PrintStream stream){
		
		if(focusNode != null){
			inOrder(focusNode.left, stream);
			stream.println(focusNode.element);
			inOrder(focusNode.right, stream);
			
		}//end if
		
	}//end inOrder
	
	
	
	public void printByFrequency(PrintStream stream){
		
		cmp1 = new FreqComparator();
		ST temp = new ST(new FreqComparator());
		copy(temp, this.root);
		
		
		
		
		if(temp.root == null)
			stream.println("Tree is empty");
		else{
			stream.println("Sorted by frequency");
			stream.println("=====================");
			inOrder(temp.root, stream);
		}//end if
		
	}//end of printByFrequency
	
	public void copy(ST temp, TreeNode root){
		
		if(root == null)
			return;
		temp.insert(root.element);
		if(root.left != null)
			temp.insert(root.left.element);
		if(root.right != null)
			temp.insert(root.right.element);
		
		copy(temp, root.left);
		copy(temp, root.right);
		
	}//end of copy
	

}//end of St
