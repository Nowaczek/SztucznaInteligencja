import java.util.ArrayList;


public class Sat {
int ilosc_panstw;

		/* Pierwsyz warunek: kazde panstwo posiada jakis kolor*/
	static ArrayList<String> firstCondition(int ilosc_krajow){
		ArrayList firstCond = new ArrayList<String>();
		
		for(int i=1; i<=ilosc_krajow; i++ ){
			firstCond.add(i+"1 " + i+"2 " + i+"3 " + i+"4 0");
		}
		return firstCond;
		}
	
		/*	Drugi warunek: kazde panstwo posiada jeden kolor */
	static ArrayList<String> secondCondition(int ilosc_krajow){
		ArrayList secondCond = new ArrayList<String>();
		
		for(int i=1; i<=ilosc_krajow; i++){
			secondCond.add("-"+i+"1 " + "-"+i+"2 0");
			secondCond.add("-"+i+"1 " + "-"+i+"3 0");
			secondCond.add("-"+i+"1 " + "-"+i+"4 0");
			secondCond.add("-"+i+"2 " + "-"+i+"3 0");
			secondCond.add("-"+i+"2 " + "-"+i+"4 0");
			secondCond.add("-"+i+"3 " + "-"+i+"4 0");
		}
		return secondCond;
				
	}
	
		/* Trzeci warunek: panstwo ma inny kolor niz sasiad */
	static ArrayList<String> thirdCondition(int ilosc_krajow, ArrayList<String> sasiadujacePanstwa){
		ArrayList thirdCondition = new ArrayList<String>();
		
		for(String sasiedzi : sasiadujacePanstwa){
			String panstwo1 = sasiedzi.split("#") [0] ;
			String panstwo2 = sasiedzi.split("#") [1];
			for(int j=1; j<=4; j++){
						thirdCondition.add("-"+panstwo1+j + " -"+panstwo2+j+" 0");
			}
		}
		
		return thirdCondition;
	}
}
