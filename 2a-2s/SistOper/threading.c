#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>

//variáveis fixas
#define BUFFER_SIZE 35
#define MAX_ITEMS 20000

//buffers
int tapetecircular_buffer[BUFFER_SIZE] = {0};
int saida_buffer[MAX_ITEMS] = {0};

//variáveis de controlo
int prodi = 20001;
int in = 0;
int out = 0;
int prod_count = 0;
int cons_count = 0;
int pcook_producao[3] = {0};
int comilao_comidas[2] = {0};

//variáveis para locking e unlocking
pthread_mutex_t lockGeralProd = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t lockGeralCons = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t lockProd = PTHREAD_COND_INITIALIZER;
pthread_cond_t lockCons = PTHREAD_COND_INITIALIZER;

/*
    Função que corre nas threads PCOOK, cria os elementos a serem colocados no tapetecircular_buffer
*/
void* PCOOK(void* arg) {
    //id da thread, utilizado para gerir a produção de cada uma das threads individualmente
    int thread_id = *(int*)arg;
    
    while (1) {
        //tranca o mutex para impedir o avanço das threads PCOOK seguintes
        pthread_mutex_lock(&lockGeralProd);
        //caso ainda não estejamos no fim do programa, desbloqueia o mutex para permitir a continuação das outras threads PCOOK
        if(prodi <= 40000 ) {
            pthread_mutex_unlock(&lockGeralProd);
        } else {
            return NULL;
        }

        //Caso o tapetecircular_buffer esteja cheio, esperamos até receber sinal
        while (prod_count >= BUFFER_SIZE) {
            pthread_cond_wait(&lockProd, &lockGeralProd);
        }
        //coloca-se o valor obtido no tapetecircular
        tapetecircular_buffer[in] = prodi;

        //DEBUG PRINTS
        printf("PCOOK %d colocou %d no slot %d\n", thread_id, tapetecircular_buffer[in], in);
        /*printf("P[%lu]\n", (long unsigned) pthread_self());
        fflush(stdout);
        printf("\nProdutor %d:" , thread_id);
        fflush(stdout);
        for(int i = 0; i<35; i++) {
            printf("%d |", tapetecircular_buffer[i]);
            fflush(stdout);
        }*/
        //END OF DEBUG PRINTS

        //incrementamos o indíce que gere onde devemos colocar o próximo valor produzido pelas PCOOK, ao chegar a 34, colocamos a 0
        if (in < BUFFER_SIZE - 1) {
            in++;
        } else {
            in = 0;
        }

        //para impedir excesso de produção de valores no final do programa, retornamos as threads assim que o valor prodi supere 40000
        if(prodi >= 40001) {
            return NULL;
        }

        //incrementamos o contador de valores produzidos pela thread
        pcook_producao[thread_id-1]++;
        //incrementamos o valor de items atuais no tapetecircular
        prod_count++;
        //incrementamos o valor a produzir na próxima iteração
        prodi++;

        //enviamos um sinal para as threads COMILAO para que possam consumir os valores colocados no tapetecircular
        pthread_cond_signal(&lockCons);
        //desbloqueamos o mutex para que as restantes PCOOK possam prosseguir
        pthread_mutex_unlock(&lockGeralProd);
    }

}

