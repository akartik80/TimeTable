package timet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import static timet.Teach.teachers;
import static timet.TimeT.classes;
import static timet.TimeT.days;

//import com.aquafx_project.AquaFx;
public class GUI extends Application {
//...creating class TimeT ka object............//

    static TimeT time = new TimeT();
    static TimeT2 time2 = new TimeT2();
    static Teach list = new Teach();
    static int limit;
    int i1;
    static Button bk;
//......function to callback pages.............//

    public VBox createPage(int pageIndex) {
        VBox box = new VBox(5);
        int currentIndex = pageIndex;

        VBox element = new VBox();
        Label title = new Label("Class " + time.classes[currentIndex]);
        title.getStyleClass().add("class-label");
        //title.setStyle("-fx-font-family: sample; -fx-font-size: 30; -fx-text-alignment:center");
        box.getChildren().addAll(title, tables[currentIndex]);

        return box;
    }

//.....creating an array of tables.............//
    TableView<Info_time> tables[];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
//.......calling the GUI start function.......//
    public void start(Stage stage) {

        Button open = new Button("Load");
        Button generate = new Button("Generate");
        Button el_load = new Button("elective input");

        open.setStyle("-fx-padding: 5 22 5 22; -fx-border-color: #e2e2e2; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: #1F9166; -fx-font-family: 'Segoe UI', Helvetica, Arial, sans-serif; -fx-font-size: 11pt; -fx-text-fill: #d8d8d8;");
        generate.setStyle("-fx-padding: 5 22 5 22; -fx-border-color: #e2e2e2; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: #1F9166; -fx-font-family: 'Segoe UI', Helvetica, Arial, sans-serif; -fx-font-size: 11pt; -fx-text-fill: #d8d8d8;");
        el_load.setStyle("-fx-padding: 5 22 5 22; -fx-border-color: #e2e2e2; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: #1F9166; -fx-font-family: 'Segoe UI', Helvetica, Arial, sans-serif; -fx-font-size: 11pt; -fx-text-fill: #d8d8d8;");

        open.translateXProperty().bind(stage.widthProperty().divide(2).subtract(75));
        generate.translateXProperty().bind(stage.widthProperty().divide(2).subtract(75));
        el_load.translateXProperty().bind(stage.widthProperty().divide(2).subtract(75));
        open.setTranslateY(50);
        generate.setTranslateY(60);
        el_load.setTranslateY(70);
        generate.translateYProperty().bind(stage.heightProperty().divide(2).subtract(75));
        open.translateYProperty().bind(stage.heightProperty().divide(2).subtract(75));
        el_load.translateYProperty().bind(stage.heightProperty().divide(2).subtract(75));

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        // Label candidatesLbl = new Label("Left");
        GridPane.setHalignment(open, HPos.CENTER);
        gridpane.add(open, 0, 0);
        gridpane.add(generate, 0, 1);
        GridPane.setHalignment(generate, HPos.CENTER);
        gridpane.add(el_load, 0, 2);
        GridPane.setHalignment(el_load, HPos.CENTER);

        stage.setScene(new Scene(gridpane, 300, 250));
        stage.show();
        //  scene.getStylesheets().add("style.css");

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent arg0) {
                //  Stage stage = new Stage();
                String s = "teacher_student_list.txt";
                file_list(stage, s);
            }

        });

        el_load.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent arg0) {
                el_input e = new el_input();
                el_input.start_el(stage);
            }

        });
        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent arg0) {

                try {
                    timetable(stage);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

    }

    void file_list(Stage stage, String s) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        System.out.println(file);
        File destFile = new File(s);
        if (!destFile.exists()) {
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream input = null;
        OutputStream output = null;

        try {

            // FileInputStream to read streams
            input = new FileInputStream(file);

            // FileOutputStream to write streams
            output = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != input) {
                    input.close();
                }

                if (null != output) {
                    output.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    void timetable(Stage stage) throws FileNotFoundException {
        limit = list.generate_teacherlist();
        System.out.println(limit);
        time.generate();
        time2.generate();
        stage.setWidth(440);
        stage.setHeight(550);
        Font.loadFont(getClass().getResourceAsStream("/resources/fonts/lato.ttf"), 14);
        
        bk = new Button("Check Validity");
        bk.translateXProperty().bind(stage.widthProperty().divide(2).subtract(75));
        bk.translateYProperty().bind(stage.heightProperty().divide(2).subtract(350));
        bk.getStyleClass().addAll("button", "actual-button");
        
        bk.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent arg0) {
                boolean k = time2.checkValid();
                if (k == true) {
                    Stage dialogStage = new Stage();
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.setScene(new Scene(VBoxBuilder.create().
                            children(new Text("All is Well"), new Button("Ok.")).
                            alignment(Pos.CENTER).padding(new Insets(5)).build()));
                    dialogStage.show();
                } else {
                    if (k == true) {
                        Stage dialogStage = new Stage();
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        dialogStage.setScene(new Scene(VBoxBuilder.create().
                                children(new Text("All NOT Well"), new Button("Ok")).
                                alignment(Pos.CENTER).padding(new Insets(5)).build()));
                        dialogStage.show();
                    }
                }
            }

        });
//.......creating c classes.........//
        tables = new TableView[time.c];

        for (int i = 0; i < time.c; i++) {
//.....table creation.......
            tables[i] = new TableView<>();
            tables[i].setEditable(true);
            tables[i].setFixedCellSize(60);
            tables[i].setMinHeight(350);

            AnchorPane.setLeftAnchor(tables[i], 0.0);
            AnchorPane.setTopAnchor(tables[i], 0.0);
            AnchorPane.setBottomAnchor(tables[i], 0.0);
            AnchorPane.setRightAnchor(tables[i], 0.0);
//.........end.......//

//..........coloumn.......................//
            TableColumn[] t = new TableColumn[6];

            //  tables[i].setEditable(true);
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    t[j] = new TableColumn("Day");

                    t[j].setCellValueFactory(
                            new PropertyValueFactory<>("dayi")
                    );

                    t[j].getStyleClass().add("first-col");
                } else if (j == 5) {
                    t[j] = new TableColumn("Lab");

                    t[j].setCellValueFactory(
                            new PropertyValueFactory<>("lab")
                    );
                    t[j].getStyleClass().add("col");
                } else {
                    t[j] = new TableColumn("Period" + j);

                    t[j].setCellValueFactory(
                            new PropertyValueFactory<>("period" + j)
                    );
                }

                t[j].getStyleClass().add("col");

                t[j].setCellFactory(new Callback() {
                    @Override
                    public Object call(Object column) {
                        return new TableCell<Info_time, String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item != null) {
                                    if (item.equals("Monday") || item.equals("Tuesday") || item.equals("Wednesday") || item.equals("Thursday") || item.equals("Friday")) {
                                        getStyleClass().add("days");
                                    } else {
                                        getStyleClass().add("else");
                                    }

                                    setText(item);
                                }
                            }
                        };
                    }
                });
            }

