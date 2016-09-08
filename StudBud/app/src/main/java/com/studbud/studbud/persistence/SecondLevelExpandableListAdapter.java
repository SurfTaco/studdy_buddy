package com.studbud.studbud.persistence;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.studbud.studbud.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Der Bar.de on 08.09.2016.
 */
class SecondLevelAdapter extends BaseExpandableListAdapter{

    private final Context context;
        private final List<String> headLine;
        private final Map<String, List<String>> childTitles;
        public SecondLevelAdapter(Context context, List<String> headLine, Map<String, List<String>> childTitles) {
            this.context = context;
            this.headLine = headLine;
            this.childTitles = childTitles;
        }
        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return this.childTitles.get(this.headLine.get(groupPosition))
                    .get(childPosition);
        }
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent)
        {
            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_list_list_item, parent, false);
            }
            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.listItem);
            txtListChild.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            txtListChild.setText(childText);
            return convertView;
        }
        @Override
        public int getChildrenCount(int groupPosition)
        {
            try {
                return this.childTitles.get(this.headLine.get(groupPosition)).size();
            } catch (Exception e) {
                return 0;
            }
        }
        @Override
        public Object getGroup(int groupPosition)
        {
            return this.headLine.get(groupPosition);
        }
        @Override
        public int getGroupCount()
        {
            return this.headLine.size();
        }
        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent)
        {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.second_expandable_list_list_group, parent, false);
            }
            TextView listHeader = (TextView) convertView
                    .findViewById(R.id.listHeader);
            listHeader.setText(headerTitle);
            listHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            listHeader.setTextColor(Color.GRAY);
            return convertView;
        }
        @Override
        public boolean hasStableIds() {
            return true;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

