package com.binanceui.Controllers;

import com.binanceapibackend.General.GeneralData;
import com.binanceapibackend.Market.MarketData;
import com.binanceapibackend.Market.Models.Candlestick;
import com.binanceui.Symbol;
import com.binanceui.ValidationCommon;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeController {

    private final MarketData marketData = new MarketData();
    private final GeneralData generalData = new GeneralData();

    @FXML
    private LineChart<String, BigDecimal> chartDate;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private TextField txtFieldCrypto;

    private List<String> symbols = (List<String>) generalData.getSymbols().get("LIST_SYMBOLS");
    private XYChart.Series series = new XYChart.Series<>();
    private String crypto = Symbol.BTCUSDT;

    /**
     * @Initialize
     * Initialize - przed wyświetleniem widoku ta metoda jest odpalana.
     */
    @FXML
    public void initialize() {
        setChart();
        TextFields.bindAutoCompletion(txtFieldCrypto, symbols);
        txtFieldCrypto.setText(crypto);
    }

    private void setChart(){
        chartDate.setAnimated(true);
        chartDate.setCreateSymbols(false);
        chartDate.setLegendVisible(false);
        chartDate.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        chartDate.getXAxis().setSide(Side.LEFT);
    }

    @FXML
    private void buttonOnAction() throws IOException {
        if(ValidationCommon.isValueNullOrEmpty(fromDate.getValue(), fromDate) || ValidationCommon.isValueNullOrEmpty(toDate.getValue(), fromDate))
            return;

        if(fromDate.getValue().compareTo(toDate.getValue()) > 0)
            return;

        if(toDate.getValue().compareTo(LocalDate.now()) > 0)
            return;

        series =  new XYChart.Series<>();
        if(chartDate.getData() != null)
            chartDate.getData().clear();

        if(crypto != txtFieldCrypto.getText())
            txtFieldCryptoOnAction();

        LocalDate date = toDate.getValue();
        ZoneId zoneId = ZoneId.systemDefault();
        long epochToDate = date.atStartOfDay(zoneId).toEpochSecond();
        date = fromDate.getValue();
        long epochFromDate = date.atStartOfDay(zoneId).toEpochSecond();


        ArrayList candlestick = marketData.getCandlestickData(crypto, "1d","1000",epochFromDate + "000", epochToDate + "000");
        getData(candlestick, chartDate);
    }

    private void getData(ArrayList candlestick, LineChart<String, BigDecimal> chartDay) {
        for (int i = 0; i < candlestick.size(); i++) {
            Candlestick temp = (Candlestick) candlestick.get(i);
            series.getData().add(new XYChart.Data(Integer.toString(i), Double.valueOf(temp.getOpen())));
            i += 1;
            series.getData().add(new XYChart.Data(Integer.toString((i)), Double.valueOf(temp.getClose())));
        }
        chartDay.getData().addAll(series);
    }

    public void txtFieldCryptoOnAction(){
        String result;

        result = txtFieldCrypto.getText();

        if(result == null || result.isBlank())
            return;

        crypto = result;
        txtFieldCrypto.setText(result);
    }


}
