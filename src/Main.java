import java.util.ArrayList;
import java.util.Scanner;


public class Main {

	
	
	private static Scanner reader;

	public static void main(String[] args) {
		reader = new Scanner(System.in);
		int iloscPanstw;
		ArrayList<String> sasiadujacePanstwa = new ArrayList<String>();
		ArrayList<String> cnf = new ArrayList<String>();
		int  iloscSasiadow;
		String sasiedzi;
		String wynikSat;
		
		System.out.println("Podaj ilosc panstw: ");
		iloscPanstw = reader.nextInt();
		
		System.out.println("Ilosc par sasiadow bez powtarzania: ");
		iloscSasiadow = reader.nextInt();
		
		System.out.println("Podaj sasiadujace panstwa w formacie Panstwo1#Panstwo2: ");
		for(int i=0; i<iloscSasiadow; i++){
			sasiedzi = reader.next();
			sasiadujacePanstwa.add(sasiedzi);
		}
	
		cnf.addAll(Sat.firstCondition(iloscPanstw));
		cnf.addAll(Sat.secondCondition(iloscPanstw));
		cnf.addAll(Sat.thirdCondition(iloscPanstw, sasiadujacePanstwa));

		for(String linia : cnf){
			System.out.println(linia);
		}
		
		ArrayList<String> zmienne = new ArrayList<String>();
		zmienne =  Sat.firstCondition(iloscPanstw);
		int [] tablicaZmiennych = new int [iloscPanstw*4];
		int x=0;
		for(String zmienna: zmienne){
			for(int i=0; i<4; i++){
				tablicaZmiennych[x]=Integer.parseInt(zmienna.split(" ")[i]);
				x++;
			}

		}
		
		System.out.println("Podaj wynik z SAT Solvera: ");
		wynikSat =  "-1 -34 -33 -32 31 -11 -21 -30 -29 -28 -27 -26 -25 -24 -23 22 -12 -20 -19 -18 -17 -16 -15 -14 13 -10 -9 -8 -7 -6 -5 -4 -3 -2 0";
		
		String[] wynikSplit = wynikSat.split(" ");
		int [] wynikowaTablica = new int[wynikSplit.length];

		for(int i=0; i!= wynikSplit.length; i++){
			wynikowaTablica[i] = Integer.parseInt(wynikSplit[i]);
			//System.out.println(wynikSplit[i]);	
		}
		
		for(int i=0; i!=wynikSplit.length; i++){
			for(int j=0; j<iloscPanstw*4; j++){
				if(tablicaZmiennych[j] == wynikowaTablica[i])
					System.out.println(tablicaZmiennych[j] + " ");
			}
		}
		
		
		
	}

}
