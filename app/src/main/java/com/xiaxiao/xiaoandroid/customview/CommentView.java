package com.xiaxiao.xiaoandroid.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.RelativeLayout;

import com.xiaxiao.xiaoandroid.R;
import com.xiaxiao.xiaoandroid.util.XiaoUtil;

/**
 * Created by xiaxiao on 2017/4/28.
 * 自定义的评论view,响应 SendListener
 */

public class CommentView extends LinearLayout {
    private View root;
    private RelativeLayout comment_bg;
    private EditText editText;
    private Button sendBtn;
    private TextWatcher mTextWatcher;
    private SendListener mSendListener;

    public CommentView(Context context) {
        super(context);
        root=LayoutInflater.from(context).inflate(R.layout.customview_comment_view, this);
        initViews();
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        root=LayoutInflater.from(context).inflate(R.layout.customview_comment_view, this);
        initViews();
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        root=LayoutInflater.from(context).inflate(R.layout.customview_comment_view, this);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        root=LayoutInflater.from(context).inflate(R.layout.customview_comment_view, this);
        initViews();
    }

    private void initViews() {
        comment_bg = (RelativeLayout) root.findViewById(R.id.comment_root);
        editText = (EditText) root.findViewById(R.id.editview);
        sendBtn = (Button) root.findViewById(R.id.send_btn);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >0) {
                    setSendEnable(true);
                } else {
                    setSendEnable(false);
                }
            }
        });

        sendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSendListener!=null) {
                    mSendListener.onSend(getText());
                }
            }
        });

    }

    public String getText() {
        return editText.getText().toString();
    }

    public void clearText() {
        editText.setText("");
    }

    /*public void setEditWatcher(TextWatcher textWatcher) {
        mTextWatcher = textWatcher;

    }*/

    public void setSendListener(SendListener sendListener) {
        mSendListener = sendListener;
    }

    public void setSendEnable(boolean sendEnable) {
        sendBtn.setEnabled(sendEnable);
    }

//    public void setBackgroundColor(int id) {
//
//    }
    public interface  SendListener {
        public void onSend(String text);
    }

}
