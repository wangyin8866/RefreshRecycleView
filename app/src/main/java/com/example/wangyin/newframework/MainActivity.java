package com.example.wangyin.newframework;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    EasyRefreshLayout easyRefreshLayout;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        easyRefreshLayout = (EasyRefreshLayout) findViewById(R.id.easylayout);
        for (int i = 0; i < 5; i++) {
            User user = new User("zhangsan" + i);
            users.add(user);
        }
        final MyAdapter myAdapter = new MyAdapter(R.layout.item_main, users);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, position + "a", Toast.LENGTH_SHORT).show();
            }
        });
        myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, position + "b", Toast.LENGTH_SHORT).show();
            }
        });
        myAdapter.addHeaderView(View.inflate(this, R.layout.header, null));
        myAdapter.setOnDelListener(new MyAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos) {
                Toast.makeText(MainActivity.this, pos + "c", Toast.LENGTH_SHORT).show();
                users.remove(pos-1);
                myAdapter.notifyItemRemoved(pos);//推荐用这个
                if (pos != (users.size())) { // 如果移除的是最后一个，忽略 注意：这里的mDataAdapter.getDataList()不需要-1，因为上面已经-1了
                    myAdapter.notifyItemRangeChanged(pos, users.size() - pos);
                }
            }
        });
        easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<User> list = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            User user = new User("zhangsan" + i);
                            list.add(user);
                        }

                        //adapter.addData(list);

                        easyRefreshLayout.loadMoreComplete(new EasyRefreshLayout.Event() {
                            @Override
                            public void complete() {
                                myAdapter.getData().addAll(list);
                                myAdapter.notifyDataSetChanged();

                            }
                        }, 500);

                    }
                }, 2000);


            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<User> list = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            User user = new User("zhangsan" + i);
                            list.add(user);
                        }
                        myAdapter.setNewData(list);
                        easyRefreshLayout.refreshComplete();
                        Toast.makeText(getApplicationContext(), "refresh success", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);

            }
        });


    }
}
