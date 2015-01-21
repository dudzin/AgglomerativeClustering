package pl.pw.elka.ddudzin1.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MatrixReader {

	public DistanceMatrix read(String path, String joinType) {

		BufferedReader br = null;
		String line = "";
		DistanceMatrix dm = new DistanceMatrix();

		try {

			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {

				String[] fields = line.split("	");
				DistanceMatrixRow dmrow = new DistanceMatrixRow(fields[0],
						joinType);

				fields = fields[1].split(",");
				for (int i = 0; i < fields.length; i++) {
					dmrow.addDistace("" + i, new Double(fields[i]));

				}
				dm.addRow(dmrow);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dm;
	}
}
