package com.ubercode.databinding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter({"profileAvatarUrl"})
    public static void setprofileAvatarUrl(ImageView imageView, String profileAvatarUrl) {
        Context context = imageView.getContext();
        if (!TextUtils.isEmpty(profileAvatarUrl)) {
            Picasso.with(context)
                    .load(profileAvatarUrl)
//                    .transform(new CircleTransformPurple())
                    .centerCrop()
                    .fit()
                    .into(imageView);
        }
    }

}
