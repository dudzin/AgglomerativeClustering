package pl.pw.elka.ddudzin1.algorithm;

public class HC {

	public static void main(String[] args) {
		String inputFile, outputFile, copheneticFile, connType, runType, threadCnt;
		outputFile = "result.txt";
		copheneticFile = "cophenetic.txt";

		if (args.length < 4) {
			System.out.println("wrong number of input parameters");
		}

		inputFile = args[0];
		connType = args[1];
		runType = args[2];
		threadCnt = args[3];
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);
		System.out.println(args[3]);

	}

}
