package com.xjy.coindata.controller;

import com.xjy.coindata.entity.Coin;
import com.xjy.coindata.entity.Global;
import com.xjy.coindata.entity.Quotes;
import com.xjy.coindata.repository.CoinRepository;
import com.xjy.coindata.repository.GlobalRepository;
import com.xjy.coindata.repository.QuotesRepository;
import com.xjy.coindata.third.coinmarketcap.CoinBase;
import com.xjy.coindata.third.coinmarketcap.CoinGlobal;
import com.xjy.coindata.third.coinmarketcap.CoinInfo;
import com.xjy.coindata.third.coinmarketcap.QuoteInfo;
import com.xjy.coindata.util.HttpClientUtil;
import com.xjy.coindata.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    public static String[] CONVERT = {"AUD", "BRL","CAD", "CHF","CLP","CNY","CZK","DKK","EUR","GBP","HKD","HUF","IDR","ILS","INR","JPY","KRW","MXN","MYR","NOK",
            "NZD","PHP","PKR","PLN","RUB","SEK","SGD","THB","TRY","TWD","ZAR","BTC","ETH","XRP","LTC","BCH"};

    @Autowired
    private CoinRepository coinRepository;
    @Autowired
    private GlobalRepository globalRepository;
    @Autowired
    private QuotesRepository quotesRepository;


    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    //获取加密货币列表
    @GetMapping("/saveCoinList")
    public void save() {

        HttpClientUtil http=new HttpClientUtil();
        //返回前台的map
        Map<Object, Object> resMap =http.get("https://api.coinmarketcap.com/v2/listings/");

        //返回结果数组
        List<CoinBase> coinList = (List<CoinBase>) resMap.get("data");

        //加密货币总数
        int count = 0;
        int pageSize = 100;

        if(coinList != null && coinList.size() > 0){
            count = coinList.size();
        }

        //查询次数
        int size = count % pageSize > 0 ? count/pageSize + 1 : count/pageSize;

        for (int i=0;i<size;i++){

            int start = i*pageSize + 1;

            //每种货币的各种计价
            for(int m=0;m<CONVERT.length;m++) {
                String convert = CONVERT[m];
                //返回前台的map
                resMap = http.get("https://api.coinmarketcap.com/v2/ticker/?start=" + start + "&limit=" + pageSize + "&sort=id&structure=array&convert="+convert);

                //返回结果数组
                List<CoinInfo> coinInfoList = (List<CoinInfo>) resMap.get("data");

                for (int j = 0; j < coinInfoList.size(); j++) {

                    //加密货币信息
                    CoinInfo coinInfo = JsonUtil.json2Obj(JsonUtil.obj2Json(coinInfoList.get(i)), CoinInfo.class);

                    Coin coin = new Coin();
                    coin.setApiId(coinInfo.getId());
                    coin.setName(coinInfo.getName());
                    coin.setSymbol(coinInfo.getSymbol());
                    coin.setWebsiteSlug(coinInfo.getWebsite_slug());
                    coin.setRank(coinInfo.getRank());
                    coin.setCirculatingSupply(BigDecimal.valueOf(coinInfo.getCirculating_supply()));
                    coin.setTotalSupply(BigDecimal.valueOf(coinInfo.getTotal_supply()));
                    coin.setMaxSupply(BigDecimal.valueOf(coinInfo.getMax_supply()));
                    coin.setCreateTime(new Date());
                    coinRepository.save(coin);


                    /*--每种货币的不同计价方式--*/
                    Quotes quotes = new Quotes();
                    quotes.setCoinId(coinInfo.getId());
                    quotes.setSymbol(coinInfo.getSymbol());
                    quotes.setPrice(BigDecimal.valueOf(coinInfo.getQuotes().get(convert).getPrice()));
                    quotes.setVolumeOneDay(BigDecimal.valueOf(coinInfo.getQuotes().get(convert).getVolume_24h()));
                    quotes.setMarketCap(BigDecimal.valueOf(coinInfo.getQuotes().get(convert).getMarket_cap()));
                    quotes.setPercentChangeOneHour(BigDecimal.valueOf(coinInfo.getQuotes().get(convert).getPercent_change_1h()));
                    quotes.setPercentChangeOneDay(BigDecimal.valueOf(coinInfo.getQuotes().get(convert).getPercent_change_24h()));
                    quotes.setPercentChangeOneWeek(BigDecimal.valueOf(coinInfo.getQuotes().get(convert).getPercent_change_7d()));
                    quotes.setCreateTime(new Date());
                    quotesRepository.save(quotes);


                    /*--每种货币的最高全球数据--*/
                    resMap =http.get("https://api.coinmarketcap.com/v2/global/?convert="+convert);

                    //返回加密货币信息
                    CoinGlobal coinGlobal = JsonUtil.json2Obj(JsonUtil.obj2Json(resMap.get("data")),CoinGlobal.class);
                    Global global = new Global();
                    global.setActiveCryptocurrencies(coinGlobal.getActive_cryptocurrencies());
                    global.setActiveMarkets(coinGlobal.getActive_markets());
                    global.setBitcoinPercentageOfMarketCap(BigDecimal.valueOf(coinGlobal.getBitcoin_percentage_of_market_cap()));
                    globalRepository.save(global);

                    //更新此币种的全球最高数据
                    Coin coinGlo = new Coin();
                    coinGlo.setTotalMarketCap(BigDecimal.valueOf(coinGlobal.getQuotes().get(convert).getTotal_market_cap()));
                    coinGlo.setTotalVolumeOneDay(BigDecimal.valueOf(coinGlobal.getQuotes().get(convert).getTotal_volume_24h()));
                    coinGlo.setGlobalId(global.getId());
                    coinGlo.setUpdateTime(new Date());
                    coinRepository.saveAndFlush(coinGlo);
                }
            }
        }

    }



    @GetMapping(value = "/coinList")
    public void coinList() {
        HttpClientUtil http=new HttpClientUtil();
        //返回前台的map
        Map<Object, Object> resMap =http.get("https://api.coinmarketcap.com/v2/listings/");

        //返回结果数组
        List<CoinBase> coinList = (List<CoinBase>) resMap.get("data");

        for (int i=0;i<coinList.size();i++){

            //加密货币信息
            CoinBase coinBase = JsonUtil.json2Obj(JsonUtil.obj2Json(coinList.get(i)),CoinBase.class);

            logger.info(JsonUtil.obj2Json(coinBase));
        }
    }

    @GetMapping(value = "/coinDetail")
    public void coinDetail() {
        HttpClientUtil http=new HttpClientUtil();
        //返回前台的map
        Map<Object, Object> resMap =http.get("https://api.coinmarketcap.com/v2/ticker/?start=1&limit=100&sort=id&structure=array");

        //返回结果数组
        List<CoinInfo> coinInfoList = (List<CoinInfo>) resMap.get("data");

        for (int i=0;i<coinInfoList.size();i++){

            //加密货币信息
            CoinInfo coinInfo = JsonUtil.json2Obj(JsonUtil.obj2Json(coinInfoList.get(i)),CoinInfo.class);

            logger.info(JsonUtil.obj2Json(coinInfo));
        }
    }

    @GetMapping(value = "/coinDetailType")
    public void coinDetailType() {
        HttpClientUtil http=new HttpClientUtil();
        //返回前台的map
        Map<Object, Object> resMap =http.get("https://api.coinmarketcap.com/v2/ticker/1/?convert=EUR");

        //返回加密货币信息
        CoinInfo coinInfo = JsonUtil.json2Obj(JsonUtil.obj2Json(resMap.get("data")),CoinInfo.class);

        logger.info(JsonUtil.obj2Json(coinInfo));
    }

    @GetMapping(value = "/coinDetailGlobal")
    public void coinDetailGlobal() {
        HttpClientUtil http=new HttpClientUtil();
        //返回前台的map
        Map<Object, Object> resMap =http.get("https://api.coinmarketcap.com/v2/global/");

        //返回加密货币信息
        CoinGlobal coinGlobal = JsonUtil.json2Obj(JsonUtil.obj2Json(resMap.get("data")),CoinGlobal.class);

        logger.info(JsonUtil.obj2Json(coinGlobal));
    }

}
