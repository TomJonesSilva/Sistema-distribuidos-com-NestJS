package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.TicketRefeicao;
import negocio.Controlador;
import negocio.UserAtual;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import org.json.JSONObject;

public class TelaAlunoController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    ObservableList<TicketRefeicao> list = FXCollections.observableArrayList();

    @FXML
    private Label labelBemvindo;

    @FXML
    private Label labelQtdAlmoco;

    @FXML
    private Label labelQtdJantar;

    @FXML
    protected void initialize() {
        labelBemvindo.setText("Bem-vindo, " + UserAtual.getInstance().getNome());
        // Faz uma requisição para obter a quantidade de almoços não consumidos
        try {
            HttpClient client = HttpClient.newHttpClient();
            String cpf = UserAtual.getInstance().getCpf();

            // Requisição para obter a quantidade de almoços não consumidos
            HttpRequest requestAlmoco = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/ticket/nao-consumidos"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"usuario\": \"" + cpf + "\", \"tipo\": \"almoço\"}"))
                    .build();

            // Envia a requisição e obtém a resposta
            HttpResponse<String> responseAlmoco = client.send(requestAlmoco, HttpResponse.BodyHandlers.ofString());

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
                labelQtdJantar.setText(labelQtdJantar.getText() + qtdJanta);
            } else {
                labelQtdJantar.setText("Erro ao obter quantidade de jantas não consumidas");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void botaoComprarApertar(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaCompra.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Compra de Tickets");
    }

    @FXML
    protected void botaoSair(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Login");
    }

    @FXML
    protected void botaoVerCardapioApertar(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaCardapio.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Cardápio Semanal");
    }

    @FXML
    protected void botaoConsumirApertar(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaConsumo.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Consumo de Tickets");
    }

    @FXML
    protected void botaoHistoricoApertar(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaHistorico.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Histórico de Usuário");
    }

}
