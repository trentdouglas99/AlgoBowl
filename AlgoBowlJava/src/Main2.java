import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main2 {
	
	public static void main(String[] args) throws Exception {
		
		long startTime = System.nanoTime();
		Random rand = new Random();
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
			pArray.add(newPoint);
		}
		ArrayList<Point> pArrayCopy = (ArrayList<Point>) pArray.clone();
		
		
		sc.close();
		
		//System.out.println(pArray.get(1));
		
		double[][] distance = new double[n][n];
		
		int[][] manDistance = new int[n][n];
		
		//System.out.println(Math.pow(2.5, 3));
		
		//System.out.println(distanceBetweenPoints(pArray.get(0), pArray.get(0)));
		
		//ArrayList
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				distance[i][j] = distanceBetweenPoints(pArray.get(i),pArray.get(j));
				manDistance[i][j] = manDistance(pArray.get(i),pArray.get(j));
			}
		}
		
		
		
		double maxDistance = 0;
		ArrayList<Point> pivotPoints = new ArrayList<Point>();
		Point firstPivot = null;
		Point secondPivot = null;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				//System.out.print(manDistance[i][j] + " ");
				if(distance[i][j] > maxDistance) {
					firstPivot = pArray.get(i);
					secondPivot = pArray.get(j);
					maxDistance = distance[i][j];
				}
			}
			//System.out.println(" ");
		}
		
		pivotPoints.add(firstPivot);
		pivotPoints.add(secondPivot);
		
		System.out.println(pivotPoints);
		//System.out.println(pArray);
		pArray.remove(firstPivot);
		pArray.remove(secondPivot);
		System.out.println(pArray);
		
		for(int j = 2; j < k; j++) {
			Point potentialPivot = null;
			double bestDistance = -1;
			double manBestDistance = -1;
			
			ArrayList<Point> equalPoints = new ArrayList<Point>();
			for(int i = 0; i < pArray.size(); i++) {
				double distanceNewPivot = distanceBetweenNewPivot(pArray.get(i),pivotPoints);
				double manDistanceNewPivot = manDistanceBetweenNewPivot(pArray.get(i), pivotPoints);
				//System.out.println(distanceNewPivot);
				if(distanceNewPivot > bestDistance && manDistanceNewPivot > manBestDistance) {
					bestDistance = distanceNewPivot;
					manBestDistance = manDistanceNewPivot;
					potentialPivot = pArray.get(i);
				} 
				
			}
			
			
			//System.out.println(potentialPivot + " " + bestDistance);
			
			pivotPoints.add(potentialPivot);
			pArray.remove(potentialPivot);
		}
		
		ArrayList<ArrayList<Point>> finalArray = new ArrayList<ArrayList<Point>>(k);
		for(int i = 0; i < pivotPoints.size(); i++) {
			ArrayList<Point> newArray = new ArrayList<Point>();
			newArray.add(pivotPoints.get(i));
			finalArray.add(newArray);
		}
		
		System.out.println(finalArray);
		for(int i = 0; i < pArray.size(); i++) {
			Point point = pArray.get(i);
			//System.out.println("hello " + point);
			double smallestDistance = Double.MAX_VALUE;
			int index = -1;
			for(int j = 0; j < pivotPoints.size(); j++) {
				double newDistance = distanceBetweenPoints(point, pivotPoints.get(j));
				//System.out.println(newDistance);
				if(newDistance < smallestDistance) {
					smallestDistance = newDistance;
					index = j;
				}
			}
			
			finalArray.get(index).add(point);
		}
		
		//System.out.println(finalArray);
		ArrayList<ArrayList<Integer>> array3 = new ArrayList<>();
		for(int i = 0; i < finalArray.size(); i++) {
			ArrayList<Point> tempArray = finalArray.get(i);
			ArrayList<Integer> tempArray2 = new ArrayList<Integer>();
			for(int j = 0; j < tempArray.size(); j++) {
				//System.out.print(tempArray.get(j).getId() + " ");
				tempArray2.add(tempArray.get(j).getId() - 1);
			}
			array3.add(tempArray2);
			//System.out.println(" ");
		}
		System.out.println("Final Phase");
		
		double overallMax = 0;
		for(int i = 0; i < finalArray.size(); i++) {
			ArrayList<Point> tempArray = finalArray.get(i);
			int localMax = 0;
			for(int j = 0; j < tempArray.size(); j++) {
				//System.out.print(tempArray.get(j).getId() + " ");
				//System.out.println("\n\n");
				for(int l = j + 1; l < tempArray.size(); l++) {
					//System.out.println(tempArray.get(j).getId() - 1 + " " + (tempArray.get(l).getId() - 1));
					if(manDistance[tempArray.get(j).getId() - 1][tempArray.get(l).getId() - 1] > overallMax) {
						overallMax = manDistance[tempArray.get(j).getId() - 1][tempArray.get(l).getId() - 1];
					}
				}
			}
		
		}
		
		
		System.out.println("Max: " + overallMax);
		System.out.println("Array 3:" + array3);
	    step2V2(array3,pArrayCopy, manDistance);
		
	    long endTime = System.nanoTime();
	    
	    System.out.println("execution time: " + (endTime - startTime)*(Math.pow(10, -9)));
		//System.out.println(finalArray.size());
		
	}
	public static void step2V2(ArrayList<ArrayList<Integer>> array3, ArrayList<Point> pArray, int[][] manDistance) throws Exception {
		//System.out.println("Point Array: " + pArray);
		ArrayList<Integer> lArray = new ArrayList<Integer>();
		int maxL = 0;
		int smallestL = Integer.MAX_VALUE;
		int smallestIndex = 0;
		int maxIndex = -1;
		for(int i = 0; i < array3.size(); i++) {
			int localSize = 0;
			int size = array3.get(i).size();
			ArrayList<Integer> tempArray = array3.get(i);
			if(size != 1) {
				for(int j = 0; j < size; j++) {
					for(int l = j + 1; l < size; l++) {
						Point tempP1 = pArray.get(tempArray.get(j));
						Point tempP2 = pArray.get(tempArray.get(l));
						int tempSize = Math.abs(tempP1.getX() - tempP2.getX()) + Math.abs(tempP1.getY() - tempP2.getY()) + Math.abs(tempP1.getZ() - tempP2.getZ());
						if (tempSize > localSize) {
							localSize = tempSize;
						}
					}
				}
			}
			lArray.add(localSize);
			//System.out.println(localSize);
			if(localSize > maxL) {
				maxL = localSize;
				maxIndex = i;
			} else if (localSize < smallestL) {
				smallestL = localSize;
				smallestIndex = i;
			}
		}
		System.out.println("L Array: " +lArray + " " + maxIndex + " " + lArray.get(maxIndex));
		
		
		long loopSize = 800000;
		long fuelCounter = loopSize;
		System.out.println("Entering loop");
		Random rand = new Random();
		rand.setSeed(System.nanoTime());
		
		for(int i = 0; i < loopSize; i++) {
			
			//System.out.println("Iteration: " + i);
			
			ArrayList<Integer> removed = new ArrayList<Integer>();
			ArrayList<Integer> templArray = (ArrayList<Integer>) lArray.clone();
			int randomNumber1 = rand.nextInt(lArray.size());
			//randomNumber1 = maxIndex;
			int randomNumber2 = 0;
			while(true) {
				randomNumber2 = rand.nextInt(lArray.size());
				if(randomNumber2 != randomNumber1) {
					break;
				}
			}
			int randomDecision = rand.nextInt(10);
			//randomDecision = 0;
			//15*(Double.valueOf(fuelCounter)/loopSize)
			//randomDecision = 5;
			//fuelCounter % 7 != 0
			// randomDecision > 0
			if(fuelCounter % 8 != 0) {
				
				
	//			System.out.println(randomNumber1 + " " + randomNumber2);
	//			System.out.println(array3.get(randomNumber1));
	//			System.out.println(array3.get(randomNumber2));
				
				int randomNumber11 = array3.get(randomNumber1).get(rand.nextInt(array3.get(randomNumber1).size()));
				int randomNumber22 = array3.get(randomNumber2).get(rand.nextInt(array3.get(randomNumber2).size()));
	//			System.out.println(randomNumber11);
	//			System.out.println(randomNumber22);
	//			System.out.println(lArray);
				
				int localMax1 = 0;
				for(int m = 0; m < array3.get(randomNumber1).size(); m++) {
					//System.out.println("PA: " +array3.get(randomNumber1).get(m));
					int currentPoint = array3.get(randomNumber1).get(m);
					if(currentPoint == randomNumber11) {
						currentPoint = randomNumber22;
						//System.out.println("Current Point Overide");
						//System.out.println(currentPoint);
					}
					
					for(int l = m + 1; l < array3.get(randomNumber1).size(); l++) {
						int innerCurrentPoint = array3.get(randomNumber1).get(l);
						if(innerCurrentPoint == randomNumber11) {
							innerCurrentPoint = randomNumber22;
							//System.out.println("Super Override");
							
						}
						
						int distance = manDistance[currentPoint][innerCurrentPoint];
						if(distance > localMax1) {
							localMax1 = distance;
						}
					}
				}
				
				int localMax2 = 0;
				for(int m = 0; m < array3.get(randomNumber2).size(); m++) {
					//System.out.println("PA: " +array3.get(randomNumber2).get(m));
					int currentPoint = array3.get(randomNumber2).get(m);
					if(currentPoint == randomNumber22) {
						currentPoint = randomNumber11;
						//System.out.println("Current Point Overide");
						//System.out.println(currentPoint);
					}
					
					for(int l = m + 1; l < array3.get(randomNumber2).size(); l++) {
						int innerCurrentPoint = array3.get(randomNumber2).get(l);
						if(innerCurrentPoint == randomNumber22) {
							innerCurrentPoint = randomNumber11;
							//System.out.println("Super Override");
							
						}
						
						int distance = manDistance[currentPoint][innerCurrentPoint];
						if(distance > localMax2) {
							localMax2 = distance;
						}
					}
				}
				
				//System.out.println("LMs: " + localMax1 + " " + localMax2);
				//System.out.println("Old: " + lArray.get(randomNumber1) + " " + lArray.get(randomNumber2));
				long randomNumber = rand.nextInt(100);
				if(localMax1 <= lArray.get(randomNumber1) && localMax2 <= lArray.get(randomNumber2)) {
					//System.out.println(array3);
					for(int m = 0; m < array3.get(randomNumber1).size(); m++) {
						int currentPoint = array3.get(randomNumber1).get(m);
						if(currentPoint == randomNumber11) {
							array3.get(randomNumber1).set(m, randomNumber22);
						}
					}
					for(int m = 0; m < array3.get(randomNumber2).size(); m++) {
						int currentPoint = array3.get(randomNumber2).get(m);
						if(currentPoint == randomNumber22) {
							array3.get(randomNumber2).set(m, randomNumber11);
						}
					}
					
					//System.out.println("Swapped");
					//System.out.println(array3);
					lArray.set(randomNumber1, localMax1);
					lArray.set(randomNumber2, localMax2);
				} 
//				else if (randomNumber < (1*(Double.valueOf(fuelCounter)/loopSize) - 0.5)) {
//					Double amount = 1*(Double.valueOf(fuelCounter)/loopSize);
//					//System.out.println(amount);
//					for(int m = 0; m < array3.get(randomNumber1).size(); m++) {
//						int currentPoint = array3.get(randomNumber1).get(m);
//						if(currentPoint == randomNumber11) {
//							array3.get(randomNumber1).set(m, randomNumber22);
//						}
//					}
//					for(int m = 0; m < array3.get(randomNumber2).size(); m++) {
//						int currentPoint = array3.get(randomNumber2).get(m);
//						if(currentPoint == randomNumber22) {
//							array3.get(randomNumber2).set(m, randomNumber11);
//						}
//					}
//					
//					//System.out.println("Swapped");
//					//System.out.println(array3);
//					lArray.set(randomNumber1, localMax1);
//					lArray.set(randomNumber2, localMax2);
//				}
			} else {

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
				//System.out.println(removed);
				ArrayList<Integer> replaceFirst = new ArrayList<Integer>();
				for(int j = 0; j < newFirst.size(); j++) {
					replaceFirst.add(removed.get(newFirst.get(j)));
					for(int l = j + 1; l < newFirst.size(); l++) {
						
						int tempSize = manDistance[removed.get(newFirst.get(j))][removed.get(newFirst.get(l))];
						
						if (tempSize > newFirstL) {
							newFirstL = tempSize;
						}
					}
				}
				
//				int newFirstL = 0;
//				ArrayList<Integer> replaceFirst = new ArrayList<Integer>();
//				for(int j = 0; j < newFirst.size(); j++) {
//					replaceFirst.add(removed.get(newFirst.get(j)));
//					for(int l = j + 1; l < newFirst.size(); l++) {
//						Point tempP1 = pArray.get(removed.get(newFirst.get(j)));
//						Point tempP2 = pArray.get(removed.get(newFirst.get(l)));
//						//System.out.println(tempP1 + " " + tempP2);
//						int tempSize = Math.abs(tempP1.getX() - tempP2.getX()) + Math.abs(tempP1.getY() - tempP2.getY()) + Math.abs(tempP1.getZ() - tempP2.getZ());
//						
//						if (tempSize > newFirstL) {
//							newFirstL = tempSize;
//						}
//					}
//				}
				//System.out.println(newFirstL);
				
				int oldL1 = templArray.get(randomNumber1);
				templArray.set(randomNumber1, newFirstL);
				
				int newSecondL = 0;
				ArrayList<Integer> replaceSecond = new ArrayList<Integer>();
		
					
				for(int j = 0; j < newSecond.size(); j++) {
					replaceSecond.add(removed.get(newSecond.get(j)));
					for(int l = j + 1; l < newSecond.size(); l++) {
						int tempSize = manDistance[removed.get(newSecond.get(j))][removed.get(newSecond.get(l))];
						
						if (tempSize > newSecondL) {
							newSecondL = tempSize;
						}
					}
				}
				int oldL2 = templArray.get(randomNumber2);
				templArray.set(randomNumber2, newSecondL);
				
				//System.out.println(lArray);
				int tempLMax = 0;
				for(int j = 0; j < templArray.size(); j++) {
					if(templArray.get(j) > tempLMax) {
						tempLMax = templArray.get(j);
						maxIndex = j;
					}
				}
				//System.out.println(templArray);
				//System.out.println(maxL + " " + tempLMax);
				//System.out.println(array3);
				//System.out.println(array3Clone);
				
//				int deciding = rand.nextInt(100)+1;
				//int currentP = (int) ((Double.valueOf(fuelCounter)/loopSize)*100);
				//System.out.println(deciding);
//				if((deciding < currentP && tempLMax >= maxL) || (deciding > currentP && tempLMax <= maxL) ) {
				//if((tempLMax <= maxL)) {
				int randomU = rand.nextInt(100);
				if(tempLMax <= maxL) {
					
					
					array3.set(randomNumber2, replaceSecond);
					array3.set(randomNumber1, replaceFirst);
					//System.out.println(currentP);
					//array3 = array3Clone;
					maxL = tempLMax;
					lArray = templArray;
					//System.out.println("flipped");
				}
			}
//			if(randomNumber1 == 0) {
//				//return;
//			}
			
//			if(randomNumber1 == 0) {
//				int testMax = 0;
//				for(int m = 0; m < array3.get(randomNumber1).size(); m++) {
//					int tempM = m;
//					if(array3.get(randomNumber1).get(tempM) == randomNumber11) {
//						tempM = randomNumber22;
//					}
//					for(int h = m + 1; h < array3.get(randomNumber1).size(); h++) {
//						int tempH = h;
//						if(array3.get(randomNumber1).get(tempH) == randomNumber11) {
//							tempH = randomNumber22;
//						}
//						System.out.println("P: " +array3.get(randomNumber1).get(tempM) + array3.get(randomNumber1).get(tempH) + manDistance[array3.get(randomNumber1).get(tempM)][array3.get(randomNumber1).get(tempH)]);
//						if(manDistance[array3.get(randomNumber1).get(tempM)][array3.get(randomNumber1).get(tempH)] > testMax) {
//							testMax = manDistance[array3.get(randomNumber1).get(tempM)][array3.get(randomNumber1).get(tempH)];
//						}
//					}
//					//System.out.println(array3.get(randomNumber1).get(m));
////					int number = array3.get(randomNumber1).get(m);
////					if(m != randomNumber11) {
////						
////					}
//				}
//				
//				System.out.println(testMax);
//				return;
//			}
//			for(int j = 0; j < array3.get(randomNumber1).size(); j++) {
//				removed.add(array3.get(randomNumber1).get(j));
//			}
//			for(int j = 0; j < array3.get(randomNumber2).size(); j++) {
//				removed.add(array3.get(randomNumber2).get(j));
//			}
//			
//			//System.out.println(removed);
//			
//			int newSizeLeft = removed.size();
//			ArrayList<Integer> newTaken = new ArrayList<Integer>();
//			int nR1 = rand.nextInt(removed.size() - 1) + 1;
//			int nR2 = removed.size() - nR1;
//			ArrayList<Integer> newFirst = new ArrayList<Integer>();
//			for(int j = 0; j < nR1; j++) {
//				
//				int r1 = 0;
//				while(true) {
//					r1 = rand.nextInt(removed.size());
//					if(!newTaken.contains(r1)) {
//						newTaken.add(r1);
//						newFirst.add(r1);
//						break;
//					}
//
//				}
//				
//				
//			}
//			ArrayList<Integer> newSecond = new ArrayList<Integer>();
//			for(int j = 0; j < nR2; j++) {
//				
//				int r1 = 0;
//				while(true) {
//					r1 = rand.nextInt(removed.size());
//					if(!newTaken.contains(r1)) {
//						newTaken.add(r1);
//						newSecond.add(r1);
//						break;
//					}
//
//				}	
//			}
//			//System.out.println(newFirst + " " + newSecond);
//			int newFirstL = 0;
//			//System.out.println(removed);
//			ArrayList<Integer> replaceFirst = new ArrayList<Integer>();
//			for(int j = 0; j < newFirst.size(); j++) {
//				replaceFirst.add(removed.get(newFirst.get(j)));
//				for(int l = j + 1; l < newFirst.size(); l++) {
//					
//					int tempSize = manDistance[removed.get(newFirst.get(j))][removed.get(newFirst.get(l))];
//					
//					if (tempSize > newFirstL) {
//						newFirstL = tempSize;
//					}
//				}
//			}
//			
//			int newFirstL = 0;
//			ArrayList<Integer> replaceFirst = new ArrayList<Integer>();
//			for(int j = 0; j < newFirst.size(); j++) {
//				replaceFirst.add(removed.get(newFirst.get(j)));
//				for(int l = j + 1; l < newFirst.size(); l++) {
//					Point tempP1 = pArray.get(removed.get(newFirst.get(j)));
//					Point tempP2 = pArray.get(removed.get(newFirst.get(l)));
//					//System.out.println(tempP1 + " " + tempP2);
//					int tempSize = Math.abs(tempP1.getX() - tempP2.getX()) + Math.abs(tempP1.getY() - tempP2.getY()) + Math.abs(tempP1.getZ() - tempP2.getZ());
//					
//					if (tempSize > newFirstL) {
//						newFirstL = tempSize;
//					}
//				}
//			}
			//System.out.println(newFirstL);
			
//			int oldL1 = templArray.get(randomNumber1);
//			templArray.set(randomNumber1, newFirstL);
//			
//			int newSecondL = 0;
//			ArrayList<Integer> replaceSecond = new ArrayList<Integer>();
//	
//				
//			for(int j = 0; j < newSecond.size(); j++) {
//				replaceSecond.add(removed.get(newSecond.get(j)));
//				for(int l = j + 1; l < newSecond.size(); l++) {
//					int tempSize = manDistance[removed.get(newSecond.get(j))][removed.get(newSecond.get(l))];
//					
//					if (tempSize > newSecondL) {
//						newSecondL = tempSize;
//					}
//				}
//			}
//			int oldL2 = templArray.get(randomNumber2);
//			templArray.set(randomNumber2, newSecondL);
//			
//			//System.out.println(lArray);
//			int tempLMax = 0;
//			for(int j = 0; j < templArray.size(); j++) {
//				if(templArray.get(j) > tempLMax) {
//					tempLMax = templArray.get(j);
//					maxIndex = j;
//				}
//			}
//			//System.out.println(templArray);
//			//System.out.println(maxL + " " + tempLMax);
//			//System.out.println(array3);
//			//System.out.println(array3Clone);
//			
////			int deciding = rand.nextInt(100)+1;
//			//int currentP = (int) ((Double.valueOf(fuelCounter)/loopSize)*100);
//			//System.out.println(deciding);
////			if((deciding < currentP && tempLMax >= maxL) || (deciding > currentP && tempLMax <= maxL) ) {
//			//if((tempLMax <= maxL)) {
//			int randomU = rand.nextInt(100);
//			if(tempLMax <= maxL) {
//				
//				
////				array3.set(randomNumber2, replaceSecond);
////				array3.set(randomNumber1, replaceFirst);
//				//System.out.println(currentP);
//				//array3 = array3Clone;
////				maxL = tempLMax;
////				lArray = templArray;
//				//System.out.println("flipped");
//			}
			//System.out.println("Max:" + maxL);
			//System.out.println(lArray);
			fuelCounter--;
			//System.out.println(currentP + " " + deciding);
			
			
		}
		maxL = 0;
		for(int j = 0; j < lArray.size(); j++) {
			if(lArray.get(j) > maxL) {
				maxL = lArray.get(j);
			}
		}
		System.out.println("Max: " + maxL);
		System.out.println(array3);
		
		
		FileWriter myWriter1 = new FileWriter("1000_points_output.txt");
		myWriter1.write("");
		myWriter1.close();
		FileWriter myWriter = new FileWriter("1000_points_output.txt");
		myWriter.write(maxL+"\n");
		for(int i = 0; i < array3.size(); i++) {
			ArrayList<Integer> current = array3.get(i);
			for(int j = 0; j < current.size(); j++) {
				//System.out.print(current.get(j));
				myWriter.write(String.valueOf(current.get(j) + 1));
				if(j != current.size() - 1) {
					myWriter.write(" ");
					//System.out.print(" ");
				}
			}
			if(i != array3.size() - 1) {
				myWriter.write("\n");
				//System.out.println(" ");
			}
			
		}
		
		myWriter.close();
	}
	
	public static void step2(ArrayList<ArrayList<Integer>> array3, ArrayList<Point> pArray, int[][] manDistance) {
		//System.out.println("Point Array: " + pArray);
		ArrayList<Integer> lArray = new ArrayList<Integer>();
		int maxL = 0;
		int smallestL = Integer.MAX_VALUE;
		int smallestIndex = 0;
		int maxIndex = -1;
		for(int i = 0; i < array3.size(); i++) {
			int localSize = 0;
			int size = array3.get(i).size();
			ArrayList<Integer> tempArray = array3.get(i);
			if(size != 1) {
				for(int j = 0; j < size; j++) {
					for(int l = j + 1; l < size; l++) {
						Point tempP1 = pArray.get(tempArray.get(j));
						Point tempP2 = pArray.get(tempArray.get(l));
						int tempSize = Math.abs(tempP1.getX() - tempP2.getX()) + Math.abs(tempP1.getY() - tempP2.getY()) + Math.abs(tempP1.getZ() - tempP2.getZ());
						if (tempSize > localSize) {
							localSize = tempSize;
						}
					}
				}
			}
			lArray.add(localSize);
			//System.out.println(localSize);
			if(localSize > maxL) {
				maxL = localSize;
				maxIndex = i;
			} else if (localSize < smallestL) {
				smallestL = localSize;
				smallestIndex = i;
			}
		}
		System.out.println("L Array: " +lArray + " " + maxIndex + " " + lArray.get(maxIndex));
		
		
		long loopSize = 2000000;
		long fuelCounter = 1;
		System.out.println("Entering loop");
		Random rand = new Random();
		for(int i = 0; i < loopSize; i++) {
			
			//System.out.println("Iteration: " + i);
			
			ArrayList<Integer> removed = new ArrayList<Integer>();
			ArrayList<Integer> templArray = (ArrayList<Integer>) lArray.clone();
			int randomNumber1 = rand.nextInt(lArray.size());
			//randomNumber1 = maxIndex;
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
			//System.out.println(removed);
			ArrayList<Integer> replaceFirst = new ArrayList<Integer>();
			for(int j = 0; j < newFirst.size(); j++) {
				replaceFirst.add(removed.get(newFirst.get(j)));
				for(int l = j + 1; l < newFirst.size(); l++) {
					
					int tempSize = manDistance[removed.get(newFirst.get(j))][removed.get(newFirst.get(l))];
					
					if (tempSize > newFirstL) {
						newFirstL = tempSize;
					}
				}
			}
			
//			int newFirstL = 0;
//			ArrayList<Integer> replaceFirst = new ArrayList<Integer>();
//			for(int j = 0; j < newFirst.size(); j++) {
//				replaceFirst.add(removed.get(newFirst.get(j)));
//				for(int l = j + 1; l < newFirst.size(); l++) {
//					Point tempP1 = pArray.get(removed.get(newFirst.get(j)));
//					Point tempP2 = pArray.get(removed.get(newFirst.get(l)));
//					//System.out.println(tempP1 + " " + tempP2);
//					int tempSize = Math.abs(tempP1.getX() - tempP2.getX()) + Math.abs(tempP1.getY() - tempP2.getY()) + Math.abs(tempP1.getZ() - tempP2.getZ());
//					
//					if (tempSize > newFirstL) {
//						newFirstL = tempSize;
//					}
//				}
//			}
			//System.out.println(newFirstL);
			
			int oldL1 = templArray.get(randomNumber1);
			templArray.set(randomNumber1, newFirstL);
			
			int newSecondL = 0;
			ArrayList<Integer> replaceSecond = new ArrayList<Integer>();
	
				
			for(int j = 0; j < newSecond.size(); j++) {
				replaceSecond.add(removed.get(newSecond.get(j)));
				for(int l = j + 1; l < newSecond.size(); l++) {
					int tempSize = manDistance[removed.get(newSecond.get(j))][removed.get(newSecond.get(l))];
					
					if (tempSize > newSecondL) {
						newSecondL = tempSize;
					}
				}
			}
			int oldL2 = templArray.get(randomNumber2);
			templArray.set(randomNumber2, newSecondL);
			
			//System.out.println(lArray);
			int tempLMax = 0;
			for(int j = 0; j < templArray.size(); j++) {
				if(templArray.get(j) > tempLMax) {
					tempLMax = templArray.get(j);
					maxIndex = j;
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
			//if((tempLMax <= maxL)) {
			int randomU = rand.nextInt(100);
			if(tempLMax <= maxL) {
				
				
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
			fuelCounter++;
			//System.out.println(currentP + " " + deciding);
			
			
		}
		System.out.println("Max: " + maxL);
		System.out.println(array3);
	}
	
	public static double distanceBetweenPoints(Point p1, Point p2) {
		return Math.pow((p1.getX() - p2.getX()),2) + Math.pow((p1.getY() - p2.getY()),2) + Math.pow((p1.getZ() - p2.getZ()),2);
	}
	
	public static double distanceBetweenNewPivot(Point potentialPivot, ArrayList<Point> array) {
		double overallDistance = 0;
		for(int i = 0; i < array.size(); i++) {
			overallDistance += distanceBetweenPoints(potentialPivot, array.get(i));
		}
		
		
		
		//return Math.pow((p1.getX() - p2.getX()),2) + Math.pow((p1.getY() - p2.getY()),2) + Math.pow((p1.getZ() - p2.getZ()),2);
		return overallDistance;
	}
	
	public static double manDistanceBetweenNewPivot(Point potentialPivot, ArrayList<Point> array) {
		double overallDistance = 0;
		for(int i = 0; i < array.size(); i++) {
			overallDistance += manDistance(potentialPivot, array.get(i));
		}
		
		
		
		//return Math.pow((p1.getX() - p2.getX()),2) + Math.pow((p1.getY() - p2.getY()),2) + Math.pow((p1.getZ() - p2.getZ()),2);
		return overallDistance;
	}
	
	public static int manDistance(Point p1, Point p2) {
		return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()) + Math.abs(p1.getZ() - p2.getZ());
	}

}

/*System.out.println(distanceBetweenPoints(pArray.get(0), pArray.get(0)));
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				ArrayList<Point> tempArray = new ArrayList<Point>(2);
				tempArray.add(pArray.get(i));
				tempArray.add(pArray.get(j));
				distance[i][j] = distanceBetweenPoints(tempArray);
			}
		}
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(distance[i][j] + " ");
			}
			System.out.println(" ");
		}
		
		
	}
	
	
	public static double distanceBetweenPoints(ArrayList<Point> input) {
		
		double distance = 0;
		for()
		return Math.pow((p1.getX() - p2.getX()),2) + Math.pow((p1.getY() - p2.getY()),2) + Math.pow((p1.getZ() - p2.getZ()),2);
	}
*/