//...........end of coloumns.........................................//
            ObservableList<Info_time> data = FXCollections.observableArrayList();
            int h1[] = new int[4];
            String[] h = new String[4];
            String[] h2 = new String[4];
            String[] f = new String[4];
            for (int y = 0; y < 4; y++) {
                h[y] = " ";
            }
            for (int y = 0; y < 4; y++) {
                f[y] = " ";
            }
            for (int y = 0; y < 4; y++) {
                h2[y] = " ";
            }
            String dy = " ";
            String lab = " ";
            String sub = " ";
            int p = 0;
            int l = 0;
//....checking in each level.......................//
            for (int j = 0; j < 20; j++) {
                //.....when day changes add the h array to row...(adds from monday to thursday).........//
                if (j % 4 == 0) {

                    //   lab = TimeT2.lab_new[i][l++].toString();
                    if (j != 0) { /*data.add(new Info_time(dy, list.teachers.get(h1[0] - 1).toString(),
                         list.teachers.get(h1[1] - 1).toString(),
                         list.teachers.get(h1[2] - 1).toString(),
                         list.teachers.get(h1[3] - 1).toString()));*/

                        for (int jv = 0; jv < 4; jv++) {
                            if (h2[jv] == "free") {
                                f[jv] = "free";
                            } else {
                                f[jv] = h2[jv] + " \r\n ( " + h[jv] + " )";
                            }
                        }
                        data.add(new Info_time(dy, f[0], f[1], f[2], f[3], lab));

                    }
                    //..................lab kheliye...............................//
                    lab = " ";
                    String dummy = " ";
                    int size2 = time2.lab_new[i][l].size();
                    if (size2 == 0) {
                        lab = "free";
                        sub = "";
                    } else {
                        for (int ik = 0; ik < time2.lab_new[i][l].size(); ik++) {
                            if (size2 == 1 && !((int) time2.lab_new[i][l].get(ik) == -1)) {
                                lab = "" + list.teachers.get((int) time2.lab_new[i][l].get(ik));
                                sub = lab.toLowerCase().trim();
                                sub = "" + TimeT2.teacher_lab_map.get(sub).get(classes[i]);

                            } else {
                                if ((int) time2.lab_new[i][l].get(ik) == -1) {
                                    lab = "( class )" + lab;
                                    sub = "";
                                } else {
                                    dummy = list.teachers.get((int) time2.lab_new[i][l].get(ik)).toString();
                                    lab = lab + list.teachers.get((int) time2.lab_new[i][l].get(ik));
                                    sub = dummy.toLowerCase().trim();
                                    sub = "" + TimeT2.teacher_lab_map.get(sub).get(classes[i]);
                                    if (ik != size2 - 1) {
                                        lab = lab + "/";
                                        sub = sub + "/";
                                    }
                                }

                            }

                        }
                    }
                    lab = sub + "\r\n" + lab;
                    l++;
                    p = 0;
                    dy = days[(j / 4)];
                    for (int y = 0; y < 4; y++) {
                        h[y] = " ";
                    }
                    for (int y = 0; y < 4; y++) {
                        h2[y] = " ";
                    }
                    for (int y = 0; y < 4; y++) {
                        f[y] = " ";
                    }
                }
                int size = time2.out_new[i][j].size();
                int size2 = time2.out_new1[i][j].size();
                if (size2 == 0) {
                    h2[p] = "free";
                }

                for (int ik = 0; ik < size2; ik++) {
                    //     String str = time2.out_new1[i][j].get(ik).toString();
                    if (size2 == 1) {
                        h2[p] = " " + time2.out_new1[i][j].get(ik);
                    } else {

                        h2[p] = h2[p] + time2.out_new1[i][j].get(ik);

                        /*       if(ik%2 == 0)
                         {
                         h2[p] = h2[p] +"\r\n";
                         }*/
                        if (ik != size2 - 1) {
                            h2[p] = h2[p] + "/";
                        }

                    }

                }
///...................out_new kheliye......................//
                if (size == 0) {
                    h[p] = "free";
                }
                for (int ik = 0; ik < size; ik++) {
                    if (size == 1) {
                        h[p] = " " + list.teachers.get((int) time2.out_new[i][j].get(ik));
                    } else {
                        h[p] = h[p] + list.teachers.get((int) time2.out_new[i][j].get(ik));
                        if (ik != size - 1) {
                            h[p] = h[p] + "/";
                        }
                    }
                }
//.................out kheliye..............................//
                int flag1 = 0;
//........checking if teacher is avalible for that day.......//
                for (int k = 0; k < time.t; k++) {
                    if (time.out[i][k][j] == 1) {
                        flag1 = 1;
                        h1[p] = (k + 1);

                        break;
                    }
                }
                if (flag1 == 0) {
                    //      h[p] = "free";
                    h1[p] = limit;
                }
                p++;
//........add once the last level has been searched..in case of friday..........//
                if (j == 19) {


                    /* data.add(new Info_time(dy, list.teachers.get(h1[0] - 1).toString(),
                     list.teachers.get(h1[1] - 1).toString(),
                     list.teachers.get(h1[2] - 1).toString(),
                     list.teachers.get(h1[3] - 1).toString()));*/
                    for (int jv = 0; jv < 4; jv++) {
                        if (h2[jv] == "free") {
                            f[jv] = "free";
                        } else {
                            f[jv] = h2[jv] + " \r\n ( " + h[jv] + " )";
                        }
                        System.out.println(".............");
                        System.out.print(f[jv]);
                    }
                    data.add(new Info_time(dy, f[0], f[1], f[2], f[3], lab));
                }

            }

