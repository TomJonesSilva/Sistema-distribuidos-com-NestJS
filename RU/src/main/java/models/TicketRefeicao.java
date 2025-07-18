package models;

import java.time.LocalDate;
import java.util.Objects;

public class TicketRefeicao {
	private LocalDate dataVenda;
	private LocalDate dataConsumo;
	private String codigo;
	private double valor;
	
	private Usuario comprador;
	private TipoRefeicao tipo;

	public TicketRefeicao(LocalDate dataVenda, String codigo, double valor, Usuario comprador, TipoRefeicao tipo) {
		this.dataVenda = dataVenda;
		this.codigo = codigo;
		this.valor = valor;
		this.comprador = comprador;
		this.tipo = tipo;
		this.dataConsumo=LocalDate.of(1,1,1);
	}

	public TicketRefeicao(LocalDate dataVenda, LocalDate dataConsumo, String codigo, double valor, Usuario comprador,
						  TipoRefeicao tipo) {
		super();
		this.dataVenda = dataVenda;
		this.dataConsumo = dataConsumo;
		this.codigo = codigo;
		this.valor = valor;
		this.comprador = comprador;
		this.tipo = tipo;
	}
	public TicketRefeicao() {
		super();
	}
	public LocalDate getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}
	public LocalDate getDataConsumo() {
		return dataConsumo;
	}
	public void setDataConsumo(LocalDate dataConsumo) {
		this.dataConsumo = dataConsumo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Usuario getComprador() {
		return comprador;
	}
	public void setComprador(Estudante comprador) {
		this.comprador = comprador;
	}
	public TipoRefeicao getTipo() {
		return tipo;
	}
	public void setTipo(TipoRefeicao tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "TicketRefeicao [dataVenda=" + dataVenda + ", dataConsumo=" + dataConsumo + ", codigo=" + codigo
				+ ", valor=" + valor + ", comprador=" + comprador + ", tipo=" + tipo + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketRefeicao other = (TicketRefeicao) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
	
}
