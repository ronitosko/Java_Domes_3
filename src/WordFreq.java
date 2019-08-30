public class WordFreq{
	
	private String key;
	private int freq;
	
	
	//constructors
	public WordFreq(){
		key = null;
		freq = 0;
	}
	
	public WordFreq(String key){
		this.key = key;
		freq = 1;
	}
	
	public WordFreq(String key, int freq){
		this.key = key;
		this.freq = freq;
	}
	
	
	//setters and getters 
	public void setKey(String key){
		this.key = key;
	}
	
	public String key(){
		return key;
	}
	
	public void setFreq(int freq){
		this.freq = freq;
	}
	
	public int getFreq(){
		return freq;
	}
	
	@Override
	public String toString(){
		return key + " appeared " + freq;
	}
	
	
}//end of WordFreq