;DIVISAO INTEIRA

.DATA
TXTNUM DB 0AH,0DH,"Inserir numerador : $"
TXTDEN DB 0AH,0DH,"Inserir denominadir : $"
TXTERRO1 DB 0AH,0DH,"ERROR: Caracter invalido$",0DH
TXTRES DB 0AH,0DH,"Resultado : $"

;VARIAVEIS
HON DW 0            ;HIGHOrder do numerador
POSNULLRES DW 0     ;Guarda a primeira posicao nula do resultado
AUX DW 0
AUX2 DW 0           
DEZ DB 10           ;variavel com valor 10, para ajudar em assembly

;ARRAYS             ;array do denuminador
DEN DB 8 DUP        ;array do numerador
NUM DB 8 DUP        ;array do resultado
RESU DB 8 DUP(0)    ;array do resultado
V DB 8 DUP(0)       ;guarda o valor da multiplicacao 
C DB 8 DUP(0)       ;carry
DIF DB 8 DUP
CDIF DB 8 DUP
ARR DB 8 DUP

.CODE
MAIN PROC
	MOV AX,@DATA
	MOV DS,AX	
    
    CALL LEITURANUM 
    CALL LEITURADEN
    
    
    ;TAMANHO DO DEN
    TAMDEN:
        XOR DX,DX                        ;dx = 0
        XOR AX,AX                        ;ax = 0
        TAMDEN1:
            LEA BX,DEN                   ;buscar a posicao do array Den
            ADD BX,AX                    ;Bx = Bx + Ax
            INC AX                       ;AX = Ax + 1
            CMP [BX],0                   ;Compara o Bx com 0
            JE TAMDEN1                   ;Jump condicional, se a condicao anterior faz o codigo seguinte
            INC DX                       ;Incrementa +1 ao Dx 
            
             
    ;tamanho de num        
    TAMNUM:                              ;cx = 0
        XOR CX,CX                        ;ax = 0
        XOR AX,AX
        TAMNUM1:                         ;busca a posicao do array Num
            LEA BX,NUM                   ;bx = bx + ax
            ADD BX,AX                    ;ax = ax + 1
            INC AX                       ;compara bx com 0
            CMP [BX],0                   ;Jump condicional se o anterior for igual
            JE TAMDEN1                   ;incrimentar +1 ao dx
            INC DX                       ;compara cx com dx
    CMP CX,DX                            ;(cx < dx) escreve o resultado, < por ser JB
    JB PRINTRESULT
    
    COMPNUMDEN:                          ;ax = 0
        XOR AX,AX                        ;cx = 0
        XOR CX,CX                        ;dx = 0
        XOR DX,DX
        COMPNUMDEN1:                     
            LEA BX,NUM                   ;busca a posicao do Num no array
            ADD BX,AX                    ;bx = bx + ax
            MOV DX,[BX]                  ;dx = bx (porque para trabalhar com arrays tem que ser o bx)
            LEA BX,DEN                   ;buscar a posicao do array Den
            ADD BX,AX                    ;bx = bx + ax
            CMP DX,[BX]                  ;compara dx com bx
            JB PRINTRESULT               ;(dx < bx) escreve o resultado, < por ser JB
            INC AX                       ;ax = ax + 1
            CMP AX,8                     ;compara ax com 8
            JB COMPNUMDEN1
            
    HONCAL:                              
        LEA BX,NUM                       ;busca a posicao do Num no array
        MOV DX,[BX]                      ;dx = bx
        LEA BX,DEN                       ;busca a posicao do Den no array
        CMP DX,[BX]                      ;compara dx com bx
        JB HON1                          ;(dx < bx) escreve o resultado
        JAE HON2                         ;(dx >= bx) escreve o resultado
        HON1: 
            MOV AX,DX                    ;ax = dx
            MUL DEZ                      ;ax * 10
            LEA BX,NUM                   ;busca a posicao do Num no array
            ADD BX,1                     ;bx = bx + 1
            ADD AX,[BX]                  ;ax = ax + bx
            JMP CALCULO1                 ;salta para o bloco de codigo CALCULO1
        HON2:
            MOV HON,DX                   ;HON = dx
            JMP CALCULO1                 ;salta para o bloco de codigo CALCULO1
            
    CALCULO1:
        XOR CX,CX                        ;cx = 0
        CAL1LOOP:
            LEA BX,DEN                   ;busca a posicao do Den no array
            MOV AX,[BX]                  ;ax = bx
            MUL CX                       ;ax * cx
            MOV AUX,AX                   ;aux = ax
            CMP AX,HON                   ;compara ax com HON
            JA CAL1FIM                   ;(dx > HON) escreve o resultado
            JBE CAL1INTER                ;(dx <= HON) escreve o resultado
        CAL1INTER:
            INC CX                       ;cx = cx + 1
            JMP CAL1LOOP                 ;salta para o bloco de codigo CAL1LOOP
        CAL1FIM:
            DEC CX                       ;CX = CX - 1
            LEA BX,RESU                  ;busca a posicao do RESU no array
            ADD BX,POSNULLRES            ;bx = bx + POSNULLRES
            MOV [BX],CX                  ;bx = cx
            INC POSNULLRES               ;POSNULLRES = POSNULLRES + 1
        
    CALCULO2:                            ;cx = 7
        MOV CX,7                         ;aux = 7
        MOV AUX,7
        CAL2LOOP:                        
            LEA BX,DEN                   ;busca a posicao do Den no array
            ADD BX,CX                    ;bx = bx + cx
            MOV DX,[BX]                  ;dx = bx
            LEA BX,RESU                  ;busca a posicao do RESU no array
            MOV AX,POSNULLRES            ;ax = POSNULLRES
            SUB AX,1                     ;ax = ax - 1
            LEA BX,RESU                  ;busca a posicao do RESU no array
            ADD AX,BX                    ;ax = ax + bx
            MUL DX                       ;ax = ax * dx
            LEA BX,C                     ; busca a posicao do C no array
            ADD BX,CX                    ;bx = bx + cx
            ADD AX,[BX]                  ;ax = ax + bx
            MOV AUX2,AX                  ;aux2 = ax
            CMP AUX2,0                   ;compara aux2 com 0
            JE ADDINI                    ;compara se aux2 = 0
            CMP AUX2,9                   ;compara aux2 com 9
            JA ADDMAIOR9                 ;compara se aux2 > 9 
            JBE ADDMENOR9                ;compara se aux2 <= 9 
        
        ADDMAIOR9:
            LEA BX,C                     ;busca a posicao do C no array
            ADD BX,CX                    ;bx = bx + cx
            DEC BX                       ;bx - 1
            MOV AX,AUX2                  ;ax = aux2
            DIV DEZ                      ;ax / 10
            MOV [BX],AL                  ;bx = al ([bx] endereco de memoria)
            LEA BX,V                     ;busca a posicao do V no array
            ADD BX,AUX                   ;bx = bx + aux
            DEC BX                       ;bx - 1
            MOV [BX],AH                  ;bx = ah ([bx] endereco de memoria)
            DEC CX                       ;cx - 1
            JMP CAL2LOOP                 ;salta para o bloco de codigo CAL2LOOP
        ADDMENOR9:
            LEA BX,V                     ;busca a posicao do V no array
            ADD BX,AUX                   ;BX = BX + AUX
            DEC AUX                      ;AUX - 1
            MOV AX,AUX2                  ;AX = AUX2
            MOV [BX],AX                  ;BX = AX
            DEC CX                       ;cx - 1
            JMP CAL2LOOP                 ;salta para o bloco de codigo CAL2LOOP
        ADDINI:
            LEA BX,V                     ;busca a posicao do V no array
            ADD BX,AUX                   ;bx = bx + aux
            DEC AUX                      ;aux - 1
            MOV AX,AUX2                  ;ax = aux2
            MOV [BX],AX                  ;bx = ax
        DODIF:
            XOR  CX,CX                   ;cx = 0
            DODIFLOOP1:
                LEA BX,NUM               ;busca a posicao do NUM no array
                ADD BX,CX                ;bx = bx + cx
                MOV DX,[BX]              ;dx = bx
                LEA BX,V                 ;busca a posicao do V no array
                ADD BX,CX                ;bx = bx + cx
                CMP DX,[BX]              ;compara dx com bx
                JAE DODIFJMP1            ;dx >= bx
                MOV AX,POSNULLRES        ;ax = POSNULLRES
                DEC AX                   ;ax - 1
                LEA BX,RESU              ;busca a posicao do RESU no array
                ADD BX,AX                ;bx = bx + ax
                DEC [BX]                 ;bx - 1
                JMP CALCULO2             ;salta para o bloco de codigo calculo2
            DODIFJMP1:
                CMP CX,7                 ;compara cx com 7
                JAE DODIFJMP2            ;cx >= 7
                INC CX                   ;cx + 1
                JMP DODIFLOOP1:          ;salta para o bloco de codigo DODIFLOOP1
            DODIFJMP2:
                CALL DIFARRAYS           ;chama o bloco de codigo DIFARRAYS
    VERFIM:
        XOR CX,CX                        ;cx = 0
        VERFIMLOOP:
            LEA BX,NUM                   ;busca a posicao do NUM no array
            ADD BX,CX                    ;bx = bx + cx
            MOV DX,[BX]                  ;dx = bx
            LEA BX,DEN                   ;busca a posicao do DEN no array
            ADD BX,CX                    ;bx = bx + cx
            CMP DX,[BX]                  ;compara dx com bx
            JA HONCAL                    ;dx > bx
            CMP CX,7                     ;compara cx com 7
            JE PRINTRESULT               ;cx = 7
            INC CX                       ;cx + 1
            JMP VERFIMLOOP               ;salta para o bloco de codigo VERFIMLOOP
            
            
                
    PRINTRESULT:
        LEA DX,TXTRES                    ;busca a posicao do TXTRES no array
        MOV AH,09H                       ;ah = 09H (09H escreve uma string na consola)
        INT 21H                          ;ativa o dos e vai buscar a funcao que esta no AH
        XOR CX,CX                        ;cx = 0
        PRINTRESULT1:
            LEA BX,RESU                  ;busca a posicao do RESU no array
            ADD BX,CX                    ;bx = bx + cx
            MOV DX,[BX]                  ;dtx = bx
            ADD DX,48                    ;dx = dx + 48  (para tornar num caracter)
            MOV AH,09H                   ;ah = 09H (09H escreve uma string na consola)
            INT 21H                      ;ativa o dos e vai buscar a funcao que esta no AH
            INC CX                       ;cx + 1
            CMP CX,8                     ;compara cx com 8
            JB PRINTRESULT1              ;cx < 8
    
