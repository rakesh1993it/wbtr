import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GetStationList {

	
	public String[] NStationList() {
		String fileName = "masterFile.txt";
		int i = 1;
		String stName[] = new String[21];
		//int atmName[] = new int[21];
		ArrayList<String> stName1 = new ArrayList<>();
		//ArrayList<Integer> atmName1 = new ArrayList<>();

		String line = null;
		stName[0] = "";
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);


			while ((line = bufferedReader.readLine()) != null) {

				// \\s+ means any number of whitespaces between tokens
				String[] tokens = line.split("\\s+");
				float lat = Float.parseFloat(tokens[0]);
				//System.out.println(lat);
				//System.out.println("\n");
				float lon = Float.parseFloat(tokens[1]);
				//System.out.println(lon);
				String station = tokens[2];
				//int atm=Integer.parseInt(tokens[3]);
				//atmName1.add(atm);
				//atmName[i]=atm;
				
				//System.out.println(station);
				stName1.add(station);
				
				stName[i]= station;
				//System.out.println(stName[i]);
				i++;
			}
			
		
	

			// Always close files.
			bufferedReader.close();
		
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			 ex.printStackTrace();
		}
       Arrays.sort(stName);
		//System.out.println();
		
		stName[0] = "---Select---";
		// Collections.sort((java.util.List<T>) stName1);
		return stName;

	
}
}