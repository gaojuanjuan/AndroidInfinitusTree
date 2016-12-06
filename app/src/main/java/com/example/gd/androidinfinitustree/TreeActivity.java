package com.example.gd.androidinfinitustree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TreeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private LinearLayout toolBar;
    private ListView code_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = (LinearLayout)findViewById(R.id.toolBar);
        code_list = (ListView)findViewById(R.id.code_list);
        code_list.setOnItemClickListener(this);
        setTooBar(new String[]{"选中结果","","退出"},new int[]{0,3});
        setPreson();
    }

    /**
     * 设置节点，可以通过循环或递归方式添加节点
     */
    private void setPreson() {
        //创建根节点
        Node root = new Node("济南市公安局", "0000000");
        root.setIcon(R.mipmap.icon_department);
        //设置节点前有无复选框
//        root.setHasCheckBox(false);

        //创建1级子节点
        Node n1 = new Node("治安警察大队","1");
        //设置父节点
        n1.setParent(root);
        n1.setIcon(R.mipmap.icon_department);

        Node n11 = new Node("我瑜","13966664567");
        n11.setParent(n1);
        n11.setIcon(R.mipmap.icon_police);
        Node n12 = new Node("我祺","13966664567");
        n12.setParent(n1);
        n12.setIcon(R.mipmap.icon_police);

        n1.add(n11);
        n1.add(n12);


        // 创建1级子节点
        Node n2 = new Node("刑事警察大队","2");
        n2.setParent(root);
        n2.setIcon(R.mipmap.icon_department);
        Node n21 = new Node("我南","13966664567");
        n21.setParent(n2);
        n21.setIcon(R.mipmap.icon_police);
        Node n22 = new Node("我亚琴","13966664567");
        n22.setParent(n2);
        n22.setIcon(R.mipmap.icon_police);
        Node n23 = new Node("我瑾","13766604867");
        n23.setParent(n2);
        n23.setIcon(R.mipmap.icon_police);
        n2.add(n21);
        n2.add(n22);
        n2.add(n23);


        // 创建1级子节点
        Node n3 = new Node("巡警防暴大队","3");
        n3.setParent(root);
        n3.setIcon(R.mipmap.icon_department);
        Node n31 = new Node("我旭","15305510131");
        n31.setParent(n3);
        n31.setIcon(R.mipmap.icon_police);
        Node n32 = new Node("我燕飞","13855196982");
        n32.setParent(n3);
        n32.setIcon(R.mipmap.icon_police);

        //创建2级子节点
        Node n33 = new Node("巡警第一中队","31");
        n33.setParent(n3);
        n33.setIcon(R.mipmap.icon_department);

        Node n331 = new Node("我羊角","15890875672");
        n331.setParent(n33);
        //n331.setIcon(R.drawable.icon_police);
        Node n332 = new Node("我瓜","15890875672");
        n332.setParent(n33);
        n332.setIcon(R.mipmap.icon_police);
        Node n333 = new Node("我颖","15890875672");
        n333.setParent(n33);
        //n333.setIcon(R.drawable.icon_police);
        n33.add(n331);
        n33.add(n332);
        n33.add(n333);

        n3.add(n31);
        n3.add(n32);
        n3.add(n33);



        root.add(n3);
        root.add(n1);
        root.add(n2);

        TreeAdapter treeAdapter = new TreeAdapter(this, root);
        //设置整个树是否显示复选框
        treeAdapter.setCheckBox(true);
        //设置展开和折叠时图片
        treeAdapter.setExpandedCollapsedIcon(R.mipmap.tree_ec,R.mipmap.tree_ex);
        //设置默认展开级别
        treeAdapter.setExpandLevel(2);
        code_list.setAdapter(treeAdapter);

    }


    /**
     * 设置底部工具栏
     * @param name_array
     * @param pos_array
     */
    private void setTooBar(String[] name_array, int[] pos_array) {
        toolBar.removeAllViews();
        GridView toolbarGrid = new GridView(this);
        //设置每行列数
        toolbarGrid.setNumColumns(4);
        //位置居中
        toolbarGrid.setGravity(Gravity.CENTER);
        ToolbarAdapter adapter = new ToolbarAdapter(this,name_array,null,pos_array);
        toolbarGrid.setAdapter(adapter);
        toolbarGrid.setOnItemClickListener(this);
        toolbarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        //显示选中结果
                        List<Node> selectedNodes = ((TreeAdapter) code_list.getAdapter()).getSelectedNodes();
                        String msg = "";
                        for (int j = 0; j < selectedNodes.size(); j++) {
                            Node node = selectedNodes.get(i);
                            if (node.isLeaf()){
                                if (!msg.equals("")) msg+=",";
                                msg += node.getText()+"("+node.getValue()+")";
                            }
                        }
                        if (msg.equals("")){
                            Toast.makeText(TreeActivity.this, "没有选中任何项", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TreeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        TreeActivity.this.finish();
                        System.exit(0);
                        break;
                }
            }
        });
        toolBar.addView(toolbarGrid);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //这句话写在最后面
        ((TreeAdapter) adapterView.getAdapter()).ExpandOrCollapse(i);
    }
}