//...........add complete rows and coloumns to table.....//
            tables[i].setItems(data);
            tables[i].getColumns().addAll(t);
            tables[i].setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            //tables[i].setFixedCellSize(25);
            tables[i].prefHeightProperty().bind(Bindings.size(tables[i].getItems()).multiply(tables[i].getFixedCellSize()).add(30));
        }

        //....creating pages.........//
        Pagination page = new Pagination(time.c);
        page.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        //page.setStyle("-fx-border-color:blue;");
        page.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });

//......creating tab pane.........//
        Tab tab1 = new Tab();
        tab1.setText("Students");
        tab1.setGraphic(new ImageView(new Image("book.png")));

        Tab tab2 = new Tab();
        tab2.setGraphic(new ImageView(new Image("board.png")));
        tab2.setText("Teachers");

        TabPane tabPane = new StretchedTabPane();

        // start style------------------------------------------------
        tab1.getStyleClass().add("tab");
        tab2.getStyleClass().add("tab");
        tabPane.getStyleClass().add("tabPane");
        // end style---------------------------------------------------

//.......creating anchor pane_setting the pages...............//
        AnchorPane anchor = new AnchorPane();
        anchor.setTopAnchor(page, 10.0);
        anchor.setRightAnchor(page, 10.0);
        anchor.setBottomAnchor(page, 10.0);
        anchor.setLeftAnchor(page, 10.0);

