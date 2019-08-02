package com.projectThesis.SmartLife;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ViewDialog {

    Activity activity;
    Dialog dialog;
    public ViewDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_loading_layout);

        final ImageView gifImageView = (ImageView) dialog.findViewById(R.id.custom_loading_imageView);

                Glide.with(activity)
                .asGif()
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .centerCrop()
                .into(gifImageView);

                dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }

}