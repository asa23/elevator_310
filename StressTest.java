
public class StressTest {
	
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			System.out.printf("Starting test #%d\t", i);
			Main tester = new Main();
			tester.main(args);
			System.out.printf("Ending test #%d\n", i);
		}
	}

}
