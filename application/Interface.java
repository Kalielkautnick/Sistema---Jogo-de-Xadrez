package application;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;




public class Interface {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
  public static String peoesPretosCap;
	public static String peoesBrancosCap;
  public static String cavalosPretosCap;
	public static String cavalosBrancosCap;
  public static String torresPretasCap;
	public static String torresBrancasCap;
  public static String bisposPretosCap;
	public static String bisposBrancosCap;
  public static String damaPretaCap;
	public static String damaBrancaCap;
  public static String pecasCap;
  public static char[][] vetorPecasCap;
	

	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosicaoXadrez lerPosicaoXadrez (Scanner teclado) {
		try {
			//AQUI O SISEMA VAI RECEBER A POSIÇÃO ESCOLHIDA PELO USUÁRIO, COM TOLOWERCASE PARA ACEITAR MAISCULO OU MINUSCULO
	        String s = teclado.nextLine().toLowerCase();
		    char coluna = s.charAt(0);
		    int linha = Integer.parseInt(s.substring(1));
		    return new PosicaoXadrez(coluna, linha);
		}
		//SE FOR UMA POSIÇÃO FORA DAS POSIÇÕES EXISTENTES NO TABULEIRO, CHAMA A EXCEÇÃO
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro nas posições do tabuleiro");
		}
	}
	
	public static void imprimirPartida (PartidaXadrez partidaXadrez, List<PecaXadrez> pecasCap) {
		//COMEÇA IMPRIMINDO O ARRAY LIST DE PEÇAS CAPTURADAS EM TEMPO REAL
		imprimirPecasCapturadas(pecasCap);
		System.out.println();
		if (!partidaXadrez.getChequeMate()) {
		if (partidaXadrez.getJogadorAtual() == Cor.BRANCA) {
			if (partidaXadrez.getCheque() == true) {
				//IMPRIME O TABULEIRO NUM VISUAL DIFERENTE, PARA DESTACAR O CHEQUE
				imprimirTabuleiroEmCheque(partidaXadrez.getPecas());
			} else {
		imprimirTabuleiro(partidaXadrez.getPecas());
			}
		} 
		else {	
			if (partidaXadrez.getCheque() == true) {
				//IMPRIME O TABULEIRO EM VERMELHO, MAS NA VISÃO DAS PEÇAS PRETAS
				imprimirTabuleiroInvertidoEmCheque(partidaXadrez.getPecas());
			} else {
			imprimirTabuleiroInvertido(partidaXadrez.getPecas());
			}
		}
		
		System.out.println();
		
		if (partidaXadrez.getJogadorAtual() == Cor.BRANCA) {
		System.out.println("Jogada: " + ANSI_YELLOW+partidaXadrez.getVez()+ANSI_RESET);
		System.out.println("Jogam as : " + ANSI_YELLOW+ partidaXadrez.getJogadorAtual()+"S"+ANSI_RESET);
		}
		else {
			System.out.println("Jogada: " + ANSI_RED+partidaXadrez.getVez()+ANSI_RESET);
			System.out.println("Jogam as: " + ANSI_RED+ partidaXadrez.getJogadorAtual()+"S"+ANSI_RESET);
		}
		} 
		//AQUI ACABA O IF DO CHEQUE, ENTÃO SE A PEÇA NÃO ESTÁ EM CHEQUE, NEM EM POSIÇÃO NORMAL, ELA ESTÁ EM CHEQUE MATE
		//E IMPRIME O CHEQUE MATE NA VISÃO DAS PEÇAS QUE VENCERAM, QUE SERIA O JOGADOR ATUAL
		else {
			System.out.println(ANSI_PURPLE+"              CHEQUE-MATE!"+ANSI_RESET);
			if (partidaXadrez.getJogadorAtual() == Cor.BRANCA) {
				imprimirTabuleiroEmChequeMate(partidaXadrez.getPecas());
				System.out.println();
				System.out.println(ANSI_PURPLE +"VITORIA DAS : " + ANSI_YELLOW+ partidaXadrez.getJogadorAtual()+"S"+ANSI_RESET + " POR " + ANSI_PURPLE + "CHEQUE-MATE!");
				System.out.println("Jogadas: " + ANSI_YELLOW+partidaXadrez.getVez()+ANSI_RESET);
				
			} else {
				imprimirTabuleiroInvertidoEmChequeMate(partidaXadrez.getPecas());
				System.out.println();
				System.out.println(ANSI_PURPLE +"VITORIA DAS : " + ANSI_RED+ partidaXadrez.getJogadorAtual()+"S"+ANSI_RESET + " POR " + ANSI_PURPLE + "CHEQUE-MATE!");
			}
		}
	}
	
	public static void imprimirTabuleiroEmChequeMate(PecaXadrez[][] pecas) {
		System.out.println(" "+ANSI_PURPLE_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
		System.out.println(" " + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET+ANSI_PURPLE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 0; i < pecas.length; i++) {
			System.out.print(" ");
			System.out.print(ANSI_PURPLE_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_PURPLE+":"+ANSI_RESET);
			for (int j = 0; j < pecas[i].length; j++) {
				imprimirPecasPretasEmChequeMate(pecas[i][j]);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_PURPLE_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i < (pecas.length -1)) {
				//ENQUANTO ESTIVER NAS LINHAS DO MEIO, ELE IMPRIME COM CONEXÃO 
				//ENTRE AS LINHAS DE CIMA E DE BAIXO
				
			System.out.println(" " + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET+ANSI_PURPLE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET+ANSI_PURPLE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_PURPLE_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
			}
		}
	}
	
	public static void imprimirTabuleiroInvertidoEmChequeMate(PecaXadrez[][] pecas) {
		System.out.println(" "+ANSI_PURPLE_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
		System.out.println(" " + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET+ANSI_PURPLE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 7; i >= 0; i--) {
			System.out.print(" ");
			System.out.print(ANSI_PURPLE_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_PURPLE+":"+ANSI_RESET);
			for (int j = 7; j >= 0; j--) {
				imprimirPecasBrancasEmChequeMate(pecas[i][j]);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_PURPLE_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i > (0)) {
				//ENQUANTO ESTIVER NAS LINHAS DO MEIO, ELE IMPRIME COM CONEXÃO 
				//ENTRE AS LINHAS DE CIMA E DE BAIXO
				
			System.out.println(" " + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET+ANSI_PURPLE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET+ANSI_PURPLE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_PURPLE_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_PURPLE_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
			}
		}
	}

	public static void imprimirTabuleiro(PecaXadrez[][] pecas) {
		System.out.println(" "+ANSI_BLUE_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
		System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 0; i < pecas.length; i++) {
			System.out.print(" ");
			System.out.print(ANSI_BLUE_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_BLUE+":"+ANSI_RESET);
			for (int j = 0; j < pecas[i].length; j++) {
				imprimirPeca(pecas[i][j], false);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_BLUE_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i < (pecas.length -1)) {
				//ENQUANTO ESTIVER NAS LINHAS DO MEIO, ELE IMPRIME COM CONEXÃO 
				//ENTRE AS LINHAS DE CIMA E DE BAIXO
				
			System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_BLUE_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
			}
		}
	}
	
	public static void imprimirTabuleiro(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		
		System.out.println(" "+ANSI_BLUE_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
		System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 0; i < pecas.length; i++) {
			System.out.print(" ");
			System.out.print(ANSI_BLUE_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_BLUE+":"+ANSI_RESET);
			for (int j = 0; j < pecas[i].length; j++) {
				imprimirPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_BLUE_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i < (pecas.length -1)) {
				
			System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_BLUE_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
			}
		}
	}
	
	public static void imprimirTabuleiroEmCheque(PecaXadrez[][] pecas) {
		System.out.println(ANSI_RED+"                CHEQUE!"+ANSI_RESET);
		System.out.println(" "+ANSI_RED_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
		System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 0; i < pecas.length; i++) {
			System.out.print(" ");
			System.out.print(ANSI_RED_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_RED+":"+ANSI_RESET);
			for (int j = 0; j < pecas[i].length; j++) {
				imprimirPecaEmCheque(pecas[i][j], false);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_RED_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i < (pecas.length -1)) {
				
			System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_RED_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
			}
		}
	}
	

	
	public static void imprimirTabuleiroInvertido(PecaXadrez[][] pecas) {
		System.out.println(" "+ANSI_BLUE_BACKGROUND+"  "+ANSI_BLACK+"  h   g   f   e   d   c   b   a    "+ANSI_RESET);
		System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 7; i >= 0; i--) {
			System.out.print(" ");
			System.out.print(ANSI_BLUE_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_BLUE+":"+ANSI_RESET);
			for (int j = 7; j >= 0; j--) {
				imprimirPeca(pecas[i][j], false);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_BLUE_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i > (0)) {
				
			System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_BLUE_BACKGROUND+"  "+ANSI_BLACK+"  h   g   f   e   d   c   b   a    "+ANSI_RESET);
			}
		}
	}
	
	
	public static void imprimirTabuleiroInvertido(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		System.out.println(" "+ANSI_BLUE_BACKGROUND+"  "+ANSI_BLACK+"  h   g   f   e   d   c   b   a    "+ANSI_RESET);
		System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 7; i >= 0; i--) {
			System.out.print(" ");
			System.out.print(ANSI_BLUE_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_BLUE+":"+ANSI_RESET);
			for (int j = 7; j >= 0; j--) {
				imprimirPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_BLUE_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i > (0)) {
				
			System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET+ANSI_BLUE+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_BLUE_BACKGROUND+"  "+ANSI_BLACK+"  h   g   f   e   d   c   b   a    "+ANSI_RESET);
			}
		}
	}
	
	public static void imprimirTabuleiroEmCheque(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		
		System.out.println(ANSI_RED+"                CHEQUE!"+ANSI_RESET);
		System.out.println(" "+ANSI_RED_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
		System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 7; i >= 0; i--) {
			System.out.print(" ");
			System.out.print(ANSI_RED_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_RED+":"+ANSI_RESET);
			for (int j = 7; j >=0 ; j--) {
				imprimirPecaEmCheque(pecas[i][j], movimentosPossiveis[i][j]);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_RED_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i < (pecas.length -1)) {
				
			System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_RED_BACKGROUND+"  "+ANSI_BLACK+"  a   b   c   d   e   f   g   h    "+ANSI_RESET);
			}
		}
	}
	
	public static void imprimirTabuleiroInvertidoEmCheque(PecaXadrez[][] pecas) {
		System.out.println(ANSI_RED+"                CHEQUE!"+ANSI_RESET);
		System.out.println(" "+ANSI_RED_BACKGROUND+"  "+ANSI_BLACK+"  h   g   f   e   d   c   b   a    "+ANSI_RESET);
		System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 7; i >= 0; i--) {
			System.out.print(" ");
			System.out.print(ANSI_RED_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_RED+":"+ANSI_RESET);
			for (int j = 7; j >=0 ; j--) {
				imprimirPecaEmCheque(pecas[i][j], false);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_RED_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i > (0)) {
				
				System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_RED_BACKGROUND+"  "+ANSI_BLACK+"  h   g   f   e   d   c   b   a    "+ANSI_RESET);
			}
		}
	}
	
	public static void imprimirTabuleiroInvertidoEmCheque(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		System.out.println(ANSI_RED+"                CHEQUE!"+ANSI_RESET);
		System.out.println(" "+ANSI_RED_BACKGROUND+"  "+ANSI_BLACK+"  h   g   f   e   d   c   b   a    "+ANSI_RESET);
		System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
		for (int i = 7; i >= 0; i--) {
			System.out.print(" ");
			System.out.print(ANSI_RED_BACKGROUND+" "+ ANSI_BLACK+ (8-i) +ANSI_RESET+ANSI_RED+":"+ANSI_RESET);
			for (int j = 7; j >=0 ; j--) {
				imprimirPecaEmCheque(pecas[i][j], movimentosPossiveis[i][j]);
			}
			//QUANDO CHEGA NO FINAL DA LINHA, SÓ QUEBRA A LINHA
			System.out.println(ANSI_RED_BACKGROUND + " "+ ANSI_BLACK+ (8-i) +ANSI_RESET + ANSI_RESET);
			
			// EM SEGUIDA VEM AS DIVISÓRIAS DE CASAS DO TABULEIRO
			if (i > (0)) {
				
			System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
			} else {

				System.out.println(" " + ANSI_RED_BACKGROUND + "  " + ANSI_RESET+ANSI_RED+"+---+---+---+---+---+---+---+---+"+ANSI_RESET + ANSI_RED_BACKGROUND + "  " + ANSI_RESET);
				System.out.println(" "+ANSI_RED_BACKGROUND+"  "+ANSI_BLACK+"  h   g   f   e   d   c   b   a    "+ANSI_RESET);
			}
		}
	}
	
	private static void imprimirPeca (PecaXadrez peca, boolean background) {

		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND+" ");
		} else {
			System.out.print(" ");
		}
		
		//OU ELE IMPRIME UM ESPAÇO EM BRANCO, OU IMPRIME UMA PEÇA, E DEPOIS DELA,
			//IMPRIME A DIVISÓRIA COM A CASA DO LADO DIREITO
	if (peca == null) {
		System.out.print(" " + ANSI_RESET);
	} else {
		if (peca.getCor() == Cor.BRANCA) {
			//SE A PEÇA FOR BRANCA, ELE IMPRIME BRANCO NO CONSOLe
			System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
		}
		else {
			//SE A PEÇA FOR PRETA, ELE IMPRIME ELA EM AMARELO
			System.out.print(ANSI_RED + peca + ANSI_RESET);
		}
	}
	
	if (background) {
		System.out.print(ANSI_BLUE_BACKGROUND+" "+ ANSI_RESET); 
	}	else {
		System.out.print(" ");
	} 
	System.out.print(ANSI_BLUE+":"+ANSI_RESET);
		
	}
	
	private static void imprimirPecaEmCheque (PecaXadrez peca, boolean background) {

		if (background) {
			System.out.print(ANSI_RED_BACKGROUND+" ");
		} else {
			System.out.print(" ");
		}
		
		//OU ELE IMPRIME UM ESPAÇO EM BRANCO, OU IMPRIME UMA PEÇA, E DEPOIS DELA,
			//IMPRIME A DIVISÓRIA COM A CASA DO LADO DIREITO
	if (peca == null) {
		System.out.print(" " + ANSI_RESET);
	} else {
		if (peca.getCor() == Cor.BRANCA) {
			//SE A PEÇA FOR BRANCA, ELE IMPRIME BRANCO NO CONSOLe
		
			System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			
		
		}
		else {
			//SE A PEÇA FOR PRETA, ELE IMPRIME ELA EM AMARELO
			
			System.out.print(ANSI_RED + peca + ANSI_RESET);
			
		}
	}
	
	 if (background) {
		System.out.print(ANSI_RED_BACKGROUND+" "+ ANSI_RESET);
	}
		else {
		System.out.print(" ");
	} 
	System.out.print(ANSI_RED+":"+ANSI_RESET);
		
	}
	
	private static void imprimirPecasBrancasEmChequeMate (PecaXadrez peca) {

	if (peca == null) {
		System.out.print(ANSI_PURPLE+ "  " + ANSI_RESET);
	} else {
		if (peca.getCor() == Cor.BRANCA) {
			//SE A PEÇA FOR BRANCA, ELE IMPRIME BRANCO NO CONSOLe
			if (peca.toString().equals("R") && peca.getCor() == Cor.PRETA) {
				System.out.print(ANSI_PURPLE_BACKGROUND+" "+ANSI_WHITE + peca);
			} else {
			System.out.print(" "+ ANSI_YELLOW + peca + ANSI_RESET);
			}
		
		}
		else {
			//SE A PEÇA FOR PRETA, ELE IMPRIME ELA EM VERMELHO
			System.out.print(ANSI_RED + peca + ANSI_RESET);
		}
	}
	
	
	System.out.print(" "+ANSI_RESET);
	
	 
	System.out.print(ANSI_PURPLE+":"+ANSI_RESET);
		
	}
	
	private static void imprimirPecasPretasEmChequeMate (PecaXadrez peca) {

if (peca == null) {
	System.out.print(ANSI_PURPLE+ "  " + ANSI_RESET);
} else {
	if (peca.getCor() == Cor.BRANCA) {	
		System.out.print(" "+ ANSI_YELLOW + peca + ANSI_RESET);
	}
	else {
		//SE A PEÇA FOR PRETA, ELE IMPRIME ELA EM VERMELHO, MAS SE FOR O REI, IMPRIME ELE EM BRANCO
		if (peca.toString().equals("R") && peca.getCor() == Cor.PRETA) {
			System.out.print(ANSI_PURPLE_BACKGROUND+" "+ANSI_WHITE + peca);
		} else {
		System.out.print(" "+ ANSI_RED + peca + ANSI_RESET);
		}
	}
}

System.out.print(" "+ANSI_RESET);
System.out.print(ANSI_PURPLE+":"+ANSI_RESET);
	
}
	
	public static void imprimirPecasCapturadas(List<PecaXadrez> pecasCap) {
		//AQUI ELE FILTRA TODAS AS PEÇAS BRANCAS E PRETAS CAPTURADAS DURANTE O JOGO
		//EXISTE A LISTA DE TODAS AS PEÇAS CAPTURADAS, MAS AQUI ELE FILTRA POR COR
		List<PecaXadrez> brancasCap = pecasCap.stream().filter(x -> x.getCor() == Cor.BRANCA).collect(Collectors.toList());
		List<PecaXadrez> pretasCap = pecasCap.stream().filter(x -> x.getCor() == Cor.PRETA).collect(Collectors.toList());
		System.out.print(ANSI_BLUE+"Pecas brancas capturadas: ");
		System.out.print(ANSI_RESET);
		System.out.println(ANSI_YELLOW +Arrays.toString(brancasCap.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print(ANSI_BLUE +"Pecas pretas capturadas: ");
		System.out.println(ANSI_RED+Arrays.toString(pretasCap.toArray()));
		System.out.print(ANSI_RESET);
	}
}