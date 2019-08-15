package com.study.newsclient.pages.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.newsclient.R;
import com.study.newsclient.adapter.sidebar.AllocationAdapter;
import com.study.newsclient.base.BaseActivity;
import com.study.newsclient.entity.ContactInfo;
import com.study.newsclient.view.ClearEditText;
import com.study.newsclient.widget.sidebar.SideBar;
import com.study.newsclient.widget.sidebar.SideBarDataHelper;
import com.study.newsclient.widget.suspension.DividerItemDecoration;
import com.study.newsclient.widget.suspension.SuspensionDecoration;
import com.yuxuan.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;
import com.yuxuan.common.util.StatusBarUtils;

import java.util.ArrayList;

/**
 * Time: 2019-08-15
 * Author:wyy
 * Description:
 */
public class ContactListActivity extends BaseActivity {
    private ClearEditText etSearch;
    private RecyclerView recyclerView;
    private ArrayList<ContactInfo> contactInfos=new ArrayList<>();
    private ArrayList<ContactInfo> selectedContacts = new ArrayList<>();
    private ArrayList<ContactInfo> searchContacts = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private SuspensionDecoration mDecoration;
    private AllocationAdapter allocationAdapter;
    private Intent intent;
    private Bundle bundle;
    private SideBar sideBar;
    private TextView tvSideBarHint;
    private LinearLayout ll_selected_content;
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_contact_list);

    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        etSearch = (ClearEditText) findViewById(R.id.et_research);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        sideBar = (SideBar) findViewById(R.id.sideBar);
        tvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);
        ll_selected_content = (LinearLayout) findViewById(R.id.ll_selected_content);
        // 无数据展示界面

        StatusBarUtils.darkMode(this);
        StatusBarUtils.setPaddingSmart(this, ll_selected_content);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        allocationAdapter = new AllocationAdapter(this, R.layout.item_assign, searchContacts);
        recyclerView.setAdapter(allocationAdapter);

        mDecoration = new SuspensionDecoration(this, contactInfos);
        mDecoration.setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 37, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffF4F4F4)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()))
                .setColorTitleFont(getResources().getColor(R.color.primaryTitle3));
        recyclerView.addItemDecoration(mDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));


        sideBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(linearLayoutManager);//设置RecyclerView的LayoutManager
        initDatas(getResources().getStringArray(R.array.provinces));

    }
    private void initDatas(String[] data) {
        for (int i = 0; i < data.length; i++) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setFrealname(data[i]);
            //设置城市名称
            contactInfo.setFid(i);
            contactInfos.add(contactInfo);
        }
//        searchContacts=contactInfos;
        sideBar.setmSourceDatas(contactInfos)//设置数据
                .invalidate();
        searchContacts.addAll(contactInfos);
        allocationAdapter.notifyDataSetChanged();
        mDecoration.setmDatas(contactInfos);
    }
    @Override
    protected void bindEvent() {

        findViewById(R.id.ivLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        allocationAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ContactInfo contactInfo = searchContacts.get(position);
                if (contactInfo.isChecked()) {
                    contactInfo.setChecked(false);
                    selectedContacts.remove(contactInfo);

                } else {
                    contactInfo.setChecked(true);
                    selectedContacts.add(contactInfo);
                }
                allocationAdapter.notifyItemChanged(position);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()>0) {
                    searchContacts.clear();
                    for (ContactInfo contactInfo : contactInfos) {
                        if (contactInfo.getFrealname().contains(charSequence.toString())) {
                            searchContacts.add(contactInfo);
                        } else if (contactInfo.getBaseIndexTag().toUpperCase().equals(charSequence.toString().toUpperCase())) {
                            searchContacts.add(contactInfo);
                        } else if (contactInfo.getKeyword().contains(charSequence.toString().toUpperCase())) {
                            searchContacts.add(contactInfo);
                        }

                        if (searchContacts.size() == 10) {
                            break;
                        }
                    }
                }else
                {
                   searchContacts.addAll(contactInfos);
                }
                sideBar.setmSourceDatas(searchContacts).invalidate();
                SideBarDataHelper.sortSourceDatas(searchContacts);
                mDecoration.setmDatas(searchContacts);
                allocationAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    hideKeyboard(etSearch);
                    // 在这里写搜索的操作,一般都是网络请求数据
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void processClick(View paramView) {

    }


    /**
     * 隐藏软键盘
     * @param  :上下文
     * @param view    :一般为EditText
     */
    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
