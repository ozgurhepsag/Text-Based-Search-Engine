import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Window extends JPanel implements ActionListener  {	
	
	JTextField textField;
	Button btn;
	public static JTextArea display;	    
	JScrollPane scroll;	
	BufferedImage image;
	
	public Window() throws IOException {
		super();
		
		setSize(657, 520);		
		
		image = ImageIO.read(new File("C:/Users/Acer V17 Nitro/Desktop/YDK/SOS-Red-Logo.png"));

		textField = new JTextField(20);
		//textField.setLocation(8, 9);
		textField.setBounds(180, 130, 220, 20);
		add(textField);
		
		btn = new Button("Search");
		btn.setBounds(410, 130, 60, 20);
		btn.addActionListener(this);
		add(btn);
		
		display = new JTextArea ( 16, 48 );
		display.setBounds(130, 170, 390, 200);
		display.setEditable ( false ); // set textArea non-editable
		
		add(display);
		
		scroll = new JScrollPane ( display );
		scroll.setBounds(130, 170, 390, 200);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		add(scroll);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Search")){
			
			display.setText(null);
			//HashTable.ranking(textField.getText());
			HashOpen.ranking(textField.getText());
			textField.setText("");
			textField.requestFocus();
		}	
		
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 175, 15, 300, 100, this);
    }
	
	

}
