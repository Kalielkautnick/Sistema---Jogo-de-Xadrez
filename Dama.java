package pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Dama extends PecaXadrez {

	public Dama(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "D";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		//UMA MATRIZ BOOLEANA DE 8x8 QUE COMEÇA COM TODAS AS POSIÇÕES FALSAS
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//AGORA ELE PROCURA OS MOVIMENTOS POSSIVEIS DA DAMA
		// ACIMA
		p.setPosicoes(posicao.getLinha() - 1, posicao.getColuna());
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA DE CIMA NA MESMA COLUNA
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setRow(p.getLinha() - 1);
		}
		//ELE TAMBÉM RETORNA VERDADEIRO QUANDO EXISTE UMA PEÇA ADVERSÁRIA NESSA POSIÇÃO, OU SEJA,
		//SE É UMA PEÇA ADVERSÁRIA, A PEÇA TAMBÉM PODE MOVER PARA LÁ.
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// ESQUERDA
		p.setPosicoes(posicao.getLinha(), posicao.getColuna() - 1);
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A COLUNA DA ESQUERDA NA MESMA LINHA
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColumn(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// DIREITA
		p.setPosicoes(posicao.getLinha(), posicao.getColuna() + 1);
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A COLUNA DA DIREITA NA MESMA LINHA
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColumn(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// ABAIXO
		p.setPosicoes(posicao.getLinha() + 1, posicao.getColuna());
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA DE BAIXO NA MESMA COLUNA
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setRow(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// DIAGONAL ESQUERDA ACIMA
		p.setPosicoes(posicao.getLinha() - 1, posicao.getColuna() - 1);
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA ACIMA E UMA COLUNA A ESQUERDA
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setPosicoes(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// DIAGONAL DIREITA ACIMA
		p.setPosicoes(posicao.getLinha() - 1, posicao.getColuna() + 1);
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA ACIMA E UMA COLUNA A DIREITA
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setPosicoes(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// DIAGONAL DIREITA ABAIXO
		p.setPosicoes(posicao.getLinha() + 1, posicao.getColuna() + 1);
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA ABAIXO E UMA COLUNA A DIREITA
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setPosicoes(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// DIAGONAL ESQUERDA ABAIXO
		p.setPosicoes(posicao.getLinha() + 1, posicao.getColuna() - 1);
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA ABAIXO E UMA COLUNA A ESQUERDA
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setPosicoes(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}