package ws.el.p20150612a;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Li on 2015/6/11.
 */
public class DataTrans implements Runnable {

    public static int STATUS_SUCCESS = 1;
    public static int STATUS_FAIL = 0;

    private static final String HOST = "http://www....";

    private Handler handler;
    private String uploadData;

    public DataTrans(Handler h, String uploadData){
        this.handler = h;
        this.uploadData = uploadData;
    }

    @Override
    public void run() {
        String response = sendData(HOST, uploadData);
        if(response.length() > 0){
            Message.obtain(handler, DataTrans.STATUS_SUCCESS, response).sendToTarget();
        }else{
            Message.obtain(handler, DataTrans.STATUS_FAIL).sendToTarget();
        }
    }

    private String sendData(String path, String data) {
        HttpPost httpRequest = new HttpPost(path);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //TODO 附加參數
        params.add(new BasicNameValuePair("TAG_NAME", data));

        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String responseData = EntityUtils.toString(httpResponse.getEntity());
                return responseData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}


