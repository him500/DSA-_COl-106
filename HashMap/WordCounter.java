package col106.assignment4.HashMap;

import java.util.HashMap;

public class WordCounter {
	HashMap dictionary;
	public WordCounter(){
		dictionary=new HashMap(2);
	}

	public int count(String str, String word){
		int n=word.length();
		if(str.length()<n)
			return 0;
		int i=0;
		while(i+n<=str.length()){
			if(str.substring(i,i+n).equals(word)){
				if (dictionary.containsKey(word)){
					int val=(int)dictionary.get(word);
					dictionary.put(word, val+1);
				}
				else{
					dictionary.put(word,1);
				}
				// i+=n-1;
			}
			i++;
		}
		if (!dictionary.containsKey(word))
			return 0;
		return (int)dictionary.get(word);
	}
	public static void main(String[] args){
		WordCounter wobj=new WordCounter();
		String str="eee";
		String word="ee";
		System.out.print(wobj.count(str,word));
	}
}
