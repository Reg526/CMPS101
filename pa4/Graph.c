// Reginald Chand
// rzchand
// CMPS 101
// Graph ADT

#include<stdio.h>
#include<stdlib.h>
#include<assert.h>
#include"Graph.h"
#include"List.h"

#define WHITE 0
#define GRAY 1
#define BLACK 2

// GraphObj
typedef struct GraphObj{
	List* neighbors;
	// 0 = white, 1 = gray, 2 = black
	int* color;
	int* parent;
	int* distance;
	
	int order;
	int size;
	int source;
} GraphObj;

// public functions -----------------------------------------------------------

// constructor for Graph type
Graph newGraph(int n){
	Graph G = malloc(sizeof(GraphObj));
	assert(G != NULL);
	
	G->neighbors = malloc(sizeof(List)*(n+1));
	G->color = malloc(sizeof(int)*(n+1));
	G->parent = malloc(sizeof(int)*(n+1));
	G->distance = malloc(sizeof(int)*(n+1));
	
	for(int i = 1; i < n+1; i++){
		G->neighbors[i] = newList();
		G->color[i] = WHITE;
		G->parent[i] = NIL;
		G->distance[i] = INF;
	}
	
	G->order = n;
	G->size = 0;
	G->source = NIL;
}

// destructor for Graph type
void freeGraph(Graph* pG){
	if(pG != NULL && *pG != NULL){
		Graph G = *pG;
		
		for(int i = 1; i < (getOrder(G)+1); i++){
			freeList(&(G->neighbors[i]));
		}
		
		free(G->neighbors);
		free(G->color);
		free(G->parent);
		free(G->distance);
		free(*pG);
		*pG = NULL;
	}
}

/*** Access functions ***/
// returns order of graph
int getOrder(Graph G){
	return G->order;
}

// returns size of graph
int getSize(Graph G){
	return G->size;
}

// returns the source vertex most recently used in function BFS()
// or NIL if BFS() has not yet been called
int getSource(Graph G){
	return G->source;
}

// returns the parent of vertex u in the BreadthFirst tree created by BFS()
// or NIL if BFS() has not yet been called
// pre: 1 <= u <= getOrder(G)
int getParent(Graph G, int u){
	if(u < 1 || u > getOrder(G)){
		fprintf(stderr, 
         "Graph Error: invalid vertex label");
		exit(EXIT_FAILURE);
	}
	
	if(getSource(G) == NIL){
		return NIL;
	}
	
	return G->parent[u];	
	
}

// returns the distance from the most recent BFS source to vertex u
// or INF if BFS() has not yet been called
// pre: 1 <= u <= getOrder(G) 
int getDist(Graph G, int u){
	if(u < 1 || u > getOrder(G)){
		fprintf(stderr, 
         "Graph Error: invalid vertex label");
		exit(EXIT_FAILURE);
	}
	
	if(getSource(G) == NIL){
		return INF;
	}
	
	return G->distance[u];
}

// appends to the List L the vertices of a shortest path in G from source to u
// or appends to L the value NIL if no such path exists
// pre: getSource(G)!=NIL 
// pre: 1 <= u <= getOrder(G)
void getPath(List L, Graph G, int u){
	if(u < 1 || u > getOrder(G)){
		fprintf(stderr, 
         "Graph Error: invalid vertex label");
		exit(EXIT_FAILURE);
	}
	if(getSource(G) == NIL){
		fprintf(stderr, 
         "Graph Error: BFS has not yet been called");
		exit(EXIT_FAILURE);
	}
	
	// is source == u
	if(u == getSource(G)){
		prepend(L, u);
	}
	// if no path exists
	else if(getParent(G,u) == NIL){
		append(L, NIL);
	}
	// if path does exist and source != u
	else{
		getPath(L, G, getParent(G,u));
		append(L, u);
	}
	
}

