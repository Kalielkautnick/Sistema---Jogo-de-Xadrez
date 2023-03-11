package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import xadrez.Cor;
import xadrez.ExceptionXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;


public class Program{

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> captured = new ArrayList<>();

		//O PROGRAMA RODA ENQUANTO O REI NÃO ESTIVER EM CHEQUE MATE
		while (!partidaXadrez.getChequeMate()) {
			try {
				//PROGRAMA COMEÇA LIMPANDO A TELA TODA VEZ QUE É FEITO UMA JOGADA, E DEPOIS IMPRIME
				//O TABULEIRO, PEÇAS MORTAS, JOGADOR, ETC
				Interface.limparTela();
				System.out.println(Interface.ANSI_BLUE+ "Jogo de Xadrez: "+ Interface.ANSI_RESET+ "by Kaliel Kautnick");
				Interface.imprimirPartida(partidaXadrez, captured);
				
				System.out.println("");
				System.out.println("Posicao da peca que a ser movida (ex: d2): ");
				PosicaoXadrez source = Interface.lerPosicaoXadrez(sc);

				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(source);
				//AGORA, O SISTEMA LIMPA A TELA NOVAMENTE, E VAI IMPRIMIR O TABULEIRO, DESTA VEZ COM OS MOVIMENTOS 
				//POSSÍVEIS DE CADA PEÇA, UMA MATRIZ BOOLEANA QUE DEIXA UM BACKGROUND COLORIDO NAS POSIÇÕES
				//QUE A PEÇA PODE FAZER.
				Interface.limparTela();
				//AQUI ELE IMPRIME A NOSSA LISTA DE PEÇAS CAPTURADAS, ANTES DE IMPRIMIR O TABULEIRO
				Interface.imprimirPecasCapturadas(captured);
					System.out.println();
				//SE A PEÇA FOR BRANCA, ELE CHAMA A IMPRESSÃO DO TABULEIRO NA VISÃO DAS BRANCAS
				if (partidaXadrez.getJogadorAtual() == Cor.BRANCA) {
					//SE O TABULEIRO ESTIVER EM CHEQUE, ENTÃO ELE CHAMA A IMPRESSÃO DO TABULEIRO EM CHEQUE
					//O TABULEIRO FICA TODO VERMELHO DESTACANDO QUE O JOGO ESTÁ EM CHEQUE
					if (partidaXadrez.getCheque() == true) {
						Interface.imprimirTabuleiroEmCheque(partidaXadrez.getPecas(), movimentosPossiveis);
					} else {
				Interface.imprimirTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);
					}
				} 
				//SE A PECA NÃO FOR BRANCA, ELE IMPRIME O TABULEIRO INVERTIDO, NA VISÃO DAS PRETAS.
				//TAMBÉM TEM A OPÇÃO DE IMPRIMIR EM CHEQUE (VERMELHO) NA VISÃO DAS PRETAS
				else {
					if (partidaXadrez.getCheque() == true) {
						Interface.imprimirTabuleiroInvertidoEmCheque(partidaXadrez.getPecas(), movimentosPossiveis);
					} else {
					Interface.imprimirTabuleiroInvertido(partidaXadrez.getPecas(), movimentosPossiveis);
					}
				}

				System.out.println("");
				System.out.println("Posicao de destino da peca (ex: d4): ");
				PosicaoXadrez destino = Interface.lerPosicaoXadrez(sc);

				PecaXadrez pecaCapturada = partidaXadrez.movimentarPeca(source, destino);

				//AQUI O SISTEMA COMEÇA A ADICIONAR AS PEÇAS CAPTURADAS NO ARRAY LIST
				if (pecaCapturada != null) {
					captured.add(pecaCapturada);
				}
				
				//AQUI ELE TESTA SE EXISTE ALGUMA PEÇA PROMOVIDA, E PEDE PARA O USUÁRIO ESCOLHER QUAL PEÇA IRÁ SUBSTITUIR
				if (partidaXadrez.getPromovida() != null) {
					//A PESSOA PODE ESCOLHER AS LETRAS RESPECTIVAS AS PEÇAS, PARA ENTÃO CHAMAR O MÉTODO E SUBSTITUIR
					//O PEÃO PELA PEÇA ESCOLHIDA
					System.out.print("Escolha a peca para promover:");
					System.out.print(Interface.ANSI_GREEN+"D(dama)"+Interface.ANSI_RESET+" - ");
					System.out.print(Interface.ANSI_CYAN+"C(cavalo)"+Interface.ANSI_RESET+" - ");
					System.out.print(Interface.ANSI_BLUE+"T(torre)"+Interface.ANSI_RESET+" - ");
					System.out.print(Interface.ANSI_RED+"B(bispo)"+Interface.ANSI_RESET+" - ");
					String tipo = sc.nextLine().toUpperCase();
					//ENQUANTO O USUÁRIO NÃO DIGITAR AS OPÇÕES VALIDAS, ELE REPETE
						while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") & !tipo.equals("D")) {
							System.out.print("Peca Invalida! Escolha a peca para promover: ");
							System.out.print(Interface.ANSI_GREEN+"D(dama)"+Interface.ANSI_RESET+" - ");
							System.out.print(Interface.ANSI_CYAN+"C(cavalo)"+Interface.ANSI_RESET+" - ");
							System.out.print(Interface.ANSI_BLUE+"T(torre)"+Interface.ANSI_RESET+" - ");
							System.out.print(Interface.ANSI_RED+"B(bispo)"+Interface.ANSI_RESET+" - ");
						tipo = sc.nextLine().toUpperCase();
					}
					partidaXadrez.alterarPecaPromovida(tipo);
				}
			}
			catch (ExceptionXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		//QUANDO HOUVER UM CHEQUE MATE, ELE VAI SAIR O WHILE, E VAI VIR PARA CÁ
		//IMPRIMINDO NA TELA O TABULEIRO CHEQUE MATE (EM ROXO, E DESTANCANDO O REI MORTO COM BACKGROUND ROXO)
		Interface.limparTela();
		Interface.imprimirPartida(partidaXadrez, captured);

	}

}
