package app;

import exceptions.LoginInvalidoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Estudante;
import negocio.Controlador;
import negocio.UserAtual;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import org.json.JSONObject;

public class TelaLoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField CPFTextField;

    @FXML
    private PasswordField senhaPasswordField;

    @FXML
    protected void initialize() {
        if (Controlador.getInstance().getUsuario() != null) {
            CPFTextField.setText(Controlador.getInstance().getUsuario().getCpf());
            senhaPasswordField.setText(Controlador.getInstance().getUsuario().getSenha());
        }
    }

    @FXML
    protected void botaoLoginApertar(ActionEvent event) throws IOException {

        String cpf = CPFTextField.getText();
        String senha = senhaPasswordField.getText();

        // Cria um cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Obtém o tempo de início da requisição
        long startTime = System.currentTimeMillis();
        // Cria a requisição HTTP para a rota de login da API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/usuarios/signin"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"cpf\": \"" + cpf + "\", \"senha\": \"" + senha + "\"}"))
                .build();

        try {
            // Envia a requisição para a API e obtém a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Obtém o tempo de fim da requisição
            long endTime = System.currentTimeMillis();

            // Calcula o tempo de resposta
            long responseTime = endTime - startTime;

            // Imprime o tempo de resposta no console
            System.out.println("Tempo de resposta da solicitação: " + responseTime + " milissegundos");
            // Verifica se a resposta da API indica um login válido
            if (response.statusCode() == 201) {

                // System.out.println(response.body());
                UserAtual.getInstance().setCpf(cpf);
                // Converta a string do corpo da requisição em um objeto JSONObject
                JSONObject jsonObject = new JSONObject(response.body());

                // verifica se no corpa de requisição tem o campo usuario
                if (jsonObject.has("usuario")) {
                    JSONObject usuario = jsonObject.getJSONObject("usuario");
                    // verifica se no corpo de usuario tem o nome
                    if (usuario.has("nome")) {
                        // pega o nome
                        String nome = usuario.getString("nome");
                        UserAtual.getInstance().setNome(nome);
                    }
                }

                // Se o login for válido, carrega a interface correspondente
                if (response.body().contains("estudante")) {
                    UserAtual.getInstance().settipoUser(1);
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaAluno.fxml")));
                } else {
                    UserAtual.getInstance().settipoUser(2);
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaFuncionario.fxml")));
                }
                // Configura a cena e exibe a tela inicial
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setTitle("Tela inicial");
            } else {
                // Se a resposta da API indicar um login inválido, exibe um alerta
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Login inválido!");
                alerta.setContentText(response.body());
                alerta.show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
