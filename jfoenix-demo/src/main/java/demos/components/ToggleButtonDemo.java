package demos.components;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXToggleNode;

import demos.ApplicationNoModule;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ToggleButtonDemo extends ApplicationNoModule
{

   @Override
   public void start(Stage stage) throws Exception
   {

      final VBox pane = new VBox();
      pane.setSpacing(30);
      pane.setStyle("-fx-background-color:#EEE; -fx-padding: 40;");

      ToggleButton button = new ToggleButton("JavaFx Toggle");
      pane.getChildren().add(button);

      JFXToggleButton toggleButton = new JFXToggleButton();
      toggleButton.setText("New Skin");
      pane.getChildren().add(toggleButton);

      JFXToggleNode node = new JFXToggleNode();
      node.setStyle("-fx-padding: 10");
      node.setGraphic(new FontIcon(FontAwesomeSolid.HEART));

      pane.getChildren().add(node);

      final Scene scene = new Scene(pane, 600, 400, Color.valueOf("#EEE"));
      stage.setTitle("JFX Toggle Button Demo ");
      scene.getStylesheets().add(JFoenixResources.load("css/jfoenix-components.css").toExternalForm());
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();

   }

   public static void main(String[] args)
   {
      launch(args);
   }

}
