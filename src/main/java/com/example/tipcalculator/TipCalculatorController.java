package com.example.tipcalculator;

// TipCalculatorController.java
// Controller that handles calculateButton and tipPercentageSlider events
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class TipCalculatorController {
    // formatters for currency and percentages
    private static final NumberFormat currency =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percent =
            NumberFormat.getPercentInstance();
    private static final BigDecimal hundred =
            BigDecimal.valueOf(100);
    private BigDecimal tipPercentage = new BigDecimal(0.15); // 15% default

    // GUI controls defined in FXML and used by the controller's code
    @FXML
    private TextField amountTextField;

    @FXML
    private Label tipPercentageLabel;

    @FXML
    private Slider tipPercentageSlider;

    @FXML
    private TextField tipTextField;

    @FXML
    private TextField totalTextField;

    // calculates and displays the tip and total amounts
    @FXML
    private void calculateButtonPressed(ActionEvent event) {
        try {
            BigDecimal amount = new BigDecimal(amountTextField.getText());
            BigDecimal tip = amount.multiply(tipPercentage);
            BigDecimal total = amount.add(tip);

            tipTextField.setText(currency.format(tip));
            totalTextField.setText(currency.format(total));
        }
        catch (NumberFormatException ex) {
            amountTextField.setText("Enter amount");
            amountTextField.selectAll();
            amountTextField.requestFocus();
        }
    }
    private void calculateTip(){
        BigDecimal amount;
        try {
            amount = new BigDecimal(amountTextField.getText());
        } catch (NumberFormatException ex) {
            return;
        }
        BigDecimal tipFactor = BigDecimal.valueOf(tipPercentageSlider.getValue()).divide(hundred, 2, RoundingMode.HALF_UP);
        BigDecimal tip = amount.multiply(tipFactor);
        BigDecimal total = amount.add(tip);
        tipTextField.setText(currency.format(tip));
        totalTextField.setText(currency.format(total));
    }

    // called by FXMLLoader to initialize the controller
    public void initialize() {
        tipPercentageLabel.textProperty().bind(tipPercentageSlider.valueProperty().asString("%.0f %%"));

        currency.setRoundingMode(RoundingMode.HALF_UP);
        tipPercentageSlider.valueProperty().addListener(e -> calculateTip());
        amountTextField.textProperty().addListener(e -> calculateTip());
    }
}