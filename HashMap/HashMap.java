package col106.assignment4.HashMap;
import java.util.Vector;

public class HashMap<V> implements HashMapInterface<V> {
	private int N;
	private dict[] arr;
	class dict<V>{
		String key; V value;
		dict(){
			this.key=null;
			this.value=null;
		}
		
		dict(String key1,V val){
			this.key=key1;
			this.value=val;
		}
	}
	public HashMap(int size) {
		this.arr=new dict[size];
		this.N=size;
		// System.out.println(arr.length);
	}
	long hash(String unique_key){
		int a=41;
		long s=0;
		if(unique_key.length()>1){
			for(int i=unique_key.length()-1;i>0;i--){
				s+=(int)unique_key.charAt(i);
				s=(s*a)%this.N;
			}
		}
		if(unique_key.length()>0){
			s+=(int)unique_key.charAt(0);
			s=s%this.N;
		}
		return s;
	}

	public V put(String key, V val){
		int index=0;
		long tmp=this.hash(key);
		for (int i=0;i<N;i++){
			index=(int)(tmp+i)%this.N;
			// System.out.println("index "+index+" "+key);
			if (arr[index]==null){
				arr[index]=new dict(key,val);
				return null;
			}	
			else if(arr[index].key.equals(key)){
				V temp=(V) arr[index].value;
				arr[index].value=val;
				return temp;
			}
		}
		// System.out.println("full");
		return null;
	}

	public V get(String key){
		int index=0;long tmp=this.hash(key);
		for(int i=0;i<N;i++){
			index=(int)(tmp+i)%this.N;
			if(arr[index]==null)
				return null;
			else if(arr[index].key.equals(key))
				return (V)arr[index].value;
		}
		return null;
	}

	public boolean remove(String key){
		int index=0,last_index=-1;
		long tmp=this.hash(key);
		boolean ans=false;
		for(int i=0;i<N;i++){
			index=(int)(tmp+i)%this.N;
			if(arr[index]==null)
				break;
			else if(arr[index].key.equals(key)){
				arr[index]=null;
				last_index=index;
				ans=true;
			}
			else if(last_index>=((int)this.hash(arr[index].key)%this.N)){
				//swap
				dict replace=arr[index];
				// System.out.println("key "+replace.key);
				arr[last_index]=replace;
				arr[index]=null;
				// System.out.println("last key "+arr[last_index].key);
				// System.out.println("current key "+arr[index]);
				last_index=index;
			}

		}

		return ans;
	}

	public boolean contains(String key){
		int index=0;
		long tmp=this.hash(key);
	
		for(int i=0;i<N;i++){
			index=(int)(tmp+i)%this.N;
			if(arr[index]==null)
				return false;
			else if(arr[index].key.equals(key))
				return true;
		}
		return false;
	}

	public Vector<String> getKeysInOrder(){
		Vector<String> v=new Vector<>();
		for(int i=0;i<N;i++){
			if(arr[i]!=null)
				v.add(arr[i].key);
		}
		return v;
	}
	// public static void main(String[] args){
	// 	int s=10;
	// 	System.out.println("working");
	// 	HashMap<Integer> hobj=new HashMap<Integer>(s);
	// 	hobj.put("v", 1);
	// 	int a=hobj.get("v");
	// 	System.out.println(a);
	// 	hobj.put("b",134);
	// 	hobj.put("db",133);
	// 	System.out.println(hobj.get("b1"));
	// 	Vector vp=hobj.getKeysInOrder();
	// 	System.out.println("The Vector is: " + vp); 
	// 	hobj.remove("db");
	// 	Vector vp1=hobj.getKeysInOrder();
	// 	System.out.println("The Vector is: " + vp1); 
	// }
}
