//# Reginald Chand
//# rzchand
//# CMPS 101

// Sparse.java
// takes two command line arguments, reads the input file and build and Array of Lists
// then calculates the following to the output file: A, B, (1.5)A, A+B

import java.io.*;
import java.util.Scanner;
import java.io.PrintWriter;

public class Sparse{
	public static void main(String[] args) throws IOException{
		
		if(args.length != 2){
			System.err.println("Usage: Sparse input_file output_file");
			System.exit(1);
		}
		
		Scanner in  = new Scanner(new File(args[0]));
		
		String line;
		String[] token = new String[3];
		int firstLine = 0;
		int dimN = 0;
		// Matrix a non-zero entries
		int aNNZ = 0;
		// Matrix b non-zero entries
		int bNNZ = 0;
		
		int row, column, i;
		double value;
		
		// intialize matrix
		Matrix A = new Matrix(1);
		Matrix B = new Matrix(1);
		
		i = 0;
		while(in.hasNextLine()){
			// this only runs once to get the values on the first line
			if(firstLine == 0){
				line = in.nextLine().trim() + " ";
				token = line.split("\\s+");
				dimN = Integer.parseInt(token[0]);
				aNNZ = Integer.parseInt(token[1]);
				bNNZ = Integer.parseInt(token[2]);
				A = new Matrix(dimN);
				B = new Matrix(dimN);
				firstLine++;
			}
			// this only runs when i < non-zero entries of A
			else if(i < aNNZ){
				line = in.nextLine().trim() + " ";
				token = line.split("\\s+");
				
				if(token.length == 3){
					row = Integer.parseInt(token[0]);
					column = Integer.parseInt(token[1]);
					value = Double.parseDouble(token[2]);
					//System.out.println(row + " "+column+" "+value);
					A.changeEntry(row, column, value);
					i++;
				}
			}
			// this runs for the rest of the input file lines to get Matrix B
			else{
				line = in.nextLine().trim() + " ";
				token = line.split("\\s+");
				
				if(token.length == 3){
					row = Integer.parseInt(token[0]);
					column = Integer.parseInt(token[1]);
					value = Double.parseDouble(token[2]);
					B.changeEntry(row, column, value);
				}
			}
		}
		in.close();
		
		PrintWriter out = new PrintWriter(new FileWriter(args[1]));
		
		out.println("A has "+aNNZ+" non-zero entries:");
		out.println(A);
		out.println("B has "+bNNZ+" non-zero entries:");
		out.println(B);
		
		out.println("(1.5)*A =");
		out.println(A.scalarMult(1.5));
		
		out.println("A+B =");
		out.println(A.add(B));
		
		out.println("A+A =");
		out.println(A.add(A));
		
		out.println("B-A =");
		out.println(B.sub(A));
		
		out.println("A-A =");
		out.println(A.sub(A));
		
		out.println("Transpose(A) =");
		out.println(A.transpose());
		
		out.println("A*B =");
		out.println(A.mult(B));
		
		out.println("B*B =");
		out.println(B.mult(B));
		
		out.close();
		
	}
} 
