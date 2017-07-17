package com.iflytek.facedemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iflytek.facedemo.R;

import java.util.List;

/**
 * Created by xianshang.liu on 2017/7/17.
 */

public class GlobalAdapter extends RecyclerView.Adapter {
    private List<GlobalData> mGlobalDatas;

    Activity mContext;

    public GlobalAdapter(List<GlobalData> aGlobalDatas, Context aContext) {
        mGlobalDatas = aGlobalDatas;
        if (aContext instanceof Activity) {
            mContext = (Activity) aContext;
        } else {
            try {
                throw new Exception("context");
            } catch (Exception aE) {
                aE.printStackTrace();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ModelHolder(LayoutInflater.from(mContext).inflate(R.layout.holder_model, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ModelHolder) holder).bind(mContext, mGlobalDatas, position);
    }

    @Override
    public int getItemCount() {
        return mGlobalDatas.size();
    }

}