/*** Manipulation procedures ***/
// deletes all edges of G, restoring it to its original (no edge) state
void makeNull(Graph G){
	for(int i = 1; i < getOrder(G)+1; i++){
		clear(G->neighbors[i]);
			
		G->color[i] = WHITE;
		G->parent[i] = NIL;
		G->distance[i] = INF;
	}

	G->size = 0;
	G->source = NIL;
}

// inserts a new edge joining u to v
// pre: 1 <= u, v <= getOrder(G)
void addEdge(Graph G, int u, int v){
	if(u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)){
		fprintf(stderr, 
         "Graph Error: invalid vertex label");
		exit(EXIT_FAILURE);
	}
	
	// appends if list is empty or value is bigger than last element
	if(length(G->neighbors[u]) == 0 || back(G->neighbors[u]) < v){
		append(G->neighbors[u], v);
	}
	// prepends if value is less than first item in list
	else if(front(G->neighbors[u]) > v){
		prepend(G->neighbors[u], v);
	}
	// inserts element in the middle of the list
	else{
		moveFront(G->neighbors[u]);
		// moves cursor to correct spot in the list
		while(get(G->neighbors[u]) < v){
			moveNext(G->neighbors[u]);
		}
		
		insertBefore(G->neighbors[u], v);
	}
	
	if(length(G->neighbors[v]) == 0 || back(G->neighbors[v]) < u){
		append(G->neighbors[v], u);
	}
	else if(front(G->neighbors[v]) > u){
		prepend(G->neighbors[v], u);
	}
	else{
		moveFront(G->neighbors[v]);
		
		while(get(G->neighbors[v]) < u){
			moveNext(G->neighbors[v]);
		}
			
		insertBefore(G->neighbors[v], u);
	}
	
	// increase the # of edges
	G->size++;
}

// inserts a new directed edge from u to v
// pre: 1 <= u, v <= getOrder(G)
void addArc(Graph G, int u, int v){
	if(u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)){
		fprintf(stderr, 
         "Graph Error: invalid vertex label");
		exit(EXIT_FAILURE);
	}
	
	// appends if list is empty or value is bigger than last element
	if(length(G->neighbors[u]) == 0 || back(G->neighbors[u]) < v){
		append(G->neighbors[u], v);
	}
	// prepends if value is less than first item in list
	else if(front(G->neighbors[u]) > v){
		prepend(G->neighbors[u], v);
	}
	// inserts element in the middle of the list
	else{
		moveFront(G->neighbors[u]);
		// moves cursor to correct spot in the list
		while(get(G->neighbors[u]) < v){
			moveNext(G->neighbors[u]);
		}
		
		insertBefore(G->neighbors[u], v);
	}
}

// runs the BFS algorithm on the Graph G with source s
// setting the color, distance, parent,and source fields of G accordingly
void BFS(Graph G, int s){
	int i, j;
	
	// initialize for BFS
	for(int i = 0; i < (G->order + 1); ++i) {
		G->parent[i] = NIL;
		G->distance[i] = INF;
		G->color[i] = WHITE;
	}
	
	G->source = s;
	G->color[s] = GRAY;
	G->distance[s] = 0;
	G->parent[s] = NIL;
	
	// our FIFO queue manager
	List Q = newList();
	
	// Enqueue s
	prepend(Q, s);
	
	while(length(Q) != 0){
		// dequeue first value
		i = back(Q);
		deleteBack(Q);
		
		moveFront(G->neighbors[i]);
		
		while(index(G->neighbors[i]) != -1){
			j = get(G->neighbors[i]);
			
			if(G->color[j] == WHITE){
				G->color[j] = GRAY;
				G->distance[j] = G->distance[i] + 1;
				G->parent[j] = i;
				// Enqueue j
				prepend(Q, j);
			}
			moveNext(G->neighbors[i]);
		}
		G->color[i] = BLACK;
	}
	freeList(&Q);
}

/*** Other operations ***/
//prints the adjacency list representation of G 
void printGraph(FILE* out, Graph G){
 
	for(int i = 1; i < getOrder(G)+1; i++){
		fprintf(out, "%d: ", i);
		printList(out, G->neighbors[i]);
		fprintf(out, "\n");
	}
	
}

