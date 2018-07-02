package view;

import controller.GerenciadorDePesquisa;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Dados;

/**
 *
 * @author anesio
 */
public class BoraLa extends Application {
    private static GerenciadorDePesquisa control = new GerenciadorDePesquisa();
    Scene cena1, cena2;
    TableView<Dados> table;
    String palavra;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("Pesquisar");
        Button prox = new Button("prox");
        Button ant = new Button("ant");
        TextField campo = new TextField();
        TextField campoProx = new TextField();
        VBox caixaVertical = new VBox(5);
        
        ant.setOnAction(e -> primaryStage.setScene(cena1));
        
        
        btn.setOnAction(e -> {
            if (campo.getText() != null && !campo.getText().isEmpty()) {
                palavra = campo.getText();
                primaryStage.setScene(cena2);
            }
        });
        
        ToolBar b = new ToolBar();
        
        campoProx.setText(campo.getText());
        campoProx.setMinWidth(0); // Não funciona tb :\
        b.getItems().addAll(ant, prox, new Separator(), campoProx);
        
        
        
        BorderPane border = new BorderPane();
        border.setLeft(ant);
        border.setTop(b);
        cena2 = new Scene(border, 600, 500);

        Label label = new Label("Resultados");
        label.setFont(new Font("Arial", 20));
        
        // Nomeando colunas;
        TableColumn<Dados, String> tituloCol = new TableColumn<>("Título do Arquivo");
        tituloCol.setMinWidth(200);
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        
        TableColumn<Dados, Integer> acessadaCol = new TableColumn<>("Ocorrências");
        acessadaCol.setMinWidth(100);
        acessadaCol.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(getPaginas());
        table.getColumns().addAll(tituloCol, acessadaCol);
        
        VBox results = new VBox(5);
        results.setPadding(new Insets(10, 0, 0, 10));
        results.getChildren().addAll(label, table);
        
        border.setCenter(results);

        campo.setMaxWidth(250);

        caixaVertical.setAlignment(Pos.CENTER);
        //caixaVertical.setPrefSize(300, 250);
        
        caixaVertical.getChildren().addAll(campo,btn);

        cena1 = new Scene(caixaVertical, 600, 500);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(cena1);
        primaryStage.show();
    }
    
    // Carrega a lista de páginas
    public ObservableList<Dados> getPaginas(){
        ObservableList<Dados> paginas = FXCollections.observableArrayList();
        
        paginas.addAll(control.pesquisar("pai"));
        
        return paginas;
    }
    
    
}
