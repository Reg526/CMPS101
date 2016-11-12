//# Reginald Chand
//# rzchand
//# CMPS 101

// Matrix.java
// Matrix ADT

public class Matrix{
	private class Entry{
		int column;
		double value;
		
		Entry(int x, double y){
			column = x;
			value = y;
		}
		
		// checks if entries are equal
		public boolean equals(Object x){
			boolean eq = false;
			if(x instanceof Entry){
				Entry E = (Entry) x;
				if(this.column == E.column){
					if(this.value == E.value){
						eq = true;
					}
				}
			}
			return eq;
		}
		
		// converts Entry to string
		public String toString(){
			return ("(" + String.valueOf(column) + ", " + String.valueOf(value) + ")");
		}
	}
	
	private List[] arrayOfLists;
	private int size; // the number of rows and columns in the n x n Matrix
	private int NNZ;
	
	private static double dot(List P, List Q){

		Entry p, q;

		double result = 0;
	
		P.moveFront();
		Q.moveFront();
		
		while(P.index() != -1 && Q.index() != -1){
			p = (Entry) P.get();
			q = (Entry) Q.get();
			
			if(p.column < q.column){
				P.moveNext();
			}
			else if(p.column > q.column){
				Q.moveNext();
			}
			else{
				// for matrix A B: a1*b1 + a2*b2...
				result = result + p.value*q.value;
				P.moveNext();
				Q.moveNext();
			}
		}
		
		return result;
	}
	
	// constructor
	Matrix(int n){
		if(n < 1){
			throw new RuntimeException("Matrix ADT Error: Trying to construct n x n Matrix where n < 1");
		}
		
		// creates n rows for the Matrix
		// we use n+1 since we ignore row 0
		arrayOfLists = new List[n + 1];
		
		// stores size of array in size
		size = n;
	}
	
	// Access functions 
	int getSize(){
		return size;
	}
	
	int getNNZ(){
		return NNZ;
	}
	
	public boolean equals(Matrix A){
		List L;
		boolean eq = true;
		
		for(int i = 1; i <= size; i++){
			L = A.arrayOfLists[i];
			
			if(L != null && this.arrayOfLists[i] != null){
				eq = L.equals(arrayOfLists[i]);
				if(eq == false){
					return false;
				}
			}

		}
		return eq;
	}
	
	// Manipulation procedures
	void makeZero(){
		for(int i = 1; i <= size; i++){
			if(this.arrayOfLists[i] != null){
				this.arrayOfLists[i] = null;
			}
		}
		this.NNZ = 0;
	}
	
	Matrix copy(){
		Matrix M = new Matrix(this.size);
		List L;
		Entry e;
		
		for(int i = 1; i <= size; i++){
		
			L = this.arrayOfLists[i];
			
			// so this doesn't run through Lists that aren't initialized
			// for example rows consisting of only 0's or aren't intialized
			if(L != null){
				M.arrayOfLists[i] = new List();
				// run through each row and add entries to the new matrix
				L.moveFront();
				while(L.index() != -1){
					e = (Entry) L.get();
					M.arrayOfLists[i].append(new Entry(e.column, e.value));
					M.NNZ++;
					L.moveNext();
				}
			}
		}
		return M;
	}
	
	void changeEntry(int i, int j, double x){
		if(i < 1 || i > size){
			throw new RuntimeException("Matrix ADT Error: row i is outside of Matrix dimensions");
		}
		if(j < 1 || j > size){
			throw new RuntimeException("Matrix ADT Error: column i is outside of Matrix dimensions");
		}
		
		// we need a sparsed matrix, so we don't need to add in 0's
		if(x != 0){
			Entry e, c;
			// gets the row i and stores it in List L
			List L = arrayOfLists[i];
			
			if(L == null){
				L = new List();
				e = new Entry(j, x);
				L.append(e);
				NNZ++;
			}
			else{
				L.moveFront();
				
				// moves cursor to the right column in the list
				e = (Entry) L.get();
				while(e.column < j && L.index() != -1){
					L.moveNext();
					if(L.index() != -1){
						e = (Entry) L.get();
					}
				}
				// creates new Entry with the new value and inserts before the cursor
				e = new Entry(j, x);
				
				// if cursor == -1 you've reached the end of the list and new entry must be appended
				if(L.index() == -1){
					L.append(e);
					NNZ++;
				}
				else{
					L.insertBefore(e);
					NNZ++;	
				}
			}
			arrayOfLists[i] = L;
		}
	}
	
	Matrix scalarMult(double x){
		List L;
		Entry e;
		Matrix A = new Matrix(size);
		
		for(int i = 1; i <= size; i++){
			L = arrayOfLists[i];
			// Matrix can't have blank rows, so we can break out of the loop
			if(L == null){
				break;
			}
			
			A.arrayOfLists[i] = new List();
			
			L.moveFront();
			// scrolls through each entry of the List
			while(L.index() != -1){
				e = (Entry) L.get();
				// insert new Entry into new Matrix
				A.arrayOfLists[i].append(new Entry(e.column, e.value*x));
				// increment Non-zero elements
				A.NNZ++;
				L.moveNext();	
			}
		}
		return A;
	}
	
