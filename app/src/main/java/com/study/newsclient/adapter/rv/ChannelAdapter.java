package com.study.newsclient.adapter.rv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.newsclient.R;
import com.study.newsclient.adapter.label.OnItemDragListener;
import com.study.newsclient.bean.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean mIsEdit;
    private long startTime;
    // touch 间隔时间  用于分辨是否是 "点击"
    private static final long SPACE_TIME = 100;
    // touch 间隔时间  用于分辨是否是 "点击"
    private static final long TOUCH_SPACE_TIME = 100;
    // 动画持续时间
    private static final long ANIM_TIME = 400;
    // touch 点击开始时间
    private long touchStartTime;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private RecyclerView mRecyclerView;
    private boolean isEditing;

    private List<Channel> datas;
    private SelectedViewHolder selectedViewHolder;
    private TitleViewHolder titleViewHolder;

    private OnEditCompleteListener onEditFinishListener;

    public ChannelAdapter(Context mContext, List<Channel> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (mRecyclerView == null) {
            mRecyclerView = (RecyclerView) parent;
        }

        mContext = parent.getContext();
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        if (viewType == Channel.TYPE_MY) {
            titleViewHolder = new TitleViewHolder(mLayoutInflater.inflate(R.layout.item_channel_title, parent, false));
            titleViewHolder.tvEdit.setVisibility(View.VISIBLE);
            titleViewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEditing) {
                        changeEditState(false);
                        titleViewHolder.tvTitle.setText("切换栏目");
                        titleViewHolder.tvEdit.setText("编辑");
                    } else {
                        titleViewHolder.tvTitle.setText("拖动排序");
                        titleViewHolder.tvEdit.setText("完成");
                        changeEditState(true);
                    }
                }
            });
            viewHolder = titleViewHolder;
        } else if (viewType == Channel.TYPE_MY_CHANNEL) {
            selectedViewHolder = new SelectedViewHolder(mLayoutInflater.inflate(R.layout.item_channel, parent, false));
            viewHolder = selectedViewHolder;
        } else if (viewType == Channel.TYPE_OTHER) {
            TitleViewHolder titleViewHolder = new TitleViewHolder(mLayoutInflater.inflate(R.layout.item_channel_title, parent, false));
            titleViewHolder.tvEdit.setVisibility(View.GONE);
            viewHolder = titleViewHolder;

        } else if (viewType == Channel.TYPE_OTHER_CHANNEL) {
            UnselectedViewHolder unselectedViewHolder = new UnselectedViewHolder(mLayoutInflater.inflate(R.layout.item_channel, parent, false));
            viewHolder = unselectedViewHolder;
        } else if (viewType == Channel.TYPE_NO_MOVE) {
            SelectedViewHolder selectedViewHolder = new SelectedViewHolder(mLayoutInflater.inflate(R.layout.item_channel, parent, false));
            selectedViewHolder.ivDelete.setVisibility(View.GONE);
            viewHolder = selectedViewHolder;
        }
        return viewHolder;
    }

    /**
     * 开启编辑模式
     */
    private void changeEditState(boolean state) {
        if (isEditing == state) {
            return;
        }
        if (state) {
            titleViewHolder.tvTitle.setText("拖动排序");
            titleViewHolder.tvEdit.setText("完成");
        } else {
            titleViewHolder.tvTitle.setText("切换栏目");
            titleViewHolder.tvEdit.setText("编辑");
            finishEdit();

        }
        isEditing = state;
        int visibleChildCount = mRecyclerView.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = mRecyclerView.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.ivDelete);
            if (imgEdit != null) {
                // boolean isVis = imgEdit.getTag() == null ? false : (boolean) imgEdit.getTag();
                imgEdit.setVisibility(state &&datas.get(i).getItemType()==Channel.TYPE_MY_CHANNEL? View.VISIBLE : View.INVISIBLE);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Channel channel = datas.get(position);
        if (channel.getItemType() == Channel.TYPE_MY) {
            ((TitleViewHolder)holder).tvTitle.setText(channel.getCname());
        } else if (channel.getItemType() == Channel.TYPE_MY_CHANNEL) {
            bindSelectedViewHolder((SelectedViewHolder) holder,channel);
        } else if (channel.getItemType() == Channel.TYPE_OTHER) {
            ((TitleViewHolder)holder).tvTitle.setText(channel.getCname());
        } else if (channel.getItemType() == Channel.TYPE_OTHER_CHANNEL) {
            bindUnSelectedViewHolder((UnselectedViewHolder) holder,channel);
        } else if (channel.getItemType() == Channel.TYPE_NO_MOVE) {
            bindAlwaySelectedViewHolder((SelectedViewHolder) holder,channel);
        }
    }

    private void bindAlwaySelectedViewHolder(SelectedViewHolder holder, Channel channel) {
        holder.tvChannel.setText(channel.getCname());
        holder.tvChannel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!isEditing) {
                    //开启编辑模式
                    changeEditState(true);
                    //  mEditViewHolder.setText(R.id.tvEdit, "完成");
                    titleViewHolder.tvTitle.setText("拖动排序");
                    titleViewHolder.tvEdit.setText("完成");
                }
                return true;
            }
        });
    }

    private void bindUnSelectedViewHolder(final UnselectedViewHolder holder, final Channel channel) {
        holder.tvChannel.setText(channel.getCname());
        holder.tvChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLabel(holder, channel);
            }
        });

    }

    /**
     * 选中标签
     * 从未选中区域移动到选中区域
     */
    private void selectedLabel(UnselectedViewHolder viewHolder, Channel item) {
        int selectedLastPosition = getSelectedLastPosition();
        int currentPosition = viewHolder.getAdapterPosition();
        //获取到目标View
        View targetView = mRecyclerView.getLayoutManager().findViewByPosition(selectedLastPosition);
        //获取当前需要移动的View
        View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPosition);
        // 如果targetView不在屏幕内,则indexOfChild为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
        // 如果在屏幕内,则添加一个位移动画
        if (mRecyclerView.indexOfChild(targetView) >= 0 && selectedLastPosition != -1) {
            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
            int spanCount = ((GridLayoutManager) manager).getSpanCount();
            int targetX = targetView.getLeft() + targetView.getWidth();
            int targetY = targetView.getTop();

            int myChannelSize = getSelectedSize();//这里我是为了偷懒 ，算出来我的频道的大小
            if (myChannelSize % spanCount == 0) {
                //添加到我的频道后会换行，所以找到倒数第4个的位置

                View lastFourthView = mRecyclerView.getLayoutManager().findViewByPosition(getSelectedLastPosition() - (((GridLayoutManager) manager).getSpanCount() - 1));
//                                        View lastFourthView = mRecyclerView.getChildAt(getMyLastPosition() - 3);
                if (lastFourthView != null) {
                    targetX = lastFourthView.getLeft();
                    targetY = lastFourthView.getTop() + lastFourthView.getHeight();
                }
            }


            // 推荐频道 移动到 我的频道的最后一个
            item.setItemType(Channel.TYPE_MY_CHANNEL);//改为推荐频道类型
            onMove(currentPosition, selectedLastPosition + 1);
            startAnimation(currentView, targetX, targetY);
        } else {
            item.setItemType(Channel.TYPE_MY_CHANNEL);
            if (selectedLastPosition == -1) {
                selectedLastPosition = 0;
            }
            onMove(currentPosition, selectedLastPosition + 1);

        }
        finishEdit();
    }
    /**
     *
     * @param selectedViewHolder
     * @param channel
     */
    @SuppressLint("ClickableViewAccessibility")
    private void bindSelectedViewHolder(final SelectedViewHolder selectedViewHolder, final Channel channel) {

        selectedViewHolder.tvChannel.setText(channel.getCname());
        if (isEditing) {
            selectedViewHolder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            selectedViewHolder.ivDelete.setVisibility(View.GONE);
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    unselectedLabel(selectedViewHolder, channel);
                }

            }
        };
        selectedViewHolder.ivDelete.setOnClickListener(onClickListener);
        selectedViewHolder.tvChannel.setOnClickListener(onClickListener);
        selectedViewHolder.tvChannel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isEditing) return false;//正常模式无需监听触摸
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchStartTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (System.currentTimeMillis() - touchStartTime > TOUCH_SPACE_TIME) {
                            //当MOVE事件与DOWN事件的触发的间隔时间大于100ms时，则认为是拖拽starDrag
                            if (onChannelDragListener != null) {
                                onChannelDragListener.onStarDrag(selectedViewHolder);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        touchStartTime = 0;
                        break;
                }
                return false;
            }
        });
        selectedViewHolder.tvChannel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isEditing) {
                    //开启编辑模式
                    changeEditState(true);
                    //  mEditViewHolder.setText(R.id.tvEdit, "完成");
                    titleViewHolder.tvTitle.setText("拖动排序");
                    titleViewHolder.tvEdit.setText("完成");
                }
                if (onChannelDragListener != null) {
                    onChannelDragListener.onStarDrag(selectedViewHolder);
                }
                return true;
            }
        });
    }

    /**
     * 取消选中标签
     * 从选中区域移动到未选中区域
     */
    private void unselectedLabel(SelectedViewHolder viewHolder, Channel item) {
        int otherFirstPosition = getUnselectedFirstPosition();
        int currentPosition = viewHolder.getAdapterPosition();
        //获取到目标View
        View targetView = mRecyclerView.getLayoutManager().findViewByPosition(otherFirstPosition);
        //获取当前需要移动的View
        View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPosition);
        // 如果targetView不在屏幕内,则indexOfChild为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
        // 如果在屏幕内,则添加一个位移动画
        if (mRecyclerView.indexOfChild(targetView) >= 0 && otherFirstPosition != -1) {
            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
            int spanCount = ((GridLayoutManager) manager).getSpanCount();
            int targetX = targetView.getLeft();
            int targetY = targetView.getTop();
            int myChannelSize = getSelectedSize();//这里我是为了偷懒 ，算出来我的频道的大小
            if (myChannelSize % spanCount == 1) {
                //我的频道最后一行 之后一个，移动后
                targetY -= targetView.getHeight();
            }

            //我的频道 移动到 推荐频道的第一个
            item.setItemType(Channel.TYPE_OTHER_CHANNEL);//改为推荐频道类型

            onMove(currentPosition, otherFirstPosition - 1);
            startAnimation(currentView, targetX, targetY);
        } else {
            item.setItemType(Channel.TYPE_OTHER_CHANNEL);//改为推荐频道类型
            if (otherFirstPosition == -1) {
                otherFirstPosition = datas.size();
            }
            onMove(currentPosition, otherFirstPosition - 1);
            /*
            if (onChannelDragListener != null)
                onChannelDragListener.onMoveToOtherChannel(currentPosition, otherFirstPosition - 1);*/
        }
    }
    public boolean cancelEdit() {
        if (isEditing) {
            changeEditState(false);
            return true;
        }
        return false;
    }


    private void onMove(int starPos, int endPos) {
        Channel startItem = datas.get(starPos);
        //先删除之前的位置
        datas.remove(starPos);
        //添加到现在的位置
        datas.add(endPos, startItem);
        this.notifyItemMoved(starPos, endPos);
    }
    /**
     * 添加需要移动的 镜像View
     */
    private ImageView addMirrorView(ViewGroup parent, View view) {
        view.destroyDrawingCache();
        //首先开启Cache图片 ，然后调用view.getDrawingCache()就可以获取Cache图片
        view.setDrawingCacheEnabled(true);
        ImageView mirrorView = new ImageView(view.getContext());
        //获取该view的Cache图片
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        //销毁掉cache图片
        view.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);//获取当前View的坐标
        int[] parenLocations = new int[2];
        mRecyclerView.getLocationOnScreen(parenLocations);//获取RecyclerView所在坐标
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], locations[1] - parenLocations[1], 0, 0);
        parent.addView(mirrorView, params);//在RecyclerView的Parent添加我们的镜像View，parent要是FrameLayout这样才可以放到那个坐标点
        return mirrorView;
    }

    private void startAnimation(final View currentView, int targetX, int targetY) {
        final ViewGroup parent = (ViewGroup) mRecyclerView.getParent();
        final ImageView mirrorView = addMirrorView(parent, currentView);
        TranslateAnimation animator = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetX - currentView.getLeft(),
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetY - currentView.getTop());
        // RecyclerView默认移动动画250ms 这里设置360ms 是为了防止在位移动画结束后 remove(view)过早 导致闪烁
        animator.setDuration(ANIM_TIME);
        animator.setFillAfter(true);
        currentView.setVisibility(View.INVISIBLE);//暂时隐藏
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                parent.removeView(mirrorView);//删除添加的镜像View
                if (currentView.getVisibility() != View.VISIBLE) {
                    currentView.setVisibility(View.VISIBLE);//显示隐藏的View
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mirrorView.startAnimation(animator);
    }


    public int getSelectedSize() {
        int size = 0;
        for (int i = 0; i < datas.size(); i++) {
            Channel labelSelectionItem = datas.get(i);
            if (labelSelectionItem.getItemType() == Channel.TYPE_MY_CHANNEL||labelSelectionItem.getItemType()== Channel.TYPE_NO_MOVE) {
                size++;
            }
        }
        return size;

    }
    /**
     * 获取推荐频道列表的第一个position
     */
    private int getUnselectedFirstPosition() {
        for (int i = 0; i < datas.size(); i++) {
            Channel labelSelectionItem = datas.get(i);
            if (labelSelectionItem.getItemType() == Channel.TYPE_OTHER_CHANNEL) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 我的频道最后一个的position
     */
    private int getSelectedLastPosition() {
        for (int i = datas.size() - 1; i >= 0; i--) {
            Channel channel = datas.get(i);
            if (channel.getItemType() == Channel.TYPE_MY_CHANNEL || channel.getItemType() == Channel.TYPE_NO_MOVE) {
                return i;
            }
        }
        return -1;
    }



    public void setOnEditCompleteListener(OnEditCompleteListener onEditFinishListener) {
        this.onEditFinishListener = onEditFinishListener;
    }

    private void finishEdit() {
        if (onEditFinishListener != null) {
            ArrayList<Channel> selectedLabels = new ArrayList<>();
            ArrayList<Channel> unselectedLabels = new ArrayList<>();
            ArrayList<Channel> alwaySelectedLabels = new ArrayList<>();
            for (Channel labelSelectionItem : datas) {
                if (labelSelectionItem.getItemType() == Channel.TYPE_MY_CHANNEL ) {
                    selectedLabels.add(labelSelectionItem);
                } else if (labelSelectionItem.getItemType() == Channel.TYPE_OTHER_CHANNEL) {
                    unselectedLabels.add(labelSelectionItem);
                } else if (labelSelectionItem.getItemType() == Channel.TYPE_NO_MOVE) {
                    alwaySelectedLabels.add(labelSelectionItem);
                }
            }
            onEditFinishListener.onEditFinish(selectedLabels, unselectedLabels, alwaySelectedLabels);
        }
    }

    private OnItemDragListener onChannelDragListener;

    public void setOnChannelDragListener(OnItemDragListener onChannelDragListener) {
        this.onChannelDragListener = onChannelDragListener;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getItemType();
    }

    public void setNewData(List<Channel> mData) {
        this.datas = mData;
        this.notifyDataSetChanged();
    }

    public List<Channel> getData() {
        return datas;
    }


    private static class SelectedViewHolder extends RecyclerView.ViewHolder {

        private TextView tvChannel;
        private ImageView ivDelete;

        private SelectedViewHolder(View itemView) {
            super(itemView);
            tvChannel = (TextView) itemView.findViewById(R.id.tvChannel);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }


    private static class UnselectedViewHolder extends RecyclerView.ViewHolder {

        private TextView tvChannel;

        private UnselectedViewHolder(View itemView) {
            super(itemView);
            tvChannel = (TextView) itemView.findViewById(R.id.tvChannel);
        }
    }


    private static class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvEdit;

        private TitleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvEdit = (TextView) itemView.findViewById(R.id.tvEdit);
        }
    }

}
