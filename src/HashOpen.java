import java.math.BigInteger;
import java.util.zip.CRC32;

public class HashOpen {
	static int TABLE_SIZE_OpenAdr = 10000; // 1 000 033
    private static HashEntry[] tableOpenAdr = new HashEntry[TABLE_SIZE_OpenAdr];
	private static double loadFactor = 0.6;
	static int slotCounter = 0;	
	
	static int collisionNumber = 0;
	
	public static int keyGeneration(String wordName){	
		
		CRC32 crc = new CRC32();
		crc.update(wordName.getBytes());
		/*int key = 7;
		for (int i = 0; i < wordName.length(); i++)
			key = (key * 31 + wordName.charAt(i));*/
		
		return (int) crc.getValue();		
	
	}
	
	public static int hashFunction1(int key){		
		return key % TABLE_SIZE_OpenAdr ;
	}
	public static int hashFunction2(int key){
		return (int)Math.floor(key/5);
	}
	
	public static int hashForDouble(int key,int i){		
		return (hashFunction1(key)+i*hashFunction2(key)) % TABLE_SIZE_OpenAdr;
	}
	public static int hashForLinear(int key,int i){
		return (hashFunction1(key)+i) % TABLE_SIZE_OpenAdr;
	}
	public static int hashForQuadratic(int key,int i){
		return (hashFunction1(key) + 7 * i + 13 * i * i) % TABLE_SIZE_OpenAdr;
	}
	
	public static void printCol(){	
		/*for (int i = 0; i < tableOpenAdr.length; i++){
			if (tableOpenAdr[i] != null){
				HashEntry temp = tableOpenAdr[i];		
				while (temp != null){
					/*System.out.println(tableOpenAdr[i].getWord().getName());
					System.out.println("-----");
					Document tempDoc = temp.getUp();
					while (tempDoc != null){
						System.out.println(" " + tempDoc.getName() + " " + tempDoc.getFrequency() + " ");						
						tempDoc = tempDoc.getUp();
					}
					temp = temp.getRight();
				}
			}
		}*/
		System.out.println("toplam collision bu kadar hocam: " + collisionNumber);
	}		
	
	public static void rehashing(){
		
		BigInteger prime = BigInteger.valueOf(2*TABLE_SIZE_OpenAdr); 
		prime = prime.nextProbablePrime();
		
		HashEntry[] oldOpenAdr = tableOpenAdr; 
		
		TABLE_SIZE_OpenAdr = prime.intValue();
		
		tableOpenAdr = new HashEntry[TABLE_SIZE_OpenAdr];
		
		slotCounter = 0;
		for (int i = 0; i < oldOpenAdr.length; i++){			
			if (oldOpenAdr[i] != null){
				insert(oldOpenAdr[i]);
			}				
		}
		System.out.println("Oluum load factore geldik kalk kalk: " + TABLE_SIZE_OpenAdr);

	}
	
	public static void insert(HashEntry wordEntry) {
		int i = 0;
		while (true) {

			// int key = Math.abs(hashForDouble(keyGeneration(wordEntry.getWord().getName()), i));
			//int key = Math.abs(hashForLinear(keyGeneration(wordEntry.getWord().getName()), i));
			int key = Math.abs(hashForQuadratic(keyGeneration(wordEntry.getWord().getName()), i));

			if (tableOpenAdr[key] == null) {
				wordEntry.getWord().setKey(key);
				tableOpenAdr[key] = wordEntry;
				slotCounter++;
				break;
			} else if (!tableOpenAdr[key].getWord().getName().equals(wordEntry.getWord().getName())) {// collusion right here!
				i++;
				collisionNumber++;
			} else {

				if (tableOpenAdr[key].getUp().getName().equals(wordEntry.getUp().getName()))
					tableOpenAdr[key].getUp().setFrequency(
							tableOpenAdr[key].getUp().getFrequency() + wordEntry.getUp().getFrequency());

				else {

					boolean isNewDocument = true;
					Document tempDoc = tableOpenAdr[key].getUp();

					while (tempDoc.getUp() != null) {
						if (tempDoc.getUp().getName().equals(wordEntry.getUp().getName())) {
							tempDoc.getUp().setFrequency(
									tempDoc.getUp().getFrequency() + wordEntry.getUp().getFrequency());
							isNewDocument = false;
						}
						tempDoc = tempDoc.getUp();
					}

					if (isNewDocument) {
						tempDoc.setUp(wordEntry.getUp());
					}

				}

				break;
			}
			if (loadFactor <= (double) slotCounter / TABLE_SIZE_OpenAdr) {
				System.out.println("rehashing baþladý");
				rehashing();
				System.out.println("rehashing bitti");
				break;
			}

		} 
	
	}
	
