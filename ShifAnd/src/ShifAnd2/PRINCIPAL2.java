package ShifAnd2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.Scanner;

public class PRINCIPAL2 {

	public static void main(String[] args) {
		
     Scanner scan = new Scanner(System.in);
	
     
//////////////////////////////////*************************** DECLARANDO AS VARIAVEIS ********************************///////////////////////////////////////////	    
     
     	
     
        //Declarando as variaveis utlizadas no codigo.
		int posicao = 256,c=32,i,j,padraoencontrado=0;
		int L;//LINHA
		int col;//col
		char[] alfabeto = new char[posicao];
		String texto = "",padrao; 
	
	
		

		

//////////////////////////////////***************************   LE O ARQUIVO E UMA VARIAVEL RECEBE O TEXTO DO ARQUIVO  ***************************//////////////////////////////////		
		
		File arquivo = new File("arquivo.txt");
		
		//Funcao para criar arquivo e a variavel texto recebe o que tem escrito no arquivo.
		try {
			FileReader ler = new FileReader(arquivo);
			BufferedReader	lerb = new BufferedReader(ler);
			
			String arq = lerb.readLine();
			
			while(arq != null) {
				texto = texto + arq;
				arq = lerb.readLine();
				
			}
			
			
		}catch(IOException ex){
			
		}
		System.out.println(texto);
		
		//Alfabeto recebe a tabela ASCII.
		for (i = 0; i < posicao; i++)
		{
			alfabeto[i] = (char) c;
			c++;
		}
		
		
//////////////////////////////////*************************** DECLARANDO AS VARIAVEIS ***************************//////////////////////////////////			
		
		
		
		//Declaracao das matrizes principais.
		int[][][] matrizfinal;
		int[][] alfabetoxpadrao = new int[posicao][];//Matriz do alfabeto x padrao.
		int[][] ro_beat; //Matriz do ro beat a beat.
		int[][] ro_atual;// Matriz do ro atual.
		int[][] m_meio;//Matriz do meio.
		
		
		System.out.print("Informe um padrao: ");//Pegando o padrao digitado pelo usuario.
		padrao = scan.nextLine();//Guardando o padrao.
		
		//Declarando as variaveis. 
		char[] caracteres_alfabeto = padrao.toCharArray();
		char[] caracteres_texto = texto.toCharArray();
		
		//Declarando as matrizes que serao utlizados no metodo SHIF_AND.
		ro_beat = new int[texto.length()][padrao.length()];
		m_meio = new int[texto.length()][padrao.length()]; 
		ro_atual = new int[texto.length()][padrao.length()];
		alfabetoxpadrao = new int[posicao][padrao.length()];
		
		
		
//////////////////////////////////*************************** MATRIZ DO ALFABETO X PELO PADRAO ***************************//////////////////////////////////		
		
		//Preenchendo a primeira tabela // alfabeto x padrao
		for(L=0;L<posicao;L++) {
			for(col=0;col<padrao.length();col++) {
				if(caracteres_alfabeto[col] == alfabeto[L]) // Se o caracter do alfabeto for igual ao do padrao ele entre no laco e recebe 1 se nao recebe 0.
					alfabetoxpadrao[L][col] = 1; //Coloca na matriz ALFABETO X PADRAO O NUMERO 1 .
				else
					alfabetoxpadrao[L][col] = 0; //Coloca na matriz ALFABETO X PADRAO O NUMERO 0 . 
			}
		}
		
		//Primeira tabela 
		System.out.print("\n\n PRIMEIRA TABELA \n\n");
		System.out.print("ALFABETO PELO PADRAO \n ");
		
		//Imprimindo a tabela do alfabeto x padrao .
		for(L=0;L<posicao;L++) {
			for(col=0;col<padrao.length();col++) {
				System.out.print(alfabetoxpadrao[L][col]);
			}
			System.out.print("\n");//Esse comando solta uma linha. 
		}
		
		
		
//////////////////////////////////*************************** MATRIZ DO MEIO M[I] ***************************////////////////////////////////////
		
		
		// Tabela do meio. 
		//Os lacos abaixo aloca a matriz m_meio de acordo com o texto digitado.
		for(L=0;L<texto.length();L++) {
			for(col=0;col<posicao;col++) {
				if(caracteres_texto[L] == alfabeto[col]) //Se o carcter pegado que estiver passando no laco for igual ao elemento do alfabeto pertencente a tablela ASCII,
					for(j=0;j<padrao.length();j++) {
						m_meio[L][j] = alfabetoxpadrao[col][j]; // a tabela do meio que e a M[i] r,ecebe um valor que esta na tabela do alfabeto x padrao.
					}
			}
		}
		
		
		
		
		      	
		
//////////////////////////////////*************************** MATRIZ RO BEAT A BEAT ***************************//////////////////////////////////
		
		
		//As 3 Ls abaixo aloca a primeira posicao do ra_and.
		ro_beat[0][0] = 1;
		for(col=1;col<padrao.length();col++) {
			ro_beat[0][col] = 0; //Funciona especialmente para a primeira linha da matriz .
		}
		
		
		
		//Laco para alocar ro_atual e usar o metodo shiftand no ro_beat.
		for(L=1;L<texto.length();L++) {
			for(col=0;col<padrao.length();col++) {
				ro_atual[L-1][col] = ro_beat[L-1][col]*m_meio[L-1][col]; //o ro_atual recebe o ro_BEAT A BEAT multiplicado pela coluna do meio que a matriz M[I].
																		  //OBS : Pega da primeira linha pra frente.
			}
				for(j=padrao.length()-1;j>0;j--)/*Vai ta no ultimo numero do padrao , exemplo :se o tiver 4 numeros recebe as 3 primeiras  posicoes do numero*/ {
				ro_beat[L][j] = ro_atual[L-1][j-1]; //Pega sempre as primeiras posicoes eliminando a ultima 
			}
			ro_beat[L][0] = 1; //O ro_BEAT A BEAT recebe 1 na primeira posicao,completando assim o beat a beat .
		}
		
		
		//Essa linha pega a ultima posicao do ro_ATUAL pois no laco nao entrava na ultima posicao.
		ro_atual[texto.length()-1][padrao.length()-1] = ro_beat[texto.length()-1][padrao.length()-1]*m_meio[texto.length()-1][padrao.length()-1];
		

		
//////////////////////////////////*************************** CONFERINDO SE O PADRAO FOI ENCONTRADO  ***************************//////////////////////////////////		
		
		
		
		//Laco para conferir onde o metodo shiftand achou o padrao e tranformar os caracteres em maiusculos.
		for(L=0;L<texto.length();L++) {
			for(col=0;col<padrao.length();col++) {
				if(ro_atual[L][padrao.length()-1] == 1) {//Essa estrutura condicional foi usada para verificar se achou o padrao.
					padraoencontrado=1;//A variavel vai receber 1 , e significa que o padrao foi encontrado.
					for(j=L;j>L-padrao.length();j--) {
						caracteres_texto[j] = Character.toUpperCase(caracteres_texto[j]); // Character.toUpperCase (caracteres_texto[j])->essa funcao transforma o padrao em maiuscula.
					
					}	
				}
			}
		}
		
	
        //Testando a variavel criada para ver se o padrao foi encontrado.
		if(padraoencontrado !=1) {
			System.out.println("\n O Padrão nao foi encontrado  ");//Exibindo a mensagem.
			System.exit(0); //Comando usado para sair do programa .
				}
		
		
		

//////////////////////////////////*************************** PASSANDO O PADRAO PARA MAIUSCULO  ***************************//////////////////////////////////			
		
		
		
		
		
		//Declarando a string a ser mudada para maiuscula de acordo com o padrao.
		String texto_atualizado="";
		texto_atualizado = String.valueOf(caracteres_texto);// String.valueOf(caracteres_texto) -> esse comando transforma pega a variavel e transforma em string.
		
		

		
		
		


		
		
		
//////////////////////////////////***************************   REESCREVENDO SOBRE O ARQUIVO  ***************************//////////////////////////////////				
		
		
		
		//A funcao abaixo foi criada para modificar o arquivo ja existente. 
		
		try {
			
			                              //(..//caminho do arquivo)
			FileWriter fileWriter = new FileWriter(arquivo);
			BufferedWriter escrever = new BufferedWriter(fileWriter);
			
			escrever.write(texto_atualizado);
			escrever.close();
			fileWriter.close();		
			
		}
		
		catch(IOException ex){
			
		}
		
		
		
///////////////*************************   EXIBINDO  A  MATRIZ DO BEAT A BEAT , O TEXTO FINAL JA MODIFICADO E SAINDO DO PROGRAMA   ********************///////////////////////
		
		System.out.print(" \n\n TABELA FINAL  :  ");
		
		
		
								//[LINHA][COLUNA][COMPRIMENTO]     
		matrizfinal=new int [texto.length()][3][padrao.length()];
		int cont;
		
		System.out.print("\n\n\n\n\n");
		for(L=0;L<texto.length();L++) {
			for(col=0;col<3;col++) {
				for(cont=0;cont<padrao.length();cont++) {
					matrizfinal[L][col][cont]=ro_beat[L][cont]; //primeira linha
					matrizfinal[L][1][cont]=m_meio[L][cont];    //segunda linha 
					matrizfinal[L][2][cont]=ro_atual[L][cont];  //terceira linha
				}
			}
		}
		
		//imprimindo a matriz final
		System.out.print("\nTABELA R0<<1");
		System.out.print("\t\tTABELA M[I]");
		System.out.print("\t\tTABELA RO ATUAL \n");
		for(L=0;L<texto.length();L++) {
			for(col=0;col<3;col++) {
				for(cont=0;cont<padrao.length();cont++) {
					System.out.print(matrizfinal[L][col][cont]);
				}
				System.out.print("\t\t\t");
			}
			System.out.print("\n");
		}
		

	
		//Exibindo o texto final , se o padrao foi encontrado a pesquisa correspondente ja sai com a letra maiuscula.
		System.out.println("\n\n\n");
		System.out.println(texto_atualizado);
		System.exit(0);// Comando que finaliza o programa.
	}

}
