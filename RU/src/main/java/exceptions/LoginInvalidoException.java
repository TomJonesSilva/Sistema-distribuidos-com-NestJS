package exceptions;

public class LoginInvalidoException extends Exception{

    int violacao = 0;

    public LoginInvalidoException(int condicaoViolada){
        super("Login inválido: ");
        this.violacao = condicaoViolada;
    }

    public String errorMessage() {
        return switch (this.violacao) {
            case 1 -> "CPF não encontrado!";
            case 2 -> "Senha incorreta!";
            default -> "";
        };
    }

}
