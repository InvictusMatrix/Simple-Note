import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.Scanner;

public class SimpleNote extends JFrame implements ActionListener {
	
	// Declares variables
	private JTextArea noteArea;
	private JButton saveButton;
	private final String FILENAME = "mynote.txt";
	
	// Constructor for SimpleNote GUI
	public SimpleNote() {
		// super creates JFrame with the name "SimpleNote"
		super("SimpleNote");
		
		// Initializes JTextArea noteArea, wraps it, and loads existing note data into it
		noteArea = new JTextArea();
		noteArea.setLineWrap(true);
		loadNote();
		
		// Initializes JScrollPane scrollPane and adds the noteArea to it
		JScrollPane scrollPane = new JScrollPane(noteArea);
		
		// Initializes JButton saveButton and adds an Action Listener to it
		saveButton = new JButton("Save Note");
		saveButton.addActionListener(this);
		
		// Adds scrollPane and saveButton to the JFrame and aligns them using the Border Layout
		add(scrollPane, BorderLayout.CENTER);
		add(saveButton, BorderLayout.SOUTH);
		
		// Sets size of the window, its close operation, its relative location, and makes it visible
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// Action Listener for saveButton
	@Override
	public void actionPerformed(ActionEvent e) {
		// Listens for JButton to be clicked
		if(e.getSource() == saveButton) {
			// Stores noteArea data in noteContent variable and calls a method to save the data to the file
			saveNote(noteArea.getText());
			
			// Displays a new window indicating that the saveButton worked and the data was saved to the file
			JOptionPane.showMessageDialog(this, "Note saved successfully!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	// Method for writing note data to the file
	public void saveNote(String noteContent) {
		// Attempts to write to the file
		try(FileWriter fileOut = new FileWriter(FILENAME)) {
			// Writes data stored in noteContent to the file
			fileOut.write(noteContent);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method for loading existing data from the file and displaying it in noteArea
	public void loadNote() {
		// Attempts to read the file
		try(FileReader fileIn = new FileReader(FILENAME)) {
			// Initializes Scanner and StringBuilder
			Scanner input = new Scanner(fileIn);
			StringBuilder loaded = new StringBuilder();
			
			// Moves through each line of the file and stores data in loaded variable
			while(input.hasNextLine()) {
				loaded.append(input.nextLine());
				if(input.hasNextLine()) {
					loaded.append("\n");
				}
			}
			
			// Closes Scanner, moves file data to noteContent as a String, and sets noteArea text to the file data 
			input.close();
			noteArea.setText(loaded.toString());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			noteArea.setText("Welcome! Type your note here and save when you are done.\n\n");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// Creates new SimpleNote GUI
		SwingUtilities.invokeLater(SimpleNote::new);
	}

}

