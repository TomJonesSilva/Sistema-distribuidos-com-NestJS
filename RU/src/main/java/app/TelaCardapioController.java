package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.DiasDaSemana;
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
import java.time.LocalDate;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class TelaCardapioController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ChoiceBox<TipoRefeicao> cbTipo;

    @FXML
    private Label principal1SegundaLabel;

    @FXML
    private Label principal1TercaLabel;

    @FXML
    private Label principal1QuartaLabel;

    @FXML
    private Label principal1QuintaLabel;

    @FXML
    private Label principal1SextaLabel;

    @FXML
    private Label principal2SegundaLabel;

    @FXML
    private Label principal2TercaLabel;

    @FXML
    private Label principal2QuartaLabel;

    @FXML
    private Label principal2QuintaLabel;

    @FXML
    private Label principal2SextaLabel;

    @FXML
    private Label fastSegundaLabel;

    @FXML
    private Label fastTercaLabel;

    @FXML
    private Label fastQuartaLabel;

    @FXML
    private Label fastQuintaLabel;

    @FXML
    private Label fastSextaLabel;

    @FXML
    private Label vegetarianoSegundaLabel;

    @FXML
    private Label vegetarianoTercaLabel;

    @FXML
    private Label vegetarianoQuartaLabel;

    @FXML
    private Label vegetarianoQuintaLabel;

    @FXML
    private Label vegetarianoSextaLabel;

    @FXML
    private Label sucoSegundaLabel;

    @FXML
    private Label sucoTercaLabel;

    @FXML
    private Label sucoQuartaLabel;

    @FXML
    private Label sucoQuintaLabel;

    @FXML
    private Label sucoSextaLabel;

    @FXML
    private Label sobremesaSegundaLabel;

    @FXML
    private Label sobremesaTercaLabel;

    @FXML
    private Label sobremesaQuartaLabel;

    @FXML
    private Label sobremesaQuintaLabel;

    @FXML
    private Label sobremesaSextaLabel;

    @FXML
    protected void initialize() {

        ObservableList<TipoRefeicao> tipos = FXCollections.observableArrayList();
        tipos.add(TipoRefeicao.ALMOCO);
        tipos.add(TipoRefeicao.JANTAR);
        cbTipo.setItems(tipos);

        cbTipo.setOnAction(this::iniciarCardapio);

    }

    @FXML
    private void iniciarCardapio(ActionEvent event) {

        try {

            LocalDate dataAtual = LocalDate.now();

            HttpClient client = HttpClient.newHttpClient();

            // Obtém o tempo de início da requisição
            long startTime = System.currentTimeMillis();

            // HttpRequest requestCardapio;

            // Requisição para almoço não consumido
            HttpRequest requestCardapio = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/cardapio/buscar"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "{\"data_inicio\": \"" + dataAtual + "\", \"tipo\": \"" + cbTipo.getValue() + "\"}"))
                    .build();

            // Envia a requisição para a API e obtém a resposta para o almoço
            HttpResponse<String> responseCardapio = client.send(requestCardapio, HttpResponse.BodyHandlers.ofString());

            // Obtém o tempo de fim da requisição
            long endTime = System.currentTimeMillis();

            // Calcula o tempo de resposta
            long responseTime = endTime - startTime;

            // Imprime o tempo de resposta no console
            System.out.println("Tempo de resposta da solicitação: " + responseTime + " milissegundos");
            // Verifica se a resposta da API indica um login válido para o almoço
            if (responseCardapio.statusCode() == 201) {

                JSONObject jsonObject = new JSONObject(responseCardapio.body());

                // Verifica se no corpo de resposta tem o campo "opcoes"
                if (jsonObject.has("opcoes")) {
                    JSONObject opcoes = jsonObject.getJSONObject("opcoes");

                    // Verifica se no corpo de resposta tem o campo "segunda"
                    if (opcoes.has("segunda")) {
                        // Obtém o array de opções para segunda-feira
                        JSONArray segundaArray = opcoes.getJSONArray("segunda");
                        JSONArray tercaArray = opcoes.getJSONArray("terca");
                        JSONArray quartaArray = opcoes.getJSONArray("quarta");
                        JSONArray quintaArray = opcoes.getJSONArray("quinta");
                        JSONArray sextaArray = opcoes.getJSONArray("sexta");

                        // Verifica se o array possui elementos
                        if (segundaArray.length() > 0) {
                            // Obtém o primeiro elemento do array
                            JSONObject segundaObjeto = segundaArray.getJSONObject(0);
                            principal1SegundaLabel.setText(segundaObjeto.getString("opcao1"));
                            principal2SegundaLabel.setText(segundaObjeto.getString("opcao2"));
                            vegetarianoSegundaLabel.setText(segundaObjeto.getString("vegana"));
                            fastSegundaLabel.setText(segundaObjeto.getString("fast_grill"));
                            sucoSegundaLabel.setText(segundaObjeto.getString("suco"));
                            sobremesaSegundaLabel.setText(segundaObjeto.getString("sobremesa"));
                        }
                        if (tercaArray.length() > 0) {
                            // Obtém o primeiro elemento do array
                            JSONObject tercaObjeto = tercaArray.getJSONObject(0);
                            principal1TercaLabel.setText(tercaObjeto.getString("opcao1"));
                            principal2TercaLabel.setText(tercaObjeto.getString("opcao2"));
                            vegetarianoTercaLabel.setText(tercaObjeto.getString("vegana"));
                            fastTercaLabel.setText(tercaObjeto.getString("fast_grill"));
                            sucoTercaLabel.setText(tercaObjeto.getString("suco"));
                            sobremesaTercaLabel.setText(tercaObjeto.getString("sobremesa"));
                        }
                        if (quartaArray.length() > 0) {
                            // Obtém o primeiro elemento do array
                            JSONObject quartaObjeto = quartaArray.getJSONObject(0);
                            principal1QuartaLabel.setText(quartaObjeto.getString("opcao1"));
                            principal2QuartaLabel.setText(quartaObjeto.getString("opcao2"));
                            vegetarianoQuartaLabel.setText(quartaObjeto.getString("vegana"));
                            fastQuartaLabel.setText(quartaObjeto.getString("fast_grill"));
                            sucoQuartaLabel.setText(quartaObjeto.getString("suco"));
                            sobremesaQuartaLabel.setText(quartaObjeto.getString("sobremesa"));
                        }
                        if (quintaArray.length() > 0) {
                            // Obtém o primeiro elemento do array
                            JSONObject quintaObjeto = quintaArray.getJSONObject(0);
                            principal1QuintaLabel.setText(quintaObjeto.getString("opcao1"));
                            principal2QuintaLabel.setText(quintaObjeto.getString("opcao2"));
                            vegetarianoQuintaLabel.setText(quintaObjeto.getString("vegana"));
                            fastQuintaLabel.setText(quintaObjeto.getString("fast_grill"));
                            sucoQuintaLabel.setText(quintaObjeto.getString("suco"));
                            sobremesaQuintaLabel.setText(quintaObjeto.getString("sobremesa"));
                        }
                        if (sextaArray.length() > 0) {
                            // Obtém o primeiro elemento do array
                            JSONObject sextaObjeto = sextaArray.getJSONObject(0);
                            principal1SextaLabel.setText(sextaObjeto.getString("opcao1"));
                            principal2SextaLabel.setText(sextaObjeto.getString("opcao2"));
                            vegetarianoSextaLabel.setText(sextaObjeto.getString("vegana"));
                            fastSextaLabel.setText(sextaObjeto.getString("fast_grill"));
                            sucoSextaLabel.setText(sextaObjeto.getString("suco"));
                            sobremesaSextaLabel.setText(sextaObjeto.getString("sobremesa"));
                        }

                    }
                }

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
    void botaoHome(ActionEvent event) throws IOException {
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
            stage.setTitle("Tela Inicial");

        }
    }

}
