package view;

import controller.Controlador;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
            Controlador man = new Controlador();
            
        });
        TextField campo = new TextField();
        campo.setAlignment(Pos.CENTER);
        VBox caixaVertical = new VBox(); 
        caixaVertical.setSpacing(5); 
        caixaVertical.setAlignment(Pos.CENTER); 
        
        //caixaVertical.setTranslateX(10);
        //caixaVertical.setTranslateY(20);
        caixaVertical.getChildren().addAll(campo, btn);
        
        Scene cena = new Scene(caixaVertical,300, 300); 
        
        primaryStage.setTitle("Atenção!");
        primaryStage.setScene(cena);
        primaryStage.show();
    }
    
}