	public static void findFrequencyforAWord(String nameOfWord){
		
		int i = 0;
		int key;
		while (true) {
			 key = Math.abs(hashForDouble(keyGeneration(nameOfWord), i));
		    //key = Math.abs(hashForLinear(keyGeneration(nameOfWord), i));
			// key = Math.abs(hashForQuadratic(keyGeneration(nameOfWord), i));
			if (tableOpenAdr[key].getWord().getName().equals(nameOfWord))
				break;
			else if (tableOpenAdr[key] == null){
				System.out.println("Bu kelimeyi bulamadýk, tek bildiðimiz bu.");
				break;
			}
			i++;
		}
		
		HashEntry tempHash = tableOpenAdr[key];
		
		while (tempHash != null){

			if (tempHash.getWord().getName().equals(nameOfWord)){
				Document tempDoc = tempHash.getUp();
				while (tempDoc != null){			
					System.out.println(tempDoc.getName() + " : " + tempDoc.getFrequency());
					tempDoc = tempDoc.getUp();		
				}										
			}			
			tempHash = tempHash.getRight();			
			
		}
	}
	
	
	public static void ranking(String query){
		
		String[] tokens = query.split(" ");
		HashEntry[] hashEntry = new HashEntry[tokens.length];

		double[][] docTermMatrix = new double[query.length()][670];
		
		for (int i = 0; i < tokens.length; i++){
			hashEntry[i] = search(tokens[i]);	
			
			Document tempDoc = hashEntry[i].getUp();
			while (tempDoc != null) {
				for (int j = 0; j < FileOperations.documents.length; j++){
					if (tempDoc.getName().equals(FileOperations.documents[j])){
						
						double distanceQuery = 0.0;
						double distanceDoc = 0.0;
						
						double temp = ((double)tempDoc.getFrequency()/(double)FileOperations.totalNumber[j]) * hashEntry[i].getWord().getIDF();
						distanceQuery += Math.pow(temp, 2);
						distanceDoc += Math.pow(((double)tempDoc.getFrequency()/(double)FileOperations.totalNumber[j]), 2);
						
						double oklidWeight = (Math.sqrt(distanceQuery) + Math.sqrt(distanceDoc));
						
						docTermMatrix[i][j] = oklidWeight * 100;
						
					}
				}
				tempDoc = tempDoc.getUp();
			}
		}
		
		for (int x = 0; x < 670; x++){
			int counter = 0;
			for (int y = 0; y < tokens.length; y++){
				counter += Math.pow(docTermMatrix[y][x],2);
			}
			if (counter != 0){
				docTermMatrix[0][x] = Math.sqrt(counter);
				docTermMatrix[1][x] = x; 
			}
		}
	
		
		double[] tempSorted = docTermMatrix[0];
		tempSorted = Our_sort(docTermMatrix[0],0,669,64);
		
		for (int i = tempSorted.length-1; i >= 0; i--){
			if (tempSorted[i] != 0)
				Window.display.append(FileOperations.documents[i]+ " =====> "+ tempSorted[i] + "\n");

				//System.out.println(FileOperations.documents[i]+ " =====> " + tempSorted[i]);
		}
		
	}

	public static HashEntry search(String nameOfWord){
		
		System.out.println(nameOfWord + " için aramaya baþladýk sonuçlarý þimdi göstereceðiz canem... " );
		
		int i = 0;
		while (true){
			
			int key = Math.abs(hashForDouble(keyGeneration(nameOfWord), i));
			// int key = Math.abs(hashForLinear(keyGeneration(nameOfWord), i));
			//int key = Math.abs(hashForQuadratic(keyGeneration(nameOfWord), i));
			
			if (tableOpenAdr[key] != null){
				HashEntry tempHash = tableOpenAdr[key];
				boolean isThere = false;
				while (tempHash != null){
					if (tempHash.getWord().getName().equals(nameOfWord)){
						isThere = true;
						
						Document tempDoc = tempHash.getUp();
						int docLength = 0;
						while (tempDoc != null) {
							//System.out.println(tempDoc.getName() + " : " + tempDoc.getFrequency());
							//tempDoc.TF();
							docLength++;
							tempDoc = tempDoc.getUp();
						}
					
						tempHash.getWord().IDF(docLength);
						
						tempDoc = tempHash.getUp();
						while (tempDoc != null) {
							//tempDoc.setTFIDF(tempDoc.getTf() * tempHash.getWord().getIDF());
							tempDoc = tempDoc.getUp();
						}
						return tempHash;
						
					}
					tempHash = tempHash.getRight();
				}
				
				if (!isThere)
					System.out.println(i + " ncý denememiz hala bulamadýk. ");
				
			}
			i++;	
		}	
		
	}

	public static double[] Our_sort(double[] array, int p, int q, int digit) {
		int thisp=p;
		int thisq=q;
		
		while (p<=q){
			if (getBit(array[p], digit) == 1 && getBit(array[q], digit) == 0) {
				double tempVar = array[p];
				array[p] = array[q];
				array[q] = tempVar;
				String temp = FileOperations.documents[p];
				FileOperations.documents[p] = FileOperations.documents[q];
				FileOperations.documents[q] = temp;
				
			} else if (getBit(array[p], digit) == 0 && getBit(array[q], digit) == 0) {
				p++;
			} else if (getBit(array[p], digit) == 1 && getBit(array[q], digit) == 1) {
				q--;
			} else if (getBit(array[p], digit) == 0 && getBit(array[q], digit) == 1) {
				q--; 
				p++;
			}			
		}

		if(digit > 1 && (p != thisp || q!=thisq))
		{
			int temp = digit;
			Our_sort(array,thisp,q,temp-1);
			Our_sort(array,p,thisq,digit-1);
			return array;
		}
		return null;
	}
	public static int getBit(double n, int digit) {
	    return ((int)n >> digit-1) & 1;
	}
	
	
}
