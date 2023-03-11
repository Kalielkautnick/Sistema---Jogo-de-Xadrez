package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pecasXadrez.Bispo;
import pecasXadrez.Cavalo;
import pecasXadrez.Dama;
import pecasXadrez.Peao;
import pecasXadrez.Rei;
import pecasXadrez.Torre;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public class PartidaXadrez {

	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean cheque;
	private boolean chequeMate;
	private PecaXadrez enPassantPossivel;
	private PecaXadrez promovida;
	
	//NA PARTIDA, TEREMOS AS LISTAS DE PEÇAS QUE ESTÃO EM JOGO, E AS PEÇAS CAPTURADAS
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		//TODA PARTIDA DE XADREZ COMEÇA COM UM TABULEIRO DE 8X8, NA JOGADA DE NUMERO 1
		//COM AS PEÇAS BRANCAS COMEÇANDO O JOGO
		tabuleiro = new Tabuleiro(8, 8);
		vez = 1;
		jogadorAtual = Cor.BRANCA;
		tabuleiroInicial();
	}
	
	public int getVez() {
		return vez;
	}
	
	//O JOGADOR ATUAL É UMA COR, QUE RETORNA A COR DAS PEÇAS QUE ESTÃO JOGANDO AGORA.
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheque() {
		return cheque;
	}
	
	public boolean getChequeMate() {
		return chequeMate;
	}
	
	public PecaXadrez getEnPassantVulnerable() {
		return enPassantPossivel;
	}
	
	public PecaXadrez getPromovida() {
		return promovida;
	}
	
	//UM DOS MÉTODOS MAIS IMPORTANTES, RETORNA TODAS AS PEÇAS DE XADREZ DENTRO DA MATRIZ DE PEÇAS XADREZ
	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i < tabuleiro.getLinhas(); i++) {
			for (int j=0; j < tabuleiro.getColunas(); j++) {
				//ATRIBUI A MATRIIZ TODAS AS PEÇAS DO TABULEIRO, BUSCANDO NO MÉTODO QUE RETORNA AS PEÇAS, 
				//AS POSIÇÕES DA MATRIZ COMO PARAMETRO DA POSIÇÃO QUE DEVE BUSCAR A PEÇA
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez sourcePosition) {
		//ELE RECEBE A POSIÇÃO DE ORIGEM COMO PARAMETRO, E ENTÃO USA O NOSSO MÉTODO DE RECEBER
		//AS POSIÇÕES DE XADREZ (E4, D5, E6..) EM POSIÇÃO DE MATRIZ (0,0..1,5...)
		Posicao posicao = sourcePosition.convertePosicaoXadrezParaPosicaoMatriz();
		//DEPOIS ELE CHAMA O MÉTODO QUE VALIDA A POSIÇÃO DE ORIGEM, QUE AGORA JÁ ESTÁ CONVERTIDA PARA POSIÇÃO DE MATRIZ
		validaPosicaoInicial(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	//MÉTODO CRUCIAL PARA O SISTEMA FUNCIONAR, É ELE QUE VAI MOVIMENTAR AS PEÇAS
	public PecaXadrez movimentarPeca(PosicaoXadrez posicaoInicial, PosicaoXadrez posicaoDestino) {
		Posicao inicial = posicaoInicial.convertePosicaoXadrezParaPosicaoMatriz();
		Posicao destino = posicaoDestino.convertePosicaoXadrezParaPosicaoMatriz();
		validaPosicaoInicial(inicial);
		validaPosicaoDestino(inicial, destino);
		Peca pecaCapturada = makeMove(inicial, destino);
		
		if (reiEstaEmCheque(jogadorAtual)) {
			desfazerMovimento(inicial, destino, pecaCapturada);
			throw new ExceptionXadrez("Voce nao pode deixar seu rei em cheque");
		}
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);
		
		// #specialmove promotion
		promovida = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCor() == Cor.BRANCA && 
							destino.getLinha() == 0) || 
							(pecaMovida.getCor() == Cor.PRETA && 
							destino.getLinha() == 7)) {
				promovida = (PecaXadrez)tabuleiro.peca(destino);
				promovida = alterarPecaPromovida("D");
			}
		}
		
		//OPERAÇÃO TERNÁRIA, SE O REI DO OPONENTE ESTÁ EM CHEQUE, CHEQUE RECEBE TRUE, SE NÃO, RECEBE FALSO
		cheque = (reiEstaEmCheque(oponente(jogadorAtual))) ? true : false;

		//EM SEGUIDA VALIDA SE O MESMO REI DO OPONENTE ESTÁ EM CHEQUE MATE, AÍ AQUI O SISTEMA VAI ATRIBUIR TRUE
		//AO ATRIBUTO, E NO PROGRAMA PRINCIPAL ELE VAI SAIR FORA DO WHILE (!CHEQUEMATE), E ENCERRAR O JOGO.
		if (reiEstaEmChequeMate(oponente(jogadorAtual))) {
			chequeMate = true;
		} else {
			proximaJogada();
		}
		
		// #MOVIMENTO ESPECIAL EN PASSANT
		if (pecaMovida instanceof Peao && 
								(destino.getLinha() == inicial.getLinha() - 2 || 
								destino.getLinha() == inicial.getLinha() + 2)) {
			enPassantPossivel = pecaMovida;
		} else {
			enPassantPossivel = null;
		}
		
		return (PecaXadrez)pecaCapturada;
	}

	public PecaXadrez alterarPecaPromovida(String tipo) {
		if (promovida == null) {
			throw new IllegalStateException("Nao ha peca para promover");
		}
		if (!tipo.equals("B") && !tipo.equals("T") && !tipo.equals("D") & !tipo.equals("C")) {
			return promovida;
		}
		
		Posicao pos = promovida.getPosicaoXadrez().convertePosicaoXadrezParaPosicaoMatriz();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez pecaNova = newPiece(tipo, promovida.getCor());
		tabuleiro.inserirPeca(pecaNova, pos);
		pecasNoTabuleiro.add(pecaNova);
		
		return pecaNova;
	}
	
	private PecaXadrez newPiece(String type, Cor cor) {
		if (type.equals("B")) return new Bispo(tabuleiro, cor);
		if (type.equals("C")) return new Cavalo(tabuleiro, cor);
		if (type.equals("D")) return new Dama(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}
	
	private Peca makeMove(Posicao inicial, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(inicial);
		p.incrementeContadorMovimentos();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.inserirPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		// #specialmove castling kingside rook
		if (p instanceof Rei && destino.getColuna() == inicial.getColuna() + 2) {
			Posicao origemTorre = new Posicao(inicial.getLinha(), inicial.getColuna() + 3);
			Posicao destinoTorre = new Posicao(inicial.getLinha(), inicial.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemTorre);
			tabuleiro.inserirPeca(torre, destinoTorre);
			torre.incrementeContadorMovimentos();
		}

		// #MOVIMENTO ESPECIAL ROQUE GRANDE
		if (p instanceof Rei && destino.getColuna() == inicial.getColuna() - 2) {
			Posicao sourceT = new Posicao(inicial.getLinha(), inicial.getColuna() - 4);
			Posicao destinoTorre = new Posicao(inicial.getLinha(), inicial.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(sourceT);
			tabuleiro.inserirPeca(torre, destinoTorre);
			torre.incrementeContadorMovimentos();
		}		
		
		// #MOVIMENTO ESPECIAL EN PASSANTE
		if (p instanceof Peao) {
			if (inicial.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCA) {
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				}
				else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		
		return pecaCapturada;
	}
	
	//ESSE MÉTODO DE DESFAZER MOVIMENTO, SERVE PARA VOLTAR UMA PEÇA PARA A POSIÇÃO ANTERIOR, CASO
	//A MOVIMENTAÇÃO DELA TENHA SIDO INVALIDA, QUE TENHA CAUSADO CHEQUE NO PROPRIO REI, ETC...
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez peca = (PecaXadrez)tabuleiro.removerPeca(destino);
		//ELE CHAMA O DECREMENTA CONTADOR DE MOVIMENTOS, PORQUE SE POR VENTURA MOVIMENTAR UM PEÃO E VOLTAR
		//O CONTADOR DE MOVIMENTOS DELE FICARIA COM 1, E NUMA PROXIMA JOGADA ELE NÃO PODERIA ANDAR DUAS
		//CASAS PRA FRENTE, E EM SEGUIDA ELE INSERE A PEÇA MOVIMENTADA PARA A POSIÇÃO DE ORIGEM QUE ELA SAIU
		peca.decrementaContadorMovimentos();
		tabuleiro.inserirPeca(peca, origem);
		
		//SE ALGUMA PEÇA FOI CAPTURADA NESSE MOVIMENTO, MAS O MOVIMENTO TEVE QUE SER VOLTADO, ELE INSERE A PEÇA CAPTURADA
		//NA POSIÇÃO QUE ELA FOI REMOVIDA, E ELE REMOVE ESSA PEÇA DA LISTA DE CAPTURADAS, E ADICIONA NOVAMENTE ESSA 
		//PECA RECUPERADA PARA A LISTA DE PEÇAS NO TABULEIRO.
		if (pecaCapturada != null) {
			tabuleiro.inserirPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// #MOVIMENTO ESPECIAL ROQUE PEQUENO
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao posOrigemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao posDestinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(posDestinoTorre);
			tabuleiro.inserirPeca(torre, posOrigemTorre);
			torre.decrementaContadorMovimentos();
		}

		// MOVIMENTO ESPECIAL ROQUE PEQUENO
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao posOrigemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao posDestinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre2 = (PecaXadrez)tabuleiro.removerPeca(posDestinoTorre);
			tabuleiro.inserirPeca(torre2, posOrigemTorre);
			torre2.decrementaContadorMovimentos();
		}
		
		// #MOVIMENTO ESPECIAL EN PASSANT
		if (peca instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantPossivel) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if (peca.getCor() == Cor.BRANCA) {
					posicaoPeao = new Posicao(3, destino.getColuna());
				}
				else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.inserirPeca(peao, posicaoPeao);
			}
		}
	}
	
	private void validaPosicaoInicial(Posicao posicao) {
		if (!tabuleiro.existeUmaPecaNessaPosicao(posicao)) {
			throw new ExceptionXadrez("Nao existe nenhuma peca na posicao escolhida - Press Enter:");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new ExceptionXadrez("A peca que voce escolheu nao e " + getJogadorAtual());
		}
		if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
			throw new ExceptionXadrez("A peca escolhida nao tem movimentos possiveis - Press Enter:");
		}
	}
	
	private void validaPosicaoDestino(Posicao source, Posicao target) {
		if (!tabuleiro.peca(source).movimentoPossivel(target)) {
			throw new ExceptionXadrez("A peca escolhida nao pode ir para a posicao selecionada - Press Enter:");
		}
	}
	
	private void proximaJogada() {
		vez++;
		jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private Cor oponente(Cor color) {
		return (color == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private PecaXadrez king(Cor color) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == color).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("O rei da cor" + color + " nao esta no tabuleiro");
	}
	
	private boolean reiEstaEmCheque(Cor color) {
		Posicao kingPosition = king(color).getPosicaoXadrez().convertePosicaoXadrezParaPosicaoMatriz();
		List<Peca> opponentPieces = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(color)).collect(Collectors.toList());
		for (Peca p : opponentPieces) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[kingPosition.getLinha()][kingPosition.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean reiEstaEmChequeMate(Cor color) {
		if (!reiEstaEmCheque(color)) {
			return false;
		}
		List<Peca> listaAux = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == color).collect(Collectors.toList());
		for (Peca p : listaAux) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().convertePosicaoXadrezParaPosicaoMatriz();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = makeMove(origem, destino);
						boolean reiEstaEmCheque = reiEstaEmCheque(color);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (reiEstaEmCheque == false) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.inserirPeca(peca, new PosicaoXadrez(coluna, linha).convertePosicaoXadrezParaPosicaoMatriz());
		pecasNoTabuleiro.add(peca);
	}
	
	//ESSE É O MÉTODO ONDE COLOCA TODAS AS PEÇAS NAS POSIÇÕES INICIAIS DELA, MAS, O DESENVOLVEDOR PODE
	//ALTERAR A POSIÇÃO INICIAL, SE QUISER JOGAR DE UM JEITO DIFERENTE
	private void tabuleiroInicial() {
		char []i = new char[2];
		i[0]= 'a';
		i[1] = 'b';
		int [] a = new int[2];
		a[0] = 1;
		a[0] = 1;
		//PECAS ESPECIAIS BRANCAS DA ESQUERDA PARA A DIREITA
        colocarNovaPeca(i[0], a[0], new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca(i[1], a[0], new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 1, new Dama(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));
		//PEOES BRANCOS DA ESQUERDA PARA A DIREITA, NA SEGUNDA LINHA
        colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA, this));

        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 8, new Dama(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA, this));
	}
}