MAIN ENDP     

LEITURANUM PROC   
    ;escrever TXTNUM na consola       
    LEA DX,TXTNUM                        ;busca a posicao do TXTNUM no array
    MOV AH,09H                           ;ah = 09H (09H escreve uma string na consola)
    INT 21H                              ;ativa o dos e vai buscar a funcao que esta no AH   
    
    XOR CX,CX                            ;CX = 0
    PEDIRNUM:
        MOV AH,01H                       ;le o valor do input para AH
        INT 21H
        CMP AL,0DH                       ;verifica se valor lido e enter 
        JE GUARDARNUM                    ;se for vai guardar todos os valores na stack para array
        
        CMP AL,30H                       ;verifica se valor e menor que 0 na tabela ASCII
        JB ERRO1 
                                
        CMP AL,39H                       ;verifica se valor e maior que 9 na tabela ASCII
        JA ERRO1
        
        ADD AL,48                        ;AL - 48 (tornar o caracter num numero)
        XOR AH,AH         
        PUSH AX                          ;adiciona o valor lido a pilha
        JMP PEDIRNUM                     ;salta para o bloco de codigo PEDIRNUM
    
    ;imprime msg de erro e faz nova leitura    
    ERRO1:                               
        MOV AH,9H                        ;escreve uma string
        LEA DX,TXTERRO1                  ;busca a posicao do TXTERRO1 no array
        INT 21H                          ;ativa o dos e vai buscar a funcao que esta no AH
        JMP PEDIRNUM                     ;salta para o bloco de codigo PEDIRNUM
     
    
    ;tira o valor da pilha e poem no array               
    GUARDARNUM:
        XOR AX,AX                        ;ax = 0
        XOR BX,BX                        ;bx = 0
        MOV CX,7                         ;cx = 0
        XOR DX,DX                        ;dx = 0
        GUARDARNUM1:
            LEA BX,NUM                   ;busca a posicao do NUM no array
            ADD BX,CX                    ;bx = bx + cx
            POP [BX]                     ;remove o valor da  pilha e poem no array
            DEC CX                       ;cx - 1
            CMP CX,0                     ;compara ax com 7
            JAE GUARDARNUM1              ;ax <= 7
