public class disc01 {
	public static int fib(int n) {
		if (n == 0) {
			return 0;
		}else if(n == 1) {
			return 1;
		}else{
			return fib(n - 1) + fib(n - 2);}}

	public static int fib2(int n, int k, int f0, int f1){
		if ( n == k) {
			return f0;
		}else {
			return fib2(n, k + 1, f1, f0 + f1);
		}
	}

	public static void main(String[] args) {
		System.out.println(fib(3));
	}
}
