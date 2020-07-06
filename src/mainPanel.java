import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class mainPanel extends JPanel
{	
	//result of statistics.
	private String str = " ";
	private JTextArea[][] Board = new JTextArea[9][9];
	private boolean[][][] TF = new boolean[9][9][9];
	private boolean found = false;
	private JButton work;
	private JButton clear;
	public mainPanel()
	{
		
		
		setBackground(Color.LIGHT_GRAY);
		//layout panel, use borderlayout
		JPanel organizer = new JPanel();
		organizer.setBounds(getX(), getY(), getWidth(), getHeight());
		organizer.setLayout(new BorderLayout());
		JPanel BoardOfTextAreas = new JPanel();
		BoardOfTextAreas.setBounds(getX(), getY(), getWidth(), getHeight());
		BoardOfTextAreas.setLayout(new GridLayout(9, 9));
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				Board[i][j]= new JTextArea(2, 3);
				Board[i][j].setText("0");
				BoardOfTextAreas.add(Board[i][j]);
				for(int k = 0; k < 9; k++)
				{
					TF[i][j][k] = false;
				}
			}
		}
		JPanel Buttons = new JPanel();
		Buttons.setBounds(getX(), getY(), getWidth(), getHeight());
		Buttons.setLayout(new BorderLayout());
		
		organizer.add(BoardOfTextAreas, BorderLayout.NORTH);
		work = new JButton();
		work.setText("¿ªÊ¼¼ÆËã");
		work.addActionListener(new ButtonListener());
		Buttons.add(work, BorderLayout.EAST);
		
		clear = new JButton();
		clear.setText("Clear");
		clear.addActionListener(new ButtonListener());
		Buttons.add(clear, BorderLayout.WEST);
		organizer.add(Buttons, BorderLayout.SOUTH);
		
		//add organizer to main panel
		add(organizer);
		//set the main panel size
		setPreferredSize(new Dimension(400,400));
		
		
		
	}
	private void Read()
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(Board[i][j].getText().charAt(0) == '0')
				{
					for(int k = 0;k<9;k++)
					{
						TF[i][j][k] = false;
					}
					
				}
				else 
				{
					String str;
					str = Board[i][j].getText();
					char ch = str.charAt(0);
					int number = ch-'1';
					if((number >=0)&&(number<=8))
					{
						TF[i][j][number] = true;
					}
				}
			}
		}
	}
	private boolean CheckRow(int row, int col, int num)
	{
		boolean result = true;
		for(int i = 0; i < 9 ; i++)
		{
			if(i != col)
			{
				if(TF[row][i][num])
				{
					result = false;
					break;
				}
			}
		}
		return result;
	}
	private boolean CheckCol(int row, int col, int num)
	{
		boolean result = true;
		for(int i = 0; i < 9 ; i++)
		{
			if(i != row)
			{
				if(TF[i][col][num])
				{
					result = false;
					break;
				}
			}
		}
		return result;
	}
	private boolean CheckSmallGrid(int row, int col, int num)
	{
		boolean result = true;
		int rl = 0;
		int rr = 0;
		int cl = 0;
		int cr = 0;
		if((row >= 0)&&(row <= 2))
		{
			rl = 0;
			rr = 2;
		}
		if((row >= 3)&&(row <= 5))
		{
			rl = 3;
			rr = 5;
		}
		if((row >= 6)&&(row <= 8))
		{
			rl = 6;
			rr = 8;
		}
		if((col >= 0)&&(col <= 2))
		{
			cl = 0;
			cr = 2;
		}
		if((col >= 3)&&(col <= 5))
		{
			cl = 3;
			cr = 5;
		}
		if((col >= 6)&&(col <= 8))
		{
			cl = 6;
			cr = 8;
		}
		for(int i =rl; i <= rr; i++)
		{
			for(int j = cl; j <= cr; j++)
			{
				if((i != row)&&(j != col))
				{
					if(TF[i][j][num] == true)
					{
						result = false;
					}
				}
			}
		}
		return result;
	}
	private void Calculate(int r, int c)
	{
		System.out.println(Integer.toString(r+1)+" "+Integer.toString(c+1));
		int row = r;
		int col = c;
		boolean empty = true;
		for(int i = 0; i < 9; i++)
		{
			if(TF[row][col][i] == true)
				empty = false;
		}
		if(empty == false)
		{
			int nextrow = row;
			int nextcol = col+1;
			if((nextrow == 8)&&(nextcol==9))
			{
				found = true;
				return;
			}
			else 
			{	
				if(nextcol == 9)
				{
					nextrow++;
					nextcol = 0;
				}
				Calculate(nextrow, nextcol);
			}
			
		}
		else
		{
			for(int i = 0; i < 9; i++)
			{
				TF[row][col][i] = true;
				if(CheckRow(row, col, i)==false||CheckCol(row, col, i)==false||CheckSmallGrid(row, col, i)==false)
				{
					TF[row][col][i] = false;
				}					
				else
				{
					int nextrow = row;
					int nextcol = col+1;
					if((nextrow == 8)&&(nextcol==9))
					{
						Board[row][col].setText(Integer.toString(i+1));
						found = true;
						return;
					}
					else 
					{	
						if(nextcol == 9)
						{
							nextrow++;
							nextcol = 0;
						}
						Calculate(nextrow, nextcol);
					}		
				}
				if(found == true)
				{
					Board[row][col].setText(Integer.toString(i+1));
					return;
				}
				TF[row][col][i] = false;
			}
		}	
	}	
	//button listener
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			if(event.getSource() == work)
			{
				Read();
				Calculate(0, 0);
			}
			if(event.getSource() == clear)
			{
				for(int i = 0; i < 9;i++)
				{
					for(int j = 0; j < 9;j++)
					{
						Board[i][j].setText("0");
						for(int k = 0; k < 9; k++)
						{
							TF[i][j][k] = false;
						}
					}
					
				}
			}
		}
	}
}