package pl.pw.elka.ddudzin1.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import pl.pw.elka.ddudzin1.algorithm.test.Pair;

public class DistanceMatrix {

	private HashMap<String, DistanceMatrixRow> distanceMatrix;

	public DistanceMatrix() {
		distanceMatrix = new HashMap<String, DistanceMatrixRow>();

	}

	public void addRow(DistanceMatrixRow row) {
		distanceMatrix.put(row.getClusterName(), row);
	}

	public DistanceMatrixRow getRow(String clusterName) {
		return distanceMatrix.get(clusterName);
	}

	public Set<String> getClusterNames() {
		return distanceMatrix.keySet();
	}

	public void join(Pair newcluster) {
		DistanceMatrixRow newdmr = distanceMatrix.get(newcluster.getLeft())
				.join(newcluster, distanceMatrix.get(newcluster.getRight()));
		distanceMatrix.remove(newcluster.getLeft());
		distanceMatrix.remove(newcluster.getRight());

		distanceMatrix.put(newdmr.getClusterName(), newdmr);
	}

	public void recalculateMatrix(Pair newcluster) {

		Set<String> clusterNames = distanceMatrix.keySet();
		for (String clname : clusterNames) {
			if (!newcluster.getNewName().equals(clname))
				distanceMatrix.get(clname).recalculateRow(newcluster,
						distanceMatrix.get(newcluster.getNewName()));
		}

	}

	public void recalculateMatrix(ArrayList<Pair> newclusters) {
		for (Pair newcluster : newclusters) {
			recalculateMatrix(newcluster);
		}

	}

}
