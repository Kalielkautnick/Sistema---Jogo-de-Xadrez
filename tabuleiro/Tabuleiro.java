package tabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new ExceptionTabuleiro("Erro ao criar tabuleiro: o minimo de colunas ou linhas e 1");
		}
		this.linhas = rows;
		this.colunas = columns;
		pecas = new Peca[rows][columns];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int row, int column) {
		if (!positionExists(row, column)) {
			throw new ExceptionTabuleiro("A posicao escolhida nao existe no tabuleiro - Press enter:");
		}
		return pecas[row][column];
	}
	
	public Peca peca(Posicao position) {
		if (!posicaoExiste(position)) {
			throw new ExceptionTabuleiro("Posicao nao existe no tabuleiro - Press enter");
		}
		return pecas[position.getLinha()][position.getColuna()];
	}
	
	public void inserirPeca(Peca peca, Posicao posicao) {
		if (existeUmaPecaNessaPosicao(posicao)) {
			throw new ExceptionTabuleiro("Ja existe uma peca da sua cor na posicao: " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removerPeca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new ExceptionTabuleiro("A posicao escolhida nao existe no tabuleiro - Press Enter");
		}
		if (peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	private boolean positionExists(int linha, int coluna) {
		return linha >= 0 && linha < this.linhas && coluna >= 0 && coluna < this.colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return positionExists(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean existeUmaPecaNessaPosicao(Posicao position) {
		if (!posicaoExiste(position)) {
			throw new ExceptionTabuleiro("A posicao escolhida nao existe no tabuleiro - Press Enter");
		}
		return peca(position) != null;
	}
}