#include <stdio.h>          //Including the needed libraries
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include <stdbool.h>

struct Player               //making struct for the players
{
  char name[100];
  char visible[10];
  int team;
  int power;
	bool solo;
};

struct Leader{             //making struct for the leader magicians
char name[100];
int power;
int number;
};

int team_number;
int players_number;
int action_num = 0;
int super_player_num = 0;
struct Leader leader[20];
struct Player player[2000];
char empty[105];
FILE *inp, *out;


void invalid_inp(){                          //function to ouput "Invalid inputs"
	fclose(out);
  out = fopen("output.txt","w");
  fprintf(out,"Invalid inputs\n");
  exit(0);
}

void invisible(){
	fprintf(out,"This player can't play\n");
}

void frozen(){
	fprintf(out,"This player is frozen\n");
}


bool check_visible(struct Player *p){                   //function to check if the player is visible or not
  if (strcmp(p->visible,"True") == 0){return true;}
  if (strcmp(p->visible,"False") == 0){return false;}
  invalid_inp();
}

bool check_frozen(struct Player *p){                    //function to check if the player frozen
  if(p->power == 0){return true;}
  return false;
}

bool check_same_player(struct Player *p1, struct Player *p2){     //To check if same player
	return (strcmp(p1->name, p2->name) == 0);
}

bool check_same_team(struct Player *p1, struct Player *p2){       //To check if same team
	return (p1->team == p2->team);
}

void attack(struct Player *p1, struct Player *p2){                //function attack to do the action "attack" between two players
  if (!check_visible(p1)){                                        //takes two arguments (two pointers for the structs)
		invisible(); 
		return;
  }
	if(check_frozen(p1)){
		frozen();
		return;
	}
	if(!check_visible(p2)){
		p1->power = 0;
	}
  else if(p1->power>p2->power){
    p1->power = p1->power + (p1->power-p2->power);
		p2->power = 0;
    if(p1->power>1000){p1->power = 1000;}
  }
  else if (p1->power<p2->power)
  {
    p2->power = p2->power + (p2->power-p1->power);
    p1->power = 0;
    if(p2->power>1000){p2->power = 1000;}

  }
  else if (p1->power==p2->power)
  {
    p1->power = 0;
    p2->power = 0;
  }
}

void flib_visiblity(struct Player *p){                              //function to change the visibilty of a player
	if(check_frozen(p)) {
		frozen();
		return;
	}
  if(strcmp(p->visible,"True") == 0){
    strcpy(p->visible,"False");
		return;
  }
  strcpy(p->visible,"True");
}

void heal(struct Player *p1, struct Player *p2){                     //function heal to do the action "heal" also take two arguments
  if(!check_visible(p1)){                                            //two pointers for the player structs
		invisible();
		return;
	}
	if(check_frozen(p1)){
		frozen();
		return;
	}
	if(!check_same_team(p1,p2)){
		fprintf(out,"Both players should be from the same team\n");
		return;
	}
	if(check_same_player(p1,p2)){
		fprintf(out,"The player cannot heal itself\n");
		return;
	}
  p2->power += (p1->power+1)/2;
  p1->power = (p1->power+1)/2;
	if(p2->power>1000){p2->power = 1000;}
}

void super_player(struct Player *p1, struct Player *p2){                 //fuction super_player to do the action super between two players
	if(!check_visible(p1)){                                              //and check if the players from same team and not the same player is
		invisible();                                                     //trying to be super player with himself
		return;
	}
	if(check_frozen(p1)){
		frozen();
		return;
	}
	if(!check_same_team(p1,p2)){
		fprintf(out,"Both players should be from the same team\n");
		return;
	}
	if(check_same_player(p1,p2)){
		fprintf(out,"The player cannot do super action with itself\n");
		return;
	}
	int index = super_player_num + players_number;
  player[index].power = p1->power+p2->power;
  if(player[index].power>1000){player[index].power = 1000;}
  strcpy(player[index].visible,"True");
  sprintf(player[index].name,"S_%d",super_player_num);
	player[index].solo = true;
	player[index].team = p1->team;
  super_player_num++;
  p1->power = 0;
  p2->power = 0;
	p1->solo = false;
  p2->solo = false;
}

void check_leader_name(char line[]) {                               //function to check if the magicians names are correct
	for(int i=0;i<strlen(line)-1;i++) {
		if(line[i]>='A'&&line[i]<='Z')
			continue;
		if(i>0&&line[i]>='a'&&line[i]<='z')
			continue;
		invalid_inp();
	}
	line[strlen(line)-1] = '\0';
	if(strlen(line)>20)
		invalid_inp();
}

