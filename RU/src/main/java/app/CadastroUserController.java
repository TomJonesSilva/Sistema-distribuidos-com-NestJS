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
import java.io.InputStream;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
                !(matriculaAluno.getText().isEmpty()) &&
                (nascAluno.getValue().isBefore(LocalDate.now().minusYears(16)))) {
            try {
                URI uri = URI.create("http://localhost:3000/usuarios/signup");
                URL url = uri.toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String dataFormatada = nascAluno.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);

                String json = String.format(
                        "{" +
                                "\"nome\":\"%s\"," +
                                "\"cpf\":\"%s\"," +
                                "\"data_nascimento\":\"%s\"," +
                                "\"email\":\"%s\"," +
                                "\"senha\":\"%s\"," +
                                "\"tipo\":\"estudante\"," +
                                "\"matricula\":\"%s\"" +
                                "}",
                        nomeAluno.getText(),
                        cpfAluno.getText(),
                        dataFormatada,
                        emailAluno.getText(),
                        senhaAluno.getText(),
                        matriculaAluno.getText());
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                System.out.println(json);
                System.out.println(conn.getResponseMessage());
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
        if (!(nomeFun.getText().isEmpty()) &&
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

                String dataFormatadaNas = nascFun.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
                String dataFormatadaAdm = dataContrato.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);

                String json = String.format(
                        "{" +
                                "\"nome\":\"%s\"," +
                                "\"cpf\":\"%s\"," +
                                "\"data_nascimento\":\"%s\"," +
                                "\"email\":\"%s\"," +
                                "\"senha\":\"%s\"," +
                                "\"tipo\":\"funcionario\"," +
                                "\"salario\":\"%s\"," +
                                "\"data_admin\":\"%s\"" +
                                "}",
                        nomeFun.getText(),
                        cpfFun.getText(),
                        dataFormatadaNas,
                        emailFun.getText(),
                        senhaFun.getText(),
                        Double.parseDouble(salario.getText()),
                        dataFormatadaAdm);
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
            URI uri = URI.create("http://localhost:3000/usuarios/buscar");
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = String.format(
                    "{" +

                            "\"cpf\":\"%s\"" + "}",
                    cpfAluno.getText());
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            System.out.println(conn.getResponseMessage());
            System.out.println(conn.getResponseCode());
            System.out.println(conn.getInputStream());
            Map<String, List<String>> headers = conn.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            if (responseCode == 200 || responseCode == 201) {
                // Lê a resposta
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    String jsonR = response.toString();

                    if (jsonR == null || jsonR.isEmpty()) {
                        throw new ElementoNaoExisteException(cpfSearchE.getText());
                    }

                    JSONObject obj = new JSONObject(jsonR);

                    // Verifica se campos importantes existem antes de preencher
                    if (!obj.has("nome") || !obj.has("cpf") || !obj.has("data_nascimento") || !obj.has("email")) {
                        throw new ElementoNaoExisteException(cpfSearchE.getText());
                    }

                    // Preenche os campos da interface
                    displayNome.setText(obj.getString("nome"));
                    displayCpf.setText(obj.getString("cpf"));

                    // Converte data_nascimento para formato legível, se necessário
                    String dataNasc = obj.getString("data_nascimento");
                    if (dataNasc.contains("T")) {
                        dataNasc = dataNasc.split("T")[0]; // Fica só "2005-07-19"
                    }
                    displayNasc.setText(dataNasc);

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
        String cpf = cpfSearchF.getText();
        String url = "http://localhost:3000/usuarios/buscar";

        try {
            // Cria o cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Monta o JSON com o CPF
            String jsonInputString = "{\"cpf\": \"" + cpf + "\"}";

            // Cria a requisição POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            // Envia a requisição e recebe a resposta (bloqueante)
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Resposta JSON como string
                String responseBody = response.body();

                // Converte a resposta JSON para objeto usando Gson
                Gson gson = new Gson();
                JsonObject funcionario = gson.fromJson(responseBody, JsonObject.class);

                // Atualiza os campos da interface
                displayNomeFun.setText(funcionario.get("nome").getAsString());
                displayCpfFun.setText(funcionario.get("cpf").getAsString());
                displayCodFun.setText("N/A"); // Não tem código no retorno atual
                displayNascFun.setText(funcionario.get("data_nascimento").getAsString().substring(0, 10));
                displayEmailFun.setText(funcionario.get("email").getAsString());
                displaySalario.setText(funcionario.get("salario").getAsString());

            } else {
                mostrarAlertaNaoEncontrado();
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaNaoEncontrado();
        }
    }

    // Alerta separado para reuso
    private void mostrarAlertaNaoEncontrado() {
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
