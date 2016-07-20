package tv.sevlow.com.elementtvcontrol.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import tv.sevlow.com.elementtvcontrol.R;
import tv.sevlow.com.elementtvcontrol.util.TVControlHelper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.power_btn)
    public ImageButton powerBtn;

    @BindView(R.id.home_btn)
    public ImageButton homeBtn;

    @BindView(R.id.back_btn)
    public ImageButton backBtn;

    @BindView(R.id.arrow_left_btn)
    public ImageButton leftBtn;

    @BindView(R.id.arrow_right_btn)
    public ImageButton rightBtn;

    @BindView(R.id.arrow_up_btn)
    public ImageButton upBtn;

    @BindView(R.id.arrow_down_btn)
    public ImageButton downBtn;

    @BindView(R.id.enter_btn)
    public ImageButton enterBtn;

    @BindView(R.id.volume_increment_btn)
    public Button volumeIncrementBtn;

    @BindView(R.id.volume_decrement_btn)
    public Button volumeDecrementBtn;

    @BindView(R.id.mute_btn)
    public ImageButton muteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBtnEvent();
    }

    private void initBtnEvent(){
        powerBtn.setOnClickListener(new OrderOnClickListener("System.Shutdown"));

        homeBtn.setOnClickListener(new OrderOnClickListener("Input.Home"));
        backBtn.setOnClickListener(new OrderOnClickListener("Input.Back"));

        leftBtn.setOnClickListener(new OrderOnClickListener("Input.Left"));
        rightBtn.setOnClickListener(new OrderOnClickListener("Input.Right"));
        upBtn.setOnClickListener(new OrderOnClickListener("Input.Up"));
        downBtn.setOnClickListener(new OrderOnClickListener("Input.Down"));
        enterBtn.setOnClickListener(new OrderOnClickListener("Input.Select"));

        Map<String,Object>
                decrementVolumeParam = new HashMap<>(),
                incrementVolumeParam = new HashMap<>(),
                muteParam = new HashMap<>();

        incrementVolumeParam.put("volume","increment");
        volumeIncrementBtn.setOnClickListener(new OrderOnClickListener("Application.SetVolume",incrementVolumeParam));

        decrementVolumeParam.put("volume","decrement");
        volumeDecrementBtn.setOnClickListener(new OrderOnClickListener("Application.SetVolume",decrementVolumeParam));

        muteParam.put("mute","toggle");
        muteBtn.setOnClickListener(new OrderOnClickListener("Application.SetMute",muteParam));


    }

    public class OrderOnClickListener implements View.OnClickListener{

        private String order;

        private Map<String,Object> params;

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public OrderOnClickListener(String order){
            this.order = order;
        }

        public OrderOnClickListener(String order,Map<String,Object> params){
            this.order = order;
            this.params = params;
        }

        @Override
        public void onClick(View v) {
            TVControlHelper.send(this.order,this.params);
        }
    }


}