/*
    Função que corre nas threads COMILAO, consome os elementos do tapetecircular_buffer e coloca-os no saida_buffer após subtrair 20000
*/
void* COMILAO(void* arg) {
    //id da thread, utilizado para gerir o consumo de cada uma das threads individualmente
    int thread_id = *(int*)arg;

    while (1) {
        //tranca o mutex para impedir o avanço das threads COMILAO seguintes
        pthread_mutex_lock(&lockGeralCons);
        
        //caso ainda faltem valores para consumir, desbloqueamos o mutex para que as threads COMILAO seguintes possam continuar
        if (cons_count < MAX_ITEMS) {
            pthread_mutex_unlock(&lockGeralCons);
        } else {
            return NULL;
        }

        //enquanto não existirem items a consumir, esperamos até receber sinal
        while (prod_count <= 0) {
            pthread_cond_wait(&lockCons, &lockGeralCons);
        }

        //colocamos o valor retirado do tapetecircular na saida_buffer subtraindo 20000
        saida_buffer[cons_count] = tapetecircular_buffer[out] - 20000;

        //DEBUG PRINTS
        printf("COMILAO %d retirou %d do slot %d e colocou no slot %d\n", thread_id, saida_buffer[cons_count], out, cons_count);
        /*tapetecircular_buffer[out] = -1;
        printf("C[%lu]\n", (long unsigned) pthread_self());
        fflush(stdout);
        printf("\nConsumidor %d:", thread_id);
        fflush(stdout);
        for(int i = 0; i<35; i++) {
            printf("%d :", tapetecircular_buffer[i]);
            fflush(stdout);
        }*/
        //END OF DEBUG PRINTS

        //incrementamos o indíce que gere de onde devemos retirar o próximo valor produzido pelas PCOOK, ao chegar a 34, colocamos a 0
        if (out < BUFFER_SIZE - 1) {
            out++;
        } else {
            out = 0;
        }
        
        //para impedir excesso de consumo de valores no final do programa, retornamos as threads assim que o valor cons_count supere 20000
        if(cons_count >= MAX_ITEMS) {
            return NULL;
        }

        //incrementamos a variável que gere o consumo de cada thread COMILAO individualmente
        comilao_comidas[thread_id-1]++;
        //incrementamos a quantidade total de valores consumidos
        cons_count++;
        //decrementamos a quantidade total de valores no tapetecircular
        prod_count--;
        
        //enviamos um sinal para as threads PCOOK para que possam prosseguir produção
        pthread_cond_signal(&lockProd);
        //desbloqueamos o mutex para que as restantes COMILAO possam prosseguir
        pthread_mutex_unlock(&lockGeralCons);
    }

}

/*
    Código que trata da criação das threads bem como os prints finais de informação
*/
int main() {
    //declaração das threads a serem criadas
    pthread_t PCOOK_1, PCOOK_2, PCOOK_3, COMILAO_1, COMILAO_2;
    //arrays para os ids das threads
    int comilao_ids[2] = {1, 2};
    int pcook_ids[3] = {1, 2, 3};

    // Criação das Threads produtoras, atribuindo um ID a cada uma
    pthread_create(&PCOOK_1, NULL, PCOOK, &pcook_ids[0]);
    pthread_create(&PCOOK_2, NULL, PCOOK, &pcook_ids[1]);
    pthread_create(&PCOOK_3, NULL, PCOOK, &pcook_ids[2]);

    // Criação das Threads consumidoras, atribuindo um ID a cada uma
    pthread_create(&COMILAO_1, NULL, COMILAO, &comilao_ids[0]);
    pthread_create(&COMILAO_2, NULL, COMILAO, &comilao_ids[1]);

    // Espera pelas consumidoras para prosseguir
    pthread_join(COMILAO_1, NULL);
    pthread_join(COMILAO_2, NULL);

    //escreve o total de valores produzidos por cada thread PCOOK
    for(int i = 0; i<3; i++) {
        printf("\nPCOOK %d: %d", i, pcook_producao[i]);
    }
    //escreve o total de valores consumidos por cada thread COMILAO
    for(int i = 0; i<2; i++) {
        printf("\nCOMILAO %d: %d", i, comilao_comidas[i]);
    }
    
    //iterar o array, só pra verificar se os números estão certos
    /*for(int i = 0; i < 20000; i++) {
        printf("%d - ",saida_buffer[i]);
    }*/

    //espera qualquer input para sair(utilizado para a janela não fechar quando compilado e executado em windows)
    getchar();

    return 0;
}
