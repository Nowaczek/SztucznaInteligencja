import java.util.ArrayList;


public class Sat {
int ilosc_panstw;
static int kolor1,kolor2,kolor3,kolor4;

		/* Pierwsyz warunek: kazde panstwo posiada jakis kolor*/
	static ArrayList<String> firstCondition(int ilosc_krajow){
		ArrayList<String> firstCond = new ArrayList<String>();
		
		for(int i=1; i<=ilosc_krajow; i++ ){
			kolor1=4*i-3;
			kolor2=4*i-2;
			kolor3=4*i-1;
			kolor4=4*i;
			firstCond.add(kolor1 + " " + kolor2 + " " + kolor3 + " " + kolor4 + " 0" );
		}
		return firstCond;
	}
	
		
		/*	Drugi warunek: kazde panstwo posiada jeden kolor */
	static ArrayList<String> secondCondition(int ilosc_krajow){
		ArrayList<String> secondCond = new ArrayList<String>();
		
		for(int i=1; i<=ilosc_krajow; i++){
			for(int j=2; j>=0; j--){
				kolor1=4*i-3;
				kolor2=4*i-j;
				secondCond.add("-" + kolor1 + " -" + kolor2 + " 0");
			}
			
			for(int j=1; j>=0; j--){
				kolor1=4*i-2;
				kolor2=4*i-j;
				secondCond.add("-" + kolor1 + " -" + kolor2 + " 0");
			}
			
			kolor1=4*i-1;
			kolor2=4*i;
			secondCond.add("-" + kolor1 + " -" + kolor2 + " 0");

		}
		return secondCond;
				
	}
	
		/* Trzeci warunek: panstwo ma inny kolor niz sasiad */
	static ArrayList<String> thirdCondition(int ilosc_krajow, ArrayList<String> sasiadujacePanstwa){
		ArrayList<String> thirdCondition = new ArrayList<String>();
		
		for(String sasiedzi : sasiadujacePanstwa){
			int panstwo1 = Integer.parseInt(sasiedzi.split("#")[0]);
			int panstwo2 = Integer.parseInt(sasiedzi.split("#")[1]);
			for(int i=1; i<=4; i++){
				for(int j=3; j>=0; j--){
					kolor1=4*panstwo1-j;
					kolor2=4*panstwo2-j;
					thirdCondition.add("-" + kolor1 + " -" + kolor2 + " 0");
				}
			}
		}
		
		return thirdCondition;
	}
}
