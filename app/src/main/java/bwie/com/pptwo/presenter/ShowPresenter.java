package bwie.com.pptwo.presenter;

import bwie.com.pptwo.core.BasePrsenter;
import bwie.com.pptwo.core.DataCall;
import bwie.com.pptwo.model.DataModel;
import io.reactivex.Observable;

/**
 * Author: 杨高峰(windy)
 * Date: 2019/5/16 18:35
 * Description:
 */
public class ShowPresenter extends BasePrsenter {

    public ShowPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(DataModel dataModel, Object... args) {
        return dataModel.show((String) args[0],String.valueOf(args[1]));
    }
}
