interface MyCalculatorInterface{
    int calculate(String expression);
}

public class MyCalculator implements MyCalculatorInterface{

    private MyStack<String> stk;
    private MyStack<String> result;
    int solve(String operand1,String operand2,String operator){ 
        if (operator.equals("*"))
            return (Integer.parseInt(operand1)*Integer.parseInt(operand2));
        else if(operator.equals("+"))
            return (Integer.parseInt(operand1)+Integer.parseInt(operand2));
        else if(operator.equals("-"))
            return (Integer.parseInt(operand1)-Integer.parseInt(operand2));
        else if(operator.equals("/"))
            return (Integer.parseInt(operand1)/Integer.parseInt(operand2));
        else
            return -1;
    }
    int precedence(String ch){
        if (ch.equals("-"))
            return 1;
        else if (ch.equals("+"))
            return 1;
        else if (ch.equals("*"))
            return 2;
        else if (ch.equals("/"))
            return 2;    
        else
            return -1;
    }
    MyCalculator(){
        stk=new MyStack<String>();
        result=new MyStack<String>(); 
    }
    static boolean isInt(String s){
        try{ int y = Integer.parseInt(s); return true;}
        catch(NumberFormatException er){ return false; }
    }
    String[] postfix(String expression){
        String dump;
        int i=0,j;
        while (i<expression.length()){
            //System.out.println(expression.charAt(i));
            if(expression.charAt(i)==' ')   //for space will pass it
                {   i++;}
            else if(Character.isDigit(expression.charAt(i)))    // checking for digit
                {   j=i;
                    while (j<expression.length()&&Character.isDigit(expression.charAt(j))){
                        j++;
                        //System.out.println("this is i"+i+" "+j);
                    }
                    result.push(expression.substring(i,j));
                    //System.out.println(expression.substring(i,j)+" j is "+j);
                    i=j;

                }
            else if(expression.charAt(i)=='('){     // check for left bracket
                {stk.push(Character.toString(expression.charAt(i)));
                    i++;}
            }
            else if(expression.charAt(i)==')'){
                try{
                    while(!stk.isEmpty() && !stk.top().equals("(")){
                        result.push(stk.pop());}
                    dump=stk.pop();
                    i++;
                }
                catch(Exception e){
                    System.out.println("out of bound");
                }
            }
            else{ //for operator
            try{
                if (!stk.isEmpty()){
                    //System.out.println("stack ye hai "+stk.top()+" naya character "+Character.toString(expression.charAt(i)));
                    //System.out.println("r is "+r+" * precedence"+precedence(Character.toString(expression.charAt(i))));
                    if (precedence(Character.toString(expression.charAt(i)))<=precedence(stk.top())){
                        result.push(stk.pop());         //pop using string
                        //System.out.println("after poping r "+r);
                        stk.push(Character.toString(expression.charAt(i)));  //push in string
                    }
                    else{stk.push(Character.toString(expression.charAt(i)));}
                }
                else{stk.push(Character.toString(expression.charAt(i)));}
                i++;
            }
            catch(Exception e){
                System.out.println("out of pop");
            }
            }
            //System.out.println("this is r "+r);
            
        }
        while(!stk.isEmpty()){
            try{
            result.push(stk.pop());
            }
            catch(Exception e){
                System.out.println("out of loop");
            }
        }
        //System.out.println(" ");
        String[] r=new String[result.get_counter()+1];
        for (int k=r.length-1;k>=0;k--){
            try{
            r[k]=result.pop();
            }
            catch(Exception e){
                System.out.println("out of loop");
            }
        }
        return r;
    }
    public int calculate(String expression){
        String[] post_expression,solved_expression;
        System.out.println(expression+" "+expression.length());
        post_expression=postfix(expression);
        for (int p=0;p<post_expression.length;p++)
            System.out.print(post_expression[p]);
        System.out.println(""+"length of post"+post_expression.length);
        
        String buffer="";
        while(post_expression.length!=1){
            solved_expression=new String[post_expression.length-2];
            for(int i=0;i<post_expression.length;i++){
                if(isInt(post_expression[i])){
                    continue;
                }
                else{
                    //System.out.println("kispe hai "+i+" "+post_expression[i]+" length "+post_expression.length);
                    buffer+=solve(post_expression[i-2],post_expression[i-1],post_expression[i]);
                    System.out.println("this is buffer"+buffer);
                    for (int shift=0;shift<post_expression.length;shift++){
                        if (shift<i-2)
                            solved_expression[shift]=post_expression[shift];
                        else if (shift==i-2)
                            solved_expression[shift]=buffer;
                        else if(shift>i)
                            solved_expression[shift-2]=post_expression[shift];
                    }
                    buffer="";
                    post_expression=solved_expression;
                    break;
                }
            }
        }
        
        int rr=Integer.parseInt(post_expression[0]);
        
        return rr;
    }
    
    public static void main(String[] args) {
        MyCalculator obj=new MyCalculator();
        int a=obj.calculate("0-((2*3-14)*7+4)+3");
        System.out.println(a);
    }
    
    
}