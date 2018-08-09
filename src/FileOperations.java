import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileOperations {
	
	static String DIRECTORY_NAME ="C:/Users/Acer V17 Nitro/workspace/SearchEngine/projectCorpus";
	static String[] documents;	
	static String[] stopwords = new String[]{"","<",">","{","}","[","]","+","-","(",")","&","%","$","@","!","^","#","*","'ll","'s","'m","a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can","can't","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off","on","once","only","or","other","ought","our","ours ","ourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should","shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while","who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves","###","return","arent","cant","couldnt","didnt","doesnt","dont","hadnt","hasnt","havent","hes","heres","hows","im","isnt","its","lets","mustnt","shant","shes","shouldnt","thats","theres","theyll","theyre","theyve","wasnt","were","werent","whats","whens","wheres","whos","whys","wont","wouldnt","youd","youll","youre","youve"};
	static Integer[] totalNumber;

	public static void eliminateStopWords() throws IOException{
		
		File DIRECTORY = new File(DIRECTORY_NAME);
		File[] listOfFiles = DIRECTORY.listFiles();
		
		documents = new String[listOfFiles.length];
		totalNumber=new Integer[listOfFiles.length];
		for (int i = 0; i < listOfFiles.length; i++) {
			String allWords = "";
				
			BufferedReader br = 
			new BufferedReader(
			   	new InputStreamReader(
					new FileInputStream(listOfFiles[i].getPath()), "utf-8"));
			
		    while (br.readLine() != null)
			   allWords += br.readLine();
			   
			br.close();

			int counter=0;
			allWords = allWords.replaceAll("[^\\w\\s]",""); 
			allWords = allWords.replaceAll("\\P{Print}", " "); // Yazýlamayan karakterleri replace eder.
			allWords = allWords.replaceAll("\\p{Punct}", " "); // Noktalama iþaretleri hallolur.
			
			String[] words = allWords.toLowerCase().split(" ");

			documents[i] = listOfFiles[i].getName();
			
			for (int k = 0; k < words.length; k++){
				
				for (int j = 0; j < stopwords.length; j++){						
					if (words[k].toLowerCase().equals(stopwords[j])){
						words[k] = "";						
					}						
				}
				words[k] = words[k].replaceAll("\\s{2,}"," "); 
				
				if (!words[k].equals("") && words[k].length() != 1){	
					Document doc = new Document(listOfFiles[i].getName(),1);
					doc.setTotalword(doc.getTotalword()+1);
					counter++;
					HashEntry wordEntry = 
							new HashEntry(
								new Word(words[k]),
								doc);
					
					//HashTable.insert(wordEntry);
					HashOpen.insert(wordEntry);
					
				}
				
			}	
			
			totalNumber[i]=counter;
			
		}	

		//HashTable.printCol();
		HashOpen.printCol();
	}
}
	
