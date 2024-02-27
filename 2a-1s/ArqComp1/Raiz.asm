;RAIZ QUADRADA
.DATA
TXTPEDE DB 0AH,0DH,"Inserir valor : $"
TXTERRO1 DB 0AH,0DH,"ERROR: Caracter invalido$",0DH
TXTRES DB 0AH,0DH,"Resultado : $" 

;Variaveis
POSVIRG DB 10
NPAR DB 0
AUX DW 0
POSNULLRESU DW 0
COUNT DB 0 

DEZ DB 10
DOIS DB 2

;Arrays
RAIZ DB 8 DUP(0)
RESU DB 8 DUP
PARES DB 8 DUP 
ARR DB 8 DUP

.STACK 100H

.CODE
MAIN PROC
    MOV AX,@DATA
	MOV DS,AX 
	
	CALL LEITURA
	;3.1 - 3.3
	LEA BX,RESU
	MOV [BX],0
	MOV NPAR,0
	XOR POSNULLRESU,0 
	CAL2:
	;3.5
	XOR AX,AX
	MOV AL,NPAR
	MUL DOIS
	LEA BX,RAIZ
	ADD BX,AX
	MOV DX,[BX]
	LEA BX,PARES
	ADD BX,AX
	MOV [BX],DX
	ADD AX,1
	LEA BX,RAIZ
	ADD BX,AX
	MOV DX,[BX]
	LEA BX,PARES
	ADD BX,AX
	MOV [BX],DX
	
	;3.6
	XOR CX,CX
	CAL1:
	;3.7
	CALL ARRAYTOINTRES
	
	MUL DOIS
	MUL DEZ
	ADD AX,CX
	MUL CX
	MOV AUX,AX
	
	;3.8
	CMP CX,9
	JE  CALJMP1
	CALL ARRAYTOINTPARS
	CMP AUX,AX
	JBE CALJMP2
	JMP CALJMP1
	CALJMP2:
	    INC CX
	    JMP CAL1
	;3.9
	CALJMP1:
	   CALL DIFARRAYS
	
	;3.10
	LEA BX,RESU
	ADD BX,POSNULLRESU
	MOV [BX],CX
	INC POSNULLRESU
	CMP NPAR,2
	JE  PRINTRESULT
	INC NPAR
	JMP CAL2 	    
	
	;4
	PRINTRESULT:
        LEA DX,TXTRES
        MOV AH,09H
        INT 21H
        XOR CX,CX
        PRINTRESULT1:
            LEA BX,RESU
            ADD BX,CX
            MOV DX,[BX]
            ADD DX,48 
            XOR AX,AX
            MOV AL,POSVIRG
            DIV DOIS
            XOR AH,AH
            CMP AX,CX
            JNE JMPFIM
            MOV DX,2EH
            MOV AX,09H
            INT 21H
            JMP PRINTRESULT1
                         
            JMPFIM:
            MOV AX,09H
            INT 21H
            INC CX
            CMP CX,8
            JB PRINTRESULT1
    RET	
MAIN ENDP

LEITURA PROC 
;escrever TXTNUM na consola
INICIO:       
    LEA DX,TXTPEDE
    MOV AH,09H
    INT 21H 
    
    XOR CX,CX
    LEA BX,RAIZ
    PEDIR:
        MOV AH,01H   ;
        INT 21H
        CMP AL,0DH  ;verifica se valor lido e enter 
        JE GUARDAR  ;se for vai guardar todos os valores na stack para array
        CMP AL,2CH
        JE VIRGULA
        CMP AL,30H
        JB ERRO1    ;verifica se valor e menor que 0 na tabela ASCII
        CMP AL,39H
        JA ERRO1    ;verifica se valor e maior que 9 na tabela ASCII         
        XOR AH,AH
        SUB AX,48
        PUSH AX
        INC BX 
        INC COUNT
        JMP PEDIR
    ;imprime msg de erro e faz nova leitura    
    ERRO1: 
        MOV AH,9H
        LEA DX,TXTERRO1
        INT 21H
        JMP INICIO
    GUARVIRG:
        MOV POSVIRG,CL
        JMP PEDIR 
    VIRGULA:
        CMP CX,0
        JE ERRO1
        CMP CX,7
        JE ERRO1
        CMP POSVIRG,10
        JE GUARVIRG
        JMP PEDIR
    ;tira o valor da pilha e poem no array               
    GUARDAR:
        XOR CX,CX
        MOV AL,POSVIRG
        MOV DL,2
        DIV DL
        CMP AL,1
        JNE GUARDAR1
        XOR AX,AX
        LEA BX,RAIZ
        MOV [BX],AX
        INC CX          
        GUARDAR1:
            LEA BX,RAIZ
            ADD BX,CX
            POP [BX]
            INC CX
            CMP CX,8
            JB GUARDAR1
    RET
