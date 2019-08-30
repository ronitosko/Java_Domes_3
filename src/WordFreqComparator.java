import java.util.Comparator;

final class WordFreqComparator implements Comparator<WordFreq>{

	@Override
	public int compare(WordFreq o1, WordFreq o2) {
		
		String w1 = o1.key();
		String w2 = o2.key();
		int result = w1.compareToIgnoreCase(w2);
		if(result < 0) //01 < o2
			return -1;
		else if(result > 0)
			return 1;
		return 0;
		
		
	}

}
