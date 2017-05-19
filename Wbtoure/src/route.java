
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComboBox.KeySelectionManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.text.ZoneView;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class route extends JFrame implements ActionListener, MouseListener {

	JPanel p1, panel;
	JLabel bckl;
	JTextField source, dest;
	static JLabel src, targ, blocked;
	static int[][] zoneMatrix;
	int co[],block[];
	static Graphics g = null;
	final static int N = 20;
	final static int IN = 9999999;
	static HashMap<String, Integer> stationIndex;// for indexing station->number
	static int laty[], lonx[], SOURCE, DESTN;// for storing the latitude &
	private static int sum=0;
	 //static int sum1;// longitude
	static int path[], count;// for storing the path
	static boolean hasPath, isBlocked, wasSearched;
	static int currMatrix[][] = new int[N][N];// Doing all the manipulations in
												// this matrix
	static ArrayList<Integer> blockSt1, blockSt2;// Keeping track of Blocked
	Graphics2D g2; // Route
	static String stName[];
	static int atmname[]; 
	int width, height;
	static JComboBox<String> srctxt, desttxt;
	static JCheckBox atmtxt;
	JButton Block;
	JButton findPath;
	GetStationList stlist = new GetStationList();
	GetStationList atmlist=new GetStationList();
	JPanel bckpnl;
	private Color lncolor = Color.gray;
	boolean found = false, isRouteBlocked = false;
	JLabel menubar, distance,close,atm1;
	JFrame frame;
	// constructor
	public route() {
		// Plot();
		// TODO Auto-generated constructor stub
		String st[] = {};

		setTitle("WB Tour and Travels");

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		this.width = width;
		this.height = height;

		makeUI();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setBackground(Color.white);
		setLayout(null);
		setVisible(true);

	}

	public void makeUI() {

		JPanel navbar = new JPanel();

		navbar.setBounds(0, 0, width, (int) (height * 0.05));
		navbar.setBackground(new Color(10, 10, 10, 200));

		JLabel head = new JLabel("West Bengal Tourism");
		head.setBounds(30, 0, 200, 40);
		head.setFont(new Font("segoe ui light", Font.PLAIN, 17));
		head.setForeground(Color.green);
		navbar.add(head);
		add(navbar);
       
		bckpnl = new JPanel();
		bckpnl.setBounds(0, (int) (height * 0.1), width, (int) (height * 0.8));
		bckpnl.setLayout(null);
		bckpnl.setBackground(Color.decode("#778899"));

		
		ImageIcon menu = new ImageIcon("menu.png");
		menubar = new JLabel();
		menubar.setIcon(menu);
		menubar.setBounds(80, 40, 40, 35);
		menubar.addMouseListener(this);
		menubar.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		
		add(menubar);

	}

	private void makeInnerWindow() {
		// TODO Auto-generated method stub
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(Color.decode("#2593E5"));

		JPanel panel=new JPanel();
		panel.setBounds(0,400,400,400);
		panel.setBackground(Color.GRAY);
		frame.add(panel);
		
		
		ImageIcon cross = new ImageIcon("E:\\Dijkjava\\Wbroute\\image1\\x_button.png");
		close = new JLabel();
		close.setIcon(cross);
		close.setToolTipText("close");
		close.setBounds(350, 10, 50, 50);
		close.addMouseListener(this);
		close.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		frame.add(close);

		ImageIcon src = new ImageIcon("E:\\Dijkjava\\Wbroute\\image1\\source.png");
		JLabel lab = new JLabel();
		lab.setIcon(src);
		lab.setToolTipText("Source");
		lab.setBounds(33, 100, 32, 82);
		frame.add(lab);

		ImageIcon dest = new ImageIcon("E:\\Dijkjava\\Wbroute\\image1\\dest.png");
		JLabel lab1 = new JLabel();
		lab1.setIcon(dest);
		lab1.setToolTipText("Destination");
		lab1.setBounds(35, 180, 32, 82);
		frame.add(lab1);
		
		JCheckBox chckbxAtm = new JCheckBox("ATM");
		chckbxAtm.setBounds(100, 250, 97, 23);
		chckbxAtm.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getStateChange() == ItemEvent.SELECTED
                    ? "SELECTED" : "DESELECTED");
            }

		
        });
		getContentPane().add(chckbxAtm);
		
		frame.add(chckbxAtm);
		
		JCheckBox chckbxP = new JCheckBox("Petrolpump");
		chckbxP.setBounds(100, 290, 97, 23);
		getContentPane().add(chckbxP);
		frame.add(chckbxP);
		setVisible(true);
		
		JCheckBox chckbxhotel = new JCheckBox("Hotel");
		chckbxhotel.setBounds(100, 330, 97, 23);
		getContentPane().add(chckbxhotel);
		frame.add(chckbxhotel);
		setVisible(true);



		srctxt = new JComboBox(stlist.NStationList());
		srctxt.setBounds(100, 100, 200, 30);
		srctxt.setBackground(Color.decode("#90ee90"));
		srctxt.setEditable(true);
		Object child = srctxt.getAccessibleContext().getAccessibleChild(0);
		BasicComboPopup popup = (BasicComboPopup) child;
		JList list = popup.getList();
		list.setBackground(Color.white);
		list.setSelectionBackground(Color.decode("#88bae8"));
		JTextField tf = ((JTextField) srctxt.getEditor().getEditorComponent());
		tf.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.gray));

		srctxt.setFocusable(false);
		srctxt.setMaximumRowCount(5);
		srctxt.setFont(new Font("segoe ui", Font.PLAIN, 15));
		frame.add(srctxt);

		desttxt = new JComboBox(stlist.NStationList());
		desttxt.setBounds(100, 205, 200, 30);
		desttxt.setBackground(Color.decode("#ffffff"));
		desttxt.setEditable(true);
		child = desttxt.getAccessibleContext().getAccessibleChild(0);
		popup = (BasicComboPopup) child;
		list = popup.getList();
		list.setBackground(Color.white);
		list.setSelectionBackground(Color.decode("#88bae8"));
		tf = ((JTextField) desttxt.getEditor().getEditorComponent());
		tf.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.gray));

		desttxt.setMaximumRowCount(5);
		desttxt.setFont(new Font("segoe ui", Font.PLAIN, 15));
		desttxt.setFocusable(false);
		frame.add(desttxt);
		
       // atmtxt=new JCheckbox(atmlist.NStationList());
		findPath = new JButton("Find Path");
		findPath.setBounds(100, 403, 100, 30);
		findPath.addActionListener(this);
		frame.add(findPath);

		distance = new JLabel("0Km");
		distance.setForeground(Color.BLACK);
		distance.setBounds(1500, 53, 250, 90);
		frame.add(distance);
		
		Block = new JButton("Block");
		Block.setBackground(Color.BLACK);
		Block.setForeground(Color.WHITE);
		Block.setBounds(135, 450, 100, 30);
		Block.addActionListener(this);
		frame.add(Block);


	atm1 = new JLabel("0");
	atm1.setForeground(Color.BLACK);
	atm1.setBounds(800, 50, 250, 90);
		frame.add(distance);

		
		frame.setSize(400, 600);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.setLocation(0, 100);
		frame.setLayout(null);
	}

	public void paint(Graphics g) {

		super.paint(g);

		String zoneMatrixFile = "DistanceMatrix.txt";
		createConnection(zoneMatrixFile);

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2 = (Graphics2D) g;
		g2.setRenderingHints(rh);
		// The name of the file to open.
		String fileName = "masterFile.txt";
		

		String line = null;
		
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			stationIndex = new HashMap<>();

			laty = new int[N];
			lonx = new int[N];
			stName = new String[N];
			atmname=new int[N];
			int i = 0;
			

			String[] tokens;
		
			while ((line = bufferedReader.readLine()) != null) {

				// \\s+ means any number of whitespaces between tokens
				tokens = line.split("\\s+");
				float lat = Float.parseFloat(tokens[0]);

				float lon = Float.parseFloat(tokens[1]);
				String station = tokens[2];
				int atm = Integer.parseInt(tokens[3]);
				//System.out.println("atm"+ atm);
				stName[i] = station;
				stationIndex.put(station, i);
				atmname[i]=atm;

             laty[i] = (int) ((28 - lat) * (height / 6.33));
				
				g2.setColor(new Color(50, 55, 50, 255)); // 13.71

				lonx[i] = (int) ((lon - 86.70) * (width / 3.05));// 35
				g2.fillOval(lonx[i], laty[i], 7, 7);

				

				g2.drawString(station, lonx[i] + 10, laty[i] + 15);

				i++;

					}
			if (isRouteBlocked == false) {     
			for (int a = 0; a < N; a++) {
				for (int j = 0; j < N; j++) {
					currMatrix[a][j] = zoneMatrix[a][j];

					if (a == j)// if diagonal then leave it to 0
						continue;
					if (currMatrix[a][j] == 0)// replace zero with IN=99999
						currMatrix[a][j] = IN;

				}

			}
			}

			for (i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (zoneMatrix[i][j] != 0) {
						g2.setColor(lncolor);
						g2.drawLine(lonx[i] + 2, laty[i] + 2, lonx[j] + 2,
								laty[j] + 2);

					}
				}
			}

			if (found == true) {

				for (int o = 0; o < count - 1; o++) {
					g2.setColor(Color.BLUE);
					g2.setStroke(new BasicStroke(4));
					g2.drawLine(lonx[co[o]] + 2, laty[co[o]] + 2,
							lonx[co[o + 1]] + 2, laty[co[o + 1]] + 2);
					//sum=sum+atmname[co[o]];
					//System.out.println("atm="+ atmname[co[o]]);
					
					System.out.print(co[o] + "  ");

				}
			}
				//System.out.println("sum:" +sum );
				if (isRouteBlocked == true) {

					for (int o = 0; o < count - 1; o++) {
						g2.setColor(Color.RED);
						g2.setStroke(new BasicStroke(2));
						g2.drawLine(lonx[block[o]] + 2, laty[block[o]] + 2,
								lonx[block[o + 1]] + 2, laty[block[o + 1]] + 2);
						// System.out.println(lonx[co[o]] + "   " + laty[co[o]]);

					}
				}

				
			
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex1) {
			System.out.println("Error reading file '" + fileName + "'");
			
		}
		
	//	System.out.println("Sum11:" + sum1);
	}
	

	void createConnection(String zoneMatrixFile) {
		try {
			String line1 = null;
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(zoneMatrixFile);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			// stationIndex = new HashMap<>();

			String[] token;
			int l = 0;
			zoneMatrix = new int[N][N];
			while ((line1 = bufferedReader.readLine()) != null) {

				// \\s+ means any number of whitespaces between tokens
				token = line1.split("\\s+");

				// System.out.println(tokens[2]);
				for (int j = 0; j < N; j++) {
					zoneMatrix[l][j] = Integer.parseInt(token[j]);

				}
				l++;

			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + zoneMatrixFile + "'");
		} catch (IOException ex1) {
			System.out.println("Error reading file '" + zoneMatrixFile + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}

	}

	
	public static void main(String[] args) {
		new route();
		
		// for initializing the matrix with Infinite for no Connection

	}

	@Override
	public  void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		
		  		
					
			
		if (a.getSource() == findPath) {

			int src = 0, dest = 0;
			boolean check;

			if (srctxt.getSelectedItem().equals("---Select---")
					|| desttxt.getSelectedItem().equals("---Select---")) {
				JOptionPane.showMessageDialog(p1,
						"Please specify source and destination  !");

			} else {

				src = stationIndex.get(srctxt.getSelectedItem());
				dest = stationIndex.get(desttxt.getSelectedItem());
				//boolean selected = buttonModel.isSelected();
				
				//check=atmtxt.isSelected();
				
				if (src != dest /*&& check*/) {
					SOURCE = src;
					DESTN = dest;
					System.out.println("src :" + src + " dest :" + dest);

					co = dijsktra(currMatrix, SOURCE, DESTN, "search");
				//for(int j=0;j<count;j++){
					//sum=sum+atmname[co[j]];
					//}
				    System.out.println("ATM:"+ sum);
                   
					if (co[count] != IN ) {
						distance.setText("" + co[count] + "Km");
						//atm1.setText(""+sum);
						//System.out.println("Sum1:" + ATM);
						found = true;
						repaint(); 
					} else
						distance.setText("");
					System.out.println("shortest distance::" + co[0]);
				} else
					JOptionPane.showMessageDialog(p1,
							"Source and Destination Cannot be Same !");
			}
		}
		if (a.getSource() == Block) {
			int src = 0, dest = 0;

			if (srctxt.getSelectedItem().equals("---Select---")
					|| desttxt.getSelectedItem().equals("---Select---")) {
				JOptionPane.showMessageDialog(p1,
						"Please specify source and destination  !");

			} else {

				src = stationIndex.get(srctxt.getSelectedItem());
				dest = stationIndex.get(desttxt.getSelectedItem());

				if (src != dest) {
					SOURCE = src;
					DESTN = dest;
					System.out.println("src :" + src + " dest :" + dest);

					block = dijsktra(currMatrix, SOURCE, DESTN, "blocked");
					if (count > 2) {

						JOptionPane.showMessageDialog(null,
								"There is no direct route to block");
					} else {
						int n1 = block[0];
						int n2 = block[1];
						System.out.println(n1 + "   " + n2);
						currMatrix[n1][n2] = 777777;
						currMatrix[n2][n1] = 777777;
						System.out.println(currMatrix[24][20]);
						/*
						 * for (int i = 0; i < N; i++) { for (int j = 0; j < N;
						 * j++) { System.out.print(currMatrix[i][j] + "  "); }
						 * System.out.println(); }
						 */
						isRouteBlocked = true;
						repaint();
					}
				} else
					JOptionPane.showMessageDialog(null,
							"Source and Destination Cannot be Same !");
			}
		}


		
	

	}

	public static int[] dijsktra(int cost[][], int source, int target,
			String string) {

		int list[] = new int[50];
		System.out.println("With dijkstra ::" + source + "  " + target);
		int dist[], prev[], selected[],Atm[];
		int i, m, min, start, d, j, noPath = 0;
		boolean hasNoPath = false;
		path = new int[N];
		dist = new int[N];
		prev = new int[N];
		selected = new int[N];
		Atm = new int[N];
		
		System.out.println("Entered Djikstra");

		for (i = 0; i < N; i++) {
			dist[i] = IN;
			prev[i] = -1;
		}
		start = source;
		selected[start] = 1;
		dist[start] = 0;
		//int count1=atm;
		while (selected[target] == 0) {
			min = IN;
			m = 0;
			for (i = 0; i < N; i++) {
				d = dist[start] + cost[start][i];
				if (d < dist[i] && selected[i] == 0) {
					dist[i] = d;
					prev[i] = start;
				}
				if (min > dist[i] && selected[i] == 0) {
					min = dist[i];
					m = i;
				}
			}
			start = m;
			selected[start] = 1;
			System.out.println("Within while");
			noPath++;
			if (noPath > 100)// it catches the noPath logic
			{
				hasNoPath = true;
				break;
			}
		}
		System.out.println("Out of while");

		if (hasNoPath == true) {
			hasPath = false;
			if (string.equals("search"))
				JOptionPane.showMessageDialog(null,
						"There is no route available !");
			//;

		} else {
			start = target;
			j = 0;
			count = 0;
			while (start != -1) {
				path[j] = start;
				start = prev[start];
				count++;
				j++;

				if (count > 50) {
					System.out.println("No path");
					break;
				}
				System.out.println("within while start");
			}
			System.out.println(count);
			// path[j]='\0';
			// strrev(path);
			for (j = 0; j <count; j++) {
				System.out.print(path[j] +"->");
				list[j] = path[j];
			}
			if (count >= 2)
				hasPath = true;

			
			System.out.println("count is more than 2");

		}
		
		list[count] = dist[target];
		return list;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == menubar) {
			makeInnerWindow();

		}
		if(e.getSource()==close){
			frame.dispose();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	

}