	Matrix add(Matrix M){
		// checks if Matrices are the same size or they can't be added
		if(size != M.getSize()){
			throw new RuntimeException("Matrix ADT Error: illegal Matrix dimensions");
		}
		
		double result;
		Entry a,b;
		
		// where this Matrix + M will be stored
		Matrix N;
		
		// if Matrix A = Matrix B, then scalMult by 2
		if(this.equals(M)){
			N = this.scalarMult(2.0);
		}
		else{
			N = new Matrix(this.size);
			
			for(int i = 1; i <= size; i++){
				if(this.arrayOfLists[i] != null && M.arrayOfLists[i] != null){
					// resets cursor to the front of each new row
					this.arrayOfLists[i].moveFront();
					M.arrayOfLists[i].moveFront();
					
					// new row for results
					N.arrayOfLists[i] = new List();
					
					// this loop run through each row and adds entries
					while(this.arrayOfLists[i].index() != -1 && M.arrayOfLists[i].index() != -1){
						a = (Entry) this.arrayOfLists[i].get();
						b = (Entry)	M.arrayOfLists[i].get();
						
						// this can assumed that b's column's value is 0, so a is append directly
						if(a.column < b.column){
							N.arrayOfLists[i].append(new Entry(a.column,a.value));
							N.NNZ++;
							this.arrayOfLists[i].moveNext();
						}
						else if(a.column > b.column){
							N.arrayOfLists[i].append(new Entry(b.column,b.value));
							N.NNZ++;
							M.arrayOfLists[i].moveNext();
						}
						// if columns are the same we can add the entries
						else{
							result = a.value + b.value;
							N.arrayOfLists[i].append(new Entry(a.column, result));
							N.NNZ++;
							this.arrayOfLists[i].moveNext();
							M.arrayOfLists[i].moveNext();
						}
					}
			
				
					// we have to fill in for the 0's for the left over Entries in M
					while(this.arrayOfLists[i].index() == -1 && M.arrayOfLists[i].index() != -1){
						b = (Entry) M.arrayOfLists[i].get();
						N.arrayOfLists[i].append(new Entry(b.column,b.value));
						M.arrayOfLists[i].moveNext();
					}
					// fills in left over entries for this Matrix
					while(this.arrayOfLists[i].index() != -1 && M.arrayOfLists[i].index() == -1){
						a = (Entry)this.arrayOfLists[i].get();
						N.arrayOfLists[i].append(new Entry(a.column,a.value));
						this.arrayOfLists[i].moveNext();
					}
				}
				else{
					if(this.arrayOfLists[i] == null && M.arrayOfLists[i] != null){
						if(N.arrayOfLists[i] == null){
							N.arrayOfLists[i] = new List();
						}
						
						M.arrayOfLists[i].moveFront();
						while(M.arrayOfLists[i].index() != -1){
							b = (Entry) M.arrayOfLists[i].get();
							N.arrayOfLists[i].append(new Entry(b.column, b.value));	
							M.arrayOfLists[i].moveNext();
						}
					}
					else if(this.arrayOfLists[i] != null && M.arrayOfLists[i] == null){
						if(N.arrayOfLists[i] == null){
							N.arrayOfLists[i] = new List();
						}
						
						this.arrayOfLists[i].moveFront();
						while(this.arrayOfLists[i].index() != -1){
							a = (Entry) this.arrayOfLists[i].get();
							N.arrayOfLists[i].append(new Entry(a.column, a.value));
							this.arrayOfLists[i].moveNext();
						}
					}
				}
			}
		}
		return N;
	}
	
	
	Matrix sub(Matrix M){
		// checks if Matrices are the same size or they can't be added
		if(size != M.getSize()){
			throw new RuntimeException("Matrix ADT Error: illegal Matrix dimensions");
		}
		
		double result;
		Entry a,b;
		
		// where this Matrix - M will be stored
		Matrix N;
		
		// if Matrix A = Matrix B, then zero the matrix
		if(this.equals(M)){
			N = this.copy();
			N.makeZero();
			
		}
		else{
			N = new Matrix(this.size);
			
			for(int i = 1; i <= size; i++){
				if(this.arrayOfLists[i] != null && M.arrayOfLists[i] != null){
					// resets cursor to the front of each new row
					this.arrayOfLists[i].moveFront();
					M.arrayOfLists[i].moveFront();
					
					// new row for results
					N.arrayOfLists[i] = new List();
					
					// this loop run through each row and adds entries
					while(this.arrayOfLists[i].index() != -1 && M.arrayOfLists[i].index() != -1){
						a = (Entry) this.arrayOfLists[i].get();
						b = (Entry)	M.arrayOfLists[i].get();
						
						// this can assumed that b's column's value is 0, so a is append directly
						if(a.column < b.column){
							N.arrayOfLists[i].append(new Entry(a.column, a.value));
							N.NNZ++;
							this.arrayOfLists[i].moveNext();
						}
						// this is 0 - b = -b
						else if(a.column > b.column){
							N.arrayOfLists[i].append(new Entry(b.column, (-1)*b.value));
							N.NNZ++;
							M.arrayOfLists[i].moveNext();
						}
						// if columns are the same we can sub the entries
						else{
							result = a.value - b.value;
							if(result != 0.0){
								N.arrayOfLists[i].append(new Entry(a.column, result));
								N.NNZ++;
							}
								this.arrayOfLists[i].moveNext();
								M.arrayOfLists[i].moveNext();		
						}
					}
					
					// we have to fill in for the 0's for the left over Entries in M
					// since this is sub this loop does 0 - b = -b hence the -1 multiplication
					while(this.arrayOfLists[i].index() == -1 && M.arrayOfLists[i].index() != -1){
						b = (Entry) M.arrayOfLists[i].get();
						N.arrayOfLists[i].append(new Entry(b.column, -1*b.value));
						M.arrayOfLists[i].moveNext();
					}
					
					while(this.arrayOfLists[i].index() != -1 && M.arrayOfLists[i].index() == -1){
						a = (Entry)this.arrayOfLists[i].get();
						N.arrayOfLists[i].append(new Entry(a.column, a.value));
						this.arrayOfLists[i].moveNext();
					}
				}
				else{
					if(this.arrayOfLists[i] == null && M.arrayOfLists[i] != null){
						if(N.arrayOfLists[i] == null){
							N.arrayOfLists[i] = new List();
						}
						
						M.arrayOfLists[i].moveFront();
						while(M.arrayOfLists[i].index() != -1){
							b = (Entry) M.arrayOfLists[i].get();
							N.arrayOfLists[i].append(new Entry(b.column, -1*b.value));	
							M.arrayOfLists[i].moveNext();
						}
					}
					else if(this.arrayOfLists[i] != null && M.arrayOfLists[i] == null){
						if(N.arrayOfLists[i] == null){
							N.arrayOfLists[i] = new List();
						}
						
						this.arrayOfLists[i].moveFront();
						while(this.arrayOfLists[i].index() != -1){
							a = (Entry) this.arrayOfLists[i].get();
							N.arrayOfLists[i].append(new Entry(a.column, a.value));
							this.arrayOfLists[i].moveNext();
						}
					}
				}
				// check if lists are empty and removes them
				if(N.arrayOfLists[i] != null){
					if(N.arrayOfLists[i].length() == 0){
						N.arrayOfLists[i] = null;
					}
				}
			}
		}
		return N;
	}
	
