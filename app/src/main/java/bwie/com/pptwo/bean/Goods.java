package bwie.com.pptwo.bean;

/**
 * Author: 杨高峰(windy)
 * Date: 2019/5/16 18:31
 * Description:
 */
public class Goods {

    private int commodityId;
    private String commodityName;
    private int count;
    private String pic;
    private double price;
    public boolean goodCheck;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
