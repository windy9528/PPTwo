package bwie.com.pptwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import bwie.com.pptwo.adapter.ShopAdapter;
import bwie.com.pptwo.bean.Goods;
import bwie.com.pptwo.bean.Result;
import bwie.com.pptwo.bean.Shop;
import bwie.com.pptwo.core.DataCall;
import bwie.com.pptwo.presenter.ShowPresenter;

public class MainActivity extends AppCompatActivity implements DataCall<List<Shop>> {

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.total_price)
    TextView allPrice;
    @BindView(R.id.btn_go)
    Button btnGo;
    private ShowPresenter showPresenter;
    private Unbinder bind;
    private ShopAdapter shopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        fixParent();
    }

    /**
     * 使得父布局不可伸缩,不可点击
     */
    private void fixParent() {
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        showPresenter.getData("6366", "15605649738176366");  //请求数据

        /**
         * 通过接口回调 回显数据
         */
        shopAdapter.setShowTotalPrice(new ShopAdapter.ShowTotalPrice() {
            @Override
            public void showPrice(double totalPrice, int num, boolean status) {
                allPrice.setText("￥" + totalPrice);
                btnGo.setText("去结算(" + num + ")");
                checkboxAll.setChecked(status);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        bind = ButterKnife.bind(this);
        showPresenter = new ShowPresenter(this);
        shopAdapter = new ShopAdapter(this);
        expandableListView.setAdapter(shopAdapter);
    }

    /**
     * 点击全选  实现全选和反选
     */
    @OnClick(R.id.checkbox_all)
    public void clickCheckAll() {
        List<Shop> shops = shopAdapter.getList();
        for (int i = 0; i < shops.size(); i++) {
            Shop shop = shops.get(i);
            shop.shopCheck = checkboxAll.isChecked();  //把当前全选框的状态给父级复选框
            for (int j = 0; j < shop.getShoppingCartList().size(); j++) {
                Goods goods = shop.getShoppingCartList().get(j);
                goods.goodCheck = shop.shopCheck;
            }
        }
        shopAdapter.calculate();
        shopAdapter.notifyDataSetChanged();
    }


    /**
     * 网络请求成功回调
     *
     * @param result
     */
    @Override
    public void success(List<Shop> result) {
        //遍历所有父级条目，使其默认展开
        shopAdapter.addList(result);
        for (int i = 0; i < shopAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
        shopAdapter.notifyDataSetChanged();
    }

    /**
     * 网络请求失败回调
     *
     * @param result
     */
    @Override
    public void fail(Result result) {
        Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 避免内存泄露、解绑butterknifer
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        if (showPresenter != null) {
            showPresenter.unBind();
        }
    }
}
