package app;

import exceptions.DataInvalidaException;
import exceptions.ElementoJaExisteException;
import exceptions.ElementoNaoExisteException;
import exceptions.ParametroVazioException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Estudante;
import models.Funcionario;
import negocio.Controlador;
import negocio.UserAtual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;

import org.json.JSONObject;

public class CadastroUserController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField codAluno;

    @FXML
    private TextField codFun;

    @FXML
    private TextField cpfAluno;

    @FXML
    private TextField cpfFun;

    @FXML
    private TextField cpfSearchE;

    @FXML
    private DatePicker dataContrato;

    @FXML
    private Label displayCod;

    @FXML
    private Label displayCodFun;

    @FXML
    private Label displayCpf;

    @FXML
    private Label displayCpfFun;

    @FXML
    private Label displayEmail;

    @FXML
    private Label displayEmailFun;

    @FXML
    private Label displayNasc;

    @FXML
    private Label displayNascFun;

    @FXML
    private Label displayNome;

    @FXML
    private Label displayNomeFun;

    @FXML
    private Label displaySalario;

    @FXML
    private TextField emailAluno;

    @FXML
    private TextField cpfSearchF;

    @FXML
    private TextField emailFun;

    @FXML
    private TextField matriculaAluno;

    @FXML
    private DatePicker nascAluno;

    @FXML
    private DatePicker nascFun;

    @FXML
    private TextField nomeAluno;

    @FXML
    private TextField nomeFun;

    @FXML
    private TextField salario;

    @FXML
    private TextField senhaAluno;

    @FXML
    private TextField senhaFun;

    // para editar funcionario
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField cpfTextField;
    @FXML
    private TextField codigoTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField salarioTextField;
    @FXML
    private DatePicker dataNascimentoDatePicker;
    @FXML
    private Button finalizarbutton;
    @FXML
    private Label dataAdmissaoLabel;
    @FXML
    private DatePicker dataAdmmissaoDatePicker;
    @FXML
    private TextField senhaTextField;
    @FXML
    private Label senhaLabel;

    @FXML
    protected void cadastrarEstudante() {
        if (!(senhaAluno.getText().isEmpty()) && !(nomeAluno.getText().isEmpty()) &&
                !(emailAluno.getText().isEmpty()) && !(cpfAluno.getText().isEmpty()) &&
                !(matriculaAluno.getText().isEmpty()) && !(codAluno.getText().isEmpty()) &&
                (nascAluno.getValue().isBefore(LocalDate.now().minusYears(16)))) {
            try {
                URI uri = URI.create("http://localhost:3000/usuarios/signup");
                URL url = uri.toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String json = String.format(
                        "{" +
                                "\"nome\":\"%s\"," +
                                "\"cpf\":\"%s\"," +
                                "\"senha\":\"%s\"," +
                                "\"tipo\":\"estudante\"" +
                                "}",
                        nomeAluno.getText(),
                        cpfAluno.getText(),
                        senhaAluno.getText());

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == 201 || responseCode == 200) {
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("CADASTRO REALIZADO!");
                    success.setContentText("Estudante cadastrado com sucesso!");
                    success.show();

                    nomeAluno.setText("");
                    cpfAluno.setText("");
                    nascAluno.setValue(null);
                    matriculaAluno.setText("");
                    codAluno.setText("");
                    emailAluno.setText("");
                    senhaAluno.setText("");
                } else {
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setTitle("ERRO AO CADASTRAR");
                    fail.setContentText("Erro no cadastro: " + conn.getResponseMessage());
                    fail.show();
                }

            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("FALHA");
                error.setContentText("Erro ao enviar dados: " + e.getMessage());
                error.show();
            }
        } else {
            Alert fail = new Alert(Alert.AlertType.WARNING);
            fail.setTitle("CADASTRO INVÁLIDO!");
            fail.setContentText("Verifique as informações preenchidas!");
            fail.show();
        }
    }

    @FXML
    protected void cadastrarFuncionario() {
        if (!(codFun.getText().isEmpty()) && !(nomeFun.getText().isEmpty()) &&
                !(emailFun.getText().isEmpty()) && !(cpfFun.getText().isEmpty()) &&
                !(salario.getText().isEmpty()) &&
                (nascFun.getValue().isBefore(LocalDate.now().minusYears(16)))) {
            try {
                URI uri = URI.create("http://localhost:3000/usuarios/signup");
                URL url = uri.toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String json = String.format(
                        "{" +
                                "\"nome\":\"%s\"," +
                                "\"cpf\":\"%s\"," +
                                "\"senha\":\"%s\"," +
                                "\"tipo\":\"funcionario\"" +
                                "}",
                        nomeFun.getText(),
                        cpfFun.getText(),
                        senhaFun.getText());

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == 201 || responseCode == 200) {
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("CADASTRO REALIZADO!");
                    success.setContentText("Funcionário cadastrado com sucesso!");
                    success.show();

                    nomeFun.setText("");
                    cpfFun.setText("");
                    salario.setText("");
                    codFun.setText("");
                    emailFun.setText("");
                    senhaFun.setText("");
                    nascFun.setValue(null);
                    dataContrato.setValue(null);
                } else {
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setTitle("ERRO AO CADASTRAR");
                    fail.setContentText("Erro no cadastro: " + conn.getResponseMessage());
                    fail.show();
                }

            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("FALHA");
                error.setContentText("Erro ao enviar dados: " + e.getMessage());
                error.show();
            }
        } else {
            Alert fail = new Alert(Alert.AlertType.WARNING);
            fail.setTitle("CADASTRO INVÁLIDO!");
            fail.setContentText("Verifique as informações preenchidas!");
            fail.show();
        }
    }

    @FXML
    protected void homeButton(ActionEvent event) throws IOException {
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

    @FXML
    protected void searchButton() {
        try {
            String cpf = cpfSearchE.getText();

            // Monta o JSON manualmente ou usando Gson
            String jsonInput = "{\"cpf\": \"" + cpf + "\"}";

            // URL do endpoint do backend
            URI uri = URI.create("http://localhost:3000/usuarios/buscar");
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configura a requisição
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Envia o corpo da requisição
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            if (responseCode == 200 || responseCode == 201) {
                // Lê a resposta
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    String json = response.toString(); // resposta recebida do backend
                    JSONObject obj = new JSONObject(json);

                    // Preenche os campos da interface
                    displayNome.setText(obj.getString("nome"));
                    displayCpf.setText(obj.getString("cpf"));
                    displayCod.setText(obj.getString("codigo"));
                    displayNasc.setText(obj.getString("dataDeNascimento"));
                    displayEmail.setText(obj.getString("email"));
                }
            } else {
                throw new ElementoNaoExisteException(cpfSearchE.getText());
            }

        } catch (Exception e) {
            // Limpa os campos e exibe alerta
            displayNome.setText("");
            displayCpf.setText("");
            displayCod.setText("");
            displayNasc.setText("");
            displayEmail.setText("");

            Alert fail = new Alert(Alert.AlertType.WARNING);
            fail.setTitle("ERRO");
            fail.setContentText("Estudante não encontrado ou erro de conexão!");
            fail.show();
        }
    }

    @FXML
    protected void searchButtonF() {
        int encontrado = 0;

        nomeTextField.setOpacity(0);
        cpfTextField.setOpacity(0);
        codigoTextField.setOpacity(0);
        emailTextField.setOpacity(0);
        senhaTextField.setOpacity(0);
        senhaLabel.setOpacity(0);
        dataAdmissaoLabel.setOpacity(0);
        dataAdmmissaoDatePicker.setOpacity(0);
        dataNascimentoDatePicker.setOpacity(0);
        salarioTextField.setOpacity(0);
        finalizarbutton.setOpacity(0);

        for (Funcionario f : Controlador.getInstance().listarFuncionarios()) {
            if (f.getCpf().equals(cpfSearchF.getText())) {
                displayNomeFun.setText(f.getNome());
                displayCpfFun.setText(f.getCpf());
                displayCodFun.setText(f.getCodigo());
                displayNascFun.setText(f.getDataDeNascimento().toString());
                displayEmailFun.setText(f.getEmail());
                displaySalario.setText(String.valueOf(f.getSalario()));
                encontrado = 1;
            }
        }

        if (encontrado != 1) {
            displayNome.setText("");
            displayCpf.setText("");
            displayCod.setText("");
            displayNasc.setText("");
            displayEmail.setText("");

            Alert fail = new Alert(Alert.AlertType.WARNING);
            fail.setTitle("ERRO");
            fail.setContentText("Funcionário não encontrado!");
            fail.show();
        }
    }

    @FXML
    protected void removeButton() throws ElementoNaoExisteException {

        for (Estudante e : Controlador.getInstance().listarEstudantes()) {
            if (e.getCpf().equals(cpfSearchE.getText())) {

                Controlador.getInstance().removerEstudante(e);
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Estudante removido!");
                success.setContentText("Estudante removido com sucesso!");
                success.show();
                displayNome.setText("");
                displayCpf.setText("");
                displayCod.setText("");
                displayNasc.setText("");
                displayEmail.setText("");
            } else {
                Alert fail = new Alert(Alert.AlertType.WARNING);
                fail.setTitle("ERRO");
                fail.setContentText("Estudante não pôde ser removido!");
                fail.show();
            }
        }

    }

    @FXML
    protected void removeButtonF() {
        for (Funcionario f : Controlador.getInstance().listarFuncionarios()) {
            if (f.getCpf().equals(cpfSearchF.getText())
                    && !cpfSearchF.getText().equals(Controlador.getInstance().getUsuario().getCpf())) {
                try {
                    Controlador.getInstance().removerFuncionario(f);
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Funcionário removido!");
                    success.setContentText("Funcionário removido com sucesso!");
                    success.show();
                    displayNome.setText("");
                    displayCpf.setText("");
                    displayCod.setText("");
                    displayNasc.setText("");
                    displayEmail.setText("");
                    nomeTextField.setOpacity(0);
                    cpfTextField.setOpacity(0);
                    codigoTextField.setOpacity(0);
                    emailTextField.setOpacity(0);
                    senhaTextField.setOpacity(0);
                    senhaLabel.setOpacity(0);
                    dataAdmissaoLabel.setOpacity(0);
                    dataAdmmissaoDatePicker.setOpacity(0);
                    dataNascimentoDatePicker.setOpacity(0);
                    salarioTextField.setOpacity(0);
                    finalizarbutton.setOpacity(0);
                } catch (ElementoNaoExisteException ignored) {
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setTitle("ERRO");
                    fail.setContentText("Funcionário não pôde ser removido!");
                    fail.show();
                }
            } else if (cpfSearchF.getText().equals(Controlador.getInstance().getUsuario().getCpf())) {
                Alert fail = new Alert(Alert.AlertType.WARNING);
                fail.setTitle("ERRO");
                fail.setContentText("Um funcionário não pode ser removido por ele mesmo!");
                fail.show();
            }
        }
    }

    @FXML
    protected void editButton() {
        nomeTextField.setOpacity(1);
        cpfTextField.setOpacity(1);
        codigoTextField.setOpacity(1);
        emailTextField.setOpacity(1);
        senhaTextField.setOpacity(1);
        senhaLabel.setOpacity(1);
        dataAdmissaoLabel.setOpacity(1);
        dataAdmmissaoDatePicker.setOpacity(1);
        dataNascimentoDatePicker.setOpacity(1);
        salarioTextField.setOpacity(1);
        finalizarbutton.setOpacity(1);

        for (Funcionario f : Controlador.getInstance().listarFuncionarios()) {
            if (f.getCpf().equals(cpfSearchF.getText())) {
                nomeTextField.setText(f.getNome());
                cpfTextField.setText(f.getCpf());
                codigoTextField.setText(f.getCodigo());
                dataNascimentoDatePicker.setValue(f.getDataDeNascimento());
                dataAdmmissaoDatePicker.setValue(f.getDataAdmin());
                emailTextField.setText(f.getEmail());
                salarioTextField.setText(String.valueOf(f.getSalario()));
            }
        }

    }

    @FXML
    protected void botaoFinalizar() {
        Double salario = (Double.parseDouble(salarioTextField.getText()));
        if (salario > 0) {

            for (Funcionario f : Controlador.getInstance().listarFuncionarios()) {
                if (f.getCpf().equals(cpfTextField.getText())) {
                    try {
                        Controlador.getInstance().atualizarFuncionario(codigoTextField.getText(),
                                nomeTextField.getText(), cpfTextField.getText(), dataNascimentoDatePicker.getValue(),
                                emailTextField.getText(), senhaTextField.getText(),
                                Double.parseDouble(salarioTextField.getText()), dataAdmmissaoDatePicker.getValue());
                        Alert fail = new Alert(Alert.AlertType.INFORMATION);
                        fail.setTitle("Atualização Realizada");
                        fail.setContentText("As informações do funcionário foram atualizadas.");
                        fail.show();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (ElementoNaoExisteException e) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setTitle("ERRO");
                        fail.setContentText("Funcionário não encontrado!");
                        fail.show();
                    } catch (DataInvalidaException | ParametroVazioException e) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setTitle("ERRO");
                        fail.setContentText(e.getMessage());
                        fail.show();
                    }
                }
            }

        } else {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("ERRO");
            warning.setContentText("Salário Inválido!");
            warning.show();
        }
    }
}
