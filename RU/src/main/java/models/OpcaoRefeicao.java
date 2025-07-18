package models;

import java.util.Objects;

public class OpcaoRefeicao {
    private String opcao1;
    private String opcao2;
    private String opcaoVegana;
    private String FastGrill;
    private String sobremesa;
    private String suco;
    private TipoRefeicao tipo;
    
	public OpcaoRefeicao() {
		super();
	}

	public OpcaoRefeicao(String opcao1, String opcao2, String opcaoVegana, String fastGrilll,String suco, String sobremesa,
			TipoRefeicao tipo) {
		super();
		this.opcao1 = opcao1;
		this.opcao2 = opcao2;
		this.opcaoVegana = opcaoVegana;
		this.FastGrill = fastGrilll;
		this.suco = suco;
		this.sobremesa = sobremesa;
		this.tipo = tipo;
	}
	
	public String getOpcao1() {
		return this.opcao1;
	}
	public void setOpcao1(String opcao1) {
		this.opcao1 = opcao1;
	}
	public String getOpcao2() {
		return opcao2;
	}
	public void setOpcao2(String opcao2) {
		this.opcao2 = opcao2;
	}
	public String getOpcaoVegana() {
		return opcaoVegana;
	}
	public void setOpcaoVegana(String opcaoVegana) {
		this.opcaoVegana = opcaoVegana;
	}
	
	public String getFastGrill() {
		return FastGrill;
	}

	public void setFastGrill(String fastGrill) {
		FastGrill = fastGrill;
	}

	public String getSuco() {
		return suco;
	}

	public void setSuco(String suco) {
		this.suco = suco;
	}

	public String getSobremesa() {
		return sobremesa;
	}
	public void setSobremesa(String sobremesa) {
		this.sobremesa = sobremesa;
	}
	public TipoRefeicao getTipo() {
		return tipo;
	}
	public void setTipo(TipoRefeicao tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "OpcaoRefeicao [opcao1=" + opcao1 + ", opcao2=" + opcao2 + ", opcaoVegana=" + opcaoVegana
				+ ", FastGrill=" + FastGrill + ", sobremesa=" + sobremesa + ", suco=" + suco + ", tipo=" + tipo + "]";
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OpcaoRefeicao that)) return false;
		return getOpcao1().equals(that.getOpcao1()) &&
			   getOpcao2().equals(that.getOpcao2()) &&
				getOpcaoVegana().equals(that.getOpcaoVegana()) &&
				getFastGrill().equals(that.getFastGrill()) &&
				getSobremesa().equals(that.getSobremesa()) &&
				getSuco().equals(that.getSuco()) &&
				getTipo() == that.getTipo();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getOpcao1(), getOpcao2(), getOpcaoVegana(), getFastGrill(), getSobremesa(), getSuco(), getTipo());
	}
}