int check_number(char line[]) {                                  //check if the entered value is an integer
	int num=0;
	for(int i=0;i<strlen(line)-1;i++) {
		if(line[i]<'0'||line[i]>'9')
			invalid_inp();
		num *= 10;
		num += line[i]-'0';
	}
	return num;
}

void check_visible_line(char line[]) {                               //function to check if the line 
	if (strcmp(line,"True\n") != 0 && strcmp(line,"False\n") != 0)
		invalid_inp();
	line[strlen(line)-1] = '\0';
}

int get_player(char name[]){                                          //function to get the index of a player
	if(name[strlen(name)-1] =='\n')
		name[strlen(name)-1] = '\0';
	for(int i=0;i<=players_number + super_player_num;i++) {
		if(strcmp(player[i].name,name) == 0){
			return i;
		}
	}
	invalid_inp();
}

void start_action(char action[], struct Player *p1, struct Player *p2){   //function to handle the actions
	if(strcmp(action, "attack") == 0) {
		attack(p1,p2);
	}
	else if(strcmp(action, "flip_visibility") == 0) {
		flib_visiblity(p1);
	}
	else if(strcmp(action, "heal") == 0) {
		heal(p1,p2);
	}
	else if(strcmp(action, "super") == 0) {
		super_player(p1,p2);
	}
	else
		invalid_inp();
	action_num++;
	if(action_num > 1000)
		invalid_inp();
}


int main(){
	inp = fopen("input.txt","r");                   //opening input.txt in read mode
	out = fopen("output.txt","w");                  //opening output.txt in write mode
	char line[105];
	fgets(line,100,inp);           
  	team_number = check_number(line);               //getting the number of teams from the file
	if(team_number>10||team_number<1)
		invalid_inp();
	for(int i=0;i<team_number;i++){
    fgets(line,100,inp);           
		check_leader_name(line);
		strcpy(leader[i].name,line);               //getting the leader magicians names
		leader[i].number = i;
		leader[i].power = 0;
	}

	fgets(line,100,inp);                               //getting players number from the team
  	players_number = check_number(line);
	if(players_number>100||players_number<team_number)
		invalid_inp();
	for(int i=0;i<players_number;i++) {
		fgets(line,100,inp);                           //getting player name

		check_leader_name(line);
    strcpy(player[i].name, line);                       
		fgets(line,100,inp);                          //getting player team
		player[i].team=check_number(line);
		if(player[i].team>=team_number||player[i].team<0)
			invalid_inp();

		fgets(line,100,inp);
		player[i].power=check_number(line);          //getting player power
		if(player[i].power>1000||player[i].power<0)
			invalid_inp();

		fgets(line,100,inp);                          //getting visiblity state
		check_visible_line(line);
		strcpy(player[i].visible, line);
		player[i].solo = true;
	}

	while(fgets(line,100,inp) != NULL) {             //going through the end of the file and getting lines
		char action[105];
		char name1[105];
		char name2[105];
		struct Player *p1;
		struct Player *p2;
		char *ptr = strtok(line," ");
		for(int i=1;ptr!=NULL;i++) {                //splitting lines by spaces to (action,name1,name2)
			if(i == 1){
				strcpy(action,ptr);
			}
			else if(i == 2){
				strcpy(name1,ptr);
				p1 = &player[get_player(name1)];
			}
			else if(i == 3 && strcmp(action,"flip_visibility") != 0){
				strcpy(name2,ptr);
				p2 = &player[get_player(name2)];
			}
			else
				invalid_inp();
			ptr = strtok(NULL," ");
		}
		start_action(action,p1,p2);                
	}                                                                     //checking which team (leader magician) has the biggest power
                                                                          //to consider it the winner and checking if two magicians has the 
	for(int i=0;i<=players_number+super_player_num;i++){                  //same amount power
		if(player[i].power != 0 && player[i].solo == true){
			leader[player[i].team].power += player[i].power;
		}
	}
	int max = -1;
	int j = -1;
	for(int i=0;i<team_number;i++) {
		if(leader[i].power > max) {
			max = leader[i].power;
			j = i;
		}
	}
	for(int i=0;i<team_number;i++) {
		if(leader[i].power==max && i!=j) {
			max = -1;
		}
	}
	if(max == -1){
		fprintf(out,"It's a tie\n");
	}
	else{
		fprintf(out,"The chosen wizard is %s\n",leader[j].name);
	}
}
