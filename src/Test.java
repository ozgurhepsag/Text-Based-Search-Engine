import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Test {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException{

		JFrame frame = new JFrame();
		Window window = new Window();
		
		window.setLayout(null);
		frame.setLocation(600, 300);
		frame.setSize(657, 520);
		frame.add(window);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		Window.display.append("Files are loading ... \n" );
		
		Calendar rightNow = Calendar.getInstance();
		long offset = rightNow.get(Calendar.ZONE_OFFSET)
				+ rightNow.get(Calendar.DST_OFFSET);
		long sinceMidnight1 = (rightNow.getTimeInMillis() + offset);
		
		Scanner inputAlGardas = new Scanner(System.in);
		FileOperations fileOp = new FileOperations();
		FileOperations.eliminateStopWords();
		
		rightNow = Calendar.getInstance();
		offset = rightNow.get(Calendar.ZONE_OFFSET)
				+ rightNow.get(Calendar.DST_OFFSET);
		 long sinceMidnight2 = (rightNow.getTimeInMillis() + offset);
		 String elapsedTime = String.valueOf((sinceMidnight2-sinceMidnight1)/1000);
		 elapsedTime += " saniye.";
		 
		Window.display.append(elapsedTime);
		
		/*boolean flag = true;
		while(flag){
			System.out.println("Queryi giriniz: ");
			HashTable.ranking(inputAlGardas.nextLine());
			//HashOpen.findFrequencyforAWord(inputAlGardas.nextLine());
			//HashOpen.ranking(inputAlGardas.nextLine());
			rightNow = Calendar.getInstance();
			offset = rightNow.get(Calendar.ZONE_OFFSET)
					+ rightNow.get(Calendar.DST_OFFSET);
			 long sinceMidnight2 = (rightNow.getTimeInMillis() + offset);
			 
			System.out.println((sinceMidnight2-sinceMidnight1)/1000 + " saniye.");
			//flag = false;
			
		}*/
		
		
		
	}
	
	
	
	
}