LEITURA ENDP 

ARRAYTOINTRES PROC  ;RESULTADO FICA EM AX
    XOR DX,DX
    XOR AX,AX
    
    ;2.3
    CAL:
    MUL DEZ
    LEA BX,RESU
    ADD BX,DX
    ADD AX,[BX]
    
    ;2.4
    INC DX
    CMP DX,8
    JB CAL
    
    XOR BX,BX
    XOR DX,DX
    
    RET    
    
ARRAYTOINTRES ENDP

ARRAYTOINTPARS PROC  ;RESULTADO FICA EM AX
    XOR DX,DX
    XOR AX,AX
    
    ;2.3
    CAL3:
    MUL DEZ
    LEA BX,PARES
    ADD BX,DX
    ADD AX,[BX]
    
    ;2.4
    INC DX
    CMP DX,8
    JB CAL3
    
    XOR BX,BX
    XOR DX,DX
    RET    
    
ARRAYTOINTPARS ENDP

DIFARRAYS PROC     ; PARES E ARR 
    CALL ARRAYTOINTRES
    MUL DOIS
    MUL DEZ
    ADD AX,CX
    MUL CX
    
    CALL INTTOARRAYRES
    
    COUNT1 DW 0
    AUX3 DB 8 DUP
    CAR DB 8 DUP(0)
    NU DW 0
    CALCULO:
        MOV CX,7
        CALCULOLOOP:
            LEA BX,ARR
            ADD BX,CX
            MOV DX,[BX]
            MOV NU,DX
            MOV COUNT1,0
            CALCULOLOOP2:
            LEA BX,PARES
            ADD BX,CX
            MOV AX,NU
            CMP AX,[BX]
            JE FIM
            INC COUNT1
            MOV AX,9
            CMP NU,AX
            JE JUMP1
            JNE JUMP2                
        ;2.6.3       
        JUMP1:
            MOV NU,0
            LEA BX,CAR
            ADD BX,CX
            DEC BX
            INC [BX]
            JMP CALCULOLOOP2
        JUMP2:
            INC NU
            JMP CALCULOLOOP2
        ;2.7       
        FIM:
            LEA BX,CAR
            ADD BX,CX
            MOV COUNT1,AX
            SUB AX,[BX]
            LEA BX,AUX3
            ADD BX,CX
            MOV [BX],AX
            CMP CX,0
            JNE FIM1
            JE FIM2
        FIM1:
            DEC CX
            JMP CALCULOLOOP
        FIM2:
            XOR CX,CX
            FIMLOOP:
                LEA BX,AUX
                ADD BX,CX
                MOV DX,[BX]
                LEA BX,PARES
                ADD BX,CX
                MOV [BX],DX
                INC CX
                CMP CX,7
                JBE FIMLOOP
    RET
    
DIFARRAYS ENDP

INTTOARRAYRES PROC   ;parametro em AX
    MOV CX,7
    
    LOOP1:
        LEA BX,ARR
        ADD BX,CX
        DIV DEZ
        MOV [BX],AH
        XOR AH,AH
        DEC CX
        CMP CX,0
        JAE LOOP1
    RET
INTTOARRAYRES ENDP
    
    
    
    
    