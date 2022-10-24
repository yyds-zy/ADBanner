package com.free.banner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.free.banner.adapter.RaceRecordViewAdapter;
import com.free.banner.bean.RaceDataBean;

import java.util.ArrayList;
import java.util.List;

public class GuestActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecycleView;
    RaceRecordViewAdapter adapter;
    TextView textViewGuestCount,textViewRanking;
    RaceDataBean raceDataBean;
    TextView upperTv,nextTv;
    Button upperCountBtn,nextCountBtn;
    private int page = 1;
    private int lastIndex;
    private int startIndex = 1;
    private int pageNumber = 5;
    private int upperPage = 1;  //上一页 展示的number
    private int nextPage = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        lastIndex = page * pageNumber + 1;
        loadData(startIndex, lastIndex);
        init();
    }

    private void loadData(int startIndex,int lastIndex) {
        raceDataBean = new RaceDataBean();
        raceDataBean.setRaceCount(12);
        raceDataBean.setRanking(0);
        List<RaceDataBean.RaceInfo> raceInfoList = new ArrayList<>();
        raceInfoList.clear();
        for (int i = startIndex; i < lastIndex; i++) {
            RaceDataBean.RaceInfo raceInfo = new RaceDataBean.RaceInfo();
            raceInfo.setDate(System.currentTimeMillis());
            raceInfo.setLeftCountry("德国");
            raceInfo.setRightCountry("乌拉圭");
            raceInfo.setRace("德国胜" + i);
            raceInfo.setResult("未出结果");
            raceInfoList.add(raceInfo);

        }
        raceDataBean.setRaceInfoList(raceInfoList);
    }

    private void init() {
        upperCountBtn = findViewById(R.id.btn_left_page_count);
        nextCountBtn = findViewById(R.id.btn_right_page_count);

        upperCountBtn.setSelected(true);
        upperCountBtn.setText(upperPage + "");
        nextCountBtn.setText(nextPage + "");

//        if (raceDataBean.getRaceInfoList().size() > 5) {
//            nextCountBtn.setText(2 + "");
//        } else {
//            nextCountBtn.setVisibility(View.GONE);
//        }

        upperTv = findViewById(R.id.tv_upper_page);
        nextTv = findViewById(R.id.tv_next_page);

        upperCountBtn.setOnClickListener(this);
        nextCountBtn.setOnClickListener(this);
        upperTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);

        textViewGuestCount = findViewById(R.id.tv_guest_count);
        textViewRanking = findViewById(R.id.tv_ranking);
        mRecycleView = findViewById(R.id.rv_guest_list);
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);


        textViewGuestCount.setText(raceDataBean.getRaceCount()+"");
        if (raceDataBean.getRanking() == 0) {
            textViewRanking.setText("暂无排名");
        } else {
            textViewRanking.setText(raceDataBean.getRanking()+"");
        }
        adapter = new RaceRecordViewAdapter(raceDataBean);
        mRecycleView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left_page_count:
                upperCountBtn.setSelected(true);
                nextCountBtn.setSelected(false);
                startIndex = upperPage * pageNumber + 1;
                lastIndex = nextPage * 5 + 1;
                loadData(startIndex,lastIndex);
                adapter = new RaceRecordViewAdapter(raceDataBean);
                mRecycleView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_right_page_count:
                startIndex = upperPage * pageNumber + 1;
                lastIndex = nextPage * 5 + 1;
                upperCountBtn.setSelected(false);
                nextCountBtn.setSelected(true);
                loadData(startIndex,lastIndex);
                adapter = new RaceRecordViewAdapter(raceDataBean);
                mRecycleView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_upper_page:
                if (nextCountBtn.isSelected()) {
                    upperCountBtn.setSelected(true);
                    nextCountBtn.setSelected(false);
                }
                String upperTvStr = upperCountBtn.getText().toString();
                upperPage = Integer.parseInt(upperTvStr);
                if (upperPage >= 2) {
                    upperPage = upperPage - 1;
                    nextPage = upperPage + 1;
                }
                upperCountBtn.setText(upperPage+"");
                nextCountBtn.setText(nextPage+"");

                loadData(upperPage,upperPage * pageNumber + 1);
                adapter = new RaceRecordViewAdapter(raceDataBean);
                mRecycleView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_next_page:
                if (upperCountBtn.isSelected()) {
                    upperCountBtn.setSelected(false);
                    nextCountBtn.setSelected(true);
                }
                String nextTvStr = nextCountBtn.getText().toString();
                nextPage = Integer.parseInt(nextTvStr);
                if (nextPage >= 2) {
                    nextPage = nextPage + 1;
                    upperPage = nextPage - 1;
                }
                loadData(upperPage,nextPage * pageNumber + 1);
                upperCountBtn.setText(upperPage+"");
                nextCountBtn.setText(nextPage+"");


                adapter = new RaceRecordViewAdapter(raceDataBean);
                mRecycleView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
