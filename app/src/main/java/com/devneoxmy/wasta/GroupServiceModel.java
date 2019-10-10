package com.devneoxmy.wasta;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by yasser on 3/10/2019.
 */

public class GroupServiceModel extends ExpandableGroup<ChildServiceModel> {
    public GroupServiceModel(String title, List<ChildServiceModel> items) {
        super(title, items);
    }
}
