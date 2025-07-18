package app;

import exceptions.DataInvalidaException;
import exceptions.PeriodoInvalidoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import negocio.Controlador;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class TelaRelatorioController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<List<List<String>>> tbVendas;

    @FXML
    private TableColumn<List<List<String>>, String> clnAlmocoV;

    @FXML
    private TableColumn<List<List<String>>, String> clnJantarV;

    @FXML
    private TableColumn<List<List<String>>, String> clnDataV;

    @FXML
    private TableView<List<List<String>>> tbConsumo;

    @FXML
    private TableColumn<List<List<String>>, String> clnAlmocoC;

    @FXML
    private TableColumn<List<List<String>>, String> clnJantarC;

    @FXML
    private TableColumn<List<List<String>>, String> clnDataC;

    @FXML
    private DatePicker dtInicioV;

    @FXML
    private DatePicker dtFimV;

    @FXML
    private DatePicker dtInicioC;

    @FXML
    private DatePicker dtFimC;

    @FXML
    protected void initialize() {
        dtInicioV.setValue(LocalDate.now());
        dtFimV.setValue(LocalDate.now());
        dtInicioC.setValue(LocalDate.now());
        dtFimC.setValue(LocalDate.now());
    }

    @FXML
    protected void btnRelatorioV() {
        LocalDate inicio = dtInicioV.getValue();
        LocalDate fim = dtFimV.getValue();
        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3330/relatorio/venda"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"inicio\": \"" + inicio + "\", \"fim\": \"" + fim + "\"}"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                JSONArray data = jsonResponse.getJSONArray("data");
                ObservableList<List<List<String>>> vendas = tbVendas.getItems();
                vendas.clear();

                for (int i = 0; i < data.length(); i++) {
                    JSONObject item = data.getJSONObject(i);
                    String dataVenda = item.getString("data");
                    int almoco = item.getInt("almoco");
                    int jantar = item.getInt("jantar");
                    vendas.add(new ArrayList<>(Arrays.asList(Arrays.asList(dataVenda, String.valueOf(almoco), String.valueOf(jantar)))));
                }
                tbVendas.setItems(vendas);
                clnDataV.setCellValueFactory(cellData -> cellData.getValue().get(0).get(0));
                clnAlmocoV.setCellValueFactory(cellData -> cellData.getValue().get(0).get(1));
                clnJantarV.setCellValueFactory(cellData -> cellData.getValue().get(0).get(2));
            } else {
                // Tratar erro
                // Exibir mensagem de erro
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void btnRelatorioC() {
        LocalDate inicio = dtInicioC.getValue();
        LocalDate fim = dtFimC.getValue();
        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3330/relatorio/consumo"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"inicio\": \"" + inicio + "\", \"fim\": \"" + fim + "\"}"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                JSONArray data = jsonResponse.getJSONArray("data");
                ObservableList<List<List<String>>> consumos = tbConsumo.getItems();
                consumos.clear();

                for (int i = 0; i < data.length(); i++) {
                    JSONObject item = data.getJSONObject(i);
                    String dataConsumo = item.getString("data");
                    int almoco = item.getInt("almoco");
                    int jantar = item.getInt("jantar");
                    consumos.add(new ArrayList<>(Arrays.asList(Arrays.asList(dataConsumo, String.valueOf(almoco), String.valueOf(jantar)))));
                }
                tbConsumo.setItems(consumos);
                clnDataC.setCellValueFactory(cellData -> cellData.getValue().get(0).get(0));
                clnAlmocoC.setCellValueFactory(cellData -> cellData.getValue().get(0).get(1));
                clnJantarC.setCellValueFactory(cellData -> cellData.getValue().get(0).get(2));
            } else {
                // Tratar erro
                // Exibir mensagem de erro
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void btVoltar(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaFuncionario.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Tela inicial");
    }
}
