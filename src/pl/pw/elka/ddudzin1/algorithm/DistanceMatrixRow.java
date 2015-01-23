package pl.pw.elka.ddudzin1.algorithm;

import java.util.ArrayList;
import java.util.Collection;
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

		Set<String> keys = distances.keySet();
		for (String key : keys) {
			if (joinType.equals("single")) {
				distances.put(
						key,
						Math.min(distances.get(key),
								distanceMatrixRow.getDistace(key)));
			} else if (joinType.equals("complete")) {
				distances.put(
						key,
						Math.max(distances.get(key),
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
		if (newclusterRow.getDistace(this.getClusterName()) != null) {
			distances.remove(left);
			distances.remove(right);

			distances.put(newclusterRow.getClusterName(),
					newclusterRow.getDistace(this.clusterName));
		} else {
			if (joinType.equals("single")) {
				distances.put(newclusterRow.getClusterName(),
						Math.min(distances.get(left), distances.get(right)));
				distances.remove(left);
				distances.remove(right);
			} else if (joinType.equals("complete")) {
				distances.put(newclusterRow.getClusterName(),
						Math.max(distances.get(left), distances.get(right)));
				distances.remove(left);
				distances.remove(right);
			}
		}
	}

	public String toString() {
		String s = "";
		Set<String> keys = distances.keySet();

		for (String string : keys) {
			s += "{" + string + "}" + " = " + distances.get(string) + ";'";
		}

		return s.substring(0, s.length() - 1);
	}

	public ArrayList<String> getCandidates() {
		ArrayList<String> candList = new ArrayList<String>();
		Collection<Double> values = distances.values();

		Double min = -1D, st = -1D;
		for (Double val : values) {
			if (min.equals(st) && !val.equals(0D)) {
				min = val;
			} else {
				if (min > val && !val.equals(0D)) {
					min = val;
				}
			}
		}

		Set<String> keys = distances.keySet();
		for (String key : keys) {
			if (distances.get(key) == min) {
				candList.add(key);
			}
		}

		return candList;
	}

	public ArrayList<String> getCandidates(ArrayList<Pair> oldcandidates) {
		ArrayList<String> candList = new ArrayList<String>();
		Collection<Double> values = distances.values();
		if (oldcandidates != null) {
			joinOldCandidates(oldcandidates);
		}

		Double min = -1D, st = -1D;
		for (Double val : values) {
			if (min.equals(st) && !val.equals(0D)) {
				min = val;
			} else {
				if (min > val && !val.equals(0D)) {
					min = val;
				}
			}
		}

		Set<String> keys = distances.keySet();
		for (String key : keys) {
			if (distances.get(key) == min) {
				candList.add(key);
			}
		}

		return candList;
	}

	public void joinOldCandidates(ArrayList<Pair> oldcandidates) {

		for (Pair newcluster : oldcandidates) {
			String left = newcluster.getLeft();
			String right = newcluster.getRight();
			if (distances.get(newcluster.getNewName()) != null) {

			} else {
				if (joinType.equals("single")) {
					distances
							.put(newcluster.getNewName(),
									Math.min(distances.get(left),
											distances.get(right)));
					distances.remove(left);
					distances.remove(right);
				} else if (joinType.equals("complete")) {
					distances
							.put(newcluster.getNewName(),
									Math.max(distances.get(left),
											distances.get(right)));
					distances.remove(left);
					distances.remove(right);
				}
			}
		}

	}

	public void recalculateRow(ArrayList<Pair> newclusters) {
		String left, right;

		for (Pair pair : newclusters) {

			if (distances.get(pair.getNewName()) == null) {
				left = pair.getLeft();
				right = pair.getRight();
				if (joinType.equals("single")) {
					distances
							.put(pair.getNewName(),
									Math.min(distances.get(left),
											distances.get(right)));
					distances.remove(left);
					distances.remove(right);
				} else if (joinType.equals("complete")) {
					distances
							.put(pair.getNewName(),
									Math.max(distances.get(left),
											distances.get(right)));
					distances.remove(left);
					distances.remove(right);
				}

			}
		}

	}

}
