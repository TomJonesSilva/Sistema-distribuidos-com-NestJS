package app;

import exceptions.ElementoNaoExisteException;
import exceptions.ParametroVazioException;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DiasDaSemana;
import models.OpcaoRefeicao;
import models.TipoRefeicao;
import negocio.Controlador;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class TelaBuscaCardapioController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ChoiceBox<TipoRefeicao> cbTipo;
    @FXML
    private TextField principal1SegundaTextField;

    @FXML
    private TextField principal1TercaTextField;

    @FXML
    private TextField principal1QuartaTextField;

    @FXML
    private TextField principal1QuintaTextField;

    @FXML
    private TextField principal1SextaTextField;

    @FXML
    private TextField principal2SegundaTextField;

    @FXML
    private TextField principal2TercaTextField;

    @FXML
    private TextField principal2QuartaTextField;

    @FXML
    private TextField principal2QuintaTextField;

    @FXML
    private TextField principal2SextaTextField;

    @FXML
    private TextField fastSegundaTextField;

    @FXML
    private TextField fastTercaTextField;

    @FXML
    private TextField fastQuartaTextField;

    @FXML
    private TextField fastQuintaTextField;

    @FXML
    private TextField fastSextaTextField;

    @FXML
    private TextField vegetarianoSegundaTextField;

    @FXML
    private TextField vegetarianoTercaTextField;

    @FXML
    private TextField vegetarianoQuartaTextField;

    @FXML
    private TextField vegetarianoQuintaTextField;

    @FXML
    private TextField vegetarianoSextaTextField;

    @FXML
    private TextField sucoSegundaTextField;

    @FXML
    private TextField sucoTercaTextField;

    @FXML
    private TextField sucoQuartaTextField;

    @FXML
    private TextField sucoQuintaTextField;

    @FXML
    private TextField sucoSextaTextField;

    @FXML
    private TextField sobremesaSegundaTextField;

    @FXML
    private TextField sobremesaTercaTextField;

    @FXML
    private TextField sobremesaQuartaTextField;

    @FXML
    private TextField sobremesaQuintaTextField;

    @FXML
    private TextField sobremesaSextaTextField;

    @FXML
    private DatePicker dataInicioCardapio;

    @FXML
    protected void initialize() {
        dataInicioCardapio.setValue(LocalDate.now());
        ObservableList<TipoRefeicao> tipos = FXCollections.observableArrayList();
        tipos.add(TipoRefeicao.ALMOCO);
        tipos.add(TipoRefeicao.JANTAR);
        cbTipo.setItems(tipos);

    }

    @FXML
    protected void botaoBuscarCardapio() {

        try {

            LocalDate dataAtual = LocalDate.now();
            dataAtual = dataInicioCardapio.getValue();

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
            System.out.println("entrou primeiro");
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
                            principal1SegundaTextField.setText(segundaObjeto.getString("opcao1"));
                            principal2SegundaTextField.setText(segundaObjeto.getString("opcao2"));
                            vegetarianoSegundaTextField.setText(segundaObjeto.getString("vegana"));
                            fastSegundaTextField.setText(segundaObjeto.getString("fast_grill"));
                            sucoSegundaTextField.setText(segundaObjeto.getString("suco"));
                            sobremesaSegundaTextField.setText(segundaObjeto.getString("sobremesa"));
                        }
                        if (tercaArray.length() > 0) {
                            // Obtém o primeiro elemento do array
                            JSONObject tercaObjeto = tercaArray.getJSONObject(0);
                            principal1TercaTextField.setText(tercaObjeto.getString("opcao1"));
                            principal2TercaTextField.setText(tercaObjeto.getString("opcao2"));
                            vegetarianoTercaTextField.setText(tercaObjeto.getString("vegana"));
                            fastTercaTextField.setText(tercaObjeto.getString("fast_grill"));
                            sucoTercaTextField.setText(tercaObjeto.getString("suco"));
                            sobremesaTercaTextField.setText(tercaObjeto.getString("sobremesa"));
                        }
                        if (quartaArray.length() > 0) {
                            // Obtém o primeiro elemento do array
                            JSONObject quartaObjeto = quartaArray.getJSONObject(0);
                            principal1QuartaTextField.setText(quartaObjeto.getString("opcao1"));
                            principal2QuartaTextField.setText(quartaObjeto.getString("opcao2"));
                            vegetarianoQuartaTextField.setText(quartaObjeto.getString("vegana"));
                            fastQuartaTextField.setText(quartaObjeto.getString("fast_grill"));
                            sucoQuartaTextField.setText(quartaObjeto.getString("suco"));
                            sobremesaQuartaTextField.setText(quartaObjeto.getString("sobremesa"));
                        }
                        if (quintaArray.length() > 0) {
                            // Obtém o primeiro elemento do array
                            JSONObject quintaObjeto = quintaArray.getJSONObject(0);
                            principal1QuintaTextField.setText(quintaObjeto.getString("opcao1"));
                            principal2QuintaTextField.setText(quintaObjeto.getString("opcao2"));
                            vegetarianoQuintaTextField.setText(quintaObjeto.getString("vegana"));
                            fastQuintaTextField.setText(quintaObjeto.getString("fast_grill"));
                            sucoQuintaTextField.setText(quintaObjeto.getString("suco"));
                            sobremesaQuintaTextField.setText(quintaObjeto.getString("sobremesa"));
                        }
                        if (sextaArray.length() > 0) {
                            // Obtém o primeiro elemento do array
                            JSONObject sextaObjeto = sextaArray.getJSONObject(0);
                            principal1SextaTextField.setText(sextaObjeto.getString("opcao1"));
                            principal2SextaTextField.setText(sextaObjeto.getString("opcao2"));
                            vegetarianoSextaTextField.setText(sextaObjeto.getString("vegana"));
                            fastSextaTextField.setText(sextaObjeto.getString("fast_grill"));
                            sucoSextaTextField.setText(sextaObjeto.getString("suco"));
                            sobremesaSextaTextField.setText(sextaObjeto.getString("sobremesa"));
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
    protected void botaoEditarCardapio() {
        try {
            int i = Controlador.getInstance().indexSemanaCardapio(dataInicioCardapio.getValue(), cbTipo.getValue());
            if (i != -1) {
                Map<DiasDaSemana, OpcaoRefeicao> mapa = new HashMap<>();

                mapa.put(DiasDaSemana.SEGUNDA,
                        Controlador.getInstance().inserirOpcoes(principal1SegundaTextField.getText(),
                                principal2SegundaTextField.getText(), vegetarianoSegundaTextField.getText(),
                                fastSegundaTextField.getText(),
                                sucoSegundaTextField.getText(), sobremesaSegundaTextField.getText(), cbTipo.getValue(),
                                DiasDaSemana.SEGUNDA));

                mapa.put(DiasDaSemana.TERCA, Controlador.getInstance().inserirOpcoes(principal1TercaTextField.getText(),
                        principal2TercaTextField.getText(), vegetarianoTercaTextField.getText(),
                        fastTercaTextField.getText(),
                        sucoTercaTextField.getText(), sobremesaTercaTextField.getText(), cbTipo.getValue(),
                        DiasDaSemana.TERCA));

                mapa.put(DiasDaSemana.QUARTA,
                        Controlador.getInstance().inserirOpcoes(principal1QuartaTextField.getText(),
                                principal2QuartaTextField.getText(), vegetarianoQuartaTextField.getText(),
                                fastQuartaTextField.getText(),
                                sucoQuartaTextField.getText(), sobremesaQuartaTextField.getText(), cbTipo.getValue(),
                                DiasDaSemana.QUARTA));

                mapa.put(DiasDaSemana.QUINTA,
                        Controlador.getInstance().inserirOpcoes(principal1QuintaTextField.getText(),
                                principal2QuintaTextField.getText(), vegetarianoQuintaTextField.getText(),
                                fastQuintaTextField.getText(),
                                sucoQuintaTextField.getText(), sobremesaQuintaTextField.getText(), cbTipo.getValue(),
                                DiasDaSemana.QUINTA));

                mapa.put(DiasDaSemana.SEXTA, Controlador.getInstance().inserirOpcoes(principal1SextaTextField.getText(),
                        principal2SextaTextField.getText(), vegetarianoSextaTextField.getText(),
                        fastSextaTextField.getText(),
                        sucoSextaTextField.getText(), sobremesaSextaTextField.getText(), cbTipo.getValue(),
                        DiasDaSemana.SEXTA));

                Controlador.getInstance().listarCardapioSemanal().get(i).setCardapio(mapa);

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Cardapio Atualizado");
                info.setContentText("O cardapio Atualizado com sucesso");
                info.show();
            } else {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Cardápio indisponivel");
                info.setContentText("O Cardápio que deseja atualizar não existe");
                info.show();
            }

        } catch (ParametroVazioException e) {
            Alert info = new Alert(Alert.AlertType.WARNING);
            info.setTitle("Campo vazio!");
            info.setContentText(e.getMessage());
            info.show();
        }

    }

    @FXML
    protected void botaoRemoverCardapio() {
        int i = Controlador.getInstance().indexSemanaCardapio(dataInicioCardapio.getValue(), cbTipo.getValue());
        try {
            if (i != 1 && dataInicioCardapio.getValue().isAfter(LocalDate.now())) {
                Controlador.getInstance()
                        .removerCardapioSemanal(Controlador.getInstance().listarCardapioSemanal().get(i));

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Cardapio Removido");
                info.setContentText("O cardapio foi removido com sucesso");
                info.show();
                limparTabela();

            }
        } catch (ElementoNaoExisteException e) {
            Alert info = new Alert(Alert.AlertType.WARNING);
            info.setTitle("Cardapio não existe");
            info.setContentText("O Cardapio que voce tentou remover não existe");
            info.show();
        }

    }

    @FXML
    protected void botaoVoltar(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaFuncionario.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Tela Inicial");
    }

    @FXML
    private void limparTabela() {
        principal1SegundaTextField.setText("");
        principal1TercaTextField.setText("");
        principal1QuartaTextField.setText("");
        principal1QuintaTextField.setText("");
        principal1SextaTextField.setText("");

        principal2SegundaTextField.setText("");
        principal2TercaTextField.setText("");
        principal2QuartaTextField.setText("");
        principal2QuintaTextField.setText("");
        principal2SextaTextField.setText("");

        fastSegundaTextField.setText("");
        fastTercaTextField.setText("");
        fastQuartaTextField.setText("");
        fastQuintaTextField.setText("");
        fastSextaTextField.setText("");

        vegetarianoSegundaTextField.setText("");
        vegetarianoTercaTextField.setText("");
        vegetarianoQuartaTextField.setText("");
        vegetarianoQuintaTextField.setText("");
        vegetarianoSextaTextField.setText("");

        sucoSegundaTextField.setText("");
        sucoTercaTextField.setText("");
        sucoQuartaTextField.setText("");
        sucoQuintaTextField.setText("");
        sucoSextaTextField.setText("");

        sobremesaSegundaTextField.setText("");
        sobremesaTercaTextField.setText("");
        sobremesaQuartaTextField.setText("");
        sobremesaQuintaTextField.setText("");
        sobremesaSextaTextField.setText("");

    }

}
