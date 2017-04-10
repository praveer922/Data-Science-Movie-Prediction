package movieratings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;



public class Main {
	
	
	public static void main(String [ ] args) throws FileNotFoundException, IOException
	{
		String fileName = "movieratings.csv";
		HashMap<String,Double> MovieRatings = new HashMap<String,Double>();
		HashMap<String, double[]> wordscore = new HashMap<String, double[]>();
		
		try {
		//main 
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
				String[] final_name = name.split(" \\(");
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
			//Hashmap of movie name and average score
			
			
			
			for( String title : MovieRatings.keySet()) {
				String[] split_words = title.split(" ");
				//String[] split_words = splitwords[0].split(" ");
				
				for (int i =0; i<split_words.length; i++) {
					if (wordscore.containsKey(split_words[i])) {
						double[] sum_score = wordscore.get(split_words[i]);
						double[] newsum = new double[2];
						newsum[0] = sum_score[0] + MovieRatings.get(title);
						newsum[1] = sum_score[1]+1;
						wordscore.put(split_words[i],newsum);
					} else {
						double[] sum_score = new double[2];
						sum_score[0] = MovieRatings.get(title);
						sum_score[1] = 1;
						wordscore.put(split_words[i],sum_score);
					}
				}
				
			}
			//Hashmap of word scores(value = double array(sum, count)
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("Predicted Imdb Rating: " + MovieRatings.get(args[0]));
		
		String movie_name = args[0];
		String[] split_name = movie_name.split(" ");
		double tot_score = 0;
		double tot_count = 0;
		
		for (String moviename : wordscore.keySet()) {
			double[] sumscore = wordscore.get(moviename);
			tot_score += sumscore[0];
			tot_count +=sumscore[1];
		}
		double word_avg = tot_score/tot_count;
		
		double score = 0;
		for (int i=0;i<split_name.length;i++) {
			double[] w_score = wordscore.get(split_name[i]);
			if (w_score==null) {
				//score += word_avg;
				continue;
			}
			double avg = w_score[0]/w_score[1];
			score += avg;
			
		}
		score = score/split_name.length;
		
		System.out.println("Predicted Rating: "+  score);
		
		/* WRITE FILE
		File file = new File("wordscores2.csv");
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Word, wordscore, occurrence");
		bw.newLine();
		
		for( String words : wordscore.keySet()) {
			double[] w_score = wordscore.get(words);
			double avg = w_score[0]/w_score[1];
			String line = words+ "," + String.valueOf(avg) + ", " + w_score[1];
			bw.write(line);
			bw.newLine();
		}
		bw.close();
		*/
		

		
		
		
		
	}

}
