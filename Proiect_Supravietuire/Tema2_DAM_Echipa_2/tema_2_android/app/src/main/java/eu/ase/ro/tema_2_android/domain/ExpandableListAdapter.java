package eu.ase.ro.tema_2_android.domain;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import eu.ase.ro.tema_2_android.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<SurvivalGuide> survivalGuides;

    public ExpandableListAdapter(Context context, List<SurvivalGuide> survivalGuides) {

        this.context = context;
        this.survivalGuides = survivalGuides;
    }

    @Override
    public int getGroupCount() {
        return survivalGuides.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return survivalGuides.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return survivalGuides.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView titleTextView = convertView.findViewById(R.id.iacob_maria_victoria_expand_list_title);
        TextView categoryTextView = convertView.findViewById(R.id.iacob_maria_victoria_expand_list_category);
        TextView descriptionTextView = convertView.findViewById(R.id.iacob_maria_victoria_expand_list_description);

        SurvivalGuide survivalGuide = survivalGuides.get(groupPosition);
        titleTextView.setText(survivalGuide.getTitle());
        categoryTextView.setText(survivalGuide.getCategory().toString());
        descriptionTextView.setText(survivalGuide.getDescription());

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child, null);
        }

        TextView precautionsTextView = convertView.findViewById(R.id.iacob_maria_victoria_precautionsTv);
        TextView urgencyTextView = convertView.findViewById(R.id.iacob_maria_victoria_urgencyTv);

        SurvivalGuide survivalGuide = survivalGuides.get(groupPosition);
        String precautions = TextUtils.join(" | ", survivalGuide.getPrecautionMeasures());
        precautionsTextView.setText(context.getString(R.string.expand_list_precautions_label, precautions));
        urgencyTextView.setText(context.getString(R.string.expand_list_urgency_label, survivalGuide.getUrgencyLevel()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
