package edu.kiet.www.blackbox.interfaces;

import java.util.List;

import edu.kiet.www.blackbox.models.HistoryPOJO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sooraj on 12/8/17.
 */

public interface HistoryInterface {
    @GET("history.php")
    Call<List<HistoryPOJO>> getResponse(@Query("bus_id") String bus_id,@Query("fdate") String from,@Query("tdate") String to);
}
