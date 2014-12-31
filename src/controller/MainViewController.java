package controller;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.ConjugationDesc;
import model.ConjugationManager;
import model.InnerConjugationValidator;

import java.net.URL;
import java.util.ResourceBundle;

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
    private Arc conjugation_cir;

    @FXML
    private Slider con_rad;

    @FXML
    void initialize() {
        assert cir_1_y != null : "fx:id=\"cir_1_y\" was not injected: check your FXML file 'MainView.fxml'.";
        assert cir_2_x != null : "fx:id=\"cir_2_x\" was not injected: check your FXML file 'MainView.fxml'.";
        assert cir_2_y != null : "fx:id=\"cir_2_y\" was not injected: check your FXML file 'MainView.fxml'.";
        assert circle_1 != null : "fx:id=\"circle_1\" was not injected: check your FXML file 'MainView.fxml'.";
        assert circle_2 != null : "fx:id=\"circle_2\" was not injected: check your FXML file 'MainView.fxml'.";
        assert status_lbl != null : "fx:id=\"status_lbl\" was not injected: check your FXML file 'MainView.fxml'.";
        assert action_btn != null : "fx:id=\"action_btn\" was not injected: check your FXML file 'MainView.fxml'.";
        assert viewPane != null : "fx:id=\"viewPane\" was not injected: check your FXML file 'MainView.fxml'.";
        assert cir_1_r != null : "fx:id=\"cir_1_r\" was not injected: check your FXML file 'MainView.fxml'.";
        assert cir_2_r != null : "fx:id=\"cir_2_r\" was not injected: check your FXML file 'MainView.fxml'.";
        assert clear_btn != null : "fx:id=\"clear_btn\" was not injected: check your FXML file 'MainView.fxml'.";
        assert cir_1_x != null : "fx:id=\"cir_1_x\" was not injected: check your FXML file 'MainView.fxml'.";

        // Configure bindings & listeners

        ChangeListener validationListener = (obs_v, old_v, new_v) -> {
            if (InnerConjugationValidator.validate(circle_1, circle_2, conjugation_cir.getRadiusX())) {
                conjugation_cir.setVisible(false);
                status_lbl.setText("Possible");
                action_btn.setDisable(false);
            } else {
                conjugation_cir.setVisible(false);
                status_lbl.setText("Impossible");
                action_btn.setDisable(true);
            }
        };

        // Red circle
        circle_1.centerXProperty().bindBidirectional(cir_1_x.valueProperty());
        circle_1.centerXProperty().addListener(validationListener);
        circle_1.centerYProperty().bindBidirectional(cir_1_y.valueProperty());
        circle_1.centerYProperty().addListener(validationListener);
        circle_1.radiusProperty().bindBidirectional(cir_1_r.valueProperty());
        circle_1.radiusProperty().addListener(validationListener);

        // Blue circle
        circle_2.centerXProperty().bindBidirectional(cir_2_x.valueProperty());
        circle_2.centerXProperty().addListener(validationListener);
        circle_2.centerYProperty().bindBidirectional(cir_2_y.valueProperty());
        circle_2.centerYProperty().addListener(validationListener);
        circle_2.radiusProperty().bindBidirectional(cir_2_r.valueProperty());
        circle_2.radiusProperty().addListener(validationListener);

        // Line connecting two centers
        connector_ln.startXProperty().bindBidirectional(circle_1.centerXProperty());
        connector_ln.startYProperty().bindBidirectional(circle_1.centerYProperty());
        connector_ln.endXProperty().bindBidirectional(circle_2.centerXProperty());
        connector_ln.endYProperty().bindBidirectional(circle_2.centerYProperty());

        // Conjugation radius
        conjugation_cir.radiusXProperty().bindBidirectional(con_rad.valueProperty());
        conjugation_cir.radiusYProperty().bindBidirectional(con_rad.valueProperty());
        con_rad.valueProperty().addListener((validationListener));

        // Event handling

        EventHandler<ActionEvent> action = (e) -> {
            ConjugationDesc con_desc = ConjugationManager.calculate(circle_1, circle_2, conjugation_cir.getRadiusX());
            conjugation_cir.setCenterX(con_desc.con_center.getX());
            conjugation_cir.setCenterY(con_desc.con_center.getY());
            conjugation_cir.setStartAngle(con_desc.start_angle);
            if (con_desc.end_angle * con_desc.start_angle > 0) {
                conjugation_cir.setLength(Math.abs(con_desc.end_angle - con_desc.start_angle));
            } else if (con_desc.end_angle < 0) {
                conjugation_cir.setLength(360.0 - (con_desc.start_angle + Math.abs(con_desc.end_angle)));
            } else {
                conjugation_cir.setLength(Math.abs(con_desc.start_angle) + con_desc.end_angle);
            }
            conjugation_cir.setVisible(true);
        };

        EventHandler<ActionEvent> clear = (e) -> conjugation_cir.setVisible(false);

        action_btn.setOnAction(action);
        clear_btn.setOnAction(clear);
    }
}