//........creating the list of teachers............//
        int size = list.teachers.size();
        TilePane tile = new TilePane();
        tile.setHgap(10);
        tile.setVgap(10);

//........creating button array.................//
        Button bn[] = new Button[size - 1];
        //int i1 = 0;
        for (i1 = 0; i1 < size - 1; i1++) {
            bn[i1] = new Button();
            String h = list.teachers.get(i1).toString();
            bn[i1].setText(h);
            bn[i1].getOnAction();
            bn[i1].getStyleClass().add("actual-button");
        }

        AnchorPane anchor1 = new AnchorPane();
        //tile.setStyle("-fx-border-color:blue;");

//.........adding the button array to a tile pane........//
        tile.getChildren().addAll(bn);
        tile.setPrefColumns(4);
        tile.setTileAlignment(Pos.CENTER_LEFT);
        anchor1.setTopAnchor(tile, 100.0);
        anchor1.setRightAnchor(tile, 10.0);
        anchor1.setBottomAnchor(tile, 80.0);
        anchor1.setLeftAnchor(tile, 10.0);
        anchor1.setTopAnchor(bk, 10.0);
        anchor1.getChildren().add(bk);

        anchor1.getChildren().addAll(tile);
        tab2.setContent(anchor1);

//...adding the pages to anchor.................//
        anchor.getChildren().addAll(page);
        tab1.setContent(anchor);
        tabPane.getTabs().addAll(tab1, tab2);
//.....adding anchor to scene...................//

        Scene scene = new Scene(tabPane, 600, 600);
        scene.getStylesheets().add("style.css");
        //page.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        stage.setScene(scene);
        stage.show();

//........adding eventhandler to buttons........//
        for (i1 = 0; i1 < size - 1; i1++) {
            bn[i1].setOnAction(new MyEventHandler(i1, tab2, tab1, bn, stage));
        }

        //AquaFx.style();
        //FlatterFX.style();
    }
