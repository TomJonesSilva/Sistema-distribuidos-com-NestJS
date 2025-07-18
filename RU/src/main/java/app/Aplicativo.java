package app;

import exceptions.ElementoJaExisteException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Estudante;
import models.Funcionario;
import negocio.Controlador;

import java.io.IOException;
import java.time.LocalDate;

public class Aplicativo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicativo.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    

    public static void main(String[] args) {
        try {
            Controlador.getInstance().inserirEstudante(new Estudante("1233123","José Silva","12345678900",
                    LocalDate.of(2002,3,21),"email@hotmail.com","senha","1273299786"));

        } catch (ElementoJaExisteException e) {
            System.out.println("Teste");
        }
        Funcionario f = new Funcionario("1233123","Ana Maria","12345678911",
                LocalDate.of(1990,12,12),"email@hotmail.com","senha",1500.99,LocalDate.of(2012,1,2));
        try {
            Controlador.getInstance().inserirFuncionario(f);
        } catch (ElementoJaExisteException e) {
            System.out.println("Teste");
        }
        /*try {
            Controlador.getInstance().getRepositorioTicketRefeicao().inserir(new TicketRefeicao(LocalDate.now(),LocalDate.now(), "12837163",3.50,f, TipoRefeicao.ALMOCO));
        } catch (ElementoJaExisteException e) {
            System.out.println("Teste");
        }*/
        launch();
    }
}
