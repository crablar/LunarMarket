import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ResultsOutFormatter {

	public static void main (String[] args) throws FileNotFoundException{
		
		File file = new File(args[0]);
		Scanner scanner = new Scanner(file);
				
		String[] resultArr = new String[2];
		resultArr[0] = "low_freq_values";
		resultArr[1] = "high_freq_values";
		
		int count = 0;
		int lowFreqSum = 0;
		int highFreqSum = 0;
		
		//First pass, find the means
		while(scanner.hasNextLine()){
			
			String line = scanner.nextLine();
			String[] lineArr = line.split("\t");
			
			count++;
			
			System.out.println("line " + line);
			
			int lowVal = Integer.parseInt(lineArr[1]);
			int highVal = Integer.parseInt(lineArr[1]);

			lowFreqSum += lowVal;
			highFreqSum += highVal;
		}
		
		int lowFreqMean = lowFreqSum / count;
		int highFreqMean = highFreqSum / count;
		
		scanner = new Scanner(file);

		int lowFreqValSum = 0;
		int highFreqValSum = 0;
		
		//Second pass, use the means
		while(scanner.hasNextLine()){
			
			String line = scanner.nextLine();
			String[] lineArr = line.split("\t");
			
			int lowVal = Integer.parseInt(lineArr[1]);
			int highVal = Integer.parseInt(lineArr[1]);

			int l = lowVal - lowFreqMean;
			int h = highVal - highFreqMean;
			
			lowFreqValSum += l * l;
			highFreqValSum += h * h;
		}
		
		int lowStandardDev = (int) Math.sqrt(lowFreqValSum / count);
		int highStandardDev = (int) Math.sqrt(highFreqValSum / count);
		
		// Third pass: create the file
		scanner = new Scanner(file);
		
		//Second pass, use the means
		while(scanner.hasNextLine()){
			
			String line = scanner.nextLine();
			String[] lineArr = line.split("\t");
			
			int lowVal = Integer.parseInt(lineArr[1]);
			int highVal = Integer.parseInt(lineArr[1]);

			resultArr[0] += " " + (lowVal % lowStandardDev);
			resultArr[1] += " " + (highVal % highStandardDev);

		}
		
		System.out.println(resultArr[0]);
		System.out.println(resultArr[1]);

	}
	

}
