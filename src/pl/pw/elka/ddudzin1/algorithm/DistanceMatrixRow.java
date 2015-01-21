package pl.pw.elka.ddudzin1.algorithm;

import java.util.HashMap;
import java.util.Set;

import pl.pw.elka.ddudzin1.algorithm.test.Pair;

public class DistanceMatrixRow {

	private HashMap<String, Double> distances;
	private String clusterName;
	private String joinType;

	public DistanceMatrixRow(String clusterName, String joinType) {
		distances = new HashMap<String, Double>();
		this.clusterName = clusterName;
		this.joinType = joinType;
	}

	public void addDistace(String clusterName, Double distance) {
		distances.put(clusterName, distance);
	}

	public Double getDistace(String clusterName) {
		return distances.get(clusterName);
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public DistanceMatrixRow join(Pair newcluster,
			DistanceMatrixRow distanceMatrixRow) {

		if (joinType.equals("single")) {
			Set<String> keys = distances.keySet();
			for (String key : keys) {
				distances.put(
						key,
						Math.min(distances.get(key),
								distanceMatrixRow.getDistace(key)));
			}
		}
		distances.remove(newcluster.getLeft());
		distances.remove(newcluster.getRight());

		this.clusterName = newcluster.getNewName();
		distances.put(this.clusterName, new Double(0.0D));
		return this;
	}

	public void recalculateRow(Pair newcluster, DistanceMatrixRow newclusterRow) {
		String left = newcluster.getLeft();
		String right = newcluster.getRight();
		distances.remove(left);
		distances.remove(right);

		distances.put(newclusterRow.getClusterName(),
				newclusterRow.getDistace(this.clusterName));

	}

}
