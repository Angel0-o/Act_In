#include <sys/shm.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/types.h>
#include <pthread.h>
#include <string.h>

struct sembuf operacion;
int id_semaforo,shmid;
#define TAM 100
char *memoria=NULL;

void myWait(int semN)
{
	operacion.sem_num = semN;
	operacion.sem_op = -1;
	operacion.sem_flg = SEM_UNDO;
	semop (id_semaforo, &operacion, 1);
}

void mySignal(int senN)
{
	operacion.sem_num = senN;
	operacion.sem_op = 1;
	operacion.sem_flg = SEM_UNDO;	
	semop (id_semaforo, &operacion, 1);
}

void *escritura(void *arg)
{
	int x=0;
	char* a = "Hola\0";	
	while(x<10)
	{
		myWait(0);
				//printf("Escribiendo");
				strcpy(memoria,a);
				printf("Escribiendo : %s\n", memoria);
				//a++;
				//memoria=1;
				x++;					
		mySignal(1);
	}

	printf("%d\n", semctl (id_semaforo, 0, GETVAL,0));
	printf("%d\n", semctl (id_semaforo, 1, GETVAL,0));
}
int main(int argc, char const *argv[])
{
	key_t clave;

	pthread_t hilos[2];
	int id_memoria;
	clave=ftok("/bin/ls",15);
	if( (id_semaforo=semget(clave,2,0777|IPC_EXCL| IPC_CREAT))<0){
		id_semaforo=semget(clave,2,0777);
	}
	else {
		semctl (id_semaforo, 0, SETVAL,1);
		semctl (id_semaforo, 1, SETVAL,0);
	}
	//else
	//	id_semaforo=semget(clave,2,0777|IPC_CREAT);

	//semctl (id_semaforo, 0, SETVAL,1);
	//semctl (id_semaforo, 1, SETVAL,0);

	printf("%d\n", semctl (id_semaforo, 0, GETVAL,0));
	printf("%d\n", semctl (id_semaforo, 1, GETVAL,0));

	// SHARED MEMORY
	if ((id_memoria = shmget(clave,sizeof(char)*TAM, 0777 | IPC_CREAT |IPC_EXCL)) < 0) {
    	id_memoria = shmget(clave,sizeof(char)*TAM, 0777);
    } //else {
    	//id_memoria = shmget(clave,sizeof(char)*TAM, 0777 | IPC_CREAT);
    //}

    printf("%d",id_memoria);
    memoria = (char *)shmat(id_memoria,(char *)0, 0);
    if (memoria== NULL){     
        puts("error");
        return 0;
    }

	pthread_create(&hilos[0],NULL,escritura,NULL);
	pthread_join(hilos[0],NULL);
	
	// productor no deberia liberar la shm ni sem
	//semctl(id_semaforo,0,IPC_RMID,0);
	//shmdt ((char *)memoria);
	//shmctl (id_memoria, IPC_RMID, (struct shmid_ds *)NULL);

	return 0;
}














