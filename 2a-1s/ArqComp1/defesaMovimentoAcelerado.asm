;DEFESA 
.DATA
TXTX0 DB 0AH,0DH,"Inserir X0 : $"                      
TXTV0 DB 0AH,0DH,"Inserir V0 : $"                      
TXTT DB 0AH,0DH,"Inserir T : $"                        
TXTA DB 0AH,0DH,"Inserir A : $"                        
TXTERRO1 DB 0AH,0DH,"Erro: Caracter invalido$"   
TXTRES DB 0AH,0DH,"Resultado : $"  

X0 DW 0               
V0 DW 0
T DW 0 
A DW 0
X DW 0

DEZ DB 10
DOIS DB 2
ARRAY DB 16 DUP

.STACK 100H

.CODE
MAIN PROC 
    
    MOV AX,@DATA     ;carrega as variaveis
    MOV DS,AX        ;igual ao anterior

          
          
    LEITURAX0:
        LEA DX,TXTX0          ;carrega o ponteiro txtx0 no dx
        MOV AH,09H            ;carrega a funcao que  escreve na consola
        INT 21H               ;inicia o dos e corre a funcao carregada em AH
        
        XOR CX,CX             ;cx=0
             
             
             
        PEDIRX0:              ;pedir xo
            MOV AH,01H        ;carrega a funcao que le o proximo input
            INT 21H           ;inicia o dos e corre a funcao carregada em AH
            CMP AL,0DH        ;compara o input anterior para verificar se e um enter
            JE LEITURAV0      ;caso seja passa para a funcao LEITURAV0
            
            CMP AL,30H        ;Verifica se o valor esta abaixo de 0
            JB ERRO1          ;caso seja verdade passa para a a funcao ERRO1
            
            CMP AL,39H        ;Verifica se o valor esta acima de 9
            JA ERRO1          ;caso seja verdade passa para a a funcao ERRO1
            
            SUB AL,30H        ;torna o caracter num numero
            
            XOR AH,AH         ;AH=0
            MOV DX,AX         ;dx=AX
            
            MOV AX,X0         ;AX=x0
            MUL DEZ           ;AX*10
            ADD AX,DX         ;AX+DX
            MOV X0,AX         ;X0=AX
            JMP PEDIRX0       ;passa para a funcao PEDIRX0
            
            
            
    ERRO1:                    ;funcao erro 1
        MOV AH,9H             ;carrega a funcao que escreve uma string na consola
        LEA DX,TXTERRO1       ;carrega o ponteiro txterro1 no dx
        INT 21H               ;inicia o dos e corre a funcao carregada em AH
        JMP LEITURAX0         ;Passa para a funcao LEITURAX0
        
        
        
           
            
    LEITURAV0:                ;Ler v0
        LEA DX,TXTV0          ;carrega o ponteiro txtv0 em dx
        MOV AH,09H            ;carrega a funcao que escreve uma string na consola
        INT 21H               ;inicia a funcao dos e corre a funcao carregada em AH
        
        XOR CX,CX             ;CX = 0
            
            
            
        PEDIRV0:              ;pedir v0
            MOV AH,01H        ;carrega a funcao que le o proximo input
            INT 21H           ;inicia o dos e corre a funcao carregada em AH
            CMP AL,0DH        ;compara o input anterior para verificar se e um enter
            JE LEITURAT
            
            CMP AL,30H
            JB ERRO2
            
            CMP AL,39H
            JA ERRO2
            
            SUB AL,30H
            
            XOR AH,AH
            MOV DX,AX
            
            MOV AX,V0    
            MUL DEZ
            ADD AX,DX
            MOV V0,AX
            JMP PEDIRV0
            
            
            
    ERRO2:
        MOV AH,9H
        LEA DX,TXTERRO1
        INT 21H
        JMP LEITURAV0
         
         
         
    LEITURAT:
        LEA DX,TXTT
        MOV AH,09H
        INT 21H
        
        XOR CX,CX
            
            
            
        PEDIRT:
            MOV AH,01H
            INT 21H  
            CMP AL,0DH
            JE LEITURAA
            
            CMP AL,30H
            JB ERRO3
            
            CMP AL,39H
            JA ERRO3
            
            SUB AL,30H
            
            XOR AH,AH
            MOV DX,AX
            
            MOV AX,T     
            MUL DEZ
            ADD AX,DX
            MOV T,AX
            JMP PEDIRT
            
            
            
    ERRO3:
        MOV AH,9H
        LEA DX,TXTERRO1
        INT 21H
        JMP LEITURAT
         
         
         
         
    LEITURAA:
        LEA DX,TXTA
        MOV AH,09H
        INT 21H
        
        XOR CX,CX
              
              
        PEDIRA:
            MOV AH,01H
            INT 21H  
            CMP AL,0DH
            JE CALCULO
            
            CMP AL,30H
            JB ERRO4
            
            CMP AL,39H
            JA ERRO4
            
            SUB AL,30H
            
            XOR AH,AH
            MOV DX,AX
            
            MOV AX,A     
            MUL DEZ
            ADD AX,DX
            MOV A,AX
            JMP PEDIRA
            
            
            
    ERRO4:
        MOV AH,9H
        LEA DX,TXTERRO1
        INT 21H
        JMP LEITURAA
         
         
         
         
         
    ;X = X0+V0*T+ 1/2 * A * T^2
    CALCULO:
        MOV AX,T    ;ax = t
        MUL AX      ;ax = ax*ax
        MUL A       ;ax= ax*a
        MOV CL,2    ;cl=2
        DIV CL      ;ax= ax/cl
        XOR AH,AH   ;ah=0
        MOV CX,AX   ;cx=ax(valor da multiplicacao a direita)
        MOV AX,V0   ;ax=v0
        MUL T       ;ax=t
        ADD CX,AX   ;cx=cx+ax
        ADD CX,X0   ;RESULTADO EM DX
        MOV X,CX    ;x=cx
         
         
         
         
    PRINTRESU:
        LEA DX,TXTRES
        MOV AH,09H
        INT 21H
        XOR CX,CX
    
         
         
         
    LEA BX,ARRAY
    ADD BX,15
    TOCHAR:
        XOR DL,DL
        MOV AX,X       ; AH = X%10
        DIV DEZ        ; AL = X/10
        MOV b.X,AL
        ADD AH,48
        MOV DL,AH
        XOR DH,DH        
        XOR AH,AH
        MOV [BX],DL
        DEC BX
        CMP X,0         <
        JA TOCHAR
    
           
           
           
        XOR CX,CX
        PRINTRESU1: 
            LEA BX,ARRAY
            ADD BX,CX
            MOV DL,[BX]
            INC CX
            CMP DL,30H
            JB PRINTRESU1
            CMP DL,39H
            JA PRINTRESU1
            MOV AH,02H
            INT 21H
            CMP CX,15
            JBE PRINTRESU1
            

MAIN ENDP 
                     