package pl.pw.elka.ddudzin1.algorithm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	private ArrayList<FindCandidates> fsList;
	private int threadsCnt = 16;
	private int sleepTime = 1;
	private int clusterCnt;

	public HierarchicalClusterAlgoritm(String runType) {
		this.runType = runType;
	}

	public void findCandidates() {

		rawcandidates = new HashMap<String, ArrayList<String>>();
		prepareThreads();
		runThreads();
		gatherRawCandidates();
	}

	public void prepareThreads() {

		fsList = new ArrayList<FindCandidates>();

		for (int i = 0; i < threadsCnt; i++) {
			FindCandidates fs = new FindCandidates();
			fs.setCandidates(candidates);
			fsList.add(fs);
		}

		Set<String> clnames = dm.getClusterNames();
		int i = 0;
		for (String clname : clnames) {
			if (threadsCnt > 1) {
				fsList.get(i % (threadsCnt - 1)).addRow(dm.getRow(clname));
			} else {
				fsList.get(0).addRow(dm.getRow(clname));
			}
		}

	}

	public void runThreads() {
		ArrayList<Thread> threads = new ArrayList<Thread>();
		Thread t = new Thread();
		for (FindCandidates fs : fsList) {
			t = new Thread(fs);
			threads.add(t);
			t.start();
		}
		while (areAlive(threads)) {

		}
	}

	private boolean areAlive(ArrayList<Thread> threads) {
		for (Thread thread : threads) {
			if (thread.isAlive()) {
				return true;
			}
		}
		return false;
	}

	public void gatherRawCandidates() {
		rawcandidates = new HashMap<String, ArrayList<String>>();

		for (FindCandidates fs : fsList) {
			rawcandidates.putAll(fs.getRawcandidates());
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
		for (Pair pair : candidates) {
			dm.join(pair);
			Cluster newclust = new Cluster(pair.getNewName(), clusters.get(pair
					.getLeft()), clusters.get(pair.getRight()),
					pair.getHeight());
			clusters.remove(pair.getLeft());
			clusters.remove(pair.getRight());
			clusters.put(pair.getNewName(), newclust);

		}

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
			clusterCnt++;
		}

	}

	public void getCophenetic(String fileName) throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");

		String line;
		for (int i = 0; i < clusterCnt; i++) {
			line = "" + i + "	";
			for (int j = 0; j < clusterCnt; j++) {
				line += getRoot().getCophenetic("" + i, "" + j) + ",";
			}

			line = line.substring(0, line.length() - 1);
			writer.println(line);
		}

		writer.close();
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

	public void setThreadsCnt(int threadsCnt) {
		this.threadsCnt = threadsCnt;
	}

	public int getThreadsCnt() {
		return threadsCnt;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

}
