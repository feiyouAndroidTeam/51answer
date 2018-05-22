package com.yc.answer.setting.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.jakewharton.rxbinding.view.RxView;
import com.kk.utils.ToastUtil;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;
import com.yc.answer.R;
import com.yc.answer.base.WebActivity;
import com.yc.answer.constant.BusAction;
import com.yc.answer.constant.SpConstant;
import com.yc.answer.setting.contract.MyContract;
import com.yc.answer.setting.model.bean.UserInfo;
import com.yc.answer.setting.presenter.MyPresenter;
import com.yc.answer.setting.ui.activity.BindPhoneActivity;
import com.yc.answer.setting.ui.activity.EarningsDetailActivity;
import com.yc.answer.setting.ui.activity.InvitationActivity;
import com.yc.answer.setting.ui.activity.LoginGroupActivity;
import com.yc.answer.setting.ui.activity.SettingActivity;
import com.yc.answer.setting.ui.activity.StatementActivity;
import com.yc.answer.setting.ui.widget.BaseIncomeView;
import com.yc.answer.setting.ui.widget.BaseSettingView;
import com.yc.answer.setting.ui.widget.FollowWeiXinPopupWindow;
import com.yc.answer.utils.ActivityUtils;
import com.yc.answer.utils.QQUtils;
import com.yc.answer.utils.ShareInfoHelper;
import com.yc.answer.utils.ToastUtils;
import com.yc.answer.utils.UserInfoHelper;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;
import yc.com.base.BaseActivity;
import yc.com.base.BaseFragment;


/**
 * Created by wanglin  on 2018/3/7 13:53.
 */

public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.toolbarWarpper)
    FrameLayout toolbarWarpper;
    @BindView(R.id.baseSettingView_wx)
    BaseSettingView baseSettingViewWx;
    @BindView(R.id.baseSettingView_share)
    BaseSettingView baseSettingViewShare;
    @BindView(R.id.baseSettingView_net)
    BaseSettingView baseSettingViewNet;
    @BindView(R.id.baseSettingView_setting)
    BaseSettingView baseSettingViewSetting;
    @BindView(R.id.ll_primary_school)
    LinearLayout llPrimarySchool;
    @BindView(R.id.ll_middle_school)
    LinearLayout llMiddleSchool;
    @BindView(R.id.tv_statement)
    TextView tvStatement;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.ll_not_login)
    LinearLayout llNotLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.baseShareIncomeView)
    BaseIncomeView baseShareIncomeView;
    @BindView(R.id.baseReputationIncomeView)
    BaseIncomeView baseReputationIncomeView;
    @BindView(R.id.baseInvitationIncomeView)
    BaseIncomeView baseInvitationIncomeView;
    @BindView(R.id.baseDepositIncomeView)
    BaseIncomeView baseDepositIncomeView;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    private long startTime;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void init() {
        mPresenter = new MyPresenter(getActivity(), this);
        initListener();


    }

    private void initListener() {
        RxView.clicks(baseSettingViewWx).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), LoginGroupActivity.class));
            }
        });

        RxView.clicks(baseSettingViewSetting).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        RxView.clicks(tvNickname).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (!UserInfoHelper.isGoToLogin(getActivity())) {//设置昵称
                    showDioloag();
                }
            }
        });
        RxView.clicks(ivAvatar).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (!UserInfoHelper.isGoToLogin(getActivity())) {//更改图像
                    RxPhotoTool.openLocalImage(getActivity());
                }
            }
        });
        RxView.clicks(tvPhone).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (!UserInfoHelper.isGoToLogin(getActivity())) {//更改手机号
                    Intent intent = new Intent(getActivity(), BindPhoneActivity.class);
                    intent.putExtra("flag", "修改");
                    startActivity(intent);
                }
            }
        });
        RxView.clicks(tvLoginRegister).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (!UserInfoHelper.isGoToLogin(getActivity())) {
                    //登录成功
                }
            }
        });

        RxView.clicks(baseSettingViewWx).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                FollowWeiXinPopupWindow followWeiXinPopupWindow = new FollowWeiXinPopupWindow(getActivity());
                followWeiXinPopupWindow.show();
            }
        });

        RxView.clicks(llPrimarySchool).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

                QQUtils.joinQQZhongXueGroup(getActivity(), "VPvV6KlVsB5sROLTwoQlk-eD6MZSAXYw");
            }
        });

        RxView.clicks(llMiddleSchool).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

                QQUtils.joinQQZhongXueGroup(getActivity(), "Ik1JY_oz-loc2r9OxDsVcUobxD-PmS9K");
            }
        });

        RxView.clicks(baseSettingViewShare).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ShareFragment shareFragment = new ShareFragment();
                shareFragment.setShareInfo(ShareInfoHelper.getShareInfo());
                shareFragment.show(getFragmentManager(), null);
            }
        });

        RxView.clicks(baseSettingViewNet).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", "http://m.upkao.com/zk/");
                startActivity(intent);
            }
        });

        RxView.clicks(tvStatement).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), StatementActivity.class));
            }
        });
        RxView.clicks(baseShareIncomeView).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //分享赚钱
                ShareFragment shareFragment = new ShareFragment();
                shareFragment.setShareInfo(ShareInfoHelper.getShareInfo());
                shareFragment.show(getFragmentManager(), null);

            }
        });
        RxView.clicks(baseReputationIncomeView).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startTime = System.currentTimeMillis();
                //好评赚钱
                try {
                    Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showCenterToast(getActivity(), "你手机安装的应用市场没有上线该应用，请前往其他应用市场进行点评");
                }
            }
        });
        RxView.clicks(baseInvitationIncomeView).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //邀请码
