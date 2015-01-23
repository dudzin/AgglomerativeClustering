package pl.pw.elka.ddudzin1.algorithm;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class HC {

	public static void main(String[] args) throws FileNotFoundException,
			UnsupportedEncodingException {
		String inputFile, outputFile, copheneticFile, connType, runType;
		int threadCnt;
		outputFile = "result.txt";
		copheneticFile = "cophenetic.txt";

		if (args.length < 4) {
			System.out.println("wrong number of input parameters");
			System.out
					.println("order should be 1. inputfile 2. connType 3. runType 4. threadCnt");
			return;
		}

		inputFile = args[0];
		connType = args[1];
		runType = args[2];
		threadCnt = Integer.parseInt(args[3]);
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);
		System.out.println(args[3]);

		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm(
				runType);
		MatrixReader mxr = new MatrixReader();
		DistanceMatrix dm = mxr.read(inputFile, connType);
		alg.setMatrix(dm);
		alg.run();

		System.out.println("time: " + alg.getExecutionTime() / 1000 / 1000
				+ " ms" + " iterations: " + alg.getIterationCnt());

		alg.getRoot().print("");
		alg.saveResult(outputFile);
		alg.saveCophenetic(copheneticFile);
	}

}
