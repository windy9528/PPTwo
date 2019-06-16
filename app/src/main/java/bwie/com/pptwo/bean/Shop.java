package bwie.com.pptwo.bean;

import java.util.List;

/**
 * Author: 杨高峰(windy)
 * Date: 2019/5/16 18:31
 * Description:
 */
public class Shop {

    private String categoryName;
    private List<Goods> shoppingCartList;
    public boolean shopCheck;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Goods> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<Goods> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }
}
