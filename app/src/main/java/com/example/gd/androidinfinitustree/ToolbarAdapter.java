package com.example.gd.androidinfinitustree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 高娟娟 on 2016/12/6.
 */

public class ToolbarAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> alls = new ArrayList<>();
    private LayoutInflater inflater;
    private List<Integer> posClickable = new ArrayList<>();
    //设置绑定字段
    public static final String NAME = "name";//文字
    public static final String IMAGE = "image";//图片

    /**
     * 底部适配器的构造函数
     * @param context
     * @param name_array    菜单文字数组
     * @param image_array   菜单图片数组
     * @param position      可以点击的菜单位置数组
     */
    public ToolbarAdapter(Context context,String[] name_array,int[] image_array,int[] position) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (name_array != null && name_array.length>0) {
            for (int i = 0; i < name_array.length; i++) {
                Map<String,Object> item = new HashMap<>();
                item.put(NAME,name_array[i]);
                if (image_array != null && image_array.length == name_array.length ) {
                    item.put(IMAGE,image_array[i]);
                }
                alls.add(item);
            }

            if (position != null && position.length > 0) {
                for (int i = 0; i < position.length; i++) {
                    posClickable.add(position[i]);
                }
            }
        }
    }

    public String getBh(int index){
        Map<String, Object> item = alls.get(index);
        String bh = item.get(NAME).toString();
        return bh;
    }

    @Override
    public int getCount() {
        return alls.size();
    }

    @Override
    public Object getItem(int i) {
        return alls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_toolbar,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Map<String, Object> mapItem = alls.get(i);
        String name = mapItem.get(NAME).toString();
        holder.textView.setText(name);
        if(mapItem.get(IMAGE)==null){
            //holder.ivImage.setVisibility(View.GONE);
        }else{
            //holder.ivImage.setVisibility(View.VISIBLE);
            //holder.ivImage.setImageResource(Integer.valueOf(mapItem.get(IMAGE).toString()));
        }
        view.setFocusable(false);
        view.setClickable(false);
        //控制是否可以点击和获得焦点
        if (!posClickable.contains(i)){
            view.setFocusable(true);
            view.setClickable(true);
        }
        return view;
    }

    private class ViewHolder{
        private ImageView ivImage;
        private TextView textView;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.item_text);
        }
    }
}
