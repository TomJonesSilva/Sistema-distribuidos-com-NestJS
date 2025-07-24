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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private Map<String, Integer> codigosOpcoes = new HashMap<>();

    @FXML
    protected void initialize() {
        dataInicioCardapio.setValue(LocalDate.now());
        ObservableList<TipoRefeicao> tipos = FXCollections.observableArrayList();
        tipos.add(TipoRefeicao.almoço);
        tipos.add(TipoRefeicao.janta);
        cbTipo.setItems(tipos);

    }

    @FXML
    protected void botaoBuscarCardapio() {

        try {

            String dataAtual = dataInicioCardapio.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);

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

            if (responseCardapio.statusCode() == 201) {

                JSONObject jsonObject = new JSONObject(responseCardapio.body());

                // Verifica se no corpo de resposta tem o campo "opcoes"
                if (jsonObject.has("opcoes")) {
                    JSONObject opcoes = jsonObject.getJSONObject("opcoes");

                    if (opcoes.has("segunda")) {
                        // Obtém o primeiro elemento do array
                        JSONObject segundaObjeto = opcoes.getJSONObject("segunda");
                        principal1SegundaTextField.setText(segundaObjeto.getString("opcao1"));
                        principal2SegundaTextField.setText(segundaObjeto.getString("opcao2"));
                        vegetarianoSegundaTextField.setText(segundaObjeto.getString("vegana"));
                        fastSegundaTextField.setText(segundaObjeto.getString("fast_grill"));
                        sucoSegundaTextField.setText(segundaObjeto.getString("suco"));
                        sobremesaSegundaTextField.setText(segundaObjeto.getString("sobremesa"));
                    }
                    if (opcoes.has("terca")) {
                        // Obtém o primeiro elemento do array
                        JSONObject tercaObjeto = opcoes.getJSONObject("terca");
                        principal1TercaTextField.setText(tercaObjeto.getString("opcao1"));
                        principal2TercaTextField.setText(tercaObjeto.getString("opcao2"));
                        vegetarianoTercaTextField.setText(tercaObjeto.getString("vegana"));
                        fastTercaTextField.setText(tercaObjeto.getString("fast_grill"));
                        sucoTercaTextField.setText(tercaObjeto.getString("suco"));
                        sobremesaTercaTextField.setText(tercaObjeto.getString("sobremesa"));
                    }
                    if (opcoes.has("quarta")) {
                        // Obtém o primeiro elemento do array
                        JSONObject quartaObjeto = opcoes.getJSONObject("quarta");
                        principal1QuartaTextField.setText(quartaObjeto.getString("opcao1"));
                        principal2QuartaTextField.setText(quartaObjeto.getString("opcao2"));
                        vegetarianoQuartaTextField.setText(quartaObjeto.getString("vegana"));
                        fastQuartaTextField.setText(quartaObjeto.getString("fast_grill"));
                        sucoQuartaTextField.setText(quartaObjeto.getString("suco"));
                        sobremesaQuartaTextField.setText(quartaObjeto.getString("sobremesa"));
                    }
                    if (opcoes.has("quinta")) {
                        // Obtém o primeiro elemento do array
                        JSONObject quintaObjeto = opcoes.getJSONObject("quinta");
                        principal1QuintaTextField.setText(quintaObjeto.getString("opcao1"));
                        principal2QuintaTextField.setText(quintaObjeto.getString("opcao2"));
                        vegetarianoQuintaTextField.setText(quintaObjeto.getString("vegana"));
                        fastQuintaTextField.setText(quintaObjeto.getString("fast_grill"));
                        sucoQuintaTextField.setText(quintaObjeto.getString("suco"));
                        sobremesaQuintaTextField.setText(quintaObjeto.getString("sobremesa"));
                    }
                    if (opcoes.has("sexta")) {
                        // Obtém o primeiro elemento do array
                        JSONObject sextaObjeto = opcoes.getJSONObject("sexta");
                        principal1SextaTextField.setText(sextaObjeto.getString("opcao1"));
                        principal2SextaTextField.setText(sextaObjeto.getString("opcao2"));
                        vegetarianoSextaTextField.setText(sextaObjeto.getString("vegana"));
                        fastSextaTextField.setText(sextaObjeto.getString("fast_grill"));
                        sucoSextaTextField.setText(sextaObjeto.getString("suco"));
                        sobremesaSextaTextField.setText(sextaObjeto.getString("sobremesa"));
                    }
                    codigosOpcoes.put("segunda", opcoes.getJSONObject("segunda").getInt("codigo_opcao"));
                    codigosOpcoes.put("terca", opcoes.getJSONObject("terca").getInt("codigo_opcao"));
                    codigosOpcoes.put("quarta", opcoes.getJSONObject("quarta").getInt("codigo_opcao"));
                    codigosOpcoes.put("quinta", opcoes.getJSONObject("quinta").getInt("codigo_opcao"));
                    codigosOpcoes.put("sexta", opcoes.getJSONObject("sexta").getInt("codigo_opcao"));
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

    private void editarOpcao(int codigo, String o1, String o2, String vegana, String fast, String suco,
            String sobremesa)
            throws IOException {
        String json = String.format("""
                {
                    "codigo_opcao": %d,
                    "opcao1": "%s",
                    "opcao2": "%s",
                    "vegana": "%s",
                    "fast_grill": "%s",
                    "suco": "%s",
                    "sobremesa": "%s"
                }
                """, codigo, o1, o2, vegana, fast, suco, sobremesa);

        URI uri = URI.create("http://localhost:3000/opcao/editar");
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == 200 || responseCode == 201) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {

            }
        } else {
            throw new IOException("Erro ao editar opção: código " + responseCode);
        }
    }

    private int enviarPost(String urlStr, String jsonBody) throws IOException {
        URI uri = URI.create(urlStr);
        URL url = uri.toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        return conn.getResponseCode(); // Pode ser 200, 201, 400, etc.
    }

    @FXML
    protected void botaoEditarCardapio() {
        try {

            String tipo = cbTipo.getValue().name();

            editarOpcao(codigosOpcoes.get("segunda"), principal1SegundaTextField.getText(),
                    principal2SegundaTextField.getText(), vegetarianoSegundaTextField.getText(),
                    fastSegundaTextField.getText(), sucoSegundaTextField.getText(),
                    sobremesaSegundaTextField.getText());

            editarOpcao(codigosOpcoes.get("terca"), principal1TercaTextField.getText(),
                    principal2TercaTextField.getText(), vegetarianoTercaTextField.getText(),
                    fastTercaTextField.getText(), sucoTercaTextField.getText(), sobremesaTercaTextField.getText());

            editarOpcao(codigosOpcoes.get("quarta"), principal1QuartaTextField.getText(),
                    principal2QuartaTextField.getText(), vegetarianoQuartaTextField.getText(),
                    fastQuartaTextField.getText(), sucoQuartaTextField.getText(), sobremesaQuartaTextField.getText());

            editarOpcao(codigosOpcoes.get("quinta"), principal1QuintaTextField.getText(),
                    principal2QuintaTextField.getText(), vegetarianoQuintaTextField.getText(),
                    fastQuintaTextField.getText(), sucoQuintaTextField.getText(), sobremesaQuintaTextField.getText());

            editarOpcao(codigosOpcoes.get("sexta"), principal1SextaTextField.getText(),
                    principal2SextaTextField.getText(), vegetarianoSextaTextField.getText(),
                    fastSextaTextField.getText(), sucoSextaTextField.getText(), sobremesaSextaTextField.getText());

            String dataFormatada = dataInicioCardapio.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);

            String json = String.format("""
                    {
                        "data_inicio": "%s",
                        "tipo": "%s",
                        "opcao_segunda": %d,
                        "opcao_terca": %d,
                        "opcao_quarta": %d,
                        "opcao_quinta": %d,
                        "opcao_sexta": %d
                    }
                    """, dataFormatada, tipo, codigosOpcoes.get("segunda"), codigosOpcoes.get("terca"),
                    codigosOpcoes.get("quarta"), codigosOpcoes.get("quinta"), codigosOpcoes.get("sexta"));

            int responseCode = enviarPost("http://localhost:3000/cardapio/cadastrar", json);

            if (responseCode == 201 || responseCode == 200) {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Cardápio cadastrado");
                info.setContentText("O cardápio foi editado com sucesso.");
                info.show();
            } else {
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setTitle("Erro");
                erro.setContentText("Erro ao editar  cardápio: código " + responseCode);
                erro.show();
            }

        } catch (Exception e) {
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setContentText("Erro: " + e.getMessage());
            erro.show();
            e.printStackTrace();
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
