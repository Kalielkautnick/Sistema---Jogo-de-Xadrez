package tabuleiro;

public class Posicao {

	private int row;
	private int column;
	
	public Posicao(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getLinha() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColuna() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setPosicoes(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	@Override
	public String toString() {
		return row + ", " + column;
	}
}