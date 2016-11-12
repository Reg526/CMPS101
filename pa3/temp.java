// Constructor
Matrix(int n) // Makes a new n x n zero Matrix. pre: n>=1

// Access functions
int getSize() // Returns n, the number of rows and columns of this Matrix
int getNNZ() // Returns the number of non-zero entries in this Matrix
public boolean equals(Object x) // overrides Object's equals() method

// Manipulation procedures
void makeZero() // sets this Matrix to the zero state
Matrix copy()// returns a new Matrix having the same entries as this Matrix
void changeEntry(int i, int j, double x)
	// changes ith row, jth column of this Matrix to x
	// pre: 1<=i<=getSize(), 1<=j<=getSize()
Matrix scalarMult(double x)
	// returns a new Matrix that is the scalar product of this Matrix with x
Matrix add(Matrix M)
	// returns a new Matrix that is the sum of this Matrix with M
	// pre: getSize()==M.getSize()
Matrix sub(Matrix M)
	// returns a new Matrix that is the difference of this Matrix with M
	// pre: getSize()==M.getSize()
Matrix transpose()
	// returns a new Matrix that is the transpose of this Matrix
Matrix mult(Matrix M)
	// returns a new Matrix that is the product of this Matrix with M
	// pre: getSize()==M.getSize()

// Other functions
public String toString() // overrides Object's toString() method