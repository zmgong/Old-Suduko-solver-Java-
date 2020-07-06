import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.util.*;
import java.io.*;        
import javax.swing.*;    
import javax.swing.filechooser.*;            
import javax.swing.filechooser.FileFilter;  
import java.text.SimpleDateFormat;           

public class NoteBook{
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Suduko solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(950, 720));
		//set the size to be constant.
		frame.setResizable(true);
		frame.add(new mainPanel());
		frame.pack();
		frame.setVisible(true);
	}
}