import java.util.Comparator;

final class FreqComparator implements Comparator<WordFreq>{

	@Override
	public int compare(WordFreq o1, WordFreq o2) {
		
		int w1 = o1.getFreq();
		int w2 = o2.getFreq();
		
		if(w1 < w2) //01 < o2
			return -1;
		else if(w1 > w2)
			return 1;
		return 0;
		
		
	}

}
