//# Reginald Chand
//# rzchand
//# CMPS 101

// Lex.java
// Takes two command line arguments giving the names of an input file and an output file
// and arranges the input file in alphabetical order in the output file

import java.io.*;
import java.util.Scanner;
import java.io.PrintWriter;

public class Lex{
	public static void main(String[] args) throws IOException{
		
		if(args.length != 2){
			System.err.println("Usage: Lex input_file output_file");
			System.exit(1);
		}
		int i=0 ,j=0, n =0;

		String line = null;
		String[] token = new String[1];
		int lineNumber = 0;
		
		Scanner scanNumOfLines = new Scanner(new File(args[0]));
		// used to find the number of lines for file
		while(scanNumOfLines.hasNextLine()){
			lineNumber++;
			scanNumOfLines.nextLine();
		}
		scanNumOfLines.close();
		
		String[] inputLine = new String[lineNumber];
		String[] outputLine = new String[lineNumber];
		
		Scanner in = new Scanner(new File(args[0]));
		PrintWriter out = new PrintWriter(new FileWriter(args[1]));
		
		while(in.hasNextLine()){
			line = in.nextLine()+" ";    // add extra space so split works right
			token = line.split("\\s+");  // split line around white space
			inputLine[i] = token[0];
			i++;
		}
		
		List A = new List();
		//initialize list
		A.append(0);
		// uses insertion sort algorithm to add elements sorted to linked list
		for(i = 1; i < lineNumber; i++){
			String temp = inputLine[i];
			j = i - 1;
			// reset list to start at the back of the list
			A.moveBack();
			// move cursor back when comparison is true
			n = A.get();
			while(j >= 0 && temp.compareTo(inputLine[n]) <= 0){
				--j;
				A.movePrev();	
			}
			// index is still in list, so element is added after
			if(A.index() >= 0){
				A.insertAfter(i);
			}
			// for null cursor
			else{
				A.prepend(i);
			}
		}
		// prints linked list elements to output file
		A.moveFront();
		for(i = 0; i < lineNumber; i++){
			out.println(inputLine[A.get()]);
			A.moveNext();
		}
		in.close();
		out.close();
	}
}