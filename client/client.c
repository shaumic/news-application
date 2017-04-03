#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
int main(){
    //create socket
    int client_socket = socket(AF_INET, SOCK_STREAM, 0);

    //requests to the server(specific IP and port)
    struct sockaddr_in serv_addr;
    memset(&serv_addr, 0, sizeof(serv_addr));  //each byte padded with 0
    serv_addr.sin_family = AF_INET;  //IPv4 address
    serv_addr.sin_addr.s_addr = inet_addr("127.0.0.1");  //specific IP address
    serv_addr.sin_port = htons(9999);  //port


    //To the server to initiate a connection request, the connection is successful client_socket on behalf of the client
    //and the server side of a socket connection
    if (connect(client_socket, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0)
    {
        printf("Can Not Connect To server!\n");
        exit(1);
    }

    while(1){
	char input[128];
        bzero(input, 128);//set parameters all 0
        printf("please input command:");
        scanf("%s",input);

	if(strcmp(input,"CTR-D")==0){
		break;
        }


        send(client_socket,input,strlen(input),0);
        //read date returned by server
        char buffer[1024];
        bzero(buffer, 1024);
        while(read(client_socket, buffer, sizeof(buffer)-1)==0){};

        printf("Message form server: %s\n", buffer);
    }

    //close socket
    close(client_socket);
    return 0;
}
