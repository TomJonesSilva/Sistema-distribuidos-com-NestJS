package models;

import java.time.LocalDate;

public class Estudante extends Usuario {

    private String matricula;

    public Estudante(String codigo, String nome, String cpf, LocalDate dataDeNascimento, String email, String senha,
    String matricula) {
        super(codigo, nome, cpf, dataDeNascimento, email, senha);
        this.matricula = matricula;
    }
    


    public Estudante() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getMatricula() {
        return matricula;
    }

    public void setmatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return String.format(
                "Estudante: %s\nCPF: %s\nData de nascimento: %s\nE-mail: %s\nCódigo: %s\nQuantidade de refeições disponíveis: %d",
                super.getNome(),
                super.getCpf(), super.getDataDeNascimento(), super.getEmail(), super.getCodigo(), matricula);
    }

    @Override
    public boolean equals(Object obj) {
        boolean resultado = false;
        
        if(obj != null){
            if(obj instanceof Estudante){
                Estudante estudante = ((Estudante) obj);
                if(this.getCodigo().equals(estudante.getCodigo()) || this.getCpf().equals(estudante.getCpf()) || this.getMatricula().equals(estudante.getMatricula())){
                    resultado = true;
                } 
            }
        }
        
        return resultado;
    }

}
