package pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private PartidaXadrez chessMatch;
	
	public Peao(Tabuleiro board, Cor color, PartidaXadrez chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);

		//PRIMEIRO, COMEÇAMOS A VER AS JOGADAS POSSÍVEIS DOS PEÕES BRANCOS
		//PORQUE OS BRANCOS SE MOVEM PARA CIMA, E AS PRETAS PARA BAIXO, E NÃO PODEM VOLTAR...
		if (getCor() == Cor.BRANCA) {
			//ENTÃO ELE PRIMEIRO VAI TENTAR SUBIR UMA LINHA ACIMA, E TESTAR SE NÃO EXISTE
			//NENHUMA PEÇA NA FRENTE DELE, E SE A POSIÇÃO EXISTE
			p.setPosicoes(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			//APOS ELE VALIDAR UMA LINHA ACIMA, ELE VAI TENTAR SUBIR MAIS UMA LINHA
			//E VAI VALIDAR SE NÃO EXISTE NENHUMA PEÇA NA POSIÇÃO A FRENTE, E TAMBÉM SE NÃO TEM
			//NENHUMA PEÇA NA PRÓXIMA POSIÇÃO A FRENTE AO MESMO TEMPO, POIS, ELE NÃO PODE PULAR POR CIMA DE
			//UMA PEÇA.. TAMBÉM ELE VALIDA SE A POSIÇÃO EXISTE, E SE O CONTADOR DE MOVIMENTOS DA PEÇA AINDA ESTÁ EM 0;
			//SE O PEÃO JÁ MEXEU, ELE VAI ESTAR COM O CONTADOR DE MOVIMENTOS MAIOR QUE 0, ENTÃO, O PEÃO NÃO PODERÁ
			//ANDAR DUAS CASAS PARA FRENTE.
			p.setPosicoes(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p) 
												&& getTabuleiro().posicaoExiste(p2) 
												&& !getTabuleiro().existeUmaPecaNessaPosicao(p2) 
												&& getContadorMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			//DEPOIS DE VALIDAR OS MOVIMENTOS PARA FRENTE, ELE VALIDA SE O PEÃO 
			//PODE MATAR ALGUMA PEÇA EM ALGUMA DAS DIAGONAIS:
			//--ESSA É A DIAGONAL ESQUERDA ACIMA, PROCURANDO UMA PEÇA ADVERSARIA NESSA POSIÇÃO
			p.setPosicoes(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}	
			//--ESSA É A DIAGONAL ESQUERDA ACIMA, PROCURANDO NECESSARIAMENTE POR UMA PEÇA
			//ADVERSÁRIA NESSA POSIÇÃO		
			p.setPosicoes(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}	
			
			// #MOVIMENTO ESPECIAL EN PASSANT DAS BRANCAS
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && existePecaAdversariaNessaPosicao(esquerda) 
													   && getTabuleiro().peca(esquerda) == chessMatch.getEnPassantVulnerable()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && existePecaAdversariaNessaPosicao(direita) 
														&& getTabuleiro().peca(direita) == chessMatch.getEnPassantVulnerable()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		}
		else {
			p.setPosicoes(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setPosicoes(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p) 
												&& getTabuleiro().posicaoExiste(p2) 
												&& !getTabuleiro().existeUmaPecaNessaPosicao(p2) 
												&& getContadorMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setPosicoes(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}			
			p.setPosicoes(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// #MOVIMENTO ESPECIAL UNPASSANT DAS PRETAS
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && existePecaAdversariaNessaPosicao(esquerda) 
													   && getTabuleiro().peca(esquerda) == chessMatch.getEnPassantVulnerable()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && existePecaAdversariaNessaPosicao(direita) && getTabuleiro().peca(direita) == chessMatch.getEnPassantVulnerable()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}			
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
	
}