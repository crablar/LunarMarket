import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This formatter parses the song data and eventually magnifies it as a function
 * of the standard deviation roots. I don't know if the math makes sense, so
 * please ask me if you need clarification. This is my current effort to make
 * the changes in music more pronounced once they are translated into prices.
 * 
 * @author jeffreymeyerson
 * 
 */

public class ResultsOutFormatter {

	public static void main(String[] args) throws FileNotFoundException {

		File file = new File(args[0]);
		Scanner scanner = new Scanner(file);

		String[] resultArr = new String[2];
		resultArr[0] = "low_freq_values";
		resultArr[1] = "high_freq_values";

		// Number of time intervals
		int count = 0;

		int lowFreqSum = 0;
		int highFreqSum = 0;

		// First pass, find the sum and of each of low vals and high vals
		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();
			String[] lineArr = line.split("\t");

			count++;

			System.out.println("line " + line);

			int lowVal = Integer.parseInt(lineArr[1]);
			int highVal = Integer.parseInt(lineArr[2]);

			lowFreqSum += lowVal;
			highFreqSum += highVal;
		}

		int lowFreqMean = lowFreqSum / count;
		int highFreqMean = highFreqSum / count;

		scanner = new Scanner(file);

		int lowFreqValSum = 0;
		int highFreqValSum = 0;

		// Second pass, use the means to find the square of the variance from
		// the mean for each val and keep a running sum
		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();
			String[] lineArr = line.split("\t");

			int lowVal = Integer.parseInt(lineArr[1]);
			int highVal = Integer.parseInt(lineArr[2]);

			int l = lowVal - lowFreqMean;
			int h = highVal - highFreqMean;

			lowFreqValSum += l * l;
			highFreqValSum += h * h;
		}

		int lowStandardDev = (int) Math.sqrt(lowFreqValSum / count);
		int highStandardDev = (int) Math.sqrt(highFreqValSum / count);

		scanner = new Scanner(file);

		// Third pass: create the file
		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();
			String[] lineArr = line.split("\t");

			int lowVal = Integer.parseInt(lineArr[1]);
			int highVal = Integer.parseInt(lineArr[2]);

			/**
			 * If no standard deviation, no need to normalize This is strictly
			 * for the monotonic mp3, used for testing.
			 */
			if (lowStandardDev == 0 && highStandardDev == 0) {
				lowStandardDev = Integer.MAX_VALUE;
				highStandardDev = Integer.MAX_VALUE;
			}

			// Take the square root of the standard deviation
			double lowStdDevRoot = Math.sqrt(lowStandardDev);
			double highStdDevRoot = Math.sqrt(highStandardDev);

			// Magnify the vals, keeping them a factor of the standard deviation
			// roots
			int lowExtremity = (int) (lowVal / lowStdDevRoot);
			int highExtremity = (int) (highVal / highStdDevRoot);

			resultArr[0] += " " + (lowExtremity);
			resultArr[1] += " " + (highExtremity);

		}

		System.out.println(resultArr[0]);
		System.out.println(resultArr[1]);

	}

}
