package com.example.myskety.my_application.data;

/**
 * Created by Myskety on 2015/11/9.
 */
public class ProductItem {
    private int productid;
    private String producturl;
    private String productname;
    private String productintro;
    private int hightestprice;
    private int lowestprice;
    private int cuttime;
    private int cutprice;
    private int status;
    private String sellername;
    private String sellernickname;
    private String selldate;
    private String buyername;
    private int lastprice;
    private String classify;

    public ProductItem(int productid, String producturl,
                       String productname, String productintro, int hightestprice,
                       int lowestprice, int cuttime, int cutprice, int status,
                       String sellername, String sellernickname, String selldate,
                       String buyername, int lastprice, String classify) {
        this.productid = productid;
        this.producturl = producturl;
        this.productname = productname;
        this.productintro = productintro;
        this.hightestprice = hightestprice;
        this.lowestprice = lowestprice;
        this.cuttime = cuttime;
        this.cutprice = cutprice;
        this.status = status;
        this.sellername = sellername;
        this.sellernickname = sellernickname;
        this.selldate = selldate;
        this.buyername = buyername;
        this.lastprice = lastprice;
        this.classify = classify;
    }

    public int getProductid() {
        return productid;
    }

    public String getProducturl() {
        return producturl;
    }

    public String getProductname() {
        return productname;
    }

    public String getProductintro() {
        return productintro;
    }

    public int getHightestprice() {
        return hightestprice;
    }

    public int getLowestprice() {
        return lowestprice;
    }

    public int getCuttime() {
        return cuttime;
    }

    public int getCutprice() {
        return cutprice;
    }

    public int getStatus() {
        return status;
    }

    public String getSellername() {
        return sellername;
    }

    public String getSellernickname() {
        return sellernickname;
    }

    public String getSelldate() {
        return selldate;
    }

    public String getBuyername() {
        return buyername;
    }

    public int getLastprice() {
        return lastprice;
    }

    public String getClassify() {
        return classify;
    }
}
