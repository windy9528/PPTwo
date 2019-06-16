package bwie.com.pptwo.core;


import bwie.com.pptwo.bean.Result;
import bwie.com.pptwo.model.DataModel;
import bwie.com.pptwo.util.HttpUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: 杨高峰(windy)
 * Date: 2019/5/10 14:26
 * Description:
 */
public abstract class BasePrsenter {

    private DataCall dataCall;

    public BasePrsenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }

    public void getData(Object... args) {
        DataModel dataModel = HttpUtil.getHttpUtil().getRetrofit().create(DataModel.class);
        getModel(dataModel, args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, Result>() {
                    @Override
                    public Result apply(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        return new Result();
                    }
                })
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        if ("0000".equals(result.getStatus())) {
                            dataCall.success(result.getResult());
                        } else {
                            dataCall.fail(result);
                        }
                    }
                });
    }

    protected abstract Observable getModel(DataModel dataModel, Object... args);

    public void unBind() {
        if (dataCall != null) {
            dataCall = null;
        }
    }
}
