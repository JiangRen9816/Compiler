import java.util.ArrayList;
import java.util.List;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> strs=new ArrayList<String>();

		//^Îª¿Õ×Ö·û
		strs.add("E->TA");
		strs.add("A->+TA");
		strs.add("A->^");
		strs.add("T->FB");
		strs.add("B->*FB");
		strs.add("B->^");
		strs.add("F->i");
		strs.add("F->(E)");
		System.out.println(strs);
		Oper oper=new Oper(strs);
		oper.run();
	}

}
