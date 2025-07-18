package negocio;


public class UserAtual {
    private static UserAtual instancia = null;
    private String cpf;
    private String nome;
    private int tipoUser;// tipo 1 funcionario e tipo 2 estudante

    private UserAtual(){
        this.cpf = null;
        this.nome = null;
        this.tipoUser = 0;
    }

    public static synchronized UserAtual getInstance(){
        if(instancia == null) instancia = new UserAtual();
        return instancia;
    }


    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int gettipoUser() {
        return this.tipoUser;
    }

    public void settipoUser(int tipoUser) {
        this.tipoUser = tipoUser;
    }
}
