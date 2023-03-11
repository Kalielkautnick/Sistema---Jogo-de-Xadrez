package pecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA ACIMA E UMA COLUNA A ESQUERDA
		p.setPosicoes(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setPosicoes(p.getLinha() - 1, p.getColuna() - 1);
		}
		//TAMBÉM RETORNA VERDADEIRO QUANDO CAIR FORA DO IF NO CASO DE AINDA EXISTIR A POSIÇÃO NO TABULEIRO
		//MAS ELE ACABAR ENCONTRANDO UMA PEÇA NESSA POSIÇÃO, ENTÃO ELE TESTA SE NÃO É UMA PEÇA ADVERSÁRIA.
		//SE FOR ADVERSÁRIA, AINDA RETORNA VERDADEIRO NESSA POSIÇÃO
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA ACIMA E UMA COLUNA A DIREITA
		p.setPosicoes(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setPosicoes(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA ABAIXO E UMA COLUNA A DIREITA
		p.setPosicoes(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setPosicoes(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//ENQUANTO POSIÇÃO EXISTIR, E NÃO TIVER NENHUMA PEÇA NA POSIÇÃO PROCURADA, ELE RETORNA VERDADEIRO.
		//E VAI PARA A LINHA ABAIXO E UMA COLUNA A ESQUERDA
		p.setPosicoes(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPecaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setPosicoes(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaAdversariaNessaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//AO FINAL ELE RETORNA A MATRIZ DE BOOLEANOS
		return mat;
	}
}