package com.redmart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SkiChallenge {
	static List<Point> nonEligibleList = new ArrayList<>();
	static int max = -1;
	static int maxDrop =-1;
	static int startX =-1;
	static int startY = -1;
	static int depth = -1;
	static int[][] inputArray;
	//static List<Integer> entries;
	static int rowSize;
	static int columnSize;
	static List<Point> entries = new ArrayList<>();
	static List<Point> currentEntries = new ArrayList<>();

	
	static class Point{
		int x;
		int y;
		
		Point(int x,int y){
			this.x =x;
			this.y= y; 
		}
		public boolean equals(Object obj){
			if(obj instanceof Point) {
				if(((Point) obj).x == this.x && ((Point) obj).y == this.y) {
					return true;
				}
			}
			return false;
			
		}
	}
	
	public static void getLongestPath(){
		for(int i=0;i<inputArray.length;i++){
				for (int j=0;j<inputArray.length;j++){
					if(nonEligibleList.contains(new Point(i,j))){
						continue;
					}
					int depth = checkLongestPath(i,j);
					if(max < depth){
						max = depth;
						startX = i;
						startY = j;
						entries.clear();
						currentEntries.clear();
						maxDrop = -1;
					} else if (max == depth){
						entries.clear();
						currentEntries.clear();
						entries = findPath(startX,startY);
						currentEntries = findPath(i,j);
						int startDrop= inputArray[entries.get(entries.size() -1).x][entries.get(entries.size() -1).y] - inputArray[entries.get(0).x][entries.get(0).y];
						int currentDrop= inputArray[currentEntries.get(currentEntries.size() -1).x][currentEntries.get(currentEntries.size() -1).y] - inputArray[currentEntries.get(0).x][currentEntries.get(0).y];
						if(startDrop > currentDrop){
							maxDrop = startDrop;
						}else {
							startX = i;
							startY = j;
							entries.clear();
							maxDrop = currentDrop;
							entries.addAll(currentEntries);
						}
					}
				}
			}
			if(!entries.isEmpty()){
				System.out.println(max +"--"+maxDrop);
			}
			else{
				entries = findPath(startX,startY);
				if(!entries.isEmpty()){
					maxDrop= inputArray[entries.get(0).x][entries.get(0).y] - inputArray[entries.get(entries.size() -1).x][entries.get(entries.size() -1).y];
				}
				System.out.println(max +"--"+ maxDrop);
			}
	}
	
	/*private static void printElements(List<Point> list) {
		String s = new String();
		for(Point point: list){
			s = s.concat(inputArray[point.x][point.y]+"-");
		}
		s= s.substring(0,s.length()-2);
		System.out.println(s.concat("."));
	}*/

	private static List<Point> findPath(int i, int j) {
		List<Point> localList = new ArrayList<>();
		if(i+1 < rowSize){
			if(inputArray[i][j] > inputArray[i+1][j]) {
				List<Point> newList = findPath(i+1,j);
				if(newList.size() > localList.size())
					localList = newList;
			}
		}
		if(i-1 >=0) {
			if(inputArray[i][j] > inputArray[i-1][j]){
				List<Point> newList = findPath(i-1,j);
				if(newList.size() > localList.size())
					localList = newList;
			}
		}
		if(j+1 < columnSize) {
			if(inputArray[i][j] > inputArray[i][j+1]){
				List<Point> newList = findPath(i,j+1);
				if(newList.size() > localList.size())
					localList = newList;
			}
		}
		if(j-1  >=0){
			if(inputArray[i][j] > inputArray[i][j-1]){
				List<Point> newList = findPath(i,j-1);
				if(newList.size() > localList.size())
					localList = newList;
			}
		}
		localList.add(new Point(i,j));
		return localList;
	}

	private static int checkLongestPath(int i, int j) {
		
		int count = 0;
		boolean eligible = false;
		if(i+1 < rowSize){
			if(inputArray[i][j] > inputArray[i+1][j]) {
				eligible = true;
				int newCount = checkLongestPath(i+1,j);
				if(newCount > count)
					count = newCount;
			}
		}
		if(i-1 >=0) {
			if(inputArray[i][j] > inputArray[i-1][j]){
				eligible = true;
				int newCount = checkLongestPath(i-1,j);
				if(newCount > count)
					count = newCount;
			}
		}
		if(j+1 < columnSize) {
			if(inputArray[i][j] > inputArray[i][j+1]){
				eligible = true;
				int newCount = checkLongestPath(i,j+1);
				if(newCount > count)
					count = newCount;
			}
		}
		if(j-1  >=0){
			if(inputArray[i][j] > inputArray[i][j-1]){
				eligible = true;
				int newCount = checkLongestPath(i,j-1);
				if(newCount > count)
					count = newCount;
			}
		}
		if(!eligible){
			nonEligibleList.add(new Point(i,j));
		}
		return count + 1;
	 }
	
  
	public static void main(String[]  args){
		Scanner sc = new Scanner(System.in);
		String firstLine = sc.nextLine();
		String[] sizeArray = firstLine.split(" ");
		rowSize= Integer.parseInt(sizeArray[0]);
		columnSize= Integer.parseInt(sizeArray[1]);
		
		inputArray = new int[rowSize][columnSize];
		for(int i=0;i<rowSize;i++){
			for (int j=0;j<columnSize;j++){
				inputArray[i][j]= sc.nextInt();
			}
			sc.nextLine();
		}
		getLongestPath();
	}

}
