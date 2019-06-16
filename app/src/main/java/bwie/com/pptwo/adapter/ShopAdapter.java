package bwie.com.pptwo.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

import bwie.com.pptwo.MainActivity;
import bwie.com.pptwo.R;
import bwie.com.pptwo.bean.Goods;
import bwie.com.pptwo.bean.Shop;
import retrofit2.http.POST;

/**
 * date:2019/6/16
 * name:windy
 * function:
 */
public class ShopAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Shop> shopList;
    private ShowTotalPrice showTotalPrice;

    public void setShowTotalPrice(ShowTotalPrice showTotalPrice) {
        this.showTotalPrice = showTotalPrice;
    }

    public List<Shop> getList() {
        return shopList;
    }

    public interface ShowTotalPrice {
        void showPrice(double totalPrice, int num, boolean status);
    }

    public ShopAdapter(Context context) {
        this.context = context;
        shopList = new ArrayList<>();
    }

    public void addList(List<Shop> result) {
        if (result != null)
            shopList.addAll(result);
    }

    @Override
    public int getGroupCount() {
        return shopList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return shopList.get(groupPosition).getShoppingCartList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return shopList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return shopList.get(groupPosition).getShoppingCartList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ShopHolder shopHolder = null;
        if (convertView == null) {
            shopHolder = new ShopHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_shops, parent, false);
            shopHolder.shopName = convertView.findViewById(R.id.shop_name);
            shopHolder.shopCheckBox = convertView.findViewById(R.id.shop_checkBox);
            convertView.setTag(shopHolder);
        } else {
            shopHolder = (ShopHolder) convertView.getTag();
        }

        Shop shop = shopList.get(groupPosition);

        //设置
        shopHolder.shopName.setText(shop.getCategoryName());
        shopHolder.shopCheckBox.setChecked(shop.shopCheck);
        //设置标记
        shopHolder.shopCheckBox.setTag(shop);

        //当父级复选框被选中时
        shopHolder.shopCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前标记
                Shop s = (Shop) v.getTag();
                s.shopCheck = ((CheckBox) v).isChecked();
                //与子级复选框联动
                if (s.shopCheck) {
                    for (int i = 0; i < shopList.get(groupPosition).getShoppingCartList().size(); i++) {
                        s.getShoppingCartList().get(i).goodCheck = true;
                    }
                } else {
                    for (int i = 0; i < shopList.get(groupPosition).getShoppingCartList().size(); i++) {
                        s.getShoppingCartList().get(i).goodCheck = false;
                    }
                }
                calculate();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    /**
     * 计算价格操作
     */
    public void calculate() {
        double total = 0; //总价
        int num = 0;  //数量
        boolean status = true; //状态
        for (int i = 0; i < shopList.size(); i++) {
            Shop shop = shopList.get(i);
            shop.shopCheck = true;
            for (int j = 0; j < shop.getShoppingCartList().size(); j++) {
                Goods goods = shop.getShoppingCartList().get(j);
                shop.shopCheck = shop.shopCheck && goods.goodCheck; //得出父级复选框的状态
                if (goods.goodCheck) {
                    total = total + goods.getCount() * goods.getPrice();
                    num = num + goods.getCount();
                }
            }
            if (!shop.shopCheck) {
                status = false;
            }
            if (showTotalPrice != null) {
                showTotalPrice.showPrice(total, num, status);
            }
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        GoodHolder goodHolder = null;
        if (convertView == null) {
            goodHolder = new GoodHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_goods, parent, false);

            goodHolder.goodName = convertView.findViewById(R.id.good_name);
            goodHolder.ivIcon = convertView.findViewById(R.id.iv_icon);
            goodHolder.goodCheckBox = convertView.findViewById(R.id.good_checkBox);
            goodHolder.goodPrice = convertView.findViewById(R.id.good_price);
            goodHolder.goodName = convertView.findViewById(R.id.good_name);
            goodHolder.btnMinus = convertView.findViewById(R.id.btn_minus);
            goodHolder.tvNum = convertView.findViewById(R.id.tv_num);
            goodHolder.btnAdd = convertView.findViewById(R.id.btn_add);
            convertView.setTag(goodHolder);
        } else {
            goodHolder = (GoodHolder) convertView.getTag();
        }

        Goods goods = shopList
                .get(groupPosition)
                .getShoppingCartList()
                .get(childPosition);

        //设置
        Glide
                .with(context)
                .load(goods.getPic())
                .into(goodHolder.ivIcon);
        goodHolder.goodName.setText(goods.getCommodityName());
        goodHolder.goodPrice.setText("￥" + goods.getPrice());
        goodHolder.tvNum.setText(String.valueOf(goods.getCount()));
        goodHolder.goodCheckBox.setChecked(goods.goodCheck);
        //设置标记
        goodHolder.goodCheckBox.setTag(goods);
        goodHolder.btnAdd.setTag(goods);
        goodHolder.btnMinus.setTag(goods);

        //当子级复选框被选中时
        goodHolder.goodCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前标记
                Goods g = (Goods) v.getTag();
                g.goodCheck = ((CheckBox) v).isChecked();
                calculate();  //计算总价
                notifyDataSetChanged();
            }
        });

        //加
        goodHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods g = (Goods) v.getTag();
                int count = g.getCount();
                count++;
                g.setCount(count);
                calculate();
                notifyDataSetChanged();
            }
        });

        //减
        goodHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods g = (Goods) v.getTag();
                int count = g.getCount();
                if (count > 1) {
                    count--;
                } else {
                    Toast.makeText(context, "亲，不能再减了哟！", Toast.LENGTH_SHORT).show();
                }
                g.setCount(count);
                calculate();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ShopHolder {
        CheckBox shopCheckBox;
        TextView shopName;
    }

    class GoodHolder {
        CheckBox goodCheckBox;
        ImageView ivIcon;
        TextView goodName;
        TextView goodPrice;
        RelativeLayout groupLayout;
        Button btnMinus;
        TextView tvNum;
        Button btnAdd;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
