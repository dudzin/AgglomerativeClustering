package pl.pw.elka.ddudzin1.algorithm;

public class Cluster {

	private String clusterName;
	private Cluster leftParent, rightParent;
	private Double height;

	public Cluster(String clustName, Cluster leftParent, Cluster rightParent,
			Double height) {

		this.clusterName = clustName;
		this.leftParent = leftParent;
		this.rightParent = rightParent;
		this.height = height;

	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public Cluster getRightParent() {
		return rightParent;
	}

	public void setRightParent(Cluster rightParent) {
		this.rightParent = rightParent;
	}

	public Cluster getLeftParent() {
		return leftParent;
	}

	public void setLeftParent(Cluster leftParent) {
		this.leftParent = leftParent;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}
}
