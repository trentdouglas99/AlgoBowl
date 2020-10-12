import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Random rand = new Random();
		
		File file = new File("200_points.txt"); 
		Scanner sc = new Scanner(file); 
		int n = Integer.parseInt(sc.nextLine());
		
		int k = Integer.parseInt(sc.nextLine());
		ArrayList<Point> pArray = new ArrayList<Point>(n);
		while (sc.hasNextLine()) {
			String[] array = sc.nextLine().split(" ");
			Point newPoint = new Point(array[0],array[1],array[2]);
			pArray.add(newPoint);
		}
		
		sc.close();
		
		System.out.println(pArray);
		ArrayList<Integer> idArray = new ArrayList<Integer>(n);
		ArrayList<ArrayList<Integer>> array3 = new ArrayList<>();
 		int sizeLeft = n;
 		int kFactor = k - 1;
		for(int i = 0; i < k; i++) {
			//System.out.println(array3);
			int randomN = 0;
			if(i == k - 1) {
				randomN = sizeLeft;
			} else {
				if(sizeLeft-(k - 1) <= 0) {
					randomN = 1;
					System.out.println("Hello");
				} else {
					randomN = n/k;
				}
				
			}
			
			sizeLeft -= randomN;
			ArrayList<Integer> tempArray = new ArrayList<Integer>();
			for(int j = 0; j < randomN; j++) {
				int newRand = 0;
				while(true) {
					newRand = rand.nextInt(n) + 1;
					if(!idArray.contains(newRand)) {
						idArray.add(newRand);
						tempArray.add(newRand);
						break;
					}
				}
				
			}
			array3.add(tempArray);
		}
		System.out.println("Start");
		for(int i = 0; i < array3.size(); i++) {
			System.out.print(array3.get(i).size()+" ");
		}
		System.out.println("End");
		System.out.println(array3);
		ArrayList<Integer> lArray = new ArrayList<Integer>();
		int maxL = 0;
		for(int i = 0; i < array3.size(); i++) {
			int localSize = 0;
			int size = array3.get(i).size();
			ArrayList<Integer> tempArray = array3.get(i);
			if(size != 1) {
				for(int j = 0; j < size; j++) {
					for(int l = j + 1; l < size; l++) {
						Point tempP1 = pArray.get(tempArray.get(j) - 1);
						Point tempP2 = pArray.get(tempArray.get(l) - 1);
						int tempSize = Math.abs(tempP1.getX() - tempP2.getX()) + Math.abs(tempP1.getY() - tempP2.getY()) + Math.abs(tempP1.getZ() - tempP2.getZ());
						if (tempSize > localSize) {
							localSize = tempSize;
						}
					}
				}
			}
			lArray.add(localSize);
			System.out.println(localSize);
			if(localSize > maxL) {
				maxL = localSize;
			}
		}
		System.out.println(lArray);
		
		
		//int loopSize = (int) (Math.pow(n, 2));
		long loopSize = 130000;
		long fuelCounter = loopSize;
		System.out.println("Entering loop");
		for(int i = 0; i < loopSize; i++) {
			//System.out.println("Iteration: " + i);
			
			ArrayList<Integer> removed = new ArrayList<Integer>();
			ArrayList<Integer> templArray = (ArrayList<Integer>) lArray.clone();
			int randomNumber1 = rand.nextInt(lArray.size());
			int randomNumber2 = 0;
			while(true) {
				randomNumber2 = rand.nextInt(lArray.size());
				if(randomNumber2 != randomNumber1) {
					break;
				}
			}
			//System.out.println(randomNumber1 + " " + randomNumber2);
			
			for(int j = 0; j < array3.get(randomNumber1).size(); j++) {
				removed.add(array3.get(randomNumber1).get(j));
			}
			for(int j = 0; j < array3.get(randomNumber2).size(); j++) {
				removed.add(array3.get(randomNumber2).get(j));
			}
			
			//System.out.println(removed);
			
			int newSizeLeft = removed.size();
			ArrayList<Integer> newTaken = new ArrayList<Integer>();
			int nR1 = rand.nextInt(removed.size() - 1) + 1;
			int nR2 = removed.size() - nR1;
			ArrayList<Integer> newFirst = new ArrayList<Integer>();
			for(int j = 0; j < nR1; j++) {
				
				int r1 = 0;
				while(true) {
					r1 = rand.nextInt(removed.size());
					if(!newTaken.contains(r1)) {
						newTaken.add(r1);
						newFirst.add(r1);
						break;
					}

				}
				
				
			}
			ArrayList<Integer> newSecond = new ArrayList<Integer>();
			for(int j = 0; j < nR2; j++) {
				
				int r1 = 0;
				while(true) {
					r1 = rand.nextInt(removed.size());
					if(!newTaken.contains(r1)) {
						newTaken.add(r1);
						newSecond.add(r1);
						break;
					}

				}	
			}
			//System.out.println(newFirst + " " + newSecond);
			int newFirstL = 0;
			ArrayList<Integer> replaceFirst = new ArrayList<Integer>();
			for(int j = 0; j < newFirst.size(); j++) {
				replaceFirst.add(removed.get(newFirst.get(j)));
				for(int l = j + 1; l < newFirst.size(); l++) {
					Point tempP1 = pArray.get(removed.get(newFirst.get(j)) - 1);
					Point tempP2 = pArray.get(removed.get(newFirst.get(l)) - 1);
					//System.out.println(tempP1 + " " + tempP2);
					int tempSize = Math.abs(tempP1.getX() - tempP2.getX()) + Math.abs(tempP1.getY() - tempP2.getY()) + Math.abs(tempP1.getZ() - tempP2.getZ());
					if (tempSize > newFirstL) {
						newFirstL = tempSize;
					}
				}
			}
			//System.out.println(newFirstL);
			templArray.set(randomNumber1, newFirstL);
			
			int newSecondL = 0;
			ArrayList<Integer> replaceSecond = new ArrayList<Integer>();
	
				
			for(int j = 0; j < newSecond.size(); j++) {
				replaceSecond.add(removed.get(newSecond.get(j)));
				for(int l = j + 1; l < newSecond.size(); l++) {
					Point tempP1 = pArray.get(removed.get(newSecond.get(j)) - 1);
					Point tempP2 = pArray.get(removed.get(newSecond.get(l)) - 1);
					//System.out.println(tempP1 + " " + tempP2);
					int tempSize = Math.abs(tempP1.getX() - tempP2.getX()) + Math.abs(tempP1.getY() - tempP2.getY()) + Math.abs(tempP1.getZ() - tempP2.getZ());
					if (tempSize > newSecondL) {
						newSecondL = tempSize;
					}
				}
			}
			
			templArray.set(randomNumber2, newSecondL);
			
			//System.out.println(lArray);
			int tempLMax = 0;
			for(int j = 0; j < templArray.size(); j++) {
				if(templArray.get(j) > tempLMax) {
					tempLMax = templArray.get(j);
				}
			}
			//System.out.println(templArray);
			//System.out.println(maxL + " " + tempLMax);
			//System.out.println(array3);
			//System.out.println(array3Clone);
			
//			int deciding = rand.nextInt(100)+1;
			//int currentP = (int) ((Double.valueOf(fuelCounter)/loopSize)*100);
			//System.out.println(deciding);
//			if((deciding < currentP && tempLMax >= maxL) || (deciding > currentP && tempLMax <= maxL) ) {
			if((tempLMax <= maxL)) {
				
				array3.set(randomNumber2, replaceSecond);
				array3.set(randomNumber1, replaceFirst);
				//System.out.println(currentP);
				//array3 = array3Clone;
				maxL = tempLMax;
				lArray = templArray;
				//System.out.println("flipped");
			}
			//System.out.println("Max:" + maxL);
			//System.out.println(lArray);
			//fuelCounter--;
			//System.out.println(currentP + " " + deciding);
		}
		
