package ws.el.p20150612a;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends ActionBarActivity {


    private Handler httpHandler;
    private Button btnStart;
    private TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httpHandler = new HttpHandler(this);
        findViews();
        setListeners();
    }

    private void findViews(){
        btnStart = (Button)findViewById(R.id.btnStart);
        txvResult = (TextView)findViewById(R.id.txvResult);
    }

    private void setListeners(){
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStart.setEnabled(false);
                new Thread(new DataTrans(httpHandler, "Test")).start();
            }
        });
    }


    static class HttpHandler extends Handler {

        WeakReference<Activity> activity;

        public HttpHandler(Activity activity){
            this.activity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity ma = (MainActivity)activity.get();

            if(msg.what == DataTrans.STATUS_SUCCESS){
                String response = (String)msg.obj;
                ma.txvResult.setText(response);
            }else{
                //...
            }
        }
    }

}