//.........button eventhandler function..........................//

    class MyEventHandler implements EventHandler<ActionEvent> {

        int curI;
        Tab tab;
        Tab tab1;
        Button bn[];
        String h1[] = new String[4];
        Stage stage;

        public MyEventHandler(int i, Tab tab, Tab tab1, Button bn[], Stage stage) {
            curI = i;
            this.tab = tab;
            this.tab1 = tab1;
            this.bn = bn;
            this.stage = stage;
        }

        @Override
        public void handle(ActionEvent event) {
            System.out.println("///// " + list.teachers.get(curI).toString());
            System.out.println(curI + " clicked");
            buttonClick(tab, curI, h1, tab1, bn, stage);
        }
    }

//............function to perform on button click..............................//
    void buttonClick(Tab tab2, int i1, String[] h1, Tab tab1, Button[] bn, Stage stage) {
        String dy = "";
        int i, j, k, flag;
        int p = 0;
        ObservableList<Info_time> data = FXCollections.observableArrayList();

//.....creating table for each teacher...................//
        TableView teachT = new TableView();
        TableColumn[] t = new TableColumn[6];

//........adding coloumns to the table.............//
        for (j = 0; j < 6; j++) {
            if (j == 0) {
                t[j] = new TableColumn("Day");

                t[j].setCellValueFactory(
                        new PropertyValueFactory<>("dayi")
                );

                t[j].getStyleClass().add("first-col");
            } else if (j == 5) {
                t[j] = new TableColumn("LAB");

                t[j].setCellValueFactory(
                        new PropertyValueFactory<>("lab")
                );

            } else {
                t[j] = new TableColumn("Period");

                t[j].setCellValueFactory(
                        new PropertyValueFactory<>("period" + j)
                );
            }

            t[j].getStyleClass().add("col");

            t[j].setCellFactory(new Callback() {
                @Override
                public Object call(Object column) {
                    return new TableCell<Info_time, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item != null) {
                                if (item.equals("Monday") || item.equals("Tuesday") || item.equals("Wednesday") || item.equals("Thursday") || item.equals("Friday")) {
                                    getStyleClass().add("days");
                                } else {
                                    getStyleClass().add("else");
                                }

                                setText(item);
                            }
                        }
                    };
                }
            });
        }

        System.out.println();
        System.out.println("For the class : " + teachers.get(i1));

//.........setting lable................//
        Label title = new Label("Prof : " + Teach.teachers1.get(i1));
        title.setStyle("-fx-text-fill: #5a5a5a; -fx-font-family: \"Segoe UI\", Helvetica, Arial, sans-serif; -fx-font-size: 30; -fx-text-alignment:center");
        String[] h = new String[4];
        String lab = " ";

        for (int y = 0; y < 4; y++) {
            h[y] = " ";
        }
        int tk = 0;
        //..........taking data from out.....................//
        for (j = 0; j < 20; j++) {
            if (j % 4 == 0) {

                if (j != 0) {
                    // data.add(new Info_time(dy, h1[0], h1[1], h1[2], h1[3], "lab"));
                    data.add(new Info_time(dy, h[0], h[1], h[2], h[3], lab));
                }
                int size = time2.tt_lab[i1][tk].size();
                if (size == 0) {
                    lab = "free";
                }
                for (int ik = 0; ik < size; ik++) {
                    if (size == 1) {
                        lab = "" + classes[(int) time2.tt_lab[i1][tk].get(ik)];
                        // System.out.println(lab);
                    } else {
                        lab = lab + classes[(int) time2.tt_lab[i1][tk].get(ik)];
                        if (ik != size - 1) {
                            lab = lab + "/";
                        }
                    }
                }

                dy = days[(j / 4)];
                p = 0;
                System.out.print(days[(j / 4)]);
                tk++;
                for (int xx = days[j / 4].length(); xx <= 15; xx++) {
                    System.out.print(" ");
                }
                for (int y = 0; y < 4; y++) {
                    h[y] = " ";
                }
            }

            flag = 0;

            if (j % 4 == 3) {
                flag = 1;
            }
            int flag1 = 0;
            int size = time2.tt[i1][j].size();

            if (size == 0) {
                h[p] = "free";
            }
            for (int ik = 0; ik < size; ik++) {
                if (size == 1) {
                    h[p] = " " + classes[(int) time2.tt[i1][j].get(ik)];
                } else {
                    h[p] = h[p] + classes[(int) time2.tt[i1][j].get(ik)];
                    if (ik != size - 1) {
                        h[p] = h[p] + "/";
                    }
                }
            }
            p++;
            /* for (k = 0; k < c; k++) {
             if (time2.out[k][i1][j] == 1) {
             flag1 = 1;
             h1[p] = classes[k];
             p++;
             if (k <= 9) {
             System.out.print(classes[k] + "\t");
             } else {
             System.out.print(classes[k] + "\t");
             }
             }
             }
             if (flag1 == 0) {
             h1[p] = "free";
             p++;
             System.out.print("free\t");
             }
             if (flag == 1) {
             System.out.println();
             }*/
            if (j == 19) {
                // data.add(new Info_time(dy, h1[0], h1[1], h1[2], h1[3], "lab"));
                data.add(new Info_time(dy, h[0], h[1], h[2], h[3], lab));
            }
        }

