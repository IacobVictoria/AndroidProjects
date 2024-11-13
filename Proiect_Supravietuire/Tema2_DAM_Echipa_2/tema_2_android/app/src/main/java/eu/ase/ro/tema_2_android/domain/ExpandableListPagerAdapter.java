package eu.ase.ro.tema_2_android.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import eu.ase.ro.tema_2_android.R;

public class ExpandableListPagerAdapter extends RecyclerView.Adapter<ExpandableListPagerAdapter.ViewHolder>  {
    private Context context;
    private List<SurvivalGuide> survivalGuides;

    public ExpandableListPagerAdapter(Context context, List<SurvivalGuide> survivalGuides) {
        this.context = context;
        this.survivalGuides = survivalGuides;
    }

    @NonNull
    @Override
    public ExpandableListPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expandable_list_survival_guides, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpandableListPagerAdapter.ViewHolder holder, int position) {
        SurvivalGuide guide = survivalGuides.get(position);
        holder.guideImageView.setImageResource(guide.getImageResource());
        ExpandableListAdapter adapter = new ExpandableListAdapter(context, List.of(guide));
        holder.expandableListView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return survivalGuides.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ExpandableListView expandableListView;
        ImageView guideImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expandableListView = itemView.findViewById(R.id.iacob_maria_victoria_expandableListView);
            guideImageView = itemView.findViewById(R.id.iacob_maria_victoria_guideImageView);
        }
    }


}
