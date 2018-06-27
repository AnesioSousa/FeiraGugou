package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Anésio
 */
public class View extends Application{
    /**
     * Método main da classe View.
     * Ele é somente responsável por chamar o construtor launch da aplicação javaFx, iniciando assim a aplicação.
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Método onde ficam as cenas, layouts, entre outros.
     * É onde começa a aplicação javaFx.
     * @param palco palco inicial da aplicação.
     * @throws Exception 
     */
    @Override
    public void start(Stage palco) throws Exception {
        StackPane raiz = new StackPane();
        Label lblMensagem = new Label("Estou aprendendo JavaFX!");
        raiz.getChildren().add(lblMensagem);
        
        Scene cena  = new Scene(raiz, 600, 500);
        palco.setTitle("Aprendendo JavaFx!");
        palco.setScene(cena);
        palco.show();
    }
    
    
}
