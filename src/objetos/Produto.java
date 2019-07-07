package objetos;

import java.util.Calendar;

public class Produto {
	
	private int id;
	private String nome;
	private double preco;
	private String quantidade;
	private String mercado;
	private int votos;
	private Calendar dataCriacao;
	private Calendar dataExpiracao;
	private long dias;
	
	public long getDias() {
		return dias;
	}
	public void setDias(long dias) {
		this.dias = dias;
	}
	public Calendar getDataExpiracao() {
		return dataExpiracao;
	}
	public void setDataExpiracao(Calendar dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}
	public int getVotos() {
		return votos;
	}
	public void setVotos(int votos) {
		this.votos = votos;
	}
	public Calendar getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMercado() {
		return mercado;
	}
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	
}
