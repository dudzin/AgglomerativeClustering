package pl.pw.elka.ddudzin1.algorithm;

import java.util.HashMap;

public class DistanceMatrixRow {

	private HashMap<String, Double> distances;
	private String clusterName;
	
	public DistanceMatrixRow(String clusterName){
		distances = new HashMap<String, Double>();
		this.clusterName = clusterName;
	}
	
	public void addDistace(String clusterName, Double distance){
		distances.put(clusterName, distance);
	}
	public Double getDistace(String clusterName){
		return distances.get(clusterName);
	}
	
	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
}
