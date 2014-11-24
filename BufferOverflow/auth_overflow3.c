#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int check_authentication(char *password) {
	char password_buffer[16];
	int auth_flag = 0;

	strcpy(password_buffer, password);
	
	if((strcmp(password_buffer, "brillig") == 0) ||
	  (strcmp(password_buffer, "outgrabe") == 0))
	    return 1;
	else
	    return 0;
}

int main(int argc, char *argv[]) {
	if(argc < 2) {
		printf("Usage: %s <password>\n", argv[0]);
		exit(0);
	}
	if(check_authentication(argv[1])) {
		printf("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
		printf("      Access Granted.\n");
		printf("-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
	} else {
		printf("\nAccess Denied.\n");
   }
}
	
