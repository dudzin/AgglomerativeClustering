package pl.pw.elka.ddudzin1.algorithm.test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import pl.pw.elka.ddudzin1.algorithm.Cluster;
import pl.pw.elka.ddudzin1.algorithm.DistanceMatrix;
import pl.pw.elka.ddudzin1.algorithm.HierarchicalClusterAlgoritm;
import pl.pw.elka.ddudzin1.algorithm.MatrixReader;

public class HierarchicalClusterAlgoritmTest {

	@Test
	public void singlePhaseTest() {

		String runType = "p";
		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm(
				runType);
		MatrixReader mxr = new MatrixReader();
		String joinType = "single";
		DistanceMatrix dm = mxr.read("testdata/file1", joinType);

		alg.setMatrix(dm);
		alg.findCandidates();

		assertEquals(4, alg.getRawCandidates().size());
		assertEquals(1, alg.getRawCandidates().get("0").size());
		assertEquals(1, alg.getRawCandidates().get("1").size());
		assertEquals("1", alg.getRawCandidates().get("0").get(0));

		alg.verifyCandidates();

		assertEquals(1, alg.getCandidates().size());
		assertEquals(new Pair("0", "1").getNewName(), alg.getCandidates()
				.get(0).getNewName());

		alg.join();

		assertEquals(3, alg.getClusters().size());
		assertEquals(3, alg.getMatrix().getClusterNames().size());

	}

	@Test
	public void singleDoublePhaseTest() {
		String runType = "p";
		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm(
				runType);
		MatrixReader mxr = new MatrixReader();
		String joinType = "single";
		DistanceMatrix dm = mxr.read("testdata/file2", joinType);

		alg.setMatrix(dm);
		alg.findCandidates();

		assertEquals(4, alg.getRawCandidates().size());
		assertEquals(1, alg.getRawCandidates().get("0").size());
		assertEquals(1, alg.getRawCandidates().get("1").size());
		assertEquals("1", alg.getRawCandidates().get("0").get(0));

		alg.verifyCandidates();

		assertEquals(2, alg.getCandidates().size());
		assertEquals(new Pair("0", "1").getNewName(), alg.getCandidates()
				.get(0).getNewName());
		assertEquals(new Pair("2", "3").getNewName(), alg.getCandidates()
				.get(1).getNewName());

		alg.join();

		assertEquals(2, alg.getClusters().size());
		assertEquals(2, alg.getMatrix().getClusterNames().size());

	}

	@Test
	public void runTest1() {
		System.out.println("Parallel ");
		String runType = "p";
		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm(
				runType);
		MatrixReader mxr = new MatrixReader();
		String joinType = "single";
		DistanceMatrix dm = mxr.read("testdata/50elems", joinType);

		alg.setMatrix(dm);
		alg.run();

		System.out.println("p time: " + alg.getExecutionTime() / 1000 / 1000
				+ " ms" + " itercnt: " + alg.getIterationCnt());
		assertEquals(1, alg.getClusters().size());
		assertEquals(1, alg.getMatrix().getClusterNames().size());
		assertEquals(8, alg.getIterationCnt());

		Cluster root = alg.getRoot();
		root.print("");

	}

	@Test
	public void runTest1Linear() {
		System.out.println("Linear ");
		String runType = "l";
		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm(
				runType);
		MatrixReader mxr = new MatrixReader();
		String joinType = "single";
		DistanceMatrix dm = mxr.read("testdata/50elems", joinType);

		alg.setMatrix(dm);
		alg.run();
		System.out.println("l time: " + alg.getExecutionTime() / 1000 / 1000
				+ " ms" + " itercnt: " + alg.getIterationCnt());
		assertEquals(1, alg.getClusters().size());
		assertEquals(1, alg.getMatrix().getClusterNames().size());
		assertEquals(9, alg.getIterationCnt());
		// Cluster root = alg.getRoot();
		// root.print("");

	}

	@Test
	public void copheneticTest() throws FileNotFoundException,
			UnsupportedEncodingException {
		System.out.println("copheneticTest ");
		String runType = "p";
		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm(
				runType);
		MatrixReader mxr = new MatrixReader();
		String joinType = "single";
		DistanceMatrix dm = mxr.read("testdata/10elems", joinType);

		alg.setMatrix(dm);
		alg.run();

		alg.getCophenetic("testResult/result1.txt");

	}

}
