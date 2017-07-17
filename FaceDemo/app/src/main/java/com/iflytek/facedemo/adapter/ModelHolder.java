package com.iflytek.facedemo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iflytek.facedemo.R;
import com.iflytek.facedemo.VideoDemo;

import java.util.List;

/**
 * Created by xianshang.liu on 2017/7/17.
 */

public class ModelHolder extends RecyclerView.ViewHolder {

    private final TextView mName;

    public ModelHolder(View itemView) {
        super(itemView);
        mName = (TextView) itemView.findViewById(R.id.text_holder_item);
    }

    protected void bind(final Activity aActivity, final List<GlobalData> aGlobalDatas, final int pos) {
        final VideoDemo demo = (VideoDemo) aActivity;
        mName.setText(aGlobalDatas.get(pos).getTitle());
        mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo.changeFace( aGlobalDatas.get(pos));
            }
        });

    }
}
