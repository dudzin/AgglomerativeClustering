package pl.pw.elka.ddudzin1.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import pl.pw.elka.ddudzin1.algorithm.test.Pair;

public class HierarchicalClusterAlgoritm {

	private DistanceMatrix dm;
	private ArrayList<Pair> candidates;
	HashMap<String, ArrayList<String>> rawcandidates;
	private HashMap<String, Cluster> clusters;

	public void findCandidates() {

		rawcandidates = new HashMap<String, ArrayList<String>>();
		Set<String> clusterNames = dm.getClusterNames();

		for (String clname : clusterNames) {
			rawcandidates.put(clname, dm.getRow(clname).getCandidates());
		}
	}

	public void verifyCandidates() {
		candidates = new ArrayList<Pair>();
		Pair newpair;
		Set<String> rawcand = rawcandidates.keySet();
		boolean add;
		for (String cand1 : rawcand) {
			ArrayList<String> subcands1 = rawcandidates.get(cand1);
			for (String cand2 : subcands1) {
				ArrayList<String> subcands2 = rawcandidates.get(cand2);
				for (String subcand : subcands2) {
					if (subcand.equals(cand1)) {
						newpair = new Pair(cand1, cand2);
						newpair.setHeight(dm.getRow(cand1).getDistace(cand2));
						add = true;
						for (Pair p : candidates) {
							if (p.getNewName().equals(newpair.getNewName())) {
								add = false;
								break;
							}

						}
						if (add) {
							candidates.add(newpair);
						}
					}
				}
			}
		}

	}

	public DistanceMatrix getMatrix() {
		return dm;
	}

	public void setMatrix(DistanceMatrix dm) {
		this.dm = dm;

		clusters = new HashMap<String, Cluster>();
		Set<String> clusterNames = dm.getClusterNames();
		Cluster cluster;

		for (String string : clusterNames) {
			cluster = new Cluster(string, null, null, null);
			clusters.put(string, cluster);
		}

	}

	public HashMap<String, ArrayList<String>> getRawCandidates() {

		return rawcandidates;
	}

	public ArrayList<Pair> getCandidates() {
		return candidates;
	}

	public void join() {

		for (Pair pair : candidates) {
			dm.join(pair);
			Cluster newclust = new Cluster(pair.getNewName(), clusters.get(pair
					.getLeft()), clusters.get(pair.getRight()),
					pair.getHeight());
			clusters.remove(pair.getLeft());
			clusters.remove(pair.getRight());
			clusters.put(pair.getNewName(), newclust);

		}

		dm.recalculateMatrix(candidates);

	}

	public HashMap<String, Cluster> getClusters() {
		return clusters;
	}

	public void run() {
		while (clusters.size() != 1) {
			findCandidates();
			verifyCandidates();
			join();
		}

	}

}
