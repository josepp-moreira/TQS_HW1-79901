/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tpc;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import org.javamoney.moneta.Money;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author joseppmoreira
 */
public class Converter extends HttpServlet {

    public static final String ACCESS_KEY = "5e03aaeb679b999b82b1caaf";
    static CloseableHttpClient httpClient = HttpClients.createDefault();
    public Map<String, Double> rates = new TreeMap<>();


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String num = request.getParameter("input_num");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String converted_num = NumConverter(num, from, to);
        out.println("<html lang=\"en\">");
        out.println("<style>\n"
                + "            #form{\n"
                + "                margin: 2%\n"
                + "            }\n"
                + "            \n"
                + "            #inputNum {\n"
                + "                margin-bottom: 2%\n"
                + "            }\n"
                + "            \n"
                + "            #submit {\n"
                + "                margin-top: 2%\n"
                + "            }\n"
                + "            #result {\n"
                + "                margin-left: 2%\n"
                + "            }\n"
                + "        </style>");
        out.println("<body>");
        out.println("<form method=\"GET\" id=\"form\">\n"
                + "            <label for=\"inputNum\">Insert number (Integer):</label>\n"
                + "            <input type=\"number\" name=\"input_num\" id=\"inputNum\" required lang=\"en\"><br>\n"
                + "            <label for=\"inputNum\">Convert from:</label>\n"
                + "            <select name=\"from\">\n"
                + "                <option value='AED' title='United Arab Emirates Dirham'>AED</option>\n"
                + "                <option value='ALL' title='Albanian Lek'>ALL</option>\n"
                + "                <option value='AMD' title='Armenian Dram'>AMD</option>\n"
                + "                <option value='ANG' title='Netherlands Antillean Guilder'>ANG</option>\n"
                + "                <option value='AOA' title='Angolan Kwanza'>AOA</option>\n"
                + "                <option value='ARS' title='Argentine Peso'>ARS</option>\n"
                + "                <option value='AUD' title='Australian Dollar'>AUD</option>\n"
                + "                <option value='AZN' title='Azerbaijani Manat'>AZN</option>\n"
                + "                <option value='BBD' title='Barbadian Dollar'>BBD</option>\n"
                + "                <option value='BDT' title='Bangladeshi Taka'>BDT</option>\n"
                + "                <option value='BGN' title='Bulgarian Lev'>BGN</option>\n"
                + "                <option value='BHD' title='Bahraini Dinar'>BHD</option>\n"
                + "                <option value='BRL' title='Brazilian Real'>BRL</option>\n"
                + "                <option value='BSD' title='Bahamian Dollar'>BSD</option>\n"
                + "                <option value='BWP' title='Botswanan Pula'>BWP</option>\n"
                + "                <option value='BYN' title='Belarusian Ruble'>BYN</option>\n"
                + "                <option value='CAD' title='Canadian Dollar'>CAD</option>\n"
                + "                <option value='CHF' title='Swiss Franc'>CHF</option>\n"
                + "                <option value='CLP' title='Chilean Peso'>CLP</option>\n"
                + "                <option value='CNY' title='Chinese Yuan'>CNY</option>\n"
                + "                <option value='COP' title='Colombian Peso'>COP</option>\n"
                + "                <option value='CZK' title='Czech Republic Koruna'>CZK</option>\n"
                + "                <option value='DKK' title='Danish Krone'>DKK</option>\n"
                + "                <option value='DOP' title='Dominican Peso'>DOP</option>\n"
                + "                <option value='EGP' title='Egyptian Pound'>EGP</option>\n"
                + "                <option value='ETB' title='Ethiopian Birr'>ETB</option>\n"
                + "                <option value='EUR' title='Euro'>EUR</option>\n"
                + "                <option value='FJD' title='Fijian Dollar'>FJD</option>\n"
                + "                <option value='GBP' title='British Pound Sterling'>GBP</option>\n"
                + "                <option value='GEL' title='Georgian Lari'>GEL</option>\n"
                + "                <option value='GHS' title='Ghanaian Cedi'>GHS</option>\n"
                + "                <option value='GTQ' title='Guatemalan Quetzal'>GTQ</option>\n"
                + "                <option value='HKD' title='Hong Kong Dollar'>HKD</option>\n"
                + "                <option value='HNL' title='Honduran Lempira'>HNL</option>\n"
                + "                <option value='HRK' title='Croatian Kuna'>HRK</option>\n"
                + "                <option value='HUF' title='Hungarian Forint'>HUF</option>\n"
                + "                <option value='IDR' title='Indonesian Rupiah'>IDR</option>\n"
                + "                <option value='ILS' title='Israeli New Sheqel'>ILS</option>\n"
                + "                <option value='INR' title='Indian Rupee'>INR</option>\n"
                + "                <option value='IQD' title='Iraqi Dinar'>IQD</option>\n"
                + "                <option value='IRR' title='Iranian Rial'>IRR</option>\n"
                + "                <option value='ISK' title='Icelandic Króna'>ISK</option>\n"
                + "                <option value='JMD' title='Jamaican Dollar'>JMD</option>\n"
                + "                <option value='JOD' title='Jordanian Dinar'>JOD</option>\n"
                + "                <option value='JPY' title='Japanese Yen'>JPY</option>\n"
                + "                <option value='KES' title='Kenyan Shilling'>KES</option>\n"
                + "                <option value='KHR' title='Cambodian Riel'>KHR</option>\n"
                + "                <option value='KRW' title='South Korean Won'>KRW</option>\n"
                + "                <option value='KWD' title='Kuwaiti Dinar'>KWD</option>\n"
                + "                <option value='KZT' title='Kazakhstani Tenge'>KZT</option>\n"
                + "                <option value='LAK' title='Laotian Kip'>LAK</option>\n"
                + "                <option value='LBP' title='Lebanese Pound'>LBP</option>\n"
                + "                <option value='LKR' title='Sri Lankan Rupee'>LKR</option>\n"
                + "                <option value='MAD' title='Moroccan Dirham'>MAD</option>\n"
                + "                <option value='MDL' title='Moldovan Leu'>MDL</option>\n"
                + "                <option value='MKD' title='Macedonian Denar'>MKD</option>\n"
                + "                <option value='MMK' title='Myanma Kyat'>MMK</option>\n"
                + "                <option value='MNT' title='Mongolian Tugrik'>MNT</option>\n"
                + "                <option value='MUR' title='Mauritian Rupee'>MUR</option>\n"
                + "                <option value='MXN' title='Mexican Peso'>MXN</option>\n"
                + "                <option value='MYR' title='Malaysian Ringgit'>MYR</option>\n"
                + "                <option value='NAD' title='Namibian Dollar'>NAD</option>\n"
                + "                <option value='NGN' title='Nigerian Naira'>NGN</option>\n"
                + "                <option value='NOK' title='Norwegian Krone'>NOK</option>\n"
                + "                <option value='NZD' title='New Zealand Dollar'>NZD</option>\n"
                + "                <option value='OMR' title='Omani Rial'>OMR</option>\n"
                + "                <option value='PAB' title='Panamanian Balboa'>PAB</option>\n"
                + "                <option value='PEN' title='Peruvian Nuevo Sol'>PEN</option>\n"
                + "                <option value='PGK' title='Papua New Guinean Kina'>PGK</option>\n"
                + "                <option value='PHP' title='Philippine Peso'>PHP</option>\n"
                + "                <option value='PKR' title='Pakistani Rupee'>PKR</option>\n"
                + "                <option value='PLN' title='Polish Zloty'>PLN</option>\n"
                + "                <option value='PYG' title='Paraguayan Guarani'>PYG</option>\n"
                + "                <option value='QAR' title='Qatari Rial'>QAR</option>\n"
                + "                <option value='RON' title='Romanian Leu'>RON</option>\n"
                + "                <option value='RSD' title='Serbian Dinar'>RSD</option>\n"
                + "                <option value='RUB' title='Russian Ruble'>RUB</option>\n"
                + "                <option value='SAR' title='Saudi Riyal'>SAR</option>\n"
                + "                <option value='SCR' title='Seychellois Rupee'>SCR</option>\n"
                + "                <option value='SEK' title='Swedish Krona'>SEK</option>\n"
                + "                <option value='SGD' title='Singapore Dollar'>SGD</option>\n"
                + "                <option value='THB' title='Thai Baht'>THB</option>\n"
                + "                <option value='TJS' title='Tajikistani Somoni'>TJS</option>\n"
                + "                <option value='TND' title='Tunisian Dinar'>TND</option>\n"
                + "                <option value='TRY' title='Turkish Lira'>TRY</option>\n"
                + "                <option value='TTD' title='Trinidad and Tobago Dollar'>TTD</option>\n"
                + "                <option value='TWD' title='New Taiwan Dollar'>TWD</option>\n"
                + "                <option value='TZS' title='Tanzanian Shilling'>TZS</option>\n"
                + "                <option value='UAH' title='Ukrainian Hryvnia'>UAH</option>\n"
                + "                <option value='USD' title='United States Dollar'>USD</option>\n"
                + "                <option value='UYU' title='Uruguayan Peso'>UYU</option>\n"
                + "                <option value='UZS' title='Uzbekistan Som'>UZS</option>\n"
                + "                <option value='VEF' title='Venezuelan Bolívar'>VEF</option>\n"
                + "                <option value='VND' title='Vietnamese Dong'>VND</option>\n"
                + "                <option value='XAF' title='CFA Franc BEAC'>XAF</option>\n"
                + "                <option value='XCD' title='East Caribbean Dollar'>XCD</option>\n"
                + "                <option value='XOF' title='CFA Franc BCEAO'>XOF</option>\n"
                + "                <option value='XPF' title='CFP Franc'>XPF</option>\n"
                + "                <option value='ZAR' title='South African Rand'>ZAR</option>\n"
                + "                <option value='ZMW' title='Zambian Kwacha'>ZMW</option>\n"
                + "            </select>\n"
                + "            <label for=\"inputNum\">to:</label>\n"
                + "            <select name=\"to\">\n"
                + "                <option value='AED' title='United Arab Emirates Dirham'>AED</option>\n"
                + "                <option value='ALL' title='Albanian Lek'>ALL</option>\n"
                + "                <option value='AMD' title='Armenian Dram'>AMD</option>\n"
                + "                <option value='ANG' title='Netherlands Antillean Guilder'>ANG</option>\n"
                + "                <option value='AOA' title='Angolan Kwanza'>AOA</option>\n"
                + "                <option value='ARS' title='Argentine Peso'>ARS</option>\n"
                + "                <option value='AUD' title='Australian Dollar'>AUD</option>\n"
                + "                <option value='AZN' title='Azerbaijani Manat'>AZN</option>\n"
                + "                <option value='BBD' title='Barbadian Dollar'>BBD</option>\n"
                + "                <option value='BDT' title='Bangladeshi Taka'>BDT</option>\n"
                + "                <option value='BGN' title='Bulgarian Lev'>BGN</option>\n"
                + "                <option value='BHD' title='Bahraini Dinar'>BHD</option>\n"
                + "                <option value='BRL' title='Brazilian Real'>BRL</option>\n"
                + "                <option value='BSD' title='Bahamian Dollar'>BSD</option>\n"
                + "                <option value='BWP' title='Botswanan Pula'>BWP</option>\n"
                + "                <option value='BYN' title='Belarusian Ruble'>BYN</option>\n"
                + "                <option value='CAD' title='Canadian Dollar'>CAD</option>\n"
                + "                <option value='CHF' title='Swiss Franc'>CHF</option>\n"
                + "                <option value='CLP' title='Chilean Peso'>CLP</option>\n"
                + "                <option value='CNY' title='Chinese Yuan'>CNY</option>\n"
                + "                <option value='COP' title='Colombian Peso'>COP</option>\n"
                + "                <option value='CZK' title='Czech Republic Koruna'>CZK</option>\n"
                + "                <option value='DKK' title='Danish Krone'>DKK</option>\n"
                + "                <option value='DOP' title='Dominican Peso'>DOP</option>\n"
                + "                <option value='EGP' title='Egyptian Pound'>EGP</option>\n"
                + "                <option value='ETB' title='Ethiopian Birr'>ETB</option>\n"
                + "                <option value='EUR' title='Euro'>EUR</option>\n"
                + "                <option value='FJD' title='Fijian Dollar'>FJD</option>\n"
                + "                <option value='GBP' title='British Pound Sterling'>GBP</option>\n"
                + "                <option value='GEL' title='Georgian Lari'>GEL</option>\n"
                + "                <option value='GHS' title='Ghanaian Cedi'>GHS</option>\n"
                + "                <option value='GTQ' title='Guatemalan Quetzal'>GTQ</option>\n"
                + "                <option value='HKD' title='Hong Kong Dollar'>HKD</option>\n"
                + "                <option value='HNL' title='Honduran Lempira'>HNL</option>\n"
                + "                <option value='HRK' title='Croatian Kuna'>HRK</option>\n"
                + "                <option value='HUF' title='Hungarian Forint'>HUF</option>\n"
                + "                <option value='IDR' title='Indonesian Rupiah'>IDR</option>\n"
                + "                <option value='ILS' title='Israeli New Sheqel'>ILS</option>\n"
                + "                <option value='INR' title='Indian Rupee'>INR</option>\n"
                + "                <option value='IQD' title='Iraqi Dinar'>IQD</option>\n"
                + "                <option value='IRR' title='Iranian Rial'>IRR</option>\n"
                + "                <option value='ISK' title='Icelandic Króna'>ISK</option>\n"
                + "                <option value='JMD' title='Jamaican Dollar'>JMD</option>\n"
                + "                <option value='JOD' title='Jordanian Dinar'>JOD</option>\n"
                + "                <option value='JPY' title='Japanese Yen'>JPY</option>\n"
                + "                <option value='KES' title='Kenyan Shilling'>KES</option>\n"
                + "                <option value='KHR' title='Cambodian Riel'>KHR</option>\n"
                + "                <option value='KRW' title='South Korean Won'>KRW</option>\n"
                + "                <option value='KWD' title='Kuwaiti Dinar'>KWD</option>\n"
                + "                <option value='KZT' title='Kazakhstani Tenge'>KZT</option>\n"
                + "                <option value='LAK' title='Laotian Kip'>LAK</option>\n"
                + "                <option value='LBP' title='Lebanese Pound'>LBP</option>\n"
                + "                <option value='LKR' title='Sri Lankan Rupee'>LKR</option>\n"
                + "                <option value='MAD' title='Moroccan Dirham'>MAD</option>\n"
                + "                <option value='MDL' title='Moldovan Leu'>MDL</option>\n"
                + "                <option value='MKD' title='Macedonian Denar'>MKD</option>\n"
                + "                <option value='MMK' title='Myanma Kyat'>MMK</option>\n"
                + "                <option value='MNT' title='Mongolian Tugrik'>MNT</option>\n"
                + "                <option value='MUR' title='Mauritian Rupee'>MUR</option>\n"
                + "                <option value='MXN' title='Mexican Peso'>MXN</option>\n"
                + "                <option value='MYR' title='Malaysian Ringgit'>MYR</option>\n"
                + "                <option value='NAD' title='Namibian Dollar'>NAD</option>\n"
                + "                <option value='NGN' title='Nigerian Naira'>NGN</option>\n"
                + "                <option value='NOK' title='Norwegian Krone'>NOK</option>\n"
                + "                <option value='NZD' title='New Zealand Dollar'>NZD</option>\n"
                + "                <option value='OMR' title='Omani Rial'>OMR</option>\n"
                + "                <option value='PAB' title='Panamanian Balboa'>PAB</option>\n"
                + "                <option value='PEN' title='Peruvian Nuevo Sol'>PEN</option>\n"
                + "                <option value='PGK' title='Papua New Guinean Kina'>PGK</option>\n"
                + "                <option value='PHP' title='Philippine Peso'>PHP</option>\n"
                + "                <option value='PKR' title='Pakistani Rupee'>PKR</option>\n"
                + "                <option value='PLN' title='Polish Zloty'>PLN</option>\n"
                + "                <option value='PYG' title='Paraguayan Guarani'>PYG</option>\n"
                + "                <option value='QAR' title='Qatari Rial'>QAR</option>\n"
                + "                <option value='RON' title='Romanian Leu'>RON</option>\n"
                + "                <option value='RSD' title='Serbian Dinar'>RSD</option>\n"
                + "                <option value='RUB' title='Russian Ruble'>RUB</option>\n"
                + "                <option value='SAR' title='Saudi Riyal'>SAR</option>\n"
                + "                <option value='SCR' title='Seychellois Rupee'>SCR</option>\n"
                + "                <option value='SEK' title='Swedish Krona'>SEK</option>\n"
                + "                <option value='SGD' title='Singapore Dollar'>SGD</option>\n"
                + "                <option value='THB' title='Thai Baht'>THB</option>\n"
                + "                <option value='TJS' title='Tajikistani Somoni'>TJS</option>\n"
                + "                <option value='TND' title='Tunisian Dinar'>TND</option>\n"
                + "                <option value='TRY' title='Turkish Lira'>TRY</option>\n"
                + "                <option value='TTD' title='Trinidad and Tobago Dollar'>TTD</option>\n"
                + "                <option value='TWD' title='New Taiwan Dollar'>TWD</option>\n"
                + "                <option value='TZS' title='Tanzanian Shilling'>TZS</option>\n"
                + "                <option value='UAH' title='Ukrainian Hryvnia'>UAH</option>\n"
                + "                <option value='USD' title='United States Dollar'>USD</option>\n"
                + "                <option value='UYU' title='Uruguayan Peso'>UYU</option>\n"
                + "                <option value='UZS' title='Uzbekistan Som'>UZS</option>\n"
                + "                <option value='VEF' title='Venezuelan Bolívar'>VEF</option>\n"
                + "                <option value='VND' title='Vietnamese Dong'>VND</option>\n"
                + "                <option value='XAF' title='CFA Franc BEAC'>XAF</option>\n"
                + "                <option value='XCD' title='East Caribbean Dollar'>XCD</option>\n"
                + "                <option value='XOF' title='CFA Franc BCEAO'>XOF</option>\n"
                + "                <option value='XPF' title='CFP Franc'>XPF</option>\n"
                + "                <option value='ZAR' title='South African Rand'>ZAR</option>\n"
                + "                <option value='ZMW' title='Zambian Kwacha'>ZMW</option>\n"
                + "            </select><br/>"
                + "            <input id=\"submit\" type=\"submit\" value=\"Submit\">\n"
                + "        </form> ");
        out.println("<label id=\"result\" for=\"output\">Result:</label>\n" + "<input type=\"text\" readonly value=\"" + converted_num + "\" name=\"output\" id=\"output\"><br>");
        out.println("</body></html>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String NumConverter(String num, String from, String to) {
        /*
        CurrencyConversion convertTo = MonetaryConversions.getConversion(to);
        if (num.contains(".")) {
            num = "0";
        }
        MonetaryAmount amount = Money.of(Integer.parseInt(num), from);
        MonetaryAmount converted_num = amount.with(convertTo);
        return converted_num.getNumber().toString() + " " + converted_num.getCurrency();
         */

        if (num.contains(".")) {
            num = "0";
        }
        double currency = getCurrencyRate(from, to);      //Caso conseguisse a permissão na api
        double amount = Long.parseLong(num) * currency;
        return amount + " " + to;

    }

    /*
    A função abaixo mostra como tentaria obter o valor para multiplicar ao montante para dar a conversão correta.
    Caso conseguisse permissão na api com a minha chave, usaria esse metodo
     */
    private double getCurrencyRate(String from, String to) {
        if(getServletContext().getAttribute(from + to) != null){
            return (double) getServletContext().getAttribute(from + to);
        }
        
        HttpGet get = new HttpGet("https://v3.exchangerate-api.com/bulk/" + ACCESS_KEY + "/" + from);
        double currencyRate = -1;

        try {
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
            
            currencyRate = exchangeRates.getJSONObject("rates").getDouble(to);
            getServletContext().setAttribute(from + to, currencyRate);
            response.close();
            
            return currencyRate;
        } catch (ClientProtocolException e) {
            return currencyRate;
        } catch (IOException | ParseException e) {
            return currencyRate;
        }
    }

}
