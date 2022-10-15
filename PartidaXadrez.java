package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

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

	private List<String> posicoesAleatorias = new ArrayList<>();
	
	public PartidaXadrez() {
		//TODA PARTIDA DE XADREZ COMEÇA COM UM TABULEIRO DE 8X8, NA JOGADA DE NUMERO 1
		//COM AS PEÇAS BRANCAS COMEÇANDO O JOGO
		tabuleiro = new Tabuleiro(8, 8);
		vez = 1;
		jogadorAtual = Cor.BRANCA;
		setPosicoesAleatorias();
		tabuleiroInicial();
	}

	private void setPosicoesAleatorias () {
		posicoesAleatorias.add("a1");
		posicoesAleatorias.add("a2");
		posicoesAleatorias.add("a3");
		posicoesAleatorias.add("a4");
		posicoesAleatorias.add("a5");
		posicoesAleatorias.add("a6");
		posicoesAleatorias.add("a7");
		posicoesAleatorias.add("a8");

		posicoesAleatorias.add("b1");
		posicoesAleatorias.add("b2");
		posicoesAleatorias.add("b3");
		posicoesAleatorias.add("b4");
		posicoesAleatorias.add("b5");
		posicoesAleatorias.add("b6");
		posicoesAleatorias.add("b7");
		posicoesAleatorias.add("b8");

		posicoesAleatorias.add("c1");
		posicoesAleatorias.add("c2");
		posicoesAleatorias.add("c3");
		posicoesAleatorias.add("c4");
		posicoesAleatorias.add("c5");
		posicoesAleatorias.add("c6");
		posicoesAleatorias.add("c7");
		posicoesAleatorias.add("c8");

		posicoesAleatorias.add("d1");
		posicoesAleatorias.add("d2");
		posicoesAleatorias.add("d3");
		posicoesAleatorias.add("d4");
		posicoesAleatorias.add("d5");
		posicoesAleatorias.add("d6");
		posicoesAleatorias.add("d7");
		posicoesAleatorias.add("d8");

		posicoesAleatorias.add("e1");
		posicoesAleatorias.add("e2");
		posicoesAleatorias.add("e3");
		posicoesAleatorias.add("e4");
		posicoesAleatorias.add("e5");
		posicoesAleatorias.add("e6");
		posicoesAleatorias.add("e7");
		posicoesAleatorias.add("e8");

		posicoesAleatorias.add("f1");
		posicoesAleatorias.add("f2");
		posicoesAleatorias.add("f3");
		posicoesAleatorias.add("f4");
		posicoesAleatorias.add("f5");
		posicoesAleatorias.add("f6");
		posicoesAleatorias.add("f7");
		posicoesAleatorias.add("f8");

		posicoesAleatorias.add("g1");
		posicoesAleatorias.add("g2");
		posicoesAleatorias.add("g3");
		posicoesAleatorias.add("g4");
		posicoesAleatorias.add("g5");
		posicoesAleatorias.add("g6");
		posicoesAleatorias.add("g7");
		posicoesAleatorias.add("g8");

		posicoesAleatorias.add("h1");
		posicoesAleatorias.add("h2");
		posicoesAleatorias.add("h3");
		posicoesAleatorias.add("h4");
		posicoesAleatorias.add("h5");
		posicoesAleatorias.add("h6");
		posicoesAleatorias.add("h7");
		posicoesAleatorias.add("h8");
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

	private String geradorPosicaoXadrezAleatoria() {
		String pos = posicoesAleatorias.get(new Random().nextInt(posicoesAleatorias.size()));
		posicoesAleatorias.remove(pos);
		return pos;
	}
	
	//ESSE É O MÉTODO ONDE COLOCA TODAS AS PEÇAS NAS POSIÇÕES INICIAIS DELA, MAS, O DESENVOLVEDOR PODE
	//ALTERAR A POSIÇÃO INICIAL, SE QUISER JOGAR DE UM JEITO DIFERENTE
	private void tabuleiroInicial() {
		String [] pA = new String[32];
		String [] pS = new String[32];
		char [] pI = new char[32];
		int [] linhasAleatorias = new int[32];

		for (int i = 0; i < pA.length; i++) {
			pA[i] = geradorPosicaoXadrezAleatoria();
			pI[i] = pA[i].charAt(0);
			pS[i] = pA[i].substring(1,2);
			linhasAleatorias[i] = Integer.parseInt(pS[i]);
		}


		colocarNovaPeca(pI[0], linhasAleatorias[0], new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca(pI[1], linhasAleatorias[1], new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca(pI[2], linhasAleatorias[2], new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca(pI[3], linhasAleatorias[3], new Dama(tabuleiro, Cor.BRANCA));
        colocarNovaPeca(pI[4], linhasAleatorias[4], new Rei(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca(pI[5], linhasAleatorias[5], new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca(pI[6], linhasAleatorias[6], new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca(pI[7], linhasAleatorias[7], new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca(pI[8], linhasAleatorias[8], new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca(pI[9], linhasAleatorias[9], new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca(pI[10], linhasAleatorias[10], new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca(pI[11], linhasAleatorias[11], new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca(pI[12], linhasAleatorias[12], new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca(pI[13], linhasAleatorias[13], new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca(pI[14], linhasAleatorias[14], new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca(pI[15], linhasAleatorias[15], new Peao(tabuleiro, Cor.BRANCA, this));

        colocarNovaPeca(pI[16], linhasAleatorias[16], new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca(pI[17], linhasAleatorias[17], new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca(pI[18], linhasAleatorias[18], new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca(pI[19], linhasAleatorias[19], new Dama(tabuleiro, Cor.PRETA));
        colocarNovaPeca(pI[20], linhasAleatorias[20], new Rei(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca(pI[21], linhasAleatorias[21], new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca(pI[22], linhasAleatorias[22], new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca(pI[23], linhasAleatorias[23], new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca(pI[24], linhasAleatorias[24], new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca(pI[25], linhasAleatorias[25], new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca(pI[26], linhasAleatorias[26], new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca(pI[27], linhasAleatorias[27], new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca(pI[28], linhasAleatorias[28], new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca(pI[29], linhasAleatorias[29], new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca(pI[30], linhasAleatorias[30], new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca(pI[31], linhasAleatorias[31], new Peao(tabuleiro, Cor.PRETA, this));
	}
}