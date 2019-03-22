package com.jtao.gankdemo.Activity.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jtao.gankdemo.R;

public class ShowGirlDialog extends Dialog {
    public ShowGirlDialog(Context context) { super((context)); }

    public ShowGirlDialog(Context context, int theme) { super(context, theme); }


    public static class Builder {
        private Context mContext;

        private Bitmap mBitmap;

        private ImageView showGirl;


        public Builder(Context context) { this.mContext = context; }

        public ShowGirlDialog.Builder setShowGirl(Bitmap image) {
            this.mBitmap = image;

            return this;
        }

        public ShowGirlDialog create() {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // instantiate the dialog with the custom Theme
            final ShowGirlDialog dialog = new ShowGirlDialog(mContext, R.style.show_girl);
            View layout = inflater.inflate(R.layout.dialog_showgirl, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            // set the dialog title
            showGirl = (ImageView) layout.findViewById(R.id.dialog_show_girl);
            showGirl.setImageBitmap(mBitmap);

            dialog.setContentView(layout);

            return dialog;
        }
    }


}
