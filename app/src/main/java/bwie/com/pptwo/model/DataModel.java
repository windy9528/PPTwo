package bwie.com.pptwo.model;


import java.util.List;

import bwie.com.pptwo.bean.Result;
import bwie.com.pptwo.bean.Shop;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Author: 杨高峰(windy)
 * Date: 2019/5/15 17:07
 * Description:
 */
public interface DataModel {

    //返回父亲
//    @GET("ks/product/getCarts")
    @GET("order/verify/v1/findShoppingCart")
    Observable<Result<List<Shop>>> show(@Header("userId") String uid,
                                        @Header("sessionId") String sid);
}
