//# Reginald Chand
//# rzchand
//# CMPS 101

// List.java
// A bi-directional queue List ADT

public class List{
	private class Node{
		Object item;
		Node next;
		Node prev;
		
		Node(Object x){
			item = x;
			next = null;
			prev = null;
		}
		
		public boolean equals(Object x){
		   boolean eq = false;
		   Node N;
		   
		   if(x instanceof Node){
			  N = (Node)x;
			  eq = this.item.equals(N.item);
		   }
		   
		   return eq;
		}
	}

	private Node front; // reference to the first Node in the List
	private Node back; // reference to the last Node in the List
	private Node cursor; 
	private int numItems;
	private int cursIndx;

	private boolean isEmpty(){
		return numItems == 0;
	}
	// constructor for List class
	List(){
		front = null;
		back = null;
		cursor = null;
		cursIndx = -1;
		numItems = 0;
	}
	
	// Access functions -------------------------------
	// Returns the number of elements in this List.
	int length(){
		return numItems;
	}
	// If cursor is defined, returns the index of the cursor element,
	// otherwise returns -1.
	int index(){
		if(cursor == null){
			return -1;
		}
		return cursIndx;
	}
	// Returns front element. Pre: length()>0
	Object front(){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling front() on empty list");
		}

		return front.item;
	
	}
	// Returns back element. Pre: length()>0
	Object back(){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling front() on empty list");
		}
		
		return back.item;
	}
	// Returns cursor element. Pre: length()>0, index()>=0
	Object get(){
		if(cursIndx < 0){
			throw new RuntimeException("List ADT Error: calling get() while cursor is undefined");
		}
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling get() on an empty list");
		}
		return cursor.item;
	}

	// Returns true if this List and L are the same integer
	// sequence. The cursor is ignored in both lists.
	public boolean equals(List L){
		if(L == null){
			throw new RuntimeException("List ADT Error: List is undefined");
		}
	
		Node temp = front;
		Node temp2 = L.front;
		
		while(temp != null && temp2 != null){
			// if the sequence does not match it'll return false
			if(!temp.equals(temp2)){
				return false;
			}
			
			temp = temp.next;
			temp2 = temp2.next;
		}
		return true;
	}
	
	// Manipulation procedures
	// Resets this List to its original empty state.
	void clear(){
		if(this != null){
			front = null;
			back = null;
			cursor = null;
			cursIndx = -1;
			numItems = 0;
		}
	}
	// If List is non-empty, places the cursor under the front element,
	// otherwise does nothing.
	void moveFront(){
		if(!(this.isEmpty())){
			cursor = front;
			cursIndx = 0;
		}
	}
	// If List is non-empty, places the cursor under the back element,
	// otherwise does nothing.
	public void moveBack(){
		if(!(this.isEmpty())){
			cursor = back;
			cursIndx = numItems - 1;
		}
	}
	// If cursor is defined and not at front, moves cursor one step toward
	// front of this List, if cursor is defined and at front, cursor becomes
	// undefined, if cursor is undefined does nothing.
	void movePrev(){
		// if cursor == front
		if(cursIndx == 0 && cursor != null){
			cursor = null;
			cursIndx = -1;
		}
		// if cursor > front
		else if(cursIndx > 0 && cursor != null){
			cursor = cursor.prev;
			cursIndx--;
		}
	}
	// If cursor is defined and not at back, moves cursor one step toward 
	// back of this List, if cursor is defined and at back, cursor becomes
	// undefined, if cursor is undefined does nothing.
	void moveNext(){
	  if(cursor != null && cursIndx != numItems - 1) {
         cursor = cursor.next;
         cursIndx++;
      }
      else if(cursor != null && cursIndx == numItems - 1) {
         cursor = null;
         cursIndx = -1;
      }
	}
	// Insert new element into this List. If List is non-empty,
	// insertion takes place before front element.
	void prepend(Object data){
		if(this.isEmpty()){
			front = new Node(data);
			back = front;
			numItems++;
		}
		else{
			Node temp = new Node(data);
			temp.next = front;
			front.prev = temp;
			front = temp;
			numItems++;
			// checks if cursor index needs to be adjusted after adding new node
			if(cursIndx != -1){
				cursIndx++;
			}
		}
	}
	// Insert new element into this List. If List is non-empty,
	// insertion takes place after back element.
	void append(Object data){
		if(this.isEmpty()){
			front = new Node(data);
			back = front;
			numItems++;
		}
		else{
			Node temp = new Node(data);
			temp.prev = back;
			back.next = temp;
			back = temp;
			numItems++;
		}
	}
	// Insert new element before cursor.
	// Pre: length()>0, index()>=0
	void insertBefore(Object data){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling insertBefore on an empty list");
		}
		if(cursIndx < 0 || cursor == null){
			throw new RuntimeException("List ADT Error: calling insertBefore() with undefined cursor");
		}
		if(numItems > 1 && cursIndx > 0){
			Node temp = new Node(data);
			temp.next = cursor;
			temp.prev = cursor.prev;
			
			cursor.prev = temp;
			temp.prev.next = temp;
		}
		else if(numItems > 1 && cursIndx == 0){
			Node temp = new Node(data);
			temp.next = front;
			front.prev = temp;
			front = temp;
		}
		else{
			Node temp = new Node(data);
			temp.next = front;
			front.prev = temp;
			front = temp;
		}
		
		numItems++;
		// cursor index need to be adjusted
		cursIndx++;
	}
	// Inserts new element after cursor.
	// Pre: length()>0, index()>=0
	void insertAfter(Object data){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling insertAfter() on an empty list");
		}
		if(cursIndx < 0 || cursor == null){
			throw new RuntimeException("List ADT Error: calling insertAfter() with undefined cursor");
		}
		if(numItems > 1){
			Node temp = new Node(data);
			temp.prev = cursor;
			temp.next = cursor.next;
			
			cursor.next.prev = temp;
			cursor.next = temp;
		}
		else{
			Node temp = new Node(data);
			temp.prev = front;
			front.next = temp;
		}
		numItems++;
	}
	// Deletes the front element. Pre: length()>0
	void deleteFront(){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling deleteFront() on an empty list");
		}
		// check if cursor is at the front,
		// set cursor to null if it is
		if(cursIndx == 0){
			cursIndx = -1;
			cursor = null;
		}
		front = front.next;
		front.prev = null;
		numItems--;
	}
	// Deletes the back element. Pre: length()>0
	void deleteBack(){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling deleteBack() on an empty list");
		}
		// check if cursor was at the back
		if(cursIndx == numItems - 1){
			cursIndx = -1;
			cursor = null;
		}
		back = back.prev;
		back.next = null;
		numItems--;
	}
	// Deletes cursor element, making cursor undefined.
	// Pre: length()>0, index()>=0
	void delete(){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling delete() on an empty list");
		}
		if(cursIndx < 0){
			throw new RuntimeException("List ADT Error: calling delete() when cursor is undefined");
		}
		
		if(cursIndx == 0){
			this.deleteFront();
		}
		else if(cursIndx == numItems - 1){
			this.deleteBack();
		}
		else{
			Node temp = cursor.prev;
			temp.next = cursor.next;
			cursor.next.prev = temp;
			numItems--;
		
			cursor = null;
			cursIndx = -1;
		}
	}
	
	List copy(){
		if(this != null){
			List A = new List();
			Node temp = this.front;
			
			while(temp != null){
				A.append(temp.item);
				temp = temp.next;
			}
			return A;
		}
		return null;
	}
	// Overrides Object's toString method. Returns a String
	// representation of this List consisting of a space 
	// separated sequence of integers, with front on left.
	public String toString(){
		if(this.isEmpty()){
			return "";
		}
		else{
			String tempString = "";
			Node temp = front;
			while(temp != null){
				tempString += (String.valueOf(temp.item)).toString() + " ";
				temp = temp.next;
			}
			return tempString;
		}
	}
	
}