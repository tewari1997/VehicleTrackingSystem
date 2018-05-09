package edu.kiet.www.blackbox.interfaces;

import edu.kiet.www.blackbox.models.smsPOJO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sooraj on 12/8/17.
 */

public interface smsInterface {
    @GET("message.php")
    Call<ResponseBody> getResponse(@Query("mobile") String mob, @Query("type") String type);

}
