package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import exceptions.DataInvalidaException;
import exceptions.ElementoJaExisteException;
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

public class TelaCDCardapioController {

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
    private DatePicker datafinalCardapio;

    @FXML
    private DatePicker dataInicioCardapio;

    @FXML
    protected void initialize() {
        dataInicioCardapio.setValue(LocalDate.now());
        datafinalCardapio.setValue(LocalDate.now());
        ObservableList<TipoRefeicao> tipos = FXCollections.observableArrayList();
        tipos.add(TipoRefeicao.ALMOCO);
        tipos.add(TipoRefeicao.JANTAR);
        cbTipo.setItems(tipos);
        cbTipo.setValue(TipoRefeicao.ALMOCO);
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

    private int cadastrarOpcao(String o1, String o2, String vegana, String fast, String suco, String sobremesa)
            throws IOException {
        String json = String.format("""
                {
                    "opcao1": "%s",
                    "opcao2": "%s",
                    "vegana": "%s",
                    "fast_grill": "%s",
                    "suco": "%s",
                    "sobremesa": "%s"
                }
                """, o1, o2, vegana, fast, suco, sobremesa);

        URI uri = URI.create("http://localhost:3000/opcao/cadastrar");
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
                String response = br.lines().collect(Collectors.joining());
                // Supondo que o backend retorna {"id": 123}
                String idStr = response.replaceAll("[^0-9]", "");
                return Integer.parseInt(idStr);
            }
        } else {
            throw new IOException("Erro ao cadastrar opção: código " + responseCode);
        }
    }

    @FXML
    protected void botaoCadastroCardapio() {
        try {
            datafinalCardapio.setValue(dataInicioCardapio.getValue().plusDays(4));
            String tipo = cbTipo.getValue().name();

            int idSegunda = cadastrarOpcao(principal1SegundaTextField.getText(),
                    principal2SegundaTextField.getText(), vegetarianoSegundaTextField.getText(),
                    fastSegundaTextField.getText(), sucoSegundaTextField.getText(),
                    sobremesaSegundaTextField.getText());

            int idTerca = cadastrarOpcao(principal1TercaTextField.getText(),
                    principal2TercaTextField.getText(), vegetarianoTercaTextField.getText(),
                    fastTercaTextField.getText(), sucoTercaTextField.getText(), sobremesaTercaTextField.getText());

            int idQuarta = cadastrarOpcao(principal1QuartaTextField.getText(),
                    principal2QuartaTextField.getText(), vegetarianoQuartaTextField.getText(),
                    fastQuartaTextField.getText(), sucoQuartaTextField.getText(), sobremesaQuartaTextField.getText());

            int idQuinta = cadastrarOpcao(principal1QuintaTextField.getText(),
                    principal2QuintaTextField.getText(), vegetarianoQuintaTextField.getText(),
                    fastQuintaTextField.getText(), sucoQuintaTextField.getText(), sobremesaQuintaTextField.getText());

            int idSexta = cadastrarOpcao(principal1SextaTextField.getText(),
                    principal2SextaTextField.getText(), vegetarianoSextaTextField.getText(),
                    fastSextaTextField.getText(), sucoSextaTextField.getText(), sobremesaSextaTextField.getText());

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
                    """, dataInicioCardapio.getValue(), tipo, idSegunda, idTerca, idQuarta, idQuinta, idSexta);

            int responseCode = enviarPost("http://localhost:3000/cardapio/cadastrar", json);

            if (responseCode == 201 || responseCode == 200) {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Cardápio cadastrado");
                info.setContentText("O cardápio foi cadastrado com sucesso.");
                info.show();
            } else {
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setTitle("Erro");
                erro.setContentText("Erro ao cadastrar cardápio: código " + responseCode);
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
    protected void botaoVoltar(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaFuncionario.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Tela Inicial");
    }

}
