/*
 * JFoenix
 * Copyright (c) 2015, JFoenix and/or its affiliates., All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package demos.components;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import demos.ApplicationNoModule;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DatePickerDemo extends ApplicationNoModule
{

   @Override
   public void start(Stage stage)
   {

      FlowPane main = new FlowPane();
      main.setVgap(20);
      main.setHgap(20);

      DatePicker datePicker = new DatePicker();

      main.getChildren().add(datePicker);
      JFXDatePicker datePickerFX = new JFXDatePicker();

      main.getChildren().add(datePickerFX);
      datePickerFX.setPromptText("pick a date");
      JFXTimePicker blueDatePicker = new JFXTimePicker();
      blueDatePicker.setDefaultColor(Color.valueOf("#3f51b5"));
      blueDatePicker.setOverLay(true);
      main.getChildren().add(blueDatePicker);

      StackPane pane = new StackPane();
      pane.getChildren().add(main);
      StackPane.setMargin(main, new Insets(100));
      pane.setStyle("-fx-background-color:WHITE");

      final Scene scene = new Scene(pane, 400, 700);
      final ObservableList<String> stylesheets = scene.getStylesheets();
      stylesheets.addAll(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(), JFoenixResources.load("css/jfoenix-design.css").toExternalForm());
      stage.setTitle("JFX Date Picker Demo");
      stage.setScene(scene);
      stage.show();

   }

   // toRun:
   // --add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix --add-exports=javafx.base/com.sun.javafx.binding=com.jfoenix --add-exports=javafx.graphics/com.sun.javafx.stage=com.jfoenix --add-exports=javafx.base/com.sun.javafx.event=com.jfoenix

   public static void main(String[] args)
   {
      launch(args);
   }

}
