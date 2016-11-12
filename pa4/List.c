// Reginald Chand
// rzchand
// CMPS 101
// Doubly linked list for sorting a List alphabetically
// List.c

#include<stdio.h>
#include<stdlib.h>
#include<assert.h>
#include"List.h"

// private types and functions ------------------------------------------------
// NodeObj
typedef struct NodeObj{
    int* item;
    struct NodeObj* next;
    struct NodeObj* prev;
} NodeObj;

// NodeObj
typedef NodeObj* Node;

// newNode()
// constructor for private Node typedef
Node newNode(int* x){
	Node N = malloc(sizeof(NodeObj));
	assert(N != NULL);
	N->item = x;
	N->next = NULL;
	N->prev = NULL;
	return N;
}

// freeNode()
// destructor for private Node type
void freeNode(Node* pN){
	if(pN != NULL && *pN != NULL){
		free(*pN);
		*pN = NULL;
	}
}

// ListObj
typedef struct ListObj{
	Node front;
	Node back;
	Node cursor;
	int numItems;
	int cursIndx;
} ListObj;

// deletes all Nodes
void deleteAll(List L){
	Node A = NULL;
	while(L->front != NULL){
		A = L->front;
		L->front = L->front->next;
		freeNode(&A);
	}
}

// returns 0 if list is empty
int isEmpty(List L){
	return (L->numItems == 0);
}
// public functions -----------------------------------------------------------
List newList(void){
	List L = malloc(sizeof(ListObj));
	assert(L != NULL);
	L->front = NULL;
	L->back = NULL;
	L->cursor = NULL;
	L->cursIndx = -1;
	L->numItems = 0;
	
	return L;
}

void freeList(List* pL){
	assert(pL !=NULL && *pL != NULL);
	while(!isEmpty(*pL)){
		deleteBack(*pL);
	}	
	free(*pL);
	*pL = NULL;
	
}

int length(List L){
	return L->numItems;
}

int index(List L){
	if(L->cursor == NULL){
		return -1;
	}
	return L->cursIndx;
}

int front(List L){
	if(L->front == NULL){
		fprintf(stderr,"List ADT Error: calling front() on empty list");
	}
	
	return L->front->item;
}

int back(List L){
	if(L->back == NULL){
		fprintf(stderr,"List ADT Error: calling back() on empty list");
	}
	
	return L->back->item;
}

int get(List L){
	if(L->cursIndx < 0){
		fprintf(stderr,"List ADT Error: calling get() while cursor is undefined");
	}
	if(L == NULL){
		fprintf(stderr,"List ADT Error: calling get() on an empty list");
	}
	
	return L->cursor->item;
}

int equals(List A, List B){
	if(A == NULL || B == NULL){
		fprintf(stderr,"List ADT Error: calling equals() with an empty list");
	}
	
	Node temp = A->front;
	Node temp2 = B->front;
	
	while(temp != NULL && temp2 != NULL){
		// if sequence does not match, break from loop
		if(temp->item != temp2->item){
			break;
		}
		
		temp = temp->next;
		temp2 = temp2->next;
	}
	// if the while loop completes
	if(temp == temp2){
		return 1;
	}
	else{
		return 0;
	}
}

void clear(List L){
	deleteAll(L);
	L->front = NULL;
	L->back = NULL;
	L->cursor = NULL;
	L->cursIndx = -1;
	L->numItems = 0;
}

void moveFront(List L){
	if(!isEmpty(L)){
		L->cursor = L->front;
		L->cursIndx = 0;
	}
}

void moveBack(List L){
	if(!isEmpty(L)){
		L->cursor = L->back;
		L->cursIndx = L->numItems - 1;
	}
}

void movePrev(List L){
	// if cursor == front
	if(L->cursIndx == 0 && L->cursor != NULL){
		L->cursor = NULL;
		L->cursIndx = -1;
	}
	// if cursor > front
	else if(L->cursIndx > 0 && L->cursor != NULL){
		L->cursor = L->cursor->prev;
		L->cursIndx--;
	}
}

void moveNext(List L){
	// if cursor == back
	if(L->cursIndx == L->numItems - 1){
		L->cursor = NULL;
		L->cursIndx = -1;
	}
	// if cursor < back
	else if(L->cursIndx < L->numItems - 1){
		L->cursor = L->cursor->next;
		L->cursIndx++;
	}
}