//.........creating back button..........................//
        Button back = new Button();
        back.setText("BACK");
        back.getStyleClass().add("actual-button");
        back.setTranslateY(220);
        back.translateXProperty().bind(stage.widthProperty().divide(2).subtract(50));

        teachT.setMinHeight(305);
        teachT.setItems(data);
        teachT.focusedProperty().addListener((a, b, c) -> {
            teachT.getSelectionModel().clearSelection();
        });

//.......adding coloumns to table,data and rezising...........................//
        teachT.getColumns().addAll(t);
        teachT.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //teachT.setFixedCellSize(25);
        teachT.prefHeightProperty().bind(Bindings.size(teachT.getItems()).multiply(teachT.getFixedCellSize()).add(50));
        teachT.setFixedCellSize(50);
        teachT.setMinHeight(305);

//..........adding table,lable and back to an anchor,modeling.................//
        AnchorPane anchor = new AnchorPane();
        anchor.setTopAnchor(teachT, 50.0);
        anchor.setRightAnchor(teachT, 50.0);
        anchor.setLeftAnchor(teachT, 50.0);
        anchor.setTopAnchor(back, 180.0);
        anchor.getChildren().add(title);
        anchor.getChildren().add(teachT);
        anchor.getChildren().add(back);

//.....action for back button.................................................//
        back.setOnAction(new MyEventHandler2(tab2, bn));
        tab2.setContent(anchor);

    }

//.......action class for back................................................//
    class MyEventHandler2 implements EventHandler<ActionEvent> {

        Tab tab2;
        Button[] bn;
        String h1[] = new String[4];

        public MyEventHandler2(Tab tab2, Button[] bn) {
            this.tab2 = tab2;
            this.bn = bn;
        }

        @Override
        public void handle(ActionEvent event) {

//.........adding the previous buttons to tilepane............................//
            TilePane tile = new TilePane();
            tile.setHgap(10);
            tile.setVgap(10);

//................adding title to anchor......................................//
            AnchorPane anchor1 = new AnchorPane();
            //tile.setStyle("-fx-border-color:blue;");
            tile.getChildren().addAll(bn);
            tile.setPrefColumns(4);
            tile.setTileAlignment(Pos.CENTER_LEFT);
            anchor1.setTopAnchor(tile, 100.0);
            AnchorPane.setRightAnchor(tile, 10.0);
            AnchorPane.setBottomAnchor(tile, 80.0);
            AnchorPane.setLeftAnchor(tile, 10.0);
            anchor1.setTopAnchor(bk, 10.0);

            anchor1.getChildren().add(bk);
            anchor1.getChildren().addAll(tile);

//..............adding ancor to tab2..........................................//
            tab2.setContent(anchor1);
        }
    }
}
