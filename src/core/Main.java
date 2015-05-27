package core;
import gui.MainView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

	
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		ArrayList<String> sasiadujacePanstwa = new ArrayList<String>();
		ArrayList<String> firstCondition = new ArrayList<String>();
		ArrayList<String> cnf = new ArrayList<String>();
		String sasiedzi;
		String wynikSat;
		int iloscPanstw;
		int iloscSasiadow;
		
		
		System.out.println("Podaj ilosc panstw: ");
		iloscPanstw = reader.nextInt();
		
		System.out.println("Ilosc par sasiadow bez powtarzania: ");
		iloscSasiadow = reader.nextInt();
		
		System.out.println("Podaj sasiadujace panstwa w formacie Panstwo1#Panstwo2: ");
		for(int i=0; i<iloscSasiadow; i++){
			sasiedzi = reader.next();
			sasiadujacePanstwa.add(sasiedzi);
		}
	
	
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
                }
            fileOut.close();
            System.out.println("the file " + filewrite + " is created!"); 
        }
        catch (Exception e){
            System.out.println(e.toString());
        }       
	//wypisuje do konsoli
	for(String linia : cnf){
		System.out.println(linia);
		
		
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
	                   
	                    
	                    System.out.println("Problem spe³noiony");
	                    
	                    System.out.println();
	                    
	                    System.out.println("Oto wynik Rsat");
	                    System.out.println(linee);
	                    wynikSat=linee;
	                    
	                    System.out.println();
	                    
	                    System.out.println("Oto odpowiedzi");
	                  
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
	            	System.out.println("Problem spe³noiony");
	            }
	        }       
	        catch (Exception e){
	            System.out.println(e.toString());
	        } 
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
		                    System.out.println(linee);
		                    wynikSat=linee;
		                    
		                    System.out.println();
		                    
		                    System.out.println("Oto odpowiedzi");
		                  
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
		            	System.out.println("Problem spe³noiony");
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
}
