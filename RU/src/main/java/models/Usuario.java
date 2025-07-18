package models;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Usuario {
    private String codigo;
    private String nome;
    private String cpf;
    private LocalDate dataDeNascimento;
    private String email;
    private String senha;

    public Usuario(String codigo, String nome, String cpf, LocalDate dataDeNascimento, String email, String senha) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
        this.email = email;
        this.senha = senha;
    }


    public Usuario() {
	super();
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s\n CPF: %s\nData de nascimento: %s\nE-mail: %s\nCódigo: %s", nome, cpf,
        dataDeNascimento, email, codigo);
    }


    @Override
    public int hashCode() {
	return Objects.hash(cpf);
    }

    @Override
    public abstract boolean equals(Object obj);

}