LEITURANUM ENDP    
               
               
LEITURADEN PROC   
    LEA DX,TXTDEN                        ;busca a posicao do TXTDEN no array
    MOV AH,09H                           ;escreve uma string
    INT 021H                             ;ativa o dos e vai buscar a funcao que esta no AH
    XOR CX,CX                            ;cx = 0
    LEA BX,ARR                           ;busca a posicao do ARR no array
    PEDIRDEN:
        MOV AH,01H                       ;le o valor do input para AH
        INT 21H                          ;ativa o dos e vai buscar a funcao que esta no AH
        
        CMP AL,0DH                       ;verifica se valor lido e enter 
        JE GUARDARDEN                    ;se for vai guardar todos os valores da stack para array
        
        CMP AL,30H
        JB ERRO1                         ;verifica se valor e menor que 0 na tabela ASCII
        
        CMP AL,39H
        JA ERRO1                         ;verifica se valor e maior que 9 na tabela ASCII
                 
        ADD AL,48                        ;AL - 48 (tornar o caracter num numero)
        XOR AH,AH         
        PUSH AX                          ;adiciona o valor lido a pilha
        JMP PEDIRNUM                     ;salta para o bloco de codigo PEDIRNUM
    
    ;imprime msg de erro e faz nova leitura    
    ERRO2: 
        MOV AH,9H                        ;escreve uma string
        LEA DX,TXTERRO1                  ;busca a posicao do TXTERRO1 no array
        INT 21H                          ;ativa o dos e vai buscar a funcao que esta no AH
        JMP PEDIRDEN                     ;salta para o bloco de codigo PEDIRDEN
                   
    ;tira o valor da pilha e poem no array               
    GUARDARDEN:
        XOR AX,AX                        ;ax = 0
        XOR BX,BX                        ;bx = 0
        MOV CX,7                         ;cx = 0
        XOR DX,DX                        ;dx = 0
        GUARDARDEN1:
            LEA BX,DEN                   ;busca a posicao do DEN no array
            ADD BX,CX                    ;bx = bx + cx
            POP [BX]                     ;remove o valor da  pilha e poem no array
            DEC CX                       ;cx - 1
            CMP CX,0                     ;compara ax com 7
            JAE GUARDARDEN1              ;ax <= 7
