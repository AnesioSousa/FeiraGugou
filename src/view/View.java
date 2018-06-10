package view;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
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
     * @param primaryStage - palco inicial da aplicação.
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button btn = new Button("Abrir");
        
        btn.setOnAction((ActionEvent e) -> {
            DirectoryChooser abrirDir = new DirectoryChooser();
            abrirDir.setInitialDirectory(new File(System.getProperty("user.home")));
            abrirDir.setTitle("Escolha o diretório:");
            
            File dirEscolhido = abrirDir.showDialog(null);
            String caminho = dirEscolhido.getAbsolutePath();
        });
        
        StackPane layout = new StackPane();
        layout.getChildren().addAll(btn);
        Scene cena = new Scene(layout,200, 100); 
        
        primaryStage.setTitle("Atenção!");
        primaryStage.setScene(cena);
        primaryStage.show();
    }
    
}
