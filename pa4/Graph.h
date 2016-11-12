// Reginald Chand
// rzchand
// CMPS 101
// Header file for Graph ADT

#ifndef _GRAPH_H_INCLUDE_
#define _GRAPH_H_INCLUDE_

#include"List.h"

#define INF -1
#define NIL 0

// Graph
// Exported reference type
typedef struct GraphObj* Graph;

/*** Constructors-Destructors ***/
// returns a Graph pointing to a newly created GraphObj 
// representing a graph having n vertices and no edges
Graph newGraph(int n);

// frees all dynamic memory associated with the Graph*pG
// then sets the handle *pG to NULL
void freeGraph(Graph* pG);

/*** Access functions ***/
// returns order of graph
int getOrder(Graph G);

// returns size of graph
int getSize(Graph G);

// returns the source vertex most recently used in function BFS()
// or NIL if BFS() has not yet been called
int getSource(Graph G);

// returns the parent of vertex u in the BreadthFirst tree created by BFS()
// or NIL if BFS() has not yet been called
// pre: 1 <= u <= getOrder(G)
int getParent(Graph G, int u);

// returns the distance from the most recent BFS source to vertex u
// or INF if BFS() has not yet been called
// pre: 1 <= u <= getOrder(G) 
int getDist(Graph G, int u);

// appends to the List L the vertices of a shortest path in G from source to u
// or appends to L the value NIL if no such path exists
// pre: getSource(G)!=NIL 
// pre: 1 <= u <= getOrder(G)
void getPath(List L, Graph G, int u);

/*** Manipulation procedures ***/
// deletes all edges of G, restoring it to its original (no edge) state
void makeNull(Graph G);

// inserts a new edge joining u to v
// pre: 1 <= u, v <= getOrder(G)
void addEdge(Graph G, int u, int v);

// inserts a new directed edge from u to v
// pre: 1 <= u, v <= getOrder(G)
void addArc(Graph G, int u, int v);

// runs the BFS algorithm on the Graph G with source s
// setting the color, distance, parent,and source fields of G accordingly
void BFS(Graph G, int s);

/*** Other operations ***/
//prints the adjacency list representation of G 
void printGraph(FILE* out, Graph G);

#endif