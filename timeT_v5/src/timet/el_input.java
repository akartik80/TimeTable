package timet;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static timet.GUI.list;
import static timet.TimeT2.basket_ece;
import static timet.TimeT2.basket_it;
import static timet.TimeT2.ece;
import static timet.TimeT2.it;
import static timet.TimeT2.sub_ECE_basket;
import static timet.TimeT2.sub_IT_basket;

public class el_input extends Application {

    TimeT2 time = new TimeT2();

    public static void start_el(Stage stage) {
        Scene scene = new Scene(new Group(), 450, 250);
        Button it = new Button("IT baskets");
        Button ece = new Button("ECE baskets");
        
        it.getStyleClass().addAll("actual-button", "button");
        ece.getStyleClass().addAll("actual-button", "button");
        
        Button back = new Button("BACK");
        Label l = new Label("Enter No: of Free Classes :");;
        TextField free = new TextField();
        free.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent arg0) {
                // firsttext(new Label("Number of Baskets in IT :"), "it");
                //  GUI g = new GUI();
                int no = Integer.parseInt(free.getText());
                TimeT2.free_classes = no;
            }

        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent arg0) {
                // firsttext(new Label("Number of Baskets in IT :"), "it");
                GUI g = new GUI();
                g.start(stage);
            }

        });
        it.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent arg0) {
                firsttext(new Label("Number of Baskets in IT :"), "it");
            }

        });
        ece.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent arg0) {
                firsttext(new Label("Number of Baskets in ece :"), "ece");
            }

        });

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(it, 0, 0);
        grid.add(ece, 0, 1);
        grid.add(l, 0, 2);
        grid.add(free, 2, 2);
        grid.add(back, 0, 3);
        // grid.add(notification, 1, 0);

        Group root = (Group) scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void firsttext(Label l, String choice) {

        Stage stage = new Stage();
        Scene scene = new Scene(new Group(), 450, 250);
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        TextField n = new TextField();
        grid.add(l, 0, 0);
        grid.add(n, 3, 0);
        n.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        // enter has been pressed in the text field.
                        // take whatever action has been required here.
                        int no = Integer.parseInt(n.getText());
                        switch (choice) {
                            case "it":
                                TimeT2.basket_it = no;
                                System.out.println("it");
                                break;
                            case "ece":
                                TimeT2.basket_ece = no;
                                System.out.println("ece");
                                break;
                        }
                        baskets(no, stage, choice);
                    }
                });
        Group root = (Group) scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
    }

    static void baskets(int n, Stage stage, String choice) {
        Scene scene = new Scene(new Group(), 450, 250);
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        TextField basket[] = new TextField[n];
        for (int i = 0; i < n; i++) {
            Label l = new Label("No: of Subjects in Basket" + (i + 1) + " :");
            basket[i] = new TextField();
            grid.add(l, 0, i);
            grid.add(basket[i], 2, i);
            basket[i].getOnAction();
        }

        Group root = (Group) scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
        for (int i1 = 0; i1 < n; i1++) {
            basket[i1].setOnAction(new MyEventHandler2(choice, basket[i1]));
        }

    }

    static class MyEventHandler2 implements EventHandler<ActionEvent> {

        String ch = "";
        TextField f = new TextField();

        public MyEventHandler2(String choice, TextField t) {
            this.f = t;
            this.ch = choice;
        }

        @Override
        public void handle(ActionEvent event) {

            int no = Integer.parseInt(f.getText());
            if (ch == "it") {

                it.add(no);

            } else {

                ece.add(no);

            }

            //textfields(no, stage, ch);
        }

    }

}
