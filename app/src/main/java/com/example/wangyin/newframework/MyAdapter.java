package com.example.wangyin.newframework;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by wangyin on 2017/8/9.
 */

public class MyAdapter extends BaseQuickAdapter<User,BaseViewHolder>{


    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void onDel(int pos);

    }

    private onSwipeListener mOnSwipeListener;

    public void setOnDelListener(onSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }
    public MyAdapter(@LayoutRes int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(final BaseViewHolder viewHolder, User item) {
        viewHolder.setText(R.id.username, item.username)
        .addOnClickListener(R.id.username);
        ((SwipeMenuView)viewHolder.getView(R.id.swipeMenuView)).setIos(false).setLeftSwipe(true);

       viewHolder.getView(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (null != mOnSwipeListener) {
                   //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                   //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                   //((CstSwipeDelMenu) holder.itemView).quickClose();
                   mOnSwipeListener.onDel(viewHolder.getLayoutPosition() );
               }
           }
       });
    }
}
