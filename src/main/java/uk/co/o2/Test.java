package uk.co.o2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Test {
 static void main(String[] args) throws InterruptedException {
//		findMissingNumber();
		
		/*ArrayList<String> list=new ArrayList<>();
		for(int i=0;i<(Integer.MAX_VALUE-100000);i++)
			list.add(new String( String.valueOf( i)));
		*//*
MyKey key=new MyKey(3);
MyValue value=new MyValue(3);


HashMap<MyKey, MyValue> hashMap=new HashMap<>();
hashMap.put(key, value);
key=null;
System.gc();
System.out.println(hashMap.get(new MyKey(3)));

key=new MyKey(3);
WeakHashMap<MyKey, MyValue> hashMap2=new WeakHashMap<>();
hashMap2.put(key, value);
key=null;
TimeUnit.SECONDS.sleep(5);
System.gc();
System.out.println(hashMap2.get(new MyKey(3)));

*/
int a=100;
int b=3;
a=a^b;
b=a^b;
a=a^b;
		System.out.println(a);
		System.out.println(b);
	
List list = new ArrayList(Arrays.asList("a","b","c","d","e"));
Iterator i = list.iterator();
while (i.hasNext()) {
	//i.remove();		//IllegalStateException
System.out.println(i.next());
i.remove();			// OK

//list.remove("d"); // Always throw concurrent modification exception
//list.add("f"); 	// Always throw concurrent modification exception
}
	}

	private static void findMissingNumber() {
		int[] ARRAY=new int[5];
		ARRAY[0]=1;
		ARRAY[1]=3;
		ARRAY[2]=2;
		ARRAY[3]=0;
		ARRAY[4]=5;
		int XOR=0;
		for(int i=0;i<5;i++)
		{
		 if(ARRAY[i] != 0){
		     XOR ^= ARRAY[i];
		 }
		 System.out.print(XOR+" ^ "+(i + 1)+" = ");
		 XOR ^= (i + 1);
		 System.out.println(XOR);
		}
		System.out.println("\n------\n"+XOR);
	}
}
class MyValue{
	int v;

	public MyValue(int v) {
		super();
		this.v = v;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + v;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyValue other = (MyValue) obj;
		if (v != other.v)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MyValue [v=" + v + "]";
	}
	
	
	
}
class MyKey{
	int d;

	public MyKey(int d) {
		super();
		this.d = d;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + d;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyKey other = (MyKey) obj;
		if (d != other.d)
			return false;
		return true;
	}
	
}