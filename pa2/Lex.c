// Reginald Chand
// rzchand
// CMPS 101
// Uses List ADT to sort list from input file into output
// Lex.c

#include <stdio.h>
#include <stdlib.h>
#include "List.h"
#include <string.h>

int main(int argc, char *argv[]){
	if(argc != 3){
		printf("Usage: Lex input_file output_file");
		exit(1);
	}
	
	int lineNum = 0;
	int ch;
	char line[300];
	
	FILE* in = fopen(argv[1], "r");
	FILE* out = fopen(argv[2], "w");
	
	// counts the number of lines in the input file
	do{
		ch = fgetc(in);
		if(ch == '\n'){
			lineNum++;
		}
	}while(ch != EOF);
	
	fclose(in);
	
	// place string in the array from the input file
	int i = 0;
	int j = 0;
	char* lineArray[lineNum];
	
	in = fopen(argv[1], "r");
	
	while(fgets(line, 300, in) != NULL){
		lineArray[i] = strdup(line);
		i++;
 	}
	
	fclose(in);
	
	List L = newList();
	// initialize list
	append(L,0);
	
	// adds elements sorted to linked list based on insertion sort algorithm
	for(i = 0; i < lineNum; i++){
		j = i - 1;
		// resets cursor to the front of the list
		moveFront(L);
		
		for(j = -i; j < 1; j++){
			if(strcmp(lineArray[get(L)], lineArray[i+1]) > 0){
				insertBefore(L, i+1);
				j = 1;
			}
			else if(index(L) == length(L) - 1){
				append(L,i+1);
				j = 1;
			}
			else{
				moveNext(L);
			}
		}
	}
	// prints linked list elements to output file
	moveFront(L);
	
	while(index(L) != -1){
		fprintf(out, "%s", lineArray[get(L)]);
		moveNext(L);
	}
	fclose(out);
	
	// free memory
	for(i = 0; i < lineNum; i++){
		free(lineArray[i]);
	}
	
	freeList(&L);
	
	return(EXIT_SUCCESS);
}