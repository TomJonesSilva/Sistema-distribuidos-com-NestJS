package app;

import exceptions.DataInvalidaException;
import exceptions.PeriodoInvalidoException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import models.Estudante;
import models.Funcionario;
import negocio.Controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TelaHistoricoController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //@FXML
    //private Label relatorio;

    @FXML
    private TableView<List<List<SimpleStringProperty>>> tbVendas;

    @FXML
    private TableColumn<List<List<SimpleStringProperty>>, String> clnAlmocoV;

    @FXML
    private TableColumn<List<List<SimpleStringProperty>>, String> clnJantarV;

    @FXML
    private TableColumn<List<List<SimpleStringProperty>>, String> clnDataV;

    @FXML
    private TableView<List<List<SimpleStringProperty>>> tbConsumo;

    @FXML
    private TableColumn<List<List<SimpleStringProperty>>, String> clnAlmocoC;

    @FXML
    private TableColumn<List<List<SimpleStringProperty>>, String> clnJantarC;

    @FXML
    private TableColumn<List<List<SimpleStringProperty>>, String> clnDataC;

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
            LocalDate data = inicio;
            Map<LocalDate, Integer> mapaAlmoco = Controlador.getInstance().historicoVendasAlmoco(inicio, fim, Controlador.getInstance().getUsuario());
            Map<LocalDate, Integer> mapaJantar = Controlador.getInstance().historicoVendasJantar(inicio, fim, Controlador.getInstance().getUsuario());
            ObservableList<List<List<SimpleStringProperty>>> vendas = FXCollections.observableArrayList();


            while (!data.isAfter(fim)) {
                int qntAlmoco = mapaAlmoco.getOrDefault(data, 0);
                int qntJanta = mapaJantar.getOrDefault(data, 0);
                if (qntAlmoco != 0 || qntJanta != 0) {
                    List<List<SimpleStringProperty>> linha = new ArrayList<>();
                    List<SimpleStringProperty> valores = new ArrayList<>();
                    valores.add(new SimpleStringProperty(data.format(form)));
                    valores.add(new SimpleStringProperty(String.valueOf(qntAlmoco)));
                    valores.add(new SimpleStringProperty(String.valueOf(qntJanta)));
                    linha.add(valores);
                    vendas.add(linha);
                }
                data = data.plusDays(1);
            }
            tbVendas.setItems(vendas);
            clnDataV.setCellValueFactory(cellData -> cellData.getValue().get(0).get(0));
            clnAlmocoV.setCellValueFactory(cellData -> cellData.getValue().get(0).get(1));
            clnJantarV.setCellValueFactory(cellData -> cellData.getValue().get(0).get(2));
        } catch (DataInvalidaException dti) {
            Alert info = new Alert(Alert.AlertType.WARNING);
            info.setTitle("Data Inválida!");
            info.setContentText(dti.getMessage());
            info.show();
        } catch (PeriodoInvalidoException pdi) {
            Alert info = new Alert(Alert.AlertType.WARNING);
            info.setTitle("Período Inválido!");
            info.setContentText(pdi.getMessage());
            info.show();
        }
    }

    @FXML
    protected void btnRelatorioC() {
        LocalDate inicio = dtInicioC.getValue();
        LocalDate fim = dtFimC.getValue();
        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate data = inicio;
            Map<LocalDate, Integer> mapaAlmoco = Controlador.getInstance().historicoConsumoAlmoco(inicio, fim,Controlador.getInstance().getUsuario());
            Map<LocalDate, Integer> mapaJantar = Controlador.getInstance().historicoConsumoJantar(inicio, fim, Controlador.getInstance().getUsuario());
            ObservableList<List<List<SimpleStringProperty>>> vendas = FXCollections.observableArrayList();


            while (!data.isAfter(fim)) {
                int qntAlmoco = mapaAlmoco.getOrDefault(data, 0);
                int qntJanta = mapaJantar.getOrDefault(data, 0);
                if (qntAlmoco != 0 || qntJanta != 0) {
                    List<List<SimpleStringProperty>> linha = new ArrayList<>();
                    List<SimpleStringProperty> valores = new ArrayList<>();
                    valores.add(new SimpleStringProperty(data.format(form)));
                    valores.add(new SimpleStringProperty(String.valueOf(qntAlmoco)));
                    valores.add(new SimpleStringProperty(String.valueOf(qntJanta)));
                    linha.add(valores);
                    vendas.add(linha);
                }
                data = data.plusDays(1);
            }
            tbConsumo.setItems(vendas);
            clnDataC.setCellValueFactory(cellData -> cellData.getValue().get(0).get(0));
            clnAlmocoC.setCellValueFactory(cellData -> cellData.getValue().get(0).get(1));
            clnJantarC.setCellValueFactory(cellData -> cellData.getValue().get(0).get(2));
            //tbRelatorio.refresh();
        } catch (DataInvalidaException dti) {
            Alert info = new Alert(Alert.AlertType.ERROR);
            info.setTitle("Data Inválida!");
            info.setContentText(dti.getMessage());
            info.show();
        } catch (PeriodoInvalidoException pdi) {
            Alert info = new Alert(Alert.AlertType.ERROR);
            info.setTitle("Período Inválido!");
            info.setContentText(pdi.getMessage());
            info.show();
        }
    }

    @FXML
    protected void botaoVoltar(ActionEvent event) throws IOException {
        if (Controlador.getInstance().getUsuario() instanceof Estudante) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaAluno.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Tela Inicial");
        } else if (Controlador.getInstance().getUsuario() instanceof Funcionario) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaFuncionario.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Tela inicial");
        }
    }
}
