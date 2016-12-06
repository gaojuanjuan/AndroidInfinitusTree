package com.example.gd.androidinfinitustree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 * Created by 高娟娟 on 2016/12/6.
 */

public class Node {
    private Node parent;//父节点
    private List<Node> children = new ArrayList<>();//子节点
    private String text;//节点显示的文字
    private String value;//节点的值
    private int icon = -1;//是否显示小图标，-1表示隐藏小图标
    private boolean isChecked;//是否处于选中状态
    private boolean isExpanded = true;//是否处于展开状态
    private boolean hasCheckBox = true;//是否拥有复选框

    /**
     * 是否是根节点
     * @return
     */
    public boolean isRoot(){
        return parent == null?true:false;
    }

    /**
     * 添加子节点
     * @param node
     */
    public void add(Node node){
        //添加子节点之前需要判断，子节点集合中是否已经包含该节点了
        if (!children.contains(node)) {
            children.add(node);
        }
    }

    /**
     * 清除所有子节点
     */
    public void clear(){
        children.clear();
    }

    /**
     * 删除一个子节点
     * @param node
     */
    public void remove(Node node){
        if (!children.contains(node)) {
            children.remove(node);
        }
    }

    /**
     * 删除指定位置的子节点
     * @param location
     */
    public void remove(int location){
        children.remove(location);
    }

    //获得节点的级数，根节点为0
    public int getLevel(){
        return parent==null?0:parent.getLevel()+1;
    }

    /**
     * 判断该节点是否有子节点
     * @return
     */
    public boolean isLeaf(){
        return children.size()<1?true:false;
    }

    /**
     * 递归判断父节点是否处于折叠状态，有一个父节点折叠则认为是折叠状态
     * @return
     */
    public boolean isParentCollapsed(){
        if (parent == null)
            return !isExpanded;
        if (!parent.isExpanded())
            return true;
        return parent.isParentCollapsed();
    }

    /**
     * 递归判断所给节点是否是当前节点的父节点
     * @param node
     * @return
     */
    public boolean isParent(Node node){
        if (parent == null)
            return false;
        if (node.equals(parent))
            return true;
        return parent.isParent(node);
    }
    /**
     * Node构造函数
     * @param text  节点显示的文字
     * @param value 节点的值
     */
    public Node(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public boolean isHasCheckBox() {
        return hasCheckBox;
    }

    public void setHasCheckBox(boolean hasCheckBox) {
        this.hasCheckBox = hasCheckBox;
    }
}
