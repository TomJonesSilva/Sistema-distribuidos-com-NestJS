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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

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
    protected void cadastrarEstudante (){
        if(!(senhaAluno.getText().equals("")) && !(nomeAluno.getText().equals("")) &&  // se não há algum
                !(emailAluno.getText().equals("")) && !(cpfAluno.getText().equals("")) &&  // campo em branco
                !(matriculaAluno.getText().equals("")) && !(codAluno.getText().equals("")) &&
                (nascAluno.getValue().isBefore(LocalDate.now().minusYears(16)))){
            try {
                Controlador.getInstance().inserirEstudante(new Estudante(codAluno.getText(),
                        nomeAluno.getText(), cpfAluno.getText(), nascAluno.getValue(),
                        emailAluno.getText(), senhaAluno.getText(), matriculaAluno.getText()));
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("CADASTRO REALIZADO!");
                success.setContentText("Estudante cadastro com sucesso!");
                success.show();

                nomeAluno.setText("");
                cpfAluno.setText("");
                nascAluno.setValue(null);
                matriculaAluno.setText("");
                codAluno.setText("");
                emailAluno.setText("");
                senhaAluno.setText("");

            } catch (ElementoJaExisteException e) {
                Alert repeated = new Alert(Alert.AlertType.WARNING);
                repeated.setTitle("CADASTRO INVÁLIDO!");
                repeated.setContentText(e.getMessage());
                repeated.show();
            }
        } else {
            Alert fail = new Alert(Alert.AlertType.WARNING);
            fail.setTitle("CADASTRO INVÁLIDO!");
            fail.setContentText("Verifique as informações preenchidas!");
            fail.show();
        }
    }

    @FXML
    protected void cadastrarFuncionario (){

        if(!(codFun.getText().equals("")) && !(nomeFun.getText().equals("")) &&  // se não há algum
                !(emailFun.getText().equals("")) && !(cpfFun.getText().equals("")) &&  // campo em branco
                !(salario.getText().equals("")) &&
                (nascFun.getValue().isBefore(LocalDate.now().minusYears(16)))){
            try {
                Controlador.getInstance().inserirFuncionario(new Funcionario(codFun.getText(),
                        nomeFun.getText(), cpfFun.getText(), nascFun.getValue(),
                        emailFun.getText(), senhaFun.getText(), Double.parseDouble(salario.getText()),
                        dataContrato.getValue()));

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("CADASTRO REALIZADO!");
                success.setContentText("Funcionário cadastro com sucesso!");
                success.show();

                nomeFun.setText("");
                cpfFun.setText("");
                salario.setText("");
                codFun.setText("");
                emailFun.setText("");
                senhaFun.setText("");
                nascFun.setValue(null);
                dataContrato.setValue(null);

            } catch (ElementoJaExisteException e) {
                Alert repeated = new Alert(Alert.AlertType.WARNING);
                repeated.setTitle("CADASTRO INVÁLIDO!");
                repeated.setContentText(e.getMessage());
                repeated.show();
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
        if (UserAtual.getInstance().gettipoUser()==1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaAluno.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Tela Inicial");
        } else if (UserAtual.getInstance().gettipoUser()==2) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TelaFuncionario.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Tela inicial");
        }
    }

    @FXML
    protected void searchButton () throws ElementoNaoExisteException {
        int encontrado = 0;

    	for (Estudante f: Controlador.getInstance().listarEstudantes()) {

            if (f.getCpf().equals(cpfSearchE.getText())) {
                displayNome.setText(f.getNome());
                displayCpf.setText(f.getCpf());
                displayCod.setText(f.getCodigo());
                displayNasc.setText(f.getDataDeNascimento().toString());
                displayEmail.setText(f.getEmail());
                encontrado = 1;
            }
        }

        if(encontrado != 1){
            displayNome.setText("");
            displayCpf.setText("");
            displayCod.setText("");
            displayNasc.setText("");
            displayEmail.setText("");

            Alert fail = new Alert(Alert.AlertType.WARNING);
            fail.setTitle("ERRO");
            fail.setContentText("Estudante não encontrado!");
            fail.show();
        }
    }

    @FXML
    protected void searchButtonF (){
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

        for (Funcionario f: Controlador.getInstance().listarFuncionarios()) {
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

        if(encontrado != 1){
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
    protected void removeButton() throws ElementoNaoExisteException{

        for (Estudante e: Controlador.getInstance().listarEstudantes()) {
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
            }else{
                Alert fail = new Alert(Alert.AlertType.WARNING);
                fail.setTitle("ERRO");
                fail.setContentText("Estudante não pôde ser removido!");
                fail.show();
            }
        }

    }

    @FXML
    protected void removeButtonF() {
        for (Funcionario f: Controlador.getInstance().listarFuncionarios()) {
            if (f.getCpf().equals(cpfSearchF.getText()) && !cpfSearchF.getText().equals(Controlador.getInstance().getUsuario().getCpf())) {
                try{
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
                }catch (ElementoNaoExisteException ignored){
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setTitle("ERRO");
                    fail.setContentText("Funcionário não pôde ser removido!");
                    fail.show();
                }
            }
            else if(cpfSearchF.getText().equals(Controlador.getInstance().getUsuario().getCpf())){
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

        for (Funcionario f: Controlador.getInstance().listarFuncionarios()) {
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
        if(salario>0){

            for (Funcionario f: Controlador.getInstance().listarFuncionarios()) {
                if (f.getCpf().equals(cpfTextField.getText())) {
    		        try {
    			        Controlador.getInstance().atualizarFuncionario(codigoTextField.getText(),nomeTextField.getText()
                                ,cpfTextField.getText(),dataNascimentoDatePicker.getValue(), emailTextField.getText()
                                ,senhaTextField.getText(),Double.parseDouble(salarioTextField.getText()),dataAdmmissaoDatePicker.getValue());
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
