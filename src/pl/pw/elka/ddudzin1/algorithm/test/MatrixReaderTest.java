package pl.pw.elka.ddudzin1.algorithm.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.pw.elka.ddudzin1.algorithm.DistanceMatrix;
import pl.pw.elka.ddudzin1.algorithm.MatrixReader;

public class MatrixReaderTest {

	@Test
	public void readDistanceMatrix() {

		MatrixReader mxr = new MatrixReader();
		DistanceMatrix dm = mxr.read("testdata/file1", "");

		assertEquals("0", dm.getRow("0").getClusterName());
		assertEquals("3", dm.getRow("3").getClusterName());

		assertEquals(new Double(0.0), dm.getRow("0").getDistace("0"));
		assertEquals(new Double(0.0), dm.getRow("2").getDistace("2"));
		assertEquals(new Double(1.0), dm.getRow("0").getDistace("1"));
		assertEquals(new Double(2.0), dm.getRow("0").getDistace("2"));

		assertEquals(new Double(23.0), dm.getRow("3").getDistace("2"));
		assertEquals(new Double(3.0), dm.getRow("3").getDistace("0"));
	}

	@Test
	public void joinSingleLinkTest() {

		String joinType = "single";
		MatrixReader mxr = new MatrixReader();
		DistanceMatrix dm = mxr.read("testdata/file1", joinType);
		Pair newcluster = new Pair("0", "1");

		dm.join(newcluster);

		assertEquals(new Double(2.0), dm.getRow("0,1").getDistace("2"));

		dm.recalculateMatrix(newcluster);
		assertEquals(new Double(2.0), dm.getRow("2").getDistace("0,1"));

		newcluster = new Pair("3", "0,1");

		dm.join(newcluster);
		assertEquals(new Double(2.0), dm.getRow("0,1,3").getDistace("2"));

		dm.recalculateMatrix(newcluster);
		assertEquals(new Double(2.0), dm.getRow("2").getDistace("0,1,3"));

	}

}
