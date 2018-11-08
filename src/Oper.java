import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator ;

public class Oper 
{
	//��ʼ��
		private char startChar;	
		//δ������������ķ�
		private List<String> expressionString;	
		//����1��������ķ�
		private Map<Character,List<String>> expressionMap=new HashMap<Character,List<String>>();
		//���ս���б�
		private List<Character> unterminators=new ArrayList<Character>();
		//�ս���б�
		private List<Character> terminators=new ArrayList<Character>();
		//First��
		private Map<Character,List<Character>> first=new HashMap<Character,List<Character>>();	
		//Follow��
		private Map<Character,List<Character>> follow=new HashMap<Character,List<Character>>();
		private String[][] PredictionForm;
		
		public void run(){
			expressionStringToMap();
			getFirst();
			getFollow();
			
			getTerminators();
			getPredictionForm();
		}
		
		//���������ķ��Ĺ��캯��
		
		public Oper(List<String> expressionString){
			this.expressionString=expressionString;
		}

		//Ĭ�Ϲ��캯��
		
		public Oper(){
		}
		
		//����δ������������ķ�

		public void setExpression(List<String> expressionString){

			this.expressionString=expressionString;

		}
		
		//�õ��ս���б�,�д�ʵ��!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		private void getTerminators()
		{
			  terminators.add((Character)'*');
			  terminators.add((Character)'i');
			  terminators.add((Character)'(');
			  terminators.add((Character)')');
			  terminators.add((Character)'+');
			  terminators.add((Character)'#');
		}
		
		//�õ����ս���ĸ���
		
		private int getUnterminatorAmount()
		{
			return unterminators.size();
		}
		
		//�õ��ս���ĸ���
		
		private int getTerminatorAmount()
		{
			return terminators.size();
		}
		
		//�õ�Ԥ���
		
		private void getPredictionForm()
		{
			PredictionForm=new String[100][100];
			for(int i=0;i<getUnterminatorAmount();i++)
			{
				for(int j=0;j<getTerminatorAmount();j++)
				{
					PredictionForm[i][j]="";
				}
			}
			Character tempC1,tempC2,tempC3;
			String tempS1="";
			String tempS2="";
			char tempc1,tempc2,tempc3 = 0;
			int x=0,y;
			int flag=0;
			Iterator iter1 = expressionMap.entrySet().iterator();//iter1 ���ս����
			while(iter1.hasNext())
			{
				Map.Entry element1=(Map.Entry)iter1.next();
				tempC1 =(Character) element1.getKey(); //��ǰ���ǵķ��ս��
				
				Collection<String> V1=(Collection) element1.getValue();//��ǰ���ǵ��Ƶ���
				Iterator<String> iter2=V1.iterator(); //iter2 �Ƶ����ʽ��
				
				while(iter2.hasNext())
				{
					tempS1="";
					tempS1=tempS1+iter2.next();
					flag=0;
					if(tempS1.charAt(0)!='^')
					{
						for(int i=0;i<tempS1.length();i++)
						{
							tempc1=tempC1;
							
							tempS2=tempc1+"->"+tempS1;
							
							if(terminators.indexOf(tempS1.charAt(i))==-1)
							{
								tempc2=tempS1.charAt(i);
								tempC2=tempc2;
			

								
								Iterator iter3 = first.entrySet().iterator();
								while(iter3.hasNext())
								{
									Map.Entry element2 = (Map.Entry)iter3.next();
									Character tempC4=(Character) element2.getKey();
									char tempc4=tempC4;
								
									
									if(tempc2==tempc4)
									{
										
										Collection<Character> V2=(Collection) element2.getValue();
										Iterator<Character> iter4=V2.iterator();
										while(iter4.hasNext())
										{
											tempC3=iter4.next();
											tempc3=tempC3;
											y=terminators.indexOf(tempC3);
											PredictionForm[x][y]="";
											PredictionForm[x][y]=PredictionForm[x][y]+tempS2;
										}
										if(tempc3!='^')
										{
											flag=1;
										}
									}
									if(flag==1)
										break;
								}
							}
							else
							{
								tempc1=tempC1;
								tempS2=tempc1+"->"+tempS1;
								y=terminators.indexOf(tempS1.charAt(i));
								PredictionForm[x][y]="";
								PredictionForm[x][y]=PredictionForm[x][y]+tempS2;
								break;
							}
							if(flag==1)
								break;
						}
					}
				}
				x++;
			}
			
			char Nullc='^';
			Character NullC=Nullc;

			Iterator iter5=first.entrySet().iterator();
			Iterator iter6=follow.entrySet().iterator();
			
			x=0;
			
			while(iter5.hasNext())
			{
				tempS1="";
				Map.Entry element5=(Map.Entry)iter5.next();
				Map.Entry element6=(Map.Entry)iter6.next();
				tempC3=(Character) element5.getKey();
				tempc3=tempC3;
				Collection<Character> V5=(Collection) element5.getValue();
				Collection<Character> V6=(Collection) element6.getValue();
				if(V5.contains(NullC))
				{
					tempS1=tempS1+tempc3+"->��";
					Iterator<Character> iter7=V6.iterator();
					while(iter7.hasNext())
					{
						tempC2=iter7.next();
						y=terminators.indexOf(tempC2);
						PredictionForm[x][y]="";
						PredictionForm[x][y]=PredictionForm[x][y]+tempS1;
					}
				}
				x++;
			}
			for(int i=0;i<getUnterminatorAmount();i++)
				for(int j=0;j<getTerminatorAmount();j++)
			{
				System.out.println(i+" "+j+PredictionForm[i][j]);
			}
		
		
		}
		
