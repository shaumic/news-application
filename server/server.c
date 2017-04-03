/*
 Author      : Shaumic, Shuxiao, Xuanti
 */

#include <netinet/in.h>    // for sockaddr_in
#include <sys/types.h>    // for socket
#include <sys/socket.h>    // for socket
#include <stdio.h>        // for printf
#include <stdlib.h>        // for exit
#include <string.h>        // for bzero

/*
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
*/
#define HELLO_WORLD_SERVER_PORT    9999
#define LENGTH_OF_LISTEN_QUEUE 20
#define BUFFER_SIZE 10240
#define CMD_NAME_MAX_SIZE 512


#include "data.h"

char return_buffer[BUFFER_SIZE];
int setReturnData(char cmd_name[]);

int main(void) {
	    //set socket address structure
	    struct sockaddr_in server_addr;
	    bzero(&server_addr,sizeof(server_addr)); //set elements of memory area to zero
	    server_addr.sin_family = AF_INET;
	    server_addr.sin_addr.s_addr = htons(INADDR_ANY);
	    server_addr.sin_port = htons(HELLO_WORLD_SERVER_PORT);

	    //create internet, TCP socket
	    int server_socket = socket(PF_INET,SOCK_STREAM,0);
	    if( server_socket < 0)
	    {
	     	printf("Failed to create socket!");
	     	exit(1);
	    }
	    int opt =1;
	    setsockopt(server_socket,SOL_SOCKET,SO_REUSEADDR,&opt,sizeof(opt));

	    //socket and socket address structure linked
	    if( bind(server_socket,(struct sockaddr*)&server_addr,sizeof(server_addr)))
	    {
	        printf("Server Bind Port : %d Failed!", HELLO_WORLD_SERVER_PORT);
	        exit(1);
	    }

	    //server socket waits for clients' request
	    if ( listen(server_socket, LENGTH_OF_LISTEN_QUEUE) )
	    {
	        printf("Server failed to Listen!");
	        exit(1);
	    }
	    while (1) //run server continuously
	    {
	        //client socket address structure
	        struct sockaddr_in client_addr;
	        socklen_t length = sizeof(client_addr);

	        //server socket accepts a connection
	        //if no connection request, wait for one
	        //new_server_socket used to communicate with the connected client
	        //new_server_socket works as a communication channel between client and server
	        int new_server_socket = accept(server_socket,(struct sockaddr*)&client_addr,&length);
	        if ( new_server_socket < 0)
	        {
	            printf("Server failed to accept!\n");
	            break;
	        }

	        char buffer[BUFFER_SIZE];
	        bzero(buffer, BUFFER_SIZE);
	        length = recv(new_server_socket,buffer,BUFFER_SIZE,0);
	        if (length < 0)
	        {
	            printf("Server failed to receive data!\n");
	            break;
	        }
	        char cmd_name[CMD_NAME_MAX_SIZE+1];
	        bzero(cmd_name, CMD_NAME_MAX_SIZE+1);
	        strncpy(cmd_name, buffer, strlen(buffer)>CMD_NAME_MAX_SIZE?CMD_NAME_MAX_SIZE:strlen(buffer));

	        printf("Received:%s\n",cmd_name);

	    	int data_length = setReturnData(cmd_name);
	    	send(new_server_socket,return_buffer,data_length,0);
            	printf("Transfer Finished:\n%s\n",return_buffer);

	        //close client connection
	        close(new_server_socket);
	    }
	    //close socket for listening
	    close(server_socket);
	    return 0;
}


int setReturnData(char cmd_name[])
{
	bzero(return_buffer, BUFFER_SIZE);
	char (*news_ret)[20][1024];
	if(strcmp(cmd_name,"home")==0){
		news_ret = &news_home;
	}else if(strcmp(cmd_name,"business")==0){
		news_ret = &news_business;
	}else if(strcmp(cmd_name,"science")==0){
		news_ret = &news_science;
	}else if(strcmp(cmd_name,"magazine")==0){
		news_ret = &news_magazine;
	}else if(strcmp(cmd_name,"health")==0){
		news_ret = &news_health;
	}

	strcat(return_buffer,cmd_name);strcat(return_buffer,"::");
	int i=0;
	for(i=0;i<19;i++){
		strcat(return_buffer,(*news_ret)[i]);strcat(return_buffer,"::");
	}
	strcat(return_buffer,(*news_ret)[i]);
}
