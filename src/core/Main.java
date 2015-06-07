package core;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.swing.JPanel;


public class Main extends JFrame {
	public Main() {
	}

	private static JTextField textIloscKrajow;
	private static JTextField textIloscParSasiadow;
	private static JTextField textParySasiadow;
	private static JTextArea textWynik;
	private static JTextArea textWejscieSAT;
	
	public static void main(String[] args) {
		
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	                
	            }
	        });
		 
		
	}
	public static class CoreResult {
		public String satWejscie;
		public String wynik;
	}
	
	public static CoreResult core (int iloscPanstw, int iloscSasiadow, ArrayList<String> sasiadujacePanstwa){
		ArrayList<String> firstCondition = new ArrayList<String>();
		ArrayList<String> cnf = new ArrayList<String>();
		String sasiedzi;
		String wynikSat;
		
		
		firstCondition =  Sat.firstCondition(iloscPanstw);
		cnf.addAll(firstCondition);
		cnf.addAll(Sat.secondCondition(iloscPanstw));
		cnf.addAll(Sat.thirdCondition(iloscPanstw, sasiadujacePanstwa));
		
		//utworzenie pliku
				try {
		            String filewrite ="problem.cnf";
					FileWriter fw = new FileWriter (filewrite);
		            BufferedWriter bw = new BufferedWriter (fw);
		            PrintWriter fileOut = new PrintWriter (bw); 
		            
		            // generowanie pierwszej lini pliku dla Solvera
		                fileOut.println ("p cnf " + iloscPanstw*4 + " " + cnf.size()); 
		               
		                // petla zapisujaca klauzule dla Rsat
		                for(String linia : cnf){
		                	fileOut.println(linia); 
		                	textWejscieSAT.setText(textWejscieSAT.getText()+linia.toString()+"\n");
		                }
		            fileOut.close();
		            System.out.println("the file " + filewrite + " is created!"); 
		        }
		        catch (Exception e){
		            System.out.println(e.toString());
		        }       
			//wypisuje do okienka
			for(String linia : cnf){
				//MainView.getTextWejscieSAT().setText(linia);
				//System.out.println(linia);
			}
			
			
			// tablica wszystkich kolorow dla kazdego panstwa
			int [] tablicaZmiennych = new int [iloscPanstw*4];
			int x=0;
			for(String zmienna: firstCondition){
				for(int i=0; i<4; i++){
					tablicaZmiennych[x]=Integer.parseInt(zmienna.split(" ")[i]);
					x++;
				}

			}
			
			//wywolanie rsatu
			try
			{
			ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", "rsat_2.01_win.exe problem.cnf -s >wynik.txt");
		        builder.redirectErrorStream(true);
		        Process p = builder.start();
		        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        String cmd;
		        while (true) {
		            cmd = r.readLine();
		            
		            if (cmd == null) { break; }
		            System.out.println(cmd);
		        }
			}
			catch (Exception e){
	            System.out.println(e.toString());
			}
			
			//wpisanie z pliku do wynik Sat
			
			 String file ="wynik.txt";

		        try{
		            InputStream ips=new FileInputStream(file); 
		            InputStreamReader ipsr=new InputStreamReader(ips);
		            BufferedReader br=new BufferedReader(ipsr);
		            String line;
		            line=br.readLine();
		            line=br.readLine();
		            line=br.readLine();
		            line=br.readLine();
		            line=br.readLine();
		           
		            line = line.replace("s ", "");
		            System.out.println();
		            
		           // System.out.println(line);
		           
		            
		            br.close(); 
		           
		           
		            if(line.equals("SATISFIABLE")  ){
		            	
		            	try{
		                     
		            		InputStream ipss=new FileInputStream(file); 
		                    InputStreamReader ipsrr=new InputStreamReader(ipss);
		                    BufferedReader brr=new BufferedReader(ipsrr);
		                    String linee;
		                    linee=brr.readLine();
		                    linee=brr.readLine();
		                    linee = linee.replace("v ", "");
		                    
		                    System.out.println();
		                   
		                   // MainView.setTextWynik(textWynik);
		                    System.out.println("Problem spe³noiony");
		                    
		                    System.out.println();
		                    
		                    System.out.println("Oto wynik Rsat");
		                    textWynik.setText("Oto wynik Rsat\n");
		                    System.out.println(linee);
		                    // tutaj tablica
		                    
		                    
		                    
		                    
		                    textWynik.setText(textWynik.getText()+"\n"+linee);
		                    wynikSat=linee;
		                    
		                    System.out.println();
		                    
		                    System.out.println("Oto odpowiedzi");
		                    textWynik.setText(textWynik.getText()+"\n"+line+"\n");
		                    brr.close(); 
		                    String[] wynikSplit = wynikSat.split(" ");
		            		int [] wynikowaTablica = new int[wynikSplit.length];

		            		for(int i=0; i!= wynikSplit.length; i++){
		            			wynikowaTablica[i] = Integer.parseInt(wynikSplit[i]);
		            		}
		            		
		            		int panstwo;
		            		int kolor;
		            		for(int i=0; i!=wynikSplit.length; i++){
		            			for(int j=0; j<iloscPanstw*4; j++){
		            				if(tablicaZmiennych[j] == wynikowaTablica[i]){
		            					panstwo = ((tablicaZmiennych[j]-1)/4)+1;
		            					kolor = ((tablicaZmiennych[j]-1)%4)+1;
		            					System.out.println("Panstwo:" + panstwo + " kolor:" + kolor);
		            					textWynik.setText(textWynik.getText()+"\n"+"Panstwo:" + panstwo + " kolor:" + kolor);
		            				}
		            			}
		            		}
		                }       
		                catch (Exception e){
		                    System.out.println(e.toString());
		                }
		            }
		            else
		            {
		            	System.out.println("Problem niespe³niony");
		            	textWynik.setText(textWynik.getText()+"\n"+"Problem niespe³niony");
        				
		            }
		        }       
		        catch (Exception e){
		            System.out.println(e.toString());
		        } 
		        
		        CoreResult object = new CoreResult();
		        object.satWejscie = "String";
		        object.wynik = "Sreing";
		        
		        return object;
		
	}
	 private static void createAndShowGUI() {
	        JFrame frame = new JFrame("4 Kolorowanie Mapy");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(700,600);
	        frame.getContentPane().setLayout(null);
	        
	        JLabel lblIloscKrajw = new JLabel("Ilosc Kraj\u00F3w:");
	        lblIloscKrajw.setBounds(10, 11, 150, 14);
	        frame.getContentPane().add(lblIloscKrajw);
	        
	        JLabel lblIloscParSsiadw = new JLabel("Ilosc Par S\u0105siad\u00F3w:");
	        lblIloscParSsiadw.setBounds(10, 36, 150, 14);
	        frame.getContentPane().add(lblIloscParSsiadw);
	        
	        textIloscKrajow = new JTextField();
	        textIloscKrajow.setText("3");
	        textIloscKrajow.setBounds(170, 8, 86, 20);
	        frame.getContentPane().add(textIloscKrajow);
	        textIloscKrajow.setColumns(10);
	        
	        textIloscParSasiadow = new JTextField();
	        textIloscParSasiadow.setText("2");
	        textIloscParSasiadow.setBounds(170, 33, 86, 20);
	        frame.getContentPane().add(textIloscParSasiadow);
	        textIloscParSasiadow.setColumns(10);
	        
	        
	        
	        JLabel lblParySasiadow = new JLabel("Pary S\u0105siad\u00F3w:");
	        lblParySasiadow.setBounds(10, 61, 100, 14);
	        frame.getContentPane().add(lblParySasiadow);
	        
	        JLabel lblPostaPastwopastwo = new JLabel("Posta\u0107: Pa\u0144stwo1#Pa\u0144stwo2");
	        lblPostaPastwopastwo.setBounds(10, 73, 150, 14);
	        frame.getContentPane().add(lblPostaPastwopastwo);
	        
	        
	        
	        
	        
	        JLabel lblWynik = new JLabel("Wynik:");
	        lblWynik.setBounds(530, 11, 46, 14);
	        frame.getContentPane().add(lblWynik);
	        
	        textWynik = new JTextArea();
	        textWynik.setFont(new Font("Tahoma", Font.PLAIN, 12));
	        textWynik.setEditable(false);
	        textWynik.setBounds(448, 36, 170, 501);
	        frame.getContentPane().add(textWynik);
	        textWynik.setColumns(10);
	        JScrollPane jp1 = new JScrollPane(textWynik);
	        frame.getContentPane().add(jp1, BorderLayout.CENTER);
	        jp1.setBounds(448, 36, 170, 501);
	       
	        jp1.setVisible(true);
	        
	       
	        
	        
	        JLabel lblWejscieSAT = new JLabel("Wej\u015Bcie do SAT Solvera:");
	        lblWejscieSAT.setBounds(281, 11, 120, 14);
	        frame.getContentPane().add(lblWejscieSAT);
	        
	        textWejscieSAT = new JTextArea();
	        textWejscieSAT.setTabSize(60);
	        textWejscieSAT.setEditable(false);
	        
	        textWejscieSAT.setBounds(268, 36, 170, 501);
	        frame.getContentPane().add(textWejscieSAT);
	        textWejscieSAT.setColumns(10);
	        JScrollPane jp = new JScrollPane(textWejscieSAT);
	        frame.getContentPane().add(jp, BorderLayout.CENTER);
	        jp.setBounds(268, 36, 170, 501);
	       
	        jp.setVisible(true);
	        
	        
	        JButton btnWykonaj = new JButton("Wykonaj");
	        btnWykonaj.setBounds(10, 98, 150, 40);
	        frame.getContentPane().add(btnWykonaj);
	        
	         final JTextPane textParySasiadow = new JTextPane();
	         textParySasiadow.setText("1#2\r\n2#3");
	        textParySasiadow.setBounds(170, 64, 86, 473);
	        frame.getContentPane().add(textParySasiadow);
	        JScrollPane jp2 = new JScrollPane(textParySasiadow);
	        frame.getContentPane().add(jp2, BorderLayout.CENTER);
	        jp2.setBounds(170, 64, 86, 473);
	       
	        jp2.setVisible(true);
	        
	        
	        btnWykonaj.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ArrayList<String> sasiadujacePanstwa = new ArrayList<String>();
					String n;
					int sasiedzi=0;
					sasiedzi=Integer.parseInt(textIloscParSasiadow.getText());
						String[] tablica;
						tablica=textParySasiadow.getText().split("\n");
						for(int i =0 ; i>sasiedzi;i++)
						{
						sasiadujacePanstwa.add(tablica[i]);
						}
					Main.core(Integer.parseInt(textIloscKrajow.getText()), sasiedzi, sasiadujacePanstwa);
					
				}
			});
	        
	        frame.setVisible(true);
	    }
}
