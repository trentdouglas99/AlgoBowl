import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Verifier {

	public static void main(String[] args) throws Exception {
		File file = new File("1000_points.txt"); 
		Scanner sc = new Scanner(file); 
		int n = Integer.parseInt(sc.nextLine());
		
		int k = Integer.parseInt(sc.nextLine());
		ArrayList<Point> pArray = new ArrayList<Point>(n);
		int counter = 0;
		while (sc.hasNextLine()) {
			counter++;
			String[] array = sc.nextLine().split(" ");
			Point newPoint = new Point(array[0],array[1],array[2]);
			newPoint.setId(counter);
			if(pArray.contains(newPoint)) {
				System.out.println("Duplicate point found");
				return;
			}
			pArray.add(newPoint);
		}
		
		File output_file = new File("1000_points_output.txt"); 
		Scanner out_sc = new Scanner(output_file); 
		
		int max_distance = Integer.parseInt(out_sc.nextLine());
		
		int lineCounter = 0;
		
		int pointCounter = 0;
		ArrayList<String> pointArray = new ArrayList<String>(n);
		while(out_sc.hasNextLine()) {
			lineCounter++;
			String[] array = out_sc.nextLine().split(" ");
			for(int i = 0; i < array.length; i++) {
				if(pointArray.contains(array[i])) {
					System.out.println("Duplicate point found in output" );
					return;
				}
				pointArray.add(array[i]);
				pointCounter++;
				for(int j = i + 1; j < array.length; j++) {
					if(Main2.manDistance(pArray.get(Integer.parseInt(array[i])), pArray.get(Integer.parseInt(array[j]))) > max_distance) {
						System.out.println("Max distance too low");
						System.out.println(Main2.manDistance(pArray.get(Integer.parseInt(array[i])), pArray.get(Integer.parseInt(array[j]))));
						return;
					}
				}
			}
		}
		if(lineCounter != k) {
			
			System.out.println("Incorrect Number of Sets");
			return;
		}
		
		if(pointCounter != n) {
			System.out.println("Not using all points");
			return;
		}
		
		
		System.out.println("Output is Verified");
		
	}

}
