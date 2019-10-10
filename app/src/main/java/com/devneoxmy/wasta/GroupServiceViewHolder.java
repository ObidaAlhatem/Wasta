package com.devneoxmy.wasta;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by yasser on 3/10/2019.
 */

public class GroupServiceViewHolder extends GroupViewHolder {
    private TextView mTextView;
    private ImageView arrow;
    public GroupServiceViewHolder(View itemView) {
        super(itemView);
        mTextView=itemView.findViewById(R.id.textView_group);
        arrow=itemView.findViewById(R.id.arrow_img);
    }
    public void bind(GroupServiceModel groupServiceModel){
        mTextView.setText(groupServiceModel.getTitle());
    }
    //arrow animation
    @Override
    public void expand() {
        animateExpand();
    }
    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

}