void prepend(List L, int data){
	if(L == NULL){
		fprintf(stderr,"List ADT Error: calling prepend() with an empty list");
	}
	
	Node N = newNode(data);
	if(isEmpty(L)){
		L->front = N;
		L->back = L->front;
	}
	else{
		N->next = L->front;
		L->front->prev = N;
		L->front = N;
		
		// checks if cursor index needs to be adjusted
		if(L->cursIndx != -1){
			L->cursIndx++;
		}
	}
	L->numItems++;
}
void append(List L, int data){
	Node N = newNode(data);
	if(isEmpty(L)){
		L->front = N;
		L->back = N;
	}
	else{
		N->prev = L->back;
		L->back->next = N;
		L->back = N;
	}
	L->numItems++;
}
void insertBefore(List L, int data){
	if(L == NULL || isEmpty(L)){
		fprintf(stderr,"List ADT Error: calling insertBefore() with an empty list");
	}
	if(L->cursIndx < 0 || L->cursor == NULL){
		fprintf(stderr,"List ADT Error: calling insertBefore() with undefined cursor");
	}
	Node N = newNode(data);
	if(L->cursor == L->front){
		N->next = L->front;
		L->front->prev = N;
		L->front = N;
	}
	else{
		N->prev = L->cursor->prev;
		L->cursor->prev->next = N;
		N->next = L->cursor;
		L->cursor->prev = N;
	}
	L->numItems++;
}
void insertAfter(List L, int data){
	if(L == NULL || isEmpty(L)){
			fprintf(stderr,"List ADT Error: calling insertAfter() on an empty list");
		}
		if(L->cursIndx < 0 || L->cursor == NULL){
			fprintf(stderr,"List ADT Error: calling insertAfter() with undefined cursor");
		}
		Node N = newNode(data);
		if(L->numItems > 1){
			N->prev = L->cursor;
			N->next = L->cursor->next;
			
			L->cursor->next->prev = N;
			L->cursor->next = N;
		}
		else{
			N->prev = L->front;
			L->front->next = N;
		}
	L->numItems++;
}	

void deleteFront(List L){
	if(L == NULL){
		fprintf(stderr,"List ADT Error: calling deleteFront() on NULL list");
	}
	if(L->numItems < 1){
		fprintf(stderr,"List ADT Error: calling deleteFront() on an empty list");
	}
	// check if cursor is at the front,
	// set cursor to NULL if it is
	if(L->cursIndx == 0){
		L->cursIndx = -1;
		L->cursor = NULL;
	}
	
	Node temp = L->front;
	L->front = L->front->next;
	L->front->prev = NULL;
	L->numItems--;
	freeNode(&temp);
}
void deleteBack(List L){
	if(L == NULL){
		fprintf(stderr,"List ADT Error: calling deleteBack() on an NULL list");
	}
	if(L->numItems < 1){
		fprintf(stderr,"List ADT Error: calling deleteFront() on an empty list");
	}
	// check if cursor was at the back
	if(L->cursIndx == L->numItems - 1){
		L->cursIndx = -1;
		L->cursor = NULL;
	}
	Node temp = L->back;
	if(L->numItems > 1){
		L->back = L->back->prev;
		L->back->next = NULL;

	}
	else{
		L->front = L->back = L->cursor = NULL;
	}
	L->numItems--;
	freeNode(&temp);
}
void delete(List L){
	if(L == NULL){
		fprintf(stderr,"List ADT Error: calling delete() on an empty list");
	}
	if(L->cursIndx < 0){
		fprintf(stderr,"List ADT Error: calling delete() when cursor is undefined");
	}
	
	Node temp = L->cursor;
		
	if(L->cursIndx == 0){
		deleteFront(L);
	}
	else if(L->cursIndx == L->numItems - 1){
		deleteBack(L);
	}
	else{
		L->cursor->prev->next = L->cursor->next;
		L->cursor->next->prev = L->cursor->prev;
		L->cursor->next = L->cursor->prev = NULL;
		
		L->numItems--;
		
		freeNode(&temp);
		L->cursor = NULL;
		L->cursIndx = -1;	
	}	
}

void printList(FILE* out, List L){
   if( L == NULL ){
      fprintf(stderr, 
         "Dictionary Error: calling printDictionary() on NULL"
         " Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   
   for(Node N = L->front; N != NULL; N = N->next){
	   fprintf(out, "%d ", N->item);
   }
}

List copyList(List L){
	List listCpy = newList();
	Node temp = L->front;
	
	while(temp != NULL){
		append(listCpy, temp->item);
		temp = temp->next;
	}
	return listCpy;
}
