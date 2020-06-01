//import java.util.EmptyStackException;
interface MyStackInterface<T>
{ 
    void push(T value);
    T pop() throws EmptyStackException;
    T top() throws EmptyStackException;
    boolean isEmpty();

} 
class MyStack <T> implements MyStackInterface<T>
{   
    private T[] copy_stack;
    private T[] stack;
    private int counter;
    private int size=5;
    
    MyStack(){
        int sz=0;
        stack =(T[]) new Object[sz];
        counter=sz-1;
        //System.out.println("constructor working");
    }
    void grow_array(int sz){
        this.copy_stack=(T[]) new Object[sz];
        
    }
    int get_counter(){
        return counter;
    }

    public void push(T value){
        //System.out.println(value);
        int i=0;
        counter++;
        if (stack.length<=counter){
            this.size=this.size*2;
            grow_array(counter+this.size);
        }
        
        for(i=0;i<counter;i++){
            copy_stack[i]=stack[i];
        }
        //System.out.println(counter);
        copy_stack[i]=value;
        stack=copy_stack;
        //System.out.println(stack.length+" "+copy_stack.length+" "+ counter);
        //for(int j=0;j<=counter;++j)
          //  System.out.println("stack "+stack[j]);
    }
    
    public T pop() throws EmptyStackException
    {   
    
        if (counter==-1){
            throw new EmptyStackException("EmptyStackException");
        }
        else{
            
            T elem=stack[counter];
            stack[counter]=null;
            this.counter--;
            //for(int j=0;j<stack.length;++j)
              //  System.out.println("stack "+stack[j]);
            //System.out.println(elem);    
            return elem;
        }    
    }
    
    public T top() throws EmptyStackException
    {   
    
        if (counter==-1){
            throw new EmptyStackException("EmptyStackException");
        }
        else{
            T last_elem=stack[counter];
            return last_elem;
        }
    
    }
    public boolean isEmpty(){
        return counter==-1? true:false;
    }
    
    public static void main(String[] args){
        System.out.println("My Stack working");
        MyStack<Integer> obj =new MyStack<Integer>(); 
        obj.push(4);
        obj.push(5);
        obj.push(25);
        int a=obj.pop();
        System.out.print("popped element "+a);
        

    }
    
}