
public class List {

	static class Node{
		String word;
		Node next;
		
		
		Node(String word){
			this.word = word;
		}
		
		Node (String word, Node t){
			this.word = word;
			next = t;
		}
		
		static void print(Node h){
			
			for(Node t = h.next; t != null; t = t.next){
				System.out.println(t.word + " \n");
			}//end for 
			
		}//end print()
		
		public String toString(){
			return word;
		}
		
	}//end of Node
	
	private Node firstNode;
	private Node lastNode;
	private String name;
	
	
	public List(String listName){
		
		setName(listName);
		firstNode = setLastNode(null);
		
	}//end of const1
	
	public void push(String newWord){
		
		Node newNode = new Node(newWord);
		newNode.next = firstNode;
		firstNode = newNode;
	}
	
	public void deleteNode(String key){
		
		Node temp = firstNode;
		Node prev = null;
		
		if(temp != null && temp.word.equalsIgnoreCase(key)){
			firstNode = temp.next;
		}
		
		while(temp != null && !temp.word.equalsIgnoreCase(key)){
			prev = temp;
			temp = temp.next;
		}
		
		if(temp == null){
			System.out.println("Word: " + key + "does not exists!");
			return;
		}
		
		prev.next = temp.next; //unlink
		
	}//end of deleteNode
	
	public void print(){
		Node start = firstNode;
		while(start != null){
			System.out.println(start.word);
			start = start.next;
		}
		
		
	}
	
	public boolean search(Node head, String w){
		
		Node temp = firstNode;
		while(temp != null){
			if(temp.word.equals(w))
				return true;
			temp = temp.next;
		}
		return false;
		
	}//end of search
	
	public boolean exists(String w){
		return search(firstNode, w);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node getLastNode() {
		return lastNode;
	}

	public Node setLastNode(Node lastNode) {
		this.lastNode = lastNode;
		return lastNode;
	}
	
	
	
}//end of List