//if (UserInfoHelper.isGoToLogin(getActivity())){}
                startActivity(new Intent(getActivity(), InvitationActivity.class));
            }
        });

        RxView.clicks(baseDepositIncomeView).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //申请提现
                ApplyDepositFragment applyDepositFragment = new ApplyDepositFragment();
                applyDepositFragment.show(getFragmentManager(), "");
            }
        });
        RxView.clicks(tvDetail).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), EarningsDetailActivity.class));
            }
        });


    }

    private void showDioloag() {
        final RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(getActivity());//提示弹窗
        rxDialogEditSureCancel.getTitleView().setText("请输入你的昵称");
        final EditText editText = rxDialogEditSureCancel.getEditText();
//                    editText.setHint(tvNickname.getText());
        rxDialogEditSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = editText.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    ToastUtil.toast(getActivity(), "昵称不能为空");
                    return;
                }
                mPresenter.updateInfo(text, "", "");
                rxDialogEditSureCancel.cancel();
            }
        });
        rxDialogEditSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogEditSureCancel.cancel();
            }
        });
        rxDialogEditSureCancel.show();
    }


    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.LOGIN_OUT)
            })
    @Override
    public void showNotLogin(String b) {
        if (ActivityUtils.isValidContext(getActivity())) {
            ivAvatar.setImageResource(R.mipmap.default_not_login);
            llNotLogin.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);

        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.LOGIN_SUCCESS)
            })

    @Override
    public void showUserInfo(UserInfo userInfo) {
        llLogin.setVisibility(View.VISIBLE);
        llNotLogin.setVisibility(View.GONE);
        Glide.with(getActivity()).load(userInfo.getFace()).apply(new RequestOptions().error(R.mipmap.default_login).circleCrop()).into(ivAvatar);
        String nick_name = userInfo.getNick_name();
        if (TextUtils.isEmpty(nick_name)) {
            nick_name = "还没设置昵称,快来设置吧!";
        }
        tvNickname.setText(nick_name);

        if (TextUtils.isEmpty(userInfo.getMobile())) {
            tvPhone.setVisibility(View.GONE);
        } else {
            tvPhone.setText(userInfo.getMobile());
            tvPhone.setVisibility(View.VISIBLE);
        }


    }


    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.GET_PICTURE)
            }
    )
    public void getPicture(Uri uri) {
        String path = RxPhotoTool.getImageAbsolutePath(getActivity(), uri);
        File file = new File(path);
        mPresenter.uploadFile(file, path.substring(path.lastIndexOf("/") + 1));
    }

    @Override
    public void showLoadingDialog(String mess) {
        ((BaseActivity) getActivity()).showLoadingDialog(mess);
    }

    @Override
    public void dismissDialog() {
        ((BaseActivity) getActivity()).dismissDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (startTime > 0) {
            if ((System.currentTimeMillis() - startTime) / 1000 >= 5) {
                //跳到应用市场
                RxSPTool.putBoolean(getActivity(), SpConstant.OPEN_MARKET, true);
            }
        }
    }
}