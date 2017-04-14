package me.zuichu.smartandroiddemo.adapter;

import android.content.Context;

import java.util.List;

import me.zuichu.sa.recyclerview.adapter.BaseSmartAdapter;
import me.zuichu.sa.recyclerview.viewholder.SmarViewHolder;
import me.zuichu.smartandroiddemo.R;

/**
 * Created by office on 2017/3/4.
 */
public class MainAdapter extends BaseSmartAdapter<String> {

    public MainAdapter(Context context, List<String> lists) {
        super(context, R.layout.item_main, lists);
    }

    @Override
    public void bindData(SmarViewHolder holder, String s) {
        holder.setText(R.id.tv_text, s);
    }
}
