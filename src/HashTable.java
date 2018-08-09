import java.util.zip.CRC32;

public class HashTable {

	private final static int TABLE_SIZE = 10000; // 40.000 50.000 is g
	static private HashEntry[] table = new HashEntry[TABLE_SIZE];
	static int collisionNumber = 0;

	public static int keyGeneration(String wordName) {
		CRC32 crc = new CRC32();
		crc.update(wordName.getBytes());
		/*
		 * int key = 7; for (int i = 0; i < wordName.length(); i++) key = (key *
		 * 31 + wordName.charAt(i));
		 */

		return (int) crc.getValue();
	}

	public static int hashFunction(int key) {
		return key % TABLE_SIZE;
	}

	public static void printCol() {
		for (int i = 0; i < table.length; i++) {

			if (table[i] != null) {

				HashEntry temp = table[i];
				while (temp != null) {
					temp = temp.getRight();
					collisionNumber++;
				}

			}
		}
		Window.display.append("You can find answer for your question. " + "\n");
		//System.out.println("Total collusion number: " + collisionNumber);
	}

	public static void ranking(String query) {

		String[] tokens = query.split(" ");
		HashEntry[] hashEntry = new HashEntry[tokens.length];

		double[][] docTermMatrix = new double[query.length()][670];

		for (int i = 0; i < tokens.length; i++) {
			hashEntry[i] = search(tokens[i]);

			Document tempDoc = hashEntry[i].getUp();
			while (tempDoc != null) {
				for (int j = 0; j < FileOperations.documents.length; j++) {

					if (tempDoc.getName().equals(FileOperations.documents[j])) {

						double distanceQuery = 0.0;
						double distanceDoc = 0.0;

						double temp = ((double)tempDoc.getFrequency()/(double)FileOperations.totalNumber[j]) * hashEntry[i].getWord().getIDF();
						distanceQuery += Math.pow(temp, 2);
						distanceDoc += Math.pow(((double)tempDoc.getFrequency()/(double)FileOperations.totalNumber[j]), 2);

						double oklidWeight = (Math.sqrt(distanceQuery) + Math.sqrt(distanceDoc));

						docTermMatrix[i][j] = oklidWeight*100;

					}
				}
				tempDoc = tempDoc.getUp();
			}
		}

		for (int x = 0; x < 670; x++) {
			int counter = 0;
			for (int y = 0; y < tokens.length; y++) {
				counter += Math.pow(docTermMatrix[y][x], 2);
			}
			if (counter != 0) {
				docTermMatrix[0][x] = Math.sqrt(counter);
				docTermMatrix[1][x] = x;
			}
		}

		double[] tempSorted = docTermMatrix[0];
		tempSorted = Our_sort(docTermMatrix[0],0,669,64);

		for (int i = tempSorted.length - 1; i >= 0; i--) {
			if (tempSorted[i] != 0)
				 Window.display.append(FileOperations.documents[i]+ " =====> "+ tempSorted[i] + "\n");
			//	System.out.println(FileOperations.documents[i] + " =====> " + (tempSorted[i]));
		}

	}

	public static void insert(HashEntry wordEntry) {

		int key = Math.abs(hashFunction(keyGeneration(wordEntry.getWord().getName())));

		if (table[key] == null) {
			wordEntry.getWord().setKey(key);
			table[key] = wordEntry;
		} else {

			boolean isNewWord = true;
			boolean isNewDocument = true;

			// 1.Kelime durumunun içinde: a) 1.Döküman Durumu b)1'. döküman
			// olmama durumu incelendi.
			if (table[key].getWord().getName().equals(wordEntry.getWord().getName())) {
				if (table[key].getUp().getName().equals(wordEntry.getUp().getName()))
					table[key].getUp()
							.setFrequency(table[key].getUp().getFrequency() + wordEntry.getUp().getFrequency());

				else {
					Document tempDoc = table[key].getUp();
					while (tempDoc.getUp() != null) {
						if (tempDoc.getUp().getName().equals(wordEntry.getUp().getName())) {
							tempDoc.getUp()
									.setFrequency(tempDoc.getUp().getFrequency() + wordEntry.getUp().getFrequency());
							isNewDocument = false;

						}
						tempDoc = tempDoc.getUp();

					}
					if (isNewDocument) {
						tempDoc.setUp(wordEntry.getUp());
					}
				}
			}
			// 1'.Kelime deðil durumu içinde: a) 1.Döküman b)1'. döküman olmama
			// durumu incelendi.
			else {
				HashEntry tempHashEntry = table[key];
				while (tempHashEntry.getRight() != null) {

					if (tempHashEntry.getWord().getName().equals(wordEntry.getWord().getName())) {
						if (table[key].getUp().getName().equals(wordEntry.getUp().getName()))
							table[key].getUp().setFrequency(table[key].getUp().getFrequency() + wordEntry.getUp().getFrequency());

						else {
							Document tempDoc = tempHashEntry.getUp();

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
							isNewWord = false;

						}
					}
					tempHashEntry = tempHashEntry.getRight();
				}
				if (isNewWord) {
					wordEntry.getWord().setKey(key);
					tempHashEntry.setRight(wordEntry);
				}
			}

		}

	}

	public static HashEntry search(String nameOfWord) {

		// Window.display.append(nameOfWord + " için aramaya baþladýk sonuçlarý
		// þimdi göstereceðiz canem... " + "\n");
		//System.out.println(nameOfWord + " için aramaya baþladýk sonuçlarý þimdi göstereceðiz canem... ");
		int key = Math.abs(hashFunction(keyGeneration(nameOfWord)));

		if (key < TABLE_SIZE) {
			HashEntry tempHash = table[key];
			boolean isThere = false;
			while (tempHash != null) {
				if (tempHash.getWord().getName().equals(nameOfWord)) {
					isThere = true;
					Document tempDoc = tempHash.getUp();
					int docLength = 0;
					while (tempDoc != null) {
						// System.out.println(tempDoc.getName() + " : " +
						// tempDoc.getFrequency());
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
			if (!isThere) {
				System.out.println("Aradýðýnýz kelimeyi bulamadýk, tek bildiðimiz bu!");
				return null;
			}
			return null;
		} else {
			System.out.println("Aradýðýnýz kelimeyi bulamadýk, tek bildiðimiz bu!");
			return null;
		}

	}

	public static double[] Our_sort(double[] array, int p, int q, int digit) {
		int thisp = p;
		int thisq = q;

		while (p <= q) {
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

		if (digit > 1 && (p != thisp || q != thisq)) {
			int temp = digit;
			Our_sort(array, thisp, q, temp - 1);
			Our_sort(array, p, thisq, digit - 1);
			return array;
		}
		return null;
	}

	public static int getBit(double n, int digit) {
		return ((int) n >> digit - 1) & 1;
	}
	
}