//		FileWriter myWriter = new FileWriter("finalOutput.txt");
//		
//		myWriter.write(maxL+"\n");
//		for(int i = 0; i < array3.size(); i++) {
//			ArrayList<Integer> current = array3.get(i);
//			for(int j = 0; j < current.size(); j++) {
//				myWriter.write(current.get(j));
//				if(j != current.size() - 1) {
//					myWriter.write(" ");
//				}
//			}
//			myWriter.write("\n");
//		}
//		
//		myWriter.close();
		//System.out.println(array3);
		System.out.println("Max: " +maxL);
		System.out.println("Done: " + fuelCounter);
		//System.out.println(array3);
		      
	}

}

//ArrayList<Integer> array = new ArrayList<Integer>();
//Double number = Math.pow(2, 1000);
//System.out.println(number);
//for(int i = 1; i < number; i++) {
//	array.add(i);
//}
//ArrayList<List<Integer>> returningList = new ArrayList<List<Integer>>();
//returningList.add(new ArrayList<Integer>());
//for(Integer number : array){
//	Integer size = returningList.size();
//	for(int i = 0; i < size; i++){
//		ArrayList<Integer> newList = new ArrayList<Integer>(returningList.get(i));
//		newList.add(number);
//		returningList.add(newList);
//	}
//}
//System.out.println(array);

//int[] array = {1, 2, 4, 5, 6};
//int first = 0;
//int last = array.length;
//int middle = (last + first)/2;
//
//int resultingNumber = 0;
//
//while(first <= last) {
//	if(array[middle] == middle + 1) {
//		resultingNumber = middle + 1;
//		last = middle - 1;
//		middle = (last + first)/2;
//	} else if (array[middle] > (middle + 1)) {
//		last = middle - 1;
//		middle = (last + first)/2;
//	} else {
//		first = middle + 1;
//		middle = (last + first)/2;
//	}
//}

//Print the smallest index that a_i = i
//If the printed index is zero, that means there was no such index
//System.out.println("Index: " + resultingNumber);
