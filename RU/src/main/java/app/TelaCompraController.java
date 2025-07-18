package app;

import exceptions.ElementoJaExisteException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Estudante;
import models.Funcionario;
import models.TipoRefeicao;
import negocio.Controlador;
import negocio.UserAtual;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Random;

import org.json.JSONObject;

public class TelaCompraController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label labelQtdAlmoco;

    @FXML
    private Label labelQtdJanta;

    Random random = new Random();

    @FXML
    protected void initialize() {
        // pegar quantidade almoço e janta do usuario
        // Faz uma requisição para obter a quantidade de almoços não consumidos
        try {
            HttpClient client = HttpClient.newHttpClient();
            String cpf = UserAtual.getInstance().getCpf();

            long startTime = System.currentTimeMillis();

            // Requisição para obter a quantidade de almoços não consumidos
            HttpRequest requestAlmoco = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/ticket/nao-consumidos"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"usuario\": \"" + cpf + "\", \"tipo\": \"almoço\"}"))
                    .build();

            // Envia a requisição e obtém a resposta
            HttpResponse<String> responseAlmoco = client.send(requestAlmoco, HttpResponse.BodyHandlers.ofString());

            // Obtém o tempo de fim da requisição
            long endTime = System.currentTimeMillis();

            // Calcula o tempo de resposta
            long responseTime = endTime - startTime;

            // Imprime o tempo de resposta no console
            System.out.println("Tempo de resposta da solicitação: " + responseTime + " milissegundos");

            // Verifica se a resposta foi bem-sucedida e atualiza o label com a quantidade
            // de almoços não consumidos
            if (responseAlmoco.statusCode() == 201) {
                JSONObject jsonObjectAlmoco = new JSONObject(responseAlmoco.body());
                int qtdAlmoco = jsonObjectAlmoco.getInt("total");
                labelQtdAlmoco.setText(labelQtdAlmoco.getText() + qtdAlmoco);
            } else {
                labelQtdAlmoco.setText("Erro ao obter quantidade de almoços não consumidos");
            }

            // Requisição para obter a quantidade de jantas não consumidas
            HttpRequest requestJanta = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/ticket/nao-consumidos"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"usuario\": \"" + cpf + "\", \"tipo\": \"janta\"}"))
                    .build();

            // Envia a requisição e obtém a resposta
            HttpResponse<String> responseJanta = client.send(requestJanta, HttpResponse.BodyHandlers.ofString());

            // Verifica se a resposta foi bem-sucedida e atualiza o label com a quantidade
            // de jantas não consumidas
            if (responseJanta.statusCode() == 201) {
                JSONObject jsonObjectJanta = new JSONObject(responseJanta.body());
                int qtdJanta = jsonObjectJanta.getInt("total");
                labelQtdJanta.setText(labelQtdJanta.getText() + qtdJanta);
            } else {
                labelQtdJanta.setText("Erro ao obter quantidade de jantas não consumidas");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void botaoComprarAlmoco() {
        try {

            String cpf = UserAtual.getInstance().getCpf();
            String tipoAlmoco = "almoço";

            HttpClient client = HttpClient.newHttpClient();

            // Requisição para almoço não consumido
            HttpRequest requestAlmoco = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/ticket/comprar"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers
                            .ofString("{\"usuario\": \"" + cpf + "\", \"tipo\": \"" + tipoAlmoco + "\"}"))
                    .build();

            // Envia a requisição para a API e obtém a resposta para o almoço
            HttpResponse<String> responseAlmoco = client.send(requestAlmoco, HttpResponse.BodyHandlers.ofString());
            // Verifica se a resposta da API indica um login válido para o almoço
            if (responseAlmoco.statusCode() == 201) {
                labelQtdAlmoco.setText("");
                labelQtdJanta.setText("");
                initialize();
            }

        } catch (IOException | InterruptedException e) {
            Alert info = new Alert(Alert.AlertType.ERROR);
            info.setTitle("Erro");
            info.setContentText("Desculpe! Tente novamente!");
            info.show();
            e.printStackTrace();
        }

    }

    @FXML
    protected void botaoComprarJanta() throws ElementoJaExisteException {
        try {

            String cpf = UserAtual.getInstance().getCpf();
            String tipoJanta = "janta";

            HttpClient client = HttpClient.newHttpClient();

            // Requisição para almoço não consumido
            HttpRequest requestAlmoco = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/ticket/comprar"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers
                            .ofString("{\"usuario\": \"" + cpf + "\", \"tipo\": \"" + tipoJanta + "\"}"))
                    .build();

            // Envia a requisição para a API e obtém a resposta para o almoço
            HttpResponse<String> responseAlmoco = client.send(requestAlmoco, HttpResponse.BodyHandlers.ofString());
            // Verifica se a resposta da API indica um login válido para o almoço
            if (responseAlmoco.statusCode() == 201) {
                labelQtdAlmoco.setText("");
                labelQtdJanta.setText("");
                initialize();
            }

        } catch (IOException | InterruptedException e) {
            Alert info = new Alert(Alert.AlertType.ERROR);
            info.setTitle("Erro");
            info.setContentText("Desculpe! Tente novamente!");
            info.show();
            e.printStackTrace();
        }
    }

    @FXML
    protected void botaoVoltar(ActionEvent event) throws IOException {
        if (UserAtual.getInstance().gettipoUser() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaAluno.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Tela Inicial");
        } else if (UserAtual.getInstance().gettipoUser() == 2) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaFuncionario.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Tela inicial");
        }
    }
}
