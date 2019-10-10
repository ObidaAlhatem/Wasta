package com.devneoxmy.wasta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by yasser on 3/10/2019.
 */

public class ChildAdapter extends ExpandableRecyclerViewAdapter<GroupServiceViewHolder,ChildServiceViewHolder> {
    public ChildAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public GroupServiceViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_group,parent,false);
        return new GroupServiceViewHolder(v);
    }

    @Override
    public ChildServiceViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_child,parent,false);

        return new ChildServiceViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(ChildServiceViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final ChildServiceModel childServiceModel=(ChildServiceModel)group.getItems().get(childIndex);
        holder.bind(childServiceModel);

    }

    @Override
    public void onBindGroupViewHolder(GroupServiceViewHolder holder, int flatPosition, ExpandableGroup group) {
        final GroupServiceModel groupServiceModel=(GroupServiceModel)group;
        holder.bind(groupServiceModel);

    }
}
