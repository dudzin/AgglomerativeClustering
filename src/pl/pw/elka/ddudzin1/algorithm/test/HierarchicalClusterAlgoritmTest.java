package pl.pw.elka.ddudzin1.algorithm.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.pw.elka.ddudzin1.algorithm.DistanceMatrix;
import pl.pw.elka.ddudzin1.algorithm.HierarchicalClusterAlgoritm;
import pl.pw.elka.ddudzin1.algorithm.MatrixReader;

public class HierarchicalClusterAlgoritmTest {

	@Test
	public void singlePhaseTest() {
		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm();
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

		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm();
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

		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm();
		MatrixReader mxr = new MatrixReader();
		String joinType = "single";
		DistanceMatrix dm = mxr.read("testdata/file2", joinType);

		alg.setMatrix(dm);
		alg.run();

		assertEquals(1, alg.getClusters().size());
		assertEquals(1, alg.getMatrix().getClusterNames().size());

	}

}
