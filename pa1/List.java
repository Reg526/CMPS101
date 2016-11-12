//# Reginald Chand
//# rzchand
//# CMPS 101

// List.java
// A bi-directional queue List ADT

public class List{
	private class Node{
		int item;
		Node next;
		Node prev;
		
		Node(int x){
			item = x;
			next = null;
			prev = null;
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
	int front(){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling front() on empty list");
		}

		return front.item;
	
	}
	// Returns back element. Pre: length()>0
	int back(){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling front() on empty list");
		}
		
		return back.item;
	}
	// Returns cursor element. Pre: length()>0, index()>=0
	int get(){
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
	boolean equals(List L){
		if(L == null){
			throw new RuntimeException("List ADT Error: List is undefined");
		}
	
		Node temp = front;
		Node temp2 = L.front;
		
		while(temp != null && temp2 != null){
			// if the sequence does not match it'll break out of the loop
			if(temp.item != temp2.item){
				break;
			}
			
			temp = temp.next;
			temp2 = temp2.next;
		}
		// if the while loop runs to completion then temp = temp2 = null
		if(temp == temp2){
			return true;
		}
		else{
			return false;
		}
	}

	
	// Manipulation procedures
	// Resets this List to its original empty state.
	void clear(){
		front = null;
		back = null;
		cursor = null;
		cursIndx = -1;
		numItems = 0;
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
		// if cursor == back
		if(cursIndx == numItems - 1){
			cursor = null;
			cursIndx = -1;
		}
		// if cursor < back
		else if(cursIndx < numItems - 1){
			cursor = cursor.next;
			cursIndx++;
		}
	}
	// Insert new element into this List. If List is non-empty,
	// insertion takes place before front element.
	void prepend(int data){
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
	void append(int data){
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
	void insertBefore(int data){
		if(this.isEmpty()){
			throw new RuntimeException("List ADT Error: calling insertBefore on an empty list");
		}
		if(cursIndx < 0 || cursor == null){
			throw new RuntimeException("List ADT Error: calling insertBefore() with undefined cursor");
		}
		if(numItems > 1){
			Node temp = new Node(data);
			temp.next = cursor;
			temp.prev = cursor.prev;
			
			cursor.prev = temp;
			temp.prev.next = temp;
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
	void insertAfter(int data){
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
	// Returns a new List representing the same integer sequence as this
	// List. The cursor in the new list is undefined, regardless of the
	// state of the cursor in this List. This List is unchanged.
	List copy(){
		List listCpy = new List();
		Node temp = front;
		
		while(temp != null){
			listCpy.append(temp.item);
			temp = temp.next;
		}
		
		return listCpy;
	}
	
	
}