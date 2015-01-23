package pl.pw.elka.ddudzin1.algorithm.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.pw.elka.ddudzin1.algorithm.Cluster;
import pl.pw.elka.ddudzin1.algorithm.DistanceMatrix;
import pl.pw.elka.ddudzin1.algorithm.HierarchicalClusterAlgoritm;
import pl.pw.elka.ddudzin1.algorithm.MatrixReader;

public class ClusterTest {

	@Test
	public void getNewickTest() {
		System.out.println("getNewickTest ");
		String runType = "p";
		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm(
				runType);
		MatrixReader mxr = new MatrixReader();
		String joinType = "single";
		DistanceMatrix dm = mxr.read("testdata/file2", joinType);

		alg.setMatrix(dm);
		alg.run();

		Cluster root = alg.getRoot();
		root.print("");

		String tempalte = "(((0:0.0,1:0.0):1.0,(2:0.0,3:0.0):4.0):11.0)";
		String newick = root.getNewickFromRoot();
		System.out.println(newick);
		assertEquals(tempalte, newick);
	}

	@Test
	public void getCophenetic() {
		System.out.println("getCophenetic ");
		String runType = "p";
		HierarchicalClusterAlgoritm alg = new HierarchicalClusterAlgoritm(
				runType);
		MatrixReader mxr = new MatrixReader();
		String joinType = "single";
		DistanceMatrix dm = mxr.read("testdata/file2", joinType);

		alg.setMatrix(dm);
		alg.run();

		Cluster root = alg.getRoot();
		root.print("");

		assertEquals(new Double(1.0), root.getCophenetic("0", "1"));
		assertEquals(new Double(11.0), root.getCophenetic("0", "2"));
		assertEquals(new Double(4.0), root.getCophenetic("2", "3"));
	}

}
