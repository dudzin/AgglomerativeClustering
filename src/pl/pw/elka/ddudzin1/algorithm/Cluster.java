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

	@Override
	public String toString() {
		if (height != null) {
			return clusterName + ":" + height;
		}
		return clusterName;
	}

	public void print(String offset) {

		System.out.println(offset + toString());
		if (leftParent != null) {
			leftParent.print(offset + "  ");
		}
		if (rightParent != null) {
			rightParent.print(offset + "  ");
		}
	}

	public String getNewickFromRoot() {
		return "(" + getNewick() + ")";

	}

	public String getNewick() {
		String s = "";
		if (leftParent != null && rightParent != null) {
			s = "(" + leftParent.getNewick() + "," + rightParent.getNewick()
					+ "):" + height;
		} else {
			s += clusterName + ":" + 0.0 + "";
		}

		return s;
	}

	public Double getCophenetic(String cl1, String cl2) {
		String left, right;
		left = leftParent.getClusterName();
		right = rightParent.getClusterName();

		if (cl1.equals(cl2)) {
			return 0.0D;
		} else if (left.contains(cl1) && left.contains(cl2)) {
			return leftParent.getCophenetic(cl1, cl2);
		} else if (right.contains(cl1) && right.contains(cl2)) {
			return rightParent.getCophenetic(cl1, cl2);
		} else {
			return height;
		}
	}

}
