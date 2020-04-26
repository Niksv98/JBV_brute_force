package com.example.nils.botaniskietermini;

import android.widget.TextView;

//class for data adding manually
public class Translation {
    private TextView textView;
    private String title;
    private String approCode;
    private boolean isIincluded;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;


    public Translation(TextView textView, String title, String approCode, boolean isIincluded) {
        this.textView = textView;
        this.title = title;
        this.isIincluded = isIincluded;
        this.approCode = approCode;
    }

    public String getLanguage()
    {
        return approCode.substring(approCode.length()-2,approCode.length()-1);
    }


    public boolean isIincluded() {
        return isIincluded;
    }

    public void setIincluded(boolean iincluded) {
        isIincluded = iincluded;
    }

    public TextView getTextView() {
        return textView;
    }

    public String getTitle() {
        return title;
    }

    public String getApproCode() {
        return approCode;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setApproCode(String approCode) {
        this.approCode = approCode;
    }
}
