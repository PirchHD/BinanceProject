package com.binanceui.Controllers;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.General.GeneralData;
import com.binanceapibackend.Market.MarketData;
import com.binanceui.Symbol;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class BotController {

    @FXML
    public JFXTextField txtfdSetSymbol;
    @FXML
    public JFXTextField txtfdSetMoney;
    public Label lblEarn;
    public Label lblStartMoney;
    public Label lblCrypro;
    public Label lblInfo;
    @FXML
    private LineChart<String, BigDecimal> chart;
    @FXML
    private NumberAxis xAxis;

    private final GeneralData generalData = new GeneralData();
    private List<String> symbols = (List<String>) generalData.getSymbols().get("LIST_SYMBOLS");
    Timer timer = new Timer();
    private boolean isStart = false;
    private double bound;
    private final MarketData marketData = new MarketData();
    private final ObservableList<XYChart.Data<String, BigDecimal>> seriesData = FXCollections.observableArrayList();
    private String symbol = Symbol.BTCUSDT;
    private Integer money;
    private boolean isSetUp = false;

    /**
     * @Initialize
     * Initialize - przed wyświetleniem widoku ta metoda jest odpalana.
     */
    @FXML
    public void initialize() {
        TextFields.bindAutoCompletion(txtfdSetSymbol, symbols);
        txtfdSetSymbol.setText(Symbol.BTCUSDT);
        setFrontChart();
    }

    private void setFrontChart(){
        setBoundxAxis();
        chart.setAnimated(true);
        chart.setCreateSymbols(false);
        chart.setLegendVisible(false);
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        chart.getXAxis().setSide(Side.LEFT);
    }

    @FXML
    public void btnStartOnAction(){
        if(!isSetUp)
            return;

        this.isStart = true;
        setChart(Symbol.BTCUSDT);
    }

    @FXML
    public void btnStopOnAction(){
        this.isStart = false;
        seriesData.clear();
        chart.getData().clear();
        lblStartMoney.setText("");
        lblEarn.setText("");
    }

    /**
     * @Attributes
     * crypto(String) - Symbol kryptowaluty np. BTCUSDT, gdzie BTC- bitcoin USDT = USD
     *
     * @Description
     * Metoda tworzy dane dla kompomentu chart
     * Jest to wykres pokazujący aktualna cene pewnej kryptowaluty
     *
     * @return -||-
     */
    public void setChart(String crypto){
        ObservableList<XYChart.Series<String, BigDecimal>> data = FXCollections.observableArrayList();
        data.add(new XYChart.Series<>(crypto, seriesData));
        chart.setData(data);

        if(isStart){
            task.run();
            timer.schedule(task, 1000, 1100);
        }

    }

    private void initializeBound() {

        DataMap price = SceneController.ifInFunctionErrorCreateInformationMessage(marketData.getSymbolPriceTicker(symbol));
        if(price.isResultError()){
            task.cancel();
        }else
            this.bound = price.getDouble("CURRENT_PRICE");
    }

    private void setBoundxAxis() {
        initializeBound();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(bound - 100);
        xAxis.setUpperBound(bound + 100);
        xAxis.setTickUnit(25);
    }

    /**
     * @Description
     * To jest wątek, który pobiera aktualna cene dla kryptowaluty
     * i jest wyświetlana na widoku
     */
    TimerTask task = new TimerTask() {
        @Override public void run() {
            Platform.runLater(() -> {
                if (!isStart)
                    return;

                if (seriesData.size() > 20) {
                    seriesData.remove(0);
                }

                DataMap price = marketData.getSymbolPriceTicker(symbol);
                if(price.isResultError()) {
                    task.cancel();
                    SceneController.creatInformationFxml(price);
                }else
                    seriesData.add(new XYChart.Data<>(String.valueOf(LocalDateTime.now().getSecond()), (BigDecimal) price.get("CURRENT_PRICE")));

                if ( (bound + 90) <= price.getDouble("CURRENT_PRICE") || (bound - 90) >= price.getDouble("CURRENT_PRICE"))
                    setBoundxAxis();

                doBot(price.getDouble("CURRENT_PRICE"));

            });
        }
    };

    Double cryptoMoney = 0.0;
    Double moneyBot = 0.0;
    Double buyCurrent = 0.0;

    boolean buy = true;

    private void doBot(Double current) {

        if(buy){
            if(money != 0) {
                cryptoMoney = money / current;
                money = 0;
            }
            else{
                cryptoMoney = moneyBot/current;
            }

            buyCurrent = current;
            lblInfo.setText("Kupilem");
            buy = false;
        }

        if(buyCurrent < current){
            buy = true;

            moneyBot = cryptoMoney * current;
            lblEarn.setText(String.valueOf(moneyBot));
            cryptoMoney = 0.0;
            lblInfo.setText("Sprzedalem");
        }else{
            lblInfo.setText("Oczekuje na wyższą cene");
        }

    }

    public void btnSetOnAction() {

        if(isStart)
            return;

        try {
            String checkSymbol = txtfdSetSymbol.getText();
            Integer checkMoney = Integer.valueOf(txtfdSetMoney.getText());

            if (checkSymbol.isEmpty() || checkMoney == null || checkSymbol.isBlank() || checkSymbol == null){
                return;
            }

        }catch (Exception e){
            SceneController.creatErrorMessage(e);
            return;
        }

        this.symbol = txtfdSetSymbol.getText();
        this.money = Integer.valueOf(txtfdSetMoney.getText());
        lblCrypro.setText(symbol.replace("USDT", ""));
        lblStartMoney.setText(String.valueOf(money));
        isSetUp = true;
    }
}