		//���ַ������ʽд��hashmap
		
		private void expressionStringToMap(){

			for(int i=0;i<this.expressionString.size();i++){

				String expression=expressionString.get(i);

				String[] strs=expression.split("->");

				char key=strs[0].charAt(0);

				if(i==0){

					startChar=key;

				}

				String value=strs[1];

				//System.out.println(strs[0]+" "+strs[1]);

				if(expressionMap.containsKey(key)){

					expressionMap.get(key).add(value);

				}else{

					List<String> values=new ArrayList<String>();

					values.add(value);

					expressionMap.put(key, values);

					unterminators.add(key);

				}

			}

			System.out.println("��ʼ��:"+startChar);

			System.out.println("�ķ�:"+expressionMap);

			System.out.println("���ս��:"+unterminators);

		}

		//�õ��׷���  �б�

		private void getFirst(){

			for(int i=0;i<unterminators.size();i++){

				List<Character> values=new ArrayList<Character>();

				first.put(unterminators.get(i),values);

			}

			boolean flag=false;

			while(!flag){

				flag=true;

				for(int i=0;i<unterminators.size();i++){

					//System.out.println(unterminators.get(i));

					if(!first(unterminators.get(i))){

						flag=false;

					}

				}

			}

			

			System.out.println("first:"+first);

		}

	

		private boolean first(char key){

			boolean flag=true;

			List<String> strings=expressionMap.get(key);

			List<Character> values=first.get(key);

			for(int i=0;i<strings.size();i++){

				char firstChar=strings.get(i).charAt(0);

				if(!unterminators.contains(firstChar)){

					if(!values.contains(firstChar)){

						values.add(firstChar);

						flag=false;

					}

				}else{

					List<Character> chars=first.get(firstChar);

					for(int j=0;j<chars.size();j++){

						if(chars.get(j)!='^'&&!values.contains(chars.get(j))){

							values.add(chars.get(j));

							flag=false;

						}

					}

				}

			}

			//System.out.println(first);

			

			return flag;

		}

		

		private boolean follow(char key){

			boolean flag=true;

			List<String> strings=expressionMap.get(key);

			for(int i=0;i<strings.size();i++){

				String str=strings.get(i);

				//System.out.println(str+" ");

				for(int j=0;j<str.length();j++){

					char dealChar=str.charAt(j);

					if(unterminators.contains(dealChar)){

						List<Character> dealValues=follow.get(dealChar);

						if(j==str.length()-1){

							List<Character> chars=follow.get(key);

							for(int m=0;m<chars.size();m++){

								if(!dealValues.contains(chars.get(m))){

									dealValues.add(chars.get(m));

									flag=false;

								}

							}

						}else{

							//��һλΪ����ֹ��

							if(unterminators.contains(str.charAt(j+1))){

								int index=j+1;

								boolean f=false;

								while(!f){

									f=true;

									if(unterminators.contains(str.charAt(index))){

										List<Character> chars=first.get(str.charAt(index));

										for(int m=0;m<chars.size();m++){

											

											if(chars.get(m)!='^'&&!dealValues.contains(chars.get(m))){

												dealValues.add(chars.get(m));

												flag=false;

											}

											if(chars.get(m)=='^'){

												index++;

												f=false;

											}

										}

									}else{

										if(!dealValues.contains(str.charAt(index))){

											dealValues.add(str.charAt(index));

											flag=false;

										}

									}

									if(index==str.length()){

										List<Character> chars=follow.get(key);

										for(int m=0;m<chars.size();m++){

											if(!dealValues.contains(chars.get(m))){

												dealValues.add(chars.get(m));

												flag=false;

											}

										}

										break;

									}

								}

								

							}else{

								if(!dealValues.contains(str.charAt(j+1))){

									dealValues.add(str.charAt(j+1));

									flag=false;

								}

							}

						}

					}

				}

			}

			//System.out.println(follow);

			return flag;

		}

	//�õ�Follow�� �б�	

		private void getFollow(){

			for(int i=0;i<unterminators.size();i++){

				List<Character> values=new ArrayList<Character>();

				if(unterminators.get(i)==startChar){

					values.add('#');

				}

				follow.put(unterminators.get(i),values);

			}

			boolean flag=false;

			while(!flag){

				flag=true;

				for(int i=0;i<unterminators.size();i++){

					//System.out.println(unterminators.get(i));

					if(!follow(unterminators.get(i))){

						flag=false;

					}

				}

			}

			

			System.out.println("follow:"+follow);

		}
}
