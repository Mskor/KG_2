package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.ConjugationManager;
import model.InnerConjugationValidator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Slider cir_1_y;

    @FXML
    private Slider cir_2_x;

    @FXML
    private Slider cir_2_y;

    @FXML
    private Circle circle_1;

    @FXML
    private Circle circle_2;

    @FXML
    private Label status_lbl;

    @FXML
    private Button action_btn;

    @FXML
    private Pane viewPane;

    @FXML
    private Slider cir_1_r;

    @FXML
    private Slider cir_2_r;

    @FXML
    private Button clear_btn;

    @FXML
    private Slider cir_1_x;

    @FXML
    private Line connector_ln;

    @FXML
    private Circle conjugation_cir;

    @FXML
    private Circle conjugation_cir1;

    @FXML
    private Slider con_rad;

    private Timer t;

    @FXML
    void initialize() {

        // Configure bindings & listeners

        ChangeListener validationListener = (obs_v, old_v, new_v) -> {
            if (InnerConjugationValidator.validate(circle_1, circle_2, conjugation_cir.getRadius())) {
                conjugation_cir.setVisible(false);
                //conjugation_cir1.setVisible(false);
                status_lbl.setText("Possible");
                action_btn.setDisable(false);
            } else {
                conjugation_cir.setVisible(false);
                //conjugation_cir1.setVisible(false);
                status_lbl.setText("Impossible");
                action_btn.setDisable(true);
            }
        };

        // Red circle
        circle_1.centerXProperty().bindBidirectional(cir_1_x.valueProperty());
        //circle_1.centerXProperty().addListener(validationListener);
        circle_1.centerYProperty().bindBidirectional(cir_1_y.valueProperty());
        //circle_1.centerYProperty().addListener(validationListener);
        circle_1.radiusProperty().bindBidirectional(cir_1_r.valueProperty());
        //circle_1.radiusProperty().addListener(validationListener);

        // Blue circle
        circle_2.centerXProperty().bindBidirectional(cir_2_x.valueProperty());
        //circle_2.centerXProperty().addListener(validationListener);
        circle_2.centerYProperty().bindBidirectional(cir_2_y.valueProperty());
        // circle_2.centerYProperty().addListener(validationListener);
        circle_2.radiusProperty().bindBidirectional(cir_2_r.valueProperty());
        //circle_2.radiusProperty().addListener(validationListener);

        // Line connecting two centers
        connector_ln.startXProperty().bindBidirectional(circle_1.centerXProperty());
        connector_ln.startYProperty().bindBidirectional(circle_1.centerYProperty());
        connector_ln.endXProperty().bindBidirectional(circle_2.centerXProperty());
        connector_ln.endYProperty().bindBidirectional(circle_2.centerYProperty());

        // Conjugation radius
        conjugation_cir.radiusProperty().bindBidirectional(con_rad.valueProperty());
        conjugation_cir1.radiusProperty().bindBidirectional(con_rad.valueProperty());
        //con_rad.valueProperty().addListener((validationListener));

        // Event handling

        EventHandler<ActionEvent> action = (e) -> {
            Point2D con_center = ConjugationManager.calculate(circle_1, circle_2, conjugation_cir.getRadius());
            conjugation_cir.setCenterX(con_center.getX());
            conjugation_cir.setCenterY(con_center.getY());
            conjugation_cir.setVisible(true);
            Point2D con_center1 = ConjugationManager.calculateInverse(circle_1, circle_2, conjugation_cir.getRadius());
            conjugation_cir1.setCenterX(con_center1.getX());
            conjugation_cir1.setCenterY(con_center1.getY());
            conjugation_cir.setVisible(true);
        };

        EventHandler<ActionEvent> clear = (e) -> {
            conjugation_cir.setVisible(false);
            conjugation_cir1.setVisible(false);
            if (t != null) {
                t.cancel();
                t.purge();
            }
        };

        EventHandler<ActionEvent> animate = (e) -> {
            Double max_con_rad = 100.0d;

            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        conjugation_cir.setRadius((System.currentTimeMillis() % 10000) / 100);
                        if ((InnerConjugationValidator.validate(circle_1, circle_2, conjugation_cir.getRadius()))) {

                            Point2D con_center = ConjugationManager.calculate(circle_1, circle_2, conjugation_cir.getRadius());
                            conjugation_cir.setCenterX(con_center.getX());
                            conjugation_cir.setCenterY(con_center.getY());
                            conjugation_cir.setVisible(true);
                            Point2D con_center1 = ConjugationManager.calculateInverse(circle_1, circle_2, conjugation_cir.getRadius());
                            conjugation_cir1.setCenterX(con_center1.getX());
                            conjugation_cir1.setCenterY(con_center1.getY());
                            conjugation_cir1.setVisible(true);
                        } else {
                            conjugation_cir.setVisible(false);
                            conjugation_cir1.setVisible(false);
                        }
                    });
                }
            };

            t = new Timer();
            t.schedule(tt, 0l, 10l);
        };

        action_btn.setOnAction(animate);
        clear_btn.setOnAction(clear);
    }

    private void setBindingsForConRad() {
        // Conjugation radius
        conjugation_cir.radiusProperty().bindBidirectional(con_rad.valueProperty());
        conjugation_cir1.radiusProperty().bindBidirectional(con_rad.valueProperty());
    }

    private void remBindingsForConfRad() {
        conjugation_cir.radiusProperty().unbindBidirectional(con_rad.valueProperty());
        conjugation_cir1.radiusProperty().unbindBidirectional(con_rad.valueProperty());
    }
}
