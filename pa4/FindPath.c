// Reginald Chand
// rzchand
// CMPS 101
// FindPath.c

#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>
#include<string.h>
#include<assert.h>
#include"Graph.h"

int main(int argc, char* argv[]){
	FILE* in;
	FILE* out;
	char* line; 	// string for holing input line
	int* value; 	// will hold the values of the input file
	int lineNum; 	// counts lines in input file
	
	// check command line for correct number of arguments
	if(argc != 3){
		printf("Usage: %s input_file output_file", argv[0]);
		exit(EXIT_FAILURE);
	}
	
   // open input file for reading 
	if( (in=fopen(argv[1], "r"))==NULL ){
	  printf("Unable to read from file %s\n", argv[1]);
	  exit(EXIT_FAILURE);
	}
	
	char ch;
	while(!feof(in))
	{
		ch = fgetc(in);
		if(ch == '\n')
		{
			lineNum++;
		}
	}
	
	fclose(in);
	
	// allocate for the number of lines for the input file
	line = calloc(lineNum, sizeof(char));
	value = calloc((lineNum - 1)*2 + 1, sizeof(int));
	assert(line != NULL && value != NULL);
	
	// reopen input file
	in = fopen(argv[1], "r");

   // open output file for writing 
	if( (out=fopen(argv[2], "w"))==NULL ){
	  printf("Unable to write to file %s\n", argv[2]);
	  exit(EXIT_FAILURE);
	}
	int i = 0, j = 0, k = 1, digit;
	// read each line of input file and extract values and stores in value
	while(fgets(line, lineNum, in) != NULL){
		i = 0;
		while(line[i] != '\0' && i < lineNum){
			if(isdigit((int)line[i])){
				// converts char to int
				digit = line[i] - '0';
				// adjust number to correct digit place
				if(k != 1){
					value[j] = value[j]*10 + digit;
				}
				else{
					value[j] = digit;
				}
				k++;
			}
			else{
				j++;
				k = 1;
			}
			i++;
		}	
	}
	
	// intialize graph
	Graph G = newGraph(value[0]);
	
	i = 1;
	while(value[i] != 0){
		addEdge(G, value[i], value[i+1]);
		// since there are two values per row
		i = i + 2;
	}
	
	// increment i so that it skips the first zero value from the input
	i = i + 2;
	
	printGraph(out, G);
	fprintf(out, "\n");
	List L = newList();
	
	while(value[i] != 0){
		BFS(G, value[i]);
		if(getDist(G,value[i+1]) == INF){
			fprintf(out, "The distance from %d to %d is infinity\n", value[i], value[i+1]);
			fprintf(out,"No %d-%d path exists", value[i], value[i+1]);
		}
		else{
			fprintf(out, "The distance from %d to %d is %d\n", value[i], value[i+1], getDist(G,value[i+1]));
			getPath(L, G, value[i+1]);
			fprintf(out,"A shortest %d-%d path is: ", value[i], value[i+1]);
			printList(out, L);
		}
		
		fprintf(out,"\n\n");
		i = i + 2;
		clear(L);
	}
	fclose(in);
	fclose(out);
	
	freeList(&L);
	freeGraph(&G);
	free(line);
	free(value);
}