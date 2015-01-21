package pl.pw.elka.ddudzin1.algorithm.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class Pair {

	private String left, right;
	
	public Pair(String left, String right){
		this.left = left;
		this.right = right;
	}

	public String getNewName(){

		String[] splitleft = left.split(",");
		String[] splitright = right.split(",");
		
		int[] array = new int[splitleft.length + splitright.length];
		int cnt=0;
		for (cnt= 0; cnt < splitleft.length; cnt++) {
			array[cnt] = Integer.parseInt(splitleft[cnt]);
		}
		for (int i = 0; i < splitright.length; i++) {
			array[cnt+i] =Integer.parseInt(splitright[i ]);
		}

		Arrays.sort(array);
		
		String newname ="";
		for (int i = 0; i < array.length; i++) {
			newname += array[i] + ",";
		}
		newname = newname.substring(0, newname.length()-1);
		
		return newname;
		
	}

	
	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}
	
}
