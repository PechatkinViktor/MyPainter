package com.pechatkin.sbt.mypainter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pechatkin.sbt.mypainter.R;
import com.pechatkin.sbt.mypainter.models.Types;

import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends BaseAdapter {

    private List<Types> mTypes;

    public TypeAdapter(List<Types> types) {
        mTypes = new ArrayList<>(types);
    }


    @Override
    public int getCount() {
        return mTypes.size();
    }

    @Override
    public Object getItem(int i) {
        return mTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup, false);
            ViewHolder holder = new ViewHolder(view);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mValue.setText(mTypes.get(i).mTypeName);
        return view;
    }

    private class ViewHolder{
        private final TextView mValue;

        ViewHolder(View view){
            mValue = view.findViewById(android.R.id.text1);
            mValue.setTextSize(view.getResources().getDimension(R.dimen.spinner_text_size));
        }

    }
}
