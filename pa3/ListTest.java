//# Reginald Chand
//# rzchand
//# CMPS 101

// ListTest.java
// Testing module for List ADT

//-----------------------------------------------------------------------------
//  ListClient.java
//  A test client for the List ADT. Use this to test your list module. The
//  correct output is given below.
//-----------------------------------------------------------------------------

public class ListTest{
	private static class Val{
		int value;
		
		Val(int x){
			value = x;
		}
		
		// checks if entries are equal
		public boolean equals(Object x){
			boolean eq = false;
			if(x instanceof Val){
				Val E = (Val) x;
				if(this.value == E.value){
					eq = true;
				}
				
			}
			return eq;
		}
		
		// converts Val to string
		public String toString(){
			return (String.valueOf(value));
		}
	}
	
   public static void main(String[] args){
      List A = new List();
      List B = new List();
      
      for(int i=1; i<=20; i++){
         A.append(new Val(i));
         B.prepend(new Val(i));
      }
      System.out.println(A);
      System.out.println(B);
      
      for(A.moveFront(); A.index()>=0; A.moveNext()){
         System.out.print(A.get()+" ");
      }
      System.out.println();
      for(B.moveBack(); B.index()>=0; B.movePrev()){
         System.out.print(B.get()+" ");
      }
      System.out.println();
      
      List C = A.copy();
      System.out.println(A.equals(B));
      System.out.println(B.equals(C));
      System.out.println(C.equals(A));
      
      A.moveFront();
      for(int i=0; i<5; i++) A.moveNext(); // at index 5
      A.insertBefore(new Val(-1));                  // at index 6
      for(int i=0; i<9; i++) A.moveNext(); // at index 15
      A.insertAfter(new Val(-2));
      for(int i=0; i<5; i++) A.movePrev(); // at index 10
      A.delete();
      System.out.println(A);
      System.out.println(A.length());
      A.clear();
      System.out.println(A.length());
   }
}

// Output of this program:
// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
// 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
// false
// false
// true
// 1 2 3 4 5 -1 6 7 8 9 11 12 13 14 15 -2 16 17 18 19 20
// 21
// 0