LEITURADEN ENDP        

DIFARRAYS PROC 
     ;definir variaveis
     COUNT DW 0                          
     AUX3 DB 8 DUP
     CAR DB 8 DUP(0)
     NU DW 0
     CALCULO:
        MOV CX,7                         ;cx = 7
        CALCULOLOOP:
            LEA BX,DEN                   ;busca a posicao do DEN no array
            ADD BX,CX                    ;bx = bx + cx
            MOV DX,[BX]                  ;dx = bx
            MOV NU,DX                    ;nu = dx
            MOV COUNT,0                  ;count = 0
            
            CALCULOLOOP2:
            LEA BX,NUM                   ;busca a posicao do NUM no array
            ADD BX,CX                    ;bx = bx + cx
            MOV AX,NU                    ;ax = nu
            CMP AX,[BX]                  ;compara ax com bx
            JE FIM                       ;ax = bx
            INC COUNT                    ;count + 1
            MOV AX,9                     ;ax = 9 (9 para ver se tem dois digitos ou apenas um)
            CMP NU,AX                    ;compara NU com Ax
            JE JUMP1                     ;NU = AX
            JNE JUMP2                    ;NU != AX
        ;2.6.3       
        JUMP1:
            MOV NU,0                     ;nu = 0
            LEA BX,CAR                   ;busca a posicao do CAR no array
            ADD BX,CX                    ;BX = BX + CX
            DEC BX                       ;BX - 1
            INC [BX]                     ;[BX] + 1
            JMP CALCULOLOOP2             ;salta para o bloco de codigo CALCULOLOOP2
        JUMP2:
            INC NU                       ;NU + 1
            JMP CALCULOLOOP2             ;salta para o bloco de codigo CALCULOLOOP2
        ;2.7       
        FIM:
            LEA BX,CAR                   ;busca a posicao do CAR no array
            ADD BX,CX                    ;bx = bx + cx
            MOV COUNT,AX                 ;count = ax
            SUB AX,[BX]                  ;ax - bx
            LEA BX,AUX3                  ;busca a posicao do AUX3 no array
            ADD BX,CX                    ;bx = bx + cx
            MOV [BX],AX                  ;bx = ax
            CMP CX,0                     ;compara cx com 0
            JNE FIM1                     ;cx != 0
            JE FIM2                      ;cx = 0
        FIM1:
            DEC CX                       ;cx - 1
            JMP CALCULOLOOP              ;salta para o bloco de codigo CALCULOLOOP
        FIM2:
            XOR CX,CX                    ;cx = 0
            FIMLOOP:
                LEA BX,AUX               ;busca a posicao do AUX no array
                ADD BX,CX                ;bx = bx + cx
                MOV DX,[BX]              ;dx = bx
                LEA BX,NUM               ;busca a posicao do NUM no array
                ADD BX,CX                ;bx = bx + cx
                MOV [BX],DX              ;bx = dx
                INC CX                   ;cx + 1
                CMP CX,7                 ;compara cx com 7
                JBE FIMLOOP              ;cx <= 7
            
DIFARRAYS ENDP                   