	Matrix transpose(){
		List L;
		Entry e;
		Matrix M = new Matrix(this.size);
		
		for(int i = 1; i <= this.size; i++){
			L = this.arrayOfLists[i];
			
			if(L != null){
				L.moveFront();
				while(L.index() != -1){
					e = (Entry) L.get();
					// switch row and column & initialize list if needed
					if(M.arrayOfLists[e.column] == null){
						M.arrayOfLists[e.column] = new List();
					}
					M.arrayOfLists[e.column].append(new Entry(i,e.value));
					M.NNZ++;
					L.moveNext();
				}
			}
		}
		return M;
	}
	
	Matrix mult(Matrix M){
		if(this.size != M.size){
			throw new RuntimeException("Matrix ADT Error: calling mult(Matrix M) with illegal matrix dimensions");
		}
		
		// transpose matrix for easier multiplaction
		Matrix transM = M.transpose();
		// where the new matrix answer will be stored
		Matrix ansM = new Matrix(size);
		List L1, L2, L3;
		double result = 0;
		
		// outer loop keeps track of the rows for the solution Matrix
		for(int i = 1; i <= size; i++){
			L1 = this.arrayOfLists[i];
			if(L1 != null){
				ansM.arrayOfLists[i] = new List();
				L3 = ansM.arrayOfLists[i];
				// inner loop keeps track of the columns for the solution Matrix
				for(int j = 1; j <= size; j++){
					L2 = transM.arrayOfLists[j];
					if(L2 != null){
						// dot product for the two list
						result = dot(L1,L2);
						// we want sparse matrix, so we don't add 0's
						if(result != 0){
							L3.append(new Entry(j, result));
							ansM.NNZ++;
						}
					}
				}
			}
			// check if lists are empty and removes them
			if(ansM.arrayOfLists[i] != null){
				if(ansM.arrayOfLists[i].length() == 0){
					ansM.arrayOfLists[i] = null;
				}
			}
		}
		
		return ansM;
	}
	
	public String toString(){
		Entry e;
		List L;
		String temp = "";
		
		for(int i = 1; i <= size; i++){
			L = arrayOfLists[i];
			if(L != null){
				temp += String.valueOf(i) + ": ";
				L.moveFront();
				while(L.index() != -1){
					e = (Entry) L.get();
					temp += e.toString();
					L.moveNext();
				}
				temp += "\n";
			}
		}
		return temp;
	}
}