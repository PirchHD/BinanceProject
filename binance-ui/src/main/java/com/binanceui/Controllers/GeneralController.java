package com.binanceui.Controllers;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.General.GeneralData;
import com.binanceapibackend.Market.MarketData;
import com.binanceapibackend.Market.Models.Candlestick;
import com.binanceapibackend.Market.Models.CandlestickInterval;
import com.binanceui.Symbol;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GeneralController {

    private final GeneralData generalData = new GeneralData();
    private final MarketData marketData = new MarketData();

    @FXML
    private LineChart<Date, BigDecimal> chartDay;
    private XYChart.Series series = new XYChart.Series<>();

    @FXML
    private ComboBox<String> cbPeriod;
    private String crypto = Symbol.BTCUSDT;
    private String fromDateEpoch = "";

    @FXML
    private ComboBox<CandlestickInterval> cbInterval;
    @FXML
    private TableView<Cryptocurrency> tableView;
    @FXML
    private TableColumn<Cryptocurrency, String> clCrypto;
    @FXML
    private TableColumn<Cryptocurrency, String> clChange;

    @FXML
    private TableView<Cryptocurrency> tableViewCurrentPrice;
    @FXML
    private TableColumn<Cryptocurrency, String> clCrypto2;
    @FXML
    private TableColumn<Cryptocurrency, String> clCurrentPrice;

    @FXML
    private TextField txtFieldCrypto;
    private List<String> symbols = (List<String>) generalData.getSymbols().get("LIST_SYMBOLS");

    public static final class Period{
        public static final String YEAR                 = "Roczny";
        public static final String MONTH                = "Miesieczny";
        public static final String WEEK                 = "Tygodniowy";
        public static final String DAY                  = "Dzienny";
    }

    private String[] symbolCheck = {"BTCUSDT", "ETHUSDT", "DOTUSDT", "SOLUSDT", "XRPUSDT", "UNIUSDT", "XLMUSDT", "LUNAUSDT", "ATOMUSDT", "TRXUSDT"};

    public class Cryptocurrency {

        private String cryptoName = null;
        private Double change = null;

        public Cryptocurrency(String cryptoName, double change) {
            this.cryptoName = cryptoName;
            this.change = change;
        }

        public String getCryptoName() {
            return cryptoName;
        }

        public Double getChange() {
            return change;
        }
    }

    /**
     * @Initialize
     * Initialize - przed wyświetleniem widoku ta metoda jest odpalana.
     */
    @FXML
    public void initialize() {
        setChart();
        setCbPeriod();
        setCbInterval();
        series.setName("");
        TextFields.bindAutoCompletion(txtFieldCrypto, symbols);
        txtFieldCrypto.setText(crypto);
        setChartDay();
        setTableForChangerPrice();
        setTableCurrencyPrice();
    }

    private void setTableCurrencyPrice(){
        clCrypto2.setCellValueFactory(new PropertyValueFactory<>("cryptoName"));
        clCurrentPrice.setCellValueFactory(new PropertyValueFactory<>("change"));
        getCurrencyPrice();
    }

    private void setTableForChangerPrice(){
        clCrypto.setCellValueFactory(new PropertyValueFactory<>("cryptoName"));
        clChange.setCellValueFactory(new PropertyValueFactory<>("change"));
        generateDataChange();
    }

    public void generateDataChange(){
        DataMap dataMap;

        for (String symbol : symbolCheck) {
            dataMap = SceneController.ifInFunctionErrorCreateInformationMessage(marketData.getPercentageChange(symbol, "1d"));
            tableView.getItems().add(new Cryptocurrency(dataMap.getString("SYMBOL").replace("USDT", ""), dataMap.getDouble("CHANGE")));
        }

    }

    public void getCurrencyPrice(){
        DataMap dataMap;

        for (String symbol : symbolCheck) {
            dataMap = SceneController.ifInFunctionErrorCreateInformationMessage(marketData.getSymbolPriceTicker(symbol));
            tableViewCurrentPrice.getItems().add(new Cryptocurrency(dataMap.getString("SYMBOL").replace("USDT", ""), dataMap.getDouble("CURRENT_PRICE")));
        }

    }


    private void setCbPeriod(){
        cbPeriod.setValue(Period.MONTH);
        cbPeriod.getItems().addAll(Period.YEAR,
                Period.MONTH,
                Period.WEEK,
                Period.DAY);
    }

    private void setCbInterval(){
        cbInterval.setValue(CandlestickInterval.DAILY);
        cbInterval.getItems().addAll(CandlestickInterval.somethingList);

        LocalDate date = LocalDate.now().minusMonths(1);
        ZoneId zoneId = ZoneId.systemDefault();
        long newEpoch = date.atStartOfDay(zoneId).toEpochSecond();
        fromDateEpoch = newEpoch + "000";

    }

    private void setChart(){
        chartDay.setAnimated(true);
        chartDay.setCreateSymbols(false);
        chartDay.setLegendVisible(false);
        chartDay.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        chartDay.getXAxis().setSide(Side.LEFT);
    }

    /**
     * @Attributes
     * crypto(String) - Symbol kryptowaluty np. BTCUSDT, gdzie BTC- bitcoin USDT = USD
     *
     * @Description
     * Metoda tworzy dane dla kompomentu chartDay
     * Jest to wykres pokazujący cene dla 31 dniach codzienne zmiany
     *
     * @return -||-
     */
    public void setChartDay() {
        ArrayList candlestick = marketData.getCandlestickData(crypto, cbInterval.getValue(), fromDateEpoch, String.valueOf(Instant.now().toEpochMilli()));
        getData(candlestick);
    }

    public void getData(ArrayList candlestick) {
        series = new XYChart.Series<>();
        if(chartDay.getData() != null)
            chartDay.getData().clear();

        DateFormat formatter = new SimpleDateFormat("dd/MM");

        for (var o : candlestick) {
            Candlestick temp = (Candlestick) o;
            Date date = new Date(Long.parseLong(temp.getOpenTime()));

            series.getData().add(new XYChart.Data(formatter.format(date), Double.valueOf(temp.getOpen())));
        }
        chartDay.getData().addAll(series);
    }

    public void cbPeriodOnAction(){
        LocalDate date;
        ZoneId zoneId = ZoneId.systemDefault();
        long newEpoch;

        switch (cbPeriod.getValue()){
            case Period.YEAR:
                date = LocalDate.now().minusYears(1);
                newEpoch = date.atStartOfDay(zoneId).toEpochSecond();
                break;
            case Period.MONTH:
                date = LocalDate.now().minusMonths(1);
                newEpoch = date.atStartOfDay(zoneId).toEpochSecond();
                break;
            case Period.WEEK:
                date = LocalDate.now().minusWeeks(1);
                newEpoch = date.atStartOfDay(zoneId).toEpochSecond();
                break;
            case Period.DAY:
                date = LocalDate.now().minusDays(1);
                newEpoch = date.atStartOfDay(zoneId).toEpochSecond();
                break;
            default:
                date = LocalDate.now().minusWeeks(10);
                newEpoch = date.atStartOfDay(zoneId).toEpochSecond();
                break;
        }
        String fromDay = String.valueOf(newEpoch);
        fromDay += "000";

        fromDateEpoch = fromDay;

        chartDay.getData().clear();
        setChartDay();
    }

    public void txtFieldCryptoOnAction(){
        String result;

        result = txtFieldCrypto.getText();

        if(result == null || result.isBlank())
            return;


        crypto = result;
        setChartDay();
    }

}
