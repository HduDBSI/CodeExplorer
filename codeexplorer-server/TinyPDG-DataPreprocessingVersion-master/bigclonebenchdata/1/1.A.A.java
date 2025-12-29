public class A{
    private long GCD (long a, long b){
        while (b!=0){
            long t=a%b;
            a=b;
            b=t;
        }
        return a;
    }
}