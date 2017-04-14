package me.zuichu.smartandroiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zuichu.sa.recyclerview.adapter.BaseSmartAdapter;
import me.zuichu.sa.recyclerview.view.ProgressStyle;
import me.zuichu.sa.recyclerview.view.SmartRecyclerview;
import me.zuichu.sa.utils.FileUtil;
import me.zuichu.sa.utils.LogUtil;
import me.zuichu.smartandroiddemo.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity implements SmartRecyclerview.LoadingListener, BaseSmartAdapter.OnRecyclerViewItemClickListener, BaseSmartAdapter.OnRecyclerViewItemLongClickListener {
    @Bind(R.id.sr)
    SmartRecyclerview recyclerview;
    private MainAdapter mainAdapter;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        FileUtil.writeText("内容",Environment.getExternalStorageDirectory()+"/ABCD/test/abc.txt");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setRefreshProgressStyle(ProgressStyle.BallBeat);
        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        recyclerview.setPullRefreshEnabled(true);
        recyclerview.setLoadingMoreEnabled(true);
        recyclerview.setLoadingListener(this);
        list = new ArrayList<String>();
        list.add("SmartRecyclerView");
        list.add("MultiItemList(多种布局)");
        list.add("GridItemList(Grid布局)");
        list.add("MultiGrid(多种Grid布局)");
        list.add("GridMultiList(Grid不规则布局)");
        list.add("EmptyList(空数据)");
        list.add("HeaderList(有头布局的)");
        list.add("更多功能敬请发现...");
        mainAdapter = new MainAdapter(this, list);
        recyclerview.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickListener(this);
        mainAdapter.setOnItemLongClickListener(this);
        LogUtil.logJsonI("{\"Json解析\":\"支持格式化高亮折叠\",\"支持XML转换\":\"支持XML转换Json,Json转XML\",\"Json格式验证\":\"更详细准确的错误信息\"}");
        LogUtil.logXmlI("<?xml version=\"1.0\" encoding=\"utf-8\"?><shape xmlns:android=\"http://schemas.android.com/apk/res/android\"><corners android:radius=\"5dp\" /><solid android:color=\"@android:color/black\" /></shape>");

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent;
        switch (position) {
            case 1:
                intent = new Intent(this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this, "长按" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerview.refreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerview.loadMoreComplete();
            }
        }, 1000);
    }
}
