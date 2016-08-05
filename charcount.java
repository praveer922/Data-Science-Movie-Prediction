package movieratings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class charcount {
	
	public static void main(String [ ] args) throws FileNotFoundException {
		
		String fileName = "movieratings.csv";
		HashMap<String,Double> MovieRatings = new HashMap<String,Double>();
		HashMap<String,ArrayList<Double>> charcounts = new HashMap<String,ArrayList<Double>>();
		
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			br.readLine(); //skip first line
			
			
			String line;
			String name;
			double rating;
			int count = 0;
			
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				name = split[0];
				rating = Integer.parseInt(split[1]);
				String[] final_name = name.split("\\(");
				name = final_name[0];
				
				if(!MovieRatings.containsKey(name)) {
					MovieRatings.put(name,rating);
					count = 1;
				} else {
					double curr_rating = MovieRatings.get(name);
					double new_rating = (curr_rating*count + rating)/(count+1);
					MovieRatings.put(name,new_rating);
					count++;
				}
			}
			//movie score hashmap
			
			for( String title : MovieRatings.keySet()) {
				double[] char_arr = new double[300];
				for (int j =0; j<title.length();j++) {
					char_arr[(int)title.charAt(j)]++;
				}
				ArrayList<Double> char_arrlist = new ArrayList<Double>();
				for (int i =0; i<char_arr.length;i++) {
					char_arrlist.add(char_arr[i]);
				}
				char_arrlist.add(MovieRatings.get(title));
				
				charcounts.put(title, char_arrlist);
			}
			//hashmap of charcounts
			
			File file = new File("charcounts.csv");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Movie Name, ");
			for (int i=1; i<=300; i++) {
				bw.write(i + ", ");
			}
			bw.write("Rating");
			bw.newLine();
			
			for( String moviename : charcounts.keySet()) {
				bw.write(moviename + ", ");
				ArrayList<Double> charcountarr =  charcounts.get(moviename);
				for (int i=0; i<charcountarr.size();i++) {
					if (i==charcountarr.size()-1) {
						bw.write(String.valueOf(charcountarr.get(i)));
					} else {
						bw.write(charcountarr.get(i) + ", ");
					}
				}
				bw.newLine();
			}
			bw.close();
			
					
		} catch (Exception e) {
			System.out.println(e);
		}
	
	}

}
