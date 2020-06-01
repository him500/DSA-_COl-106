import java.util.Arrays;
interface StackSortInterface{
    String[] sort(int[] nums);
    String[][] kSort(int[] nums);
}

public class StackSort{
    private String[] str;
    private String[] alternate_output;
    public int[] val;
    StackSort(){
        val=new int[0];
        str=new String[1];
        str[0]="kuch nahi";
        alternate_output=new String[1];
        alternate_output[0]="NOTPOSSIBLE";
    }
    public String[] sort(int[] nums){
        MyStack<Integer> q =new MyStack<Integer>();
        String output="";
        int N=nums.length;
        for(int t=0;t<N;t++){
            System.out.print(nums[t]+" ");
        }
        System.out.print("\n");

        q.push(nums[0]); 
        output+="PUSH"+" ";
        //System.out.println(q.top());
        int pos=1,string_pos=0;
        val=new int[N];
        try{
        while(pos<N){
            
            if(nums[pos]>nums[pos-1]){
                for(int i=q.get_counter();i>=0;i--){
                    if(nums[pos]>q.top()){
                        val[string_pos]=q.pop();
                        output+="POP"+" ";
                        string_pos++;
                    }
                    if (q.isEmpty()){
                        q.push(nums[pos]);
                        output+="PUSH"+" ";
                        if (pos+1<N){
                            if (val[string_pos-1]>nums[pos+1])
                                return alternate_output;
                        }
                        
                        break;
                    }
                    else if(nums[pos]<=q.top()){
                        q.push(nums[pos]);
                        output+="PUSH"+" ";
                        if (pos+1<N){
                            if (val[string_pos-1]>nums[pos+1])
                                return alternate_output;
                        }
                        break;
                    }
                   
                }
            }
            else{
                q.push(nums[pos]);
                output+="PUSH"+" ";
                if (string_pos>=1){
                    if (val[string_pos-1]>nums[pos])
                        return alternate_output;
                }
            }

            pos++;
            System.out.println(output+" "+q.top());
        }
        
    }
    catch(Exception e){
        System.out.println("out of loop");
    }
        //System.out.println("q counter "+q.get_counter()+" output counter "+output.get_counter());
        for(int i=q.get_counter();i>=0;i--){
            try{
            val[string_pos]=q.pop();
            output+="POP"+" ";
            string_pos++;
            }
            catch(Exception e){
                System.out.println("out of loop");
            }
        }

        for(int j=0;j<N;j++)
           System.out.print(val[j]+" ");
        
        str=output.split(" ");

        return str;


    }

    int[] sorted_num(int[] nums){
        MyStack<Integer> q =new MyStack<Integer>();
        String output="";
        int N=nums.length;
        // for(int t=0;t<N;t++){
        //     System.out.print(nums[t]+" ");
        // }
        System.out.print("\n");
        q.push(nums[0]); 
        output+="PUSH"+" ";
        int pos=1,string_pos=0;
        int[] sorted_val=new int[N];
        try{
        while(pos<N){
            if(nums[pos]>nums[pos-1]){
                for(int i=q.get_counter();i>=0;i--){
                    if(nums[pos]>q.top()){
                        sorted_val[string_pos]=q.pop();
                        output+="POP"+" ";
                        string_pos++;
                    }
                    if (q.isEmpty()){
                        q.push(nums[pos]);
                        output+="PUSH"+" ";                        
                        break;
                    }
                    else if(nums[pos]<=q.top()){
                        q.push(nums[pos]);
                        output+="PUSH"+" ";
                        break;
                    }
                }
            }
            else{
                q.push(nums[pos]);
                output+="PUSH"+" ";
            }
            pos++;
            //System.out.println(output+" "+q.top());
        }
    }
    catch(Exception e){
        System.out.println("out of loop");
    }
        for(int i=q.get_counter();i>=0;i--){
            try{
            sorted_val[string_pos]=q.pop();
            output+="POP"+" ";
            string_pos++;
            }
            catch(Exception e){
                System.out.println("out of loop");
            }
        }
        // for(int j=0;j<N;j++)
        //     System.out.print(val[j]+" ");
        //str=output.split(" ");
        return sorted_val;
    }

    String[] sorted(int[] nums){
        MyStack<Integer> q =new MyStack<Integer>();
        String output="";
        int N=nums.length;
        //for(int t=0;t<N;t++){
        //    System.out.print(nums[t]+" ");
        //}
        System.out.print("\n");
        q.push(nums[0]); 
        output+="PUSH"+" ";
        int pos=1,string_pos=0;
        int[] sorted_val=new int[N];
        try{
        while(pos<N){
            if(nums[pos]>nums[pos-1]){
                for(int i=q.get_counter();i>=0;i--){
                    if(nums[pos]>q.top()){
                        sorted_val[string_pos]=q.pop();
                        output+="POP"+" ";
                        string_pos++;
                    }
                    if (q.isEmpty()){
                        q.push(nums[pos]);
                        output+="PUSH"+" ";                        
                        break;
                    }
                    else if(nums[pos]<=q.top()){
                        q.push(nums[pos]);
                        output+="PUSH"+" ";
                        break;
                    }
                }
            }
            else{
                q.push(nums[pos]);
                output+="PUSH"+" ";
            }
            pos++;
            //System.out.println(output+" "+q.top());
        }
    }
    catch(Exception e){
        System.out.println("out of loop");
    }
        for(int i=q.get_counter();i>=0;i--){
            try{
            sorted_val[string_pos]=q.pop();
            output+="POP"+" ";
            string_pos++;
            }
            catch(Exception e){
                System.out.println("out of loop");
            }
        }
        // for(int j=0;j<N;j++)
        //     System.out.print(val[j]+" ");
        str=output.split(" ");
        return str;
    }
    boolean check_sort(int[] arr){
        if (arr.length<=1)
            return true;
        for(int i=1;i<arr.length;i++){
            if (arr[i-1]>arr[i])
                return false;
        }
        return true;
    }
    public String[][] kSort(int[] nums){
        int r=1,c=2*nums.length;
        String[][] b;
        StackSort obj1=new StackSort();
        String[] sequence=obj1.sorted(nums);
        int[] sorted_arr=obj1.sorted_num(nums);
        
        //System.out.println(sorted_arr.length);
        for(int t=0;t<sorted_arr.length;t++)
            System.out.print("sort"+sorted_arr[t]+" ");

        while(!check_sort(sorted_arr)){
            sorted_arr=obj1.sorted_num(sorted_arr);
            for(int t=0;t<sorted_arr.length;t++)
                System.out.print("sort"+sorted_arr[t]+" ");
            r++;
        }
        b=new String[r][c];
        sequence=obj1.sorted(nums);
        sorted_arr=obj1.sorted_num(nums);

        for(int jc=0;jc<c;jc++)
            b[0][jc]=sequence[jc];
        
        for(int i=1;i<r;i++){
            sequence=obj1.sorted(sorted_arr);
            sorted_arr=obj1.sorted_num(sorted_arr);
            for(int j=0;j<c;j++){
                b[i][j]=sequence[j];
            }
        }

        return b;
    }
    
    public static void main(String[] args){
        StackSort obj=new StackSort();
        int[] a=new int[]{7,6,13,4,1,10};
        String[][] o=obj.kSort(a);
        for(int i=0;i<o.length;i++){
            for(int j=0;j<o[0].length;j++){
                System.out.print(o[i][j]+" ");
            } 
            System.out.print("\n");
        }
        //System.out.println(Arrays.toString(o));

    }
    

}