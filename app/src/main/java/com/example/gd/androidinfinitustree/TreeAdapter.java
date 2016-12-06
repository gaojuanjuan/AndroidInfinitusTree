package com.example.gd.androidinfinitustree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高娟娟 on 2016/12/6.
 */

public class TreeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Node> allsCache = new ArrayList<>();
    private List<Node> alls = new ArrayList<>();
    private TreeAdapter oThis = this;
    private boolean hasCheckBox = true;//是否拥有复选框
    private int expandedIcon = -1;
    private int collapsedIcon = -1;


    public TreeAdapter(Context context,Node rootNode) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        addNode(rootNode);
    }

    private void addNode(Node rootNode) {
        alls.add(rootNode);
        allsCache.add(rootNode);
        if (rootNode.isLeaf()) return;
        for (int i = 0; i < rootNode.getChildren().size(); i++) {
            addNode(rootNode.getChildren().get(i));
        }
    }

    /**
     * 复选框联动
     * @param node
     * @param isChecked
     */
    private void checkNode(Node node,boolean isChecked){
        node.setChecked(isChecked);
        for (int i = 0; i < node.getChildren().size(); i++) {
            checkNode(node.getChildren().get(i),isChecked);
        }
    }

    /**
     * 获得选中节点
     * @return
     */
    public List<Node> getSelectedNodes(){
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < allsCache.size(); i++) {
            Node n = allsCache.get(i);
            if (n.isChecked()){
                nodes.add(n);
            }
        }
        return nodes;
    }

    /**
     * 控制节点的展开和折叠
     */
    private void filterNode(){
        alls.clear();
        for (int i = 0; i < allsCache.size(); i++) {
            Node node = allsCache.get(i);
            if (!node.isParentCollapsed() || node.isRoot()) {
                alls.add(node);
            }
        }
    }

    /**
     * 设置是否拥有复选框
     * @param hasCheckBox
     */
    public void setCheckBox(boolean hasCheckBox){
        this.hasCheckBox = hasCheckBox;
    }

    /**
     * 设置展开和折叠状态图标
     * @param expandedIcon  展开时图标
     * @param collapsedIcon 折叠时图标
     */
    public void setExpandedCollapsedIcon(int expandedIcon,int collapsedIcon){
        this.expandedIcon =expandedIcon;
        this.collapsedIcon = collapsedIcon;
    }

    /**
     * 设置展开级别
     * @param level
     */
    public void setExpandLevel(int level){
        alls.clear();
        for (int i = 0; i < allsCache.size(); i++) {
            Node node = allsCache.get(i);
            if (node.getLevel() <= level) {
                if (node.getLevel() < level) {
                    //上层都设置展开状态
                    node.setExpanded(true);
                }else {
                    //最后一层都设置折叠状态
                    node.setExpanded(false);
                }
                alls.add(node);
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 控制节点的展开和收缩
     * @param position
     */
    public void ExpandOrCollapse(int position){
        Node node = alls.get(position);
        if (node != null) {
            if (!node.isLeaf()) {
                node.setExpanded(!node.isExpanded());
                filterNode();
                this.notifyDataSetChanged();
            }
        }
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
            view = inflater.inflate(R.layout.listview_item_tree,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Node node = alls.get(i);
        if (node != null) {
            holder.chbSelect.setTag(node);
            holder.chbSelect.setChecked(node.isChecked());

            //是否显示复选框
            if (node.isHasCheckBox() && hasCheckBox){
                holder.chbSelect.setVisibility(View.VISIBLE);
            }else {
                holder.chbSelect.setVisibility(View.GONE);
            }

            //是否显示图标
            if (node.getIcon() == -1){
                holder.ivIcon.setVisibility(View.GONE);
            }else {
                holder.ivIcon.setVisibility(View.VISIBLE);
                holder.ivIcon.setImageResource(node.getIcon());
            }

            //显示文本
            holder.tvText.setText(node.getText());
            if (node.isLeaf()){
                //是叶节点，不显示展开和折叠状态图标
                holder.ivEXEC.setVisibility(View.GONE);
            }else {
                //单击时控制子节点展开和折叠，状态图标改变
                holder.ivEXEC.setVisibility(View.VISIBLE);
                if (node.isExpanded()) {
                    if (expandedIcon != -1)
                        holder.ivEXEC.setImageResource(expandedIcon);
                }else {
                    if (collapsedIcon != -1)
                        holder.ivEXEC.setImageResource(collapsedIcon);
                }
            }
            view.setPadding(35* node.getLevel(),3,3,3);
        }
        return view;
    }

    /**
     * 列表项控件集合
     */
    private class ViewHolder{
        private CheckBox chbSelect;//选中与否
        private ImageView ivIcon;//图标
        private TextView tvText;//文本
        private ImageView ivEXEC;//展开或折叠标记">"或"v"

        public ViewHolder(View itemView) {
            chbSelect = (CheckBox) itemView.findViewById(R.id.chbSelect);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            ivEXEC = (ImageView)itemView.findViewById(R.id.ivExEc);
            //复选框单击事件
            chbSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Node n = (Node) view.getTag();
                    checkNode(n, ((CheckBox) view).isChecked());
                    oThis.notifyDataSetChanged();
                }
            });
        }
    }
}
