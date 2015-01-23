package pl.pw.elka.ddudzin1.algorithm;

import java.util.ArrayList;
import java.util.HashMap;

import pl.pw.elka.ddudzin1.algorithm.test.Pair;

public class FindCandidates implements Runnable {

	private HashMap<String, ArrayList<String>> rawcandidates;
	private ArrayList<DistanceMatrixRow> rows;
	private ArrayList<Pair> candidates;

	public FindCandidates() {
		rows = new ArrayList<DistanceMatrixRow>();

	}

	@Override
	public void run() {
		rawcandidates = new HashMap<String, ArrayList<String>>();
		for (DistanceMatrixRow row : rows) {
			rawcandidates.put(row.getClusterName(),
					row.getCandidates(candidates));
		}
	}

	public HashMap<String, ArrayList<String>> getRawcandidates() {
		return rawcandidates;
	}

	public void setRawcandidates(
			HashMap<String, ArrayList<String>> rawcandidates) {
		this.rawcandidates = rawcandidates;
	}

	public ArrayList<DistanceMatrixRow> getRows() {
		return rows;
	}

	public void setRows(ArrayList<DistanceMatrixRow> rows) {
		this.rows = rows;
	}

	public void addRow(DistanceMatrixRow row) {
		this.rows.add(row);
	}

	public ArrayList<Pair> getCandidates() {
		return candidates;
	}

	public void setCandidates(ArrayList<Pair> candidates) {
		this.candidates = candidates;
	}

}
