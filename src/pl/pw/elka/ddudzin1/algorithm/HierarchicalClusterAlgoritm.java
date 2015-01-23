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
	private String runType;
	private int iterationCnt;
	private long startTime, endTime;

	public HierarchicalClusterAlgoritm(String runType) {
		this.runType = runType;
	}

	public void findCandidates() {

		rawcandidates = new HashMap<String, ArrayList<String>>();
		Set<String> clusterNames = dm.getClusterNames();

		// split to threads and then join into rawcandidates.putAll
		for (String clname : clusterNames) {
			rawcandidates.put(clname,
					dm.getRow(clname).getCandidates(candidates));
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
		if (runType.equals("l")) {
			Double min = -1D, st = -1D, val;
			Pair minP = new Pair(null, null);
			for (Pair p : candidates) {
				val = p.getHeight();

				if (min.equals(st) && !val.equals(0D)) {
					min = val;
					minP = p;
				} else {
					if (min > val && !val.equals(0D)) {
						min = val;
						minP = p;
					}
				}

			}

			candidates = new ArrayList<Pair>();
			candidates.add(minP);
		}
	}

	public void join() {
		long time1, time2;
		time1 = System.nanoTime();
		// System.out.println("join " + (intertime1 - intertime2) / 1000);
		for (Pair pair : candidates) {
			dm.join(pair);
			Cluster newclust = new Cluster(pair.getNewName(), clusters.get(pair
					.getLeft()), clusters.get(pair.getRight()),
					pair.getHeight());
			clusters.remove(pair.getLeft());
			clusters.remove(pair.getRight());
			clusters.put(pair.getNewName(), newclust);

		}
		// time2 = System.nanoTime();
		// System.out.println("for " + (time2 - time1) / 1000);
		// dm.recalculateMatrix(candidates);
		// time1 = System.nanoTime();
		// System.out.println("recalc " + (time1 - time2) / 1000);

	}

	public HashMap<String, Cluster> getClusters() {
		return clusters;
	}

	public void run() {
		iterationCnt = 0;
		startTime = System.nanoTime();
		long intertime1, intertime2 = startTime;
		while (clusters.size() != 1) {
			// intertime2 = System.nanoTime();
			findCandidates();

			// intertime1 = System.nanoTime();
			// System.out.println("find " + (intertime1 - intertime2) / 1000);
			// intertime2 = System.nanoTime();
			verifyCandidates();

			// intertime1 = System.nanoTime();
			// System.out.println("verify " + (intertime1 - intertime2) / 1000);
			// intertime2 = System.nanoTime();
			join();

			// intertime1 = System.nanoTime();
			// System.out.println("join " + (intertime1 - intertime2) / 1000);
			iterationCnt++;
		}
		endTime = System.nanoTime();
	}

	public Cluster getRoot() {
		if (clusters.size() == 1) {
			Set<String> keys = clusters.keySet();
			for (String string : keys) {
				return clusters.get(string);
			}

		}
		return null;
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

	public int getIterationCnt() {
		return iterationCnt;
	}

	public void setIterationCnt(int iterationCnt) {
		this.iterationCnt = iterationCnt;
	}

	public HashMap<String, ArrayList<String>> getRawCandidates() {
		return rawcandidates;
	}

	public ArrayList<Pair> getCandidates() {
		return candidates;
	}

	public long getExecutionTime() {
		return endTime - startTime;
	}
}
