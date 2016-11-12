// Reginald Chand
// rzchand
// CMPS 101
// GraphTest.c

#include<stdlib.h>
#include<stdio.h>
#include"Graph.h"

int main(int argc, char* argv[]){
   int i, s, max, min, d, n=35;
   List  C = newList(); // central vertices 
   List  P = newList(); // peripheral vertices 
   List  E = newList(); // eccentricities 
   Graph G = NULL;

   // Build graph G 
   G = newGraph(n);
   for(i=1; i<n; i++){
      if( i%7!=0 ) addEdge(G, i, i+1);
      if( i<=28  ) addEdge(G, i, i+7);
   }
   addEdge(G, 9, 31);
   addEdge(G, 17, 13);
   addEdge(G, 14, 33);

   // Print adjacency list representation of G
   printGraph(stdout, G);

   // Calculate the eccentricity of each vertex 
   for(s=1; s<=n; s++){
      BFS(G, s);
      max = getDist(G, 1);
      for(i=2; i<=n; i++){
         d = getDist(G, i);
         max = ( max<d ? d : max );
      }
      append(E, max);
   }

   // Determine the Radius and Diameter of G, as well as the Central and 
   // Peripheral vertices.
   append(C, 1);
   append(P, 1);
   min = max = front(E);
   moveFront(E);
   moveNext(E);
   for(i=2; i<=n; i++){
      d = get(E);
      if( d==min ){
         append(C, i);
      }else if( d<min ){
         min = d;
         clear(C);
         append(C, i);
      }
      if( d==max ){
         append(P, i);
      }else if( d>max ){
         max = d;
         clear(P);
         append(P, i);
      }
      moveNext(E);
   }

   // Print results 
   printf("\n");
   printf("Radius = %d\n", min);
   printf("Central vert%s: ", length(C)==1?"ex":"ices");
   printList(stdout, C);
   printf("\n");
   printf("Diameter = %d\n", max);
   printf("Peripheral vert%s: ", length(P)==1?"ex":"ices");
   printList(stdout, P);
   printf("\n\n");

   // Free objects 
   freeList(&C);
   freeList(&P);
   freeList(&E);
   freeGraph(&G);
   
   G = newGraph(10);
   
	for(int j = 1; j < 10; j++) {
		if(j % 2 == 0)
			addEdge(G, j, 10 - j);
		else{
			addArc(G, j, 10 - j);
		}
	}
	printGraph(stdout, G);
	printf("\n");
	
	List L = newList();
	
	for(int j = 1; j < 10; j++){
		BFS(G, j);
		printf("Order of graph: %d\n", getOrder(G));
		printf("Size of graph: %d\n", getSize(G));
		printf("Source of graph: %d\n\n", getSource(G));
		for(int i = 1; i < 10; i++){
			if(getDist(G, i) != INF){
				printf("Distance %d-%d: %d\n", i, j, getDist(G,i));
				printf("Parent of %d is: \n", i, getParent(G,i));
				printf("Shortest path of %d-%d: ", i, j);
				getPath(L, G, i);
				printList(stdout, L);
				printf("\n\n");
				clear(L);
			}
		}
	}
	
	makeNull(G);
	printGraph(stdout, G);
	printf("\n");
	printf("Order of graph: %d\n", getOrder(G));
	printf("Size of graph: %d\n", getSize(G));
	printf("Source of graph: %d\n", getSource(G));
	
	freeList(&L);
	freeGraph(&G);
	
   return(0);
}