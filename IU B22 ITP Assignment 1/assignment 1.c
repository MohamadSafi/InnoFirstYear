#include <stdio.h>          //Including the needed libraries
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

#define MAX_LENGHT 105      //Defining the Maximum length of the words
//#define  _GNU_SOURC
char words_arr[MAX_LENGHT][10000],temp[10000];     //Declaring and Initializing Variables
int words_number,i=0,j=0,counter=0;
char line[MAX_LENGHT];
size_t len = 0;

int main(){

  FILE *inp = fopen("input.txt","r");     //Opening The input.txt file in reading mode
  FILE *out = fopen("output.txt","w");    //Opening The output.txt file in writing mode
 
  fgets(line,MAX_LENGHT,inp);             //Reading the total amount of names from the first line
  sscanf(line,"%d",&words_number);        //and assigning to a variable (words_number)


  if(words_number>100){ //Checking if the value of (words_number) is between 1 and 100
    fprintf(out,"Error in the input.txt");  //Raising an Error if (1≤words_number≤100)
    fclose(out);
    return 0;
  }


  while(fgets(words_arr[i],MAX_LENGHT,inp) != NULL){   //Iterating through the file and saving the lines in the array(words_arr)
    if(isupper(words_arr[i][0])){                      //Checking if each name starts with an uppercase letter 
    for(j=1;words_arr[i][j] != '\0';j++){              //Then Iterating through every char

      if(words_arr[i][j] == '\n'){continue;}           //Skipping the new line character
        if(words_arr[i][j]<'a'||words_arr[i][j]>'z'){             //Checking every character if it's a letter with no numbers or spaces or symbols
          fprintf(out,"Error in the input.txt");       //Raising an Error in the output file
          fclose(out);
          return 0;
      }

    } 
      i++;             //increasing the i by one
      counter++;       //increasing counter by one
    }
    else{
      fprintf(out,"Error in the input.txt");           //Raising an Error if the (isupper) condition is not right
      fclose(out);
      return 0;}

  }

  if(words_number != counter) fprintf(out,"Error in the input.txt");   
                                                                          /*Raising an Error if the totalt amount of words (words_number)
                                                                            doesn't equal the number of lines in the input file*/


  else{
    for(i=0;i<words_number;i++){                          //Going through the array character by character 
      for(j=i+1;j<words_number;j++){

        if(strcmp(words_arr[i],words_arr[j])>0){          //Then comparing them using the strcmp() function which returns a value that
                                                          // is bigger than zero if words_arr[i] is bigger than words_arr[j]
          strcpy(temp,words_arr[i]);                      //Swapping words_arr[i] with a temporary variable
          strcpy(words_arr[i],words_arr[j]);              //Swapping words_arr[i] with words_arr[j]
          strcpy(words_arr[j],temp);                      //swapping words_arr[j] with the temporary variable
        }
      }
    }
    for(i=0;i<words_number;i++){                          //Writing the array to the output file
      fprintf(out,"%s",words_arr[i]);
    }
  }
    fclose(out);      //closing the output file
    fclose(inp);      //closing the input file

  
  return 0;
}
 
