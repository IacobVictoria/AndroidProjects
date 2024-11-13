package eu.ase.ro.tema_2_android.domain;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.ase.ro.tema_2_android.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ExpPostViewHolder> implements Filterable {
    List<ExperiencePost> experiencePosts;
    List<ExperiencePost> experiencePostsComplete;

    public RecyclerViewAdapter(List<ExperiencePost> experiencePosts) {
        this.experiencePosts = experiencePosts;
        this.experiencePostsComplete = new ArrayList<>(experiencePosts);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ExpPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_card, parent, false);
        return new RecyclerViewAdapter.ExpPostViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ExpPostViewHolder holder, int position) {
        ExperiencePost experiencePost = experiencePosts.get(position);
        holder.tvTitle.setText(experiencePost.getDisasterType());
        holder.tvDescription.setText(experiencePost.getDescription());
        holder.tvName.setText(String.format("Vezi povestea publicata de %s", experiencePost.getName()));
        holder.tvDamage.setText(String.format("Daune: %s", TextUtils.join(", ", experiencePost.getDamages())));
        holder.tvDamageLevel.setText(String.format("Nivel daune suferite: %s", experiencePost.setLossLevel()));
        holder.tvEmotionalDamage.setText(String.format("Nivel afectare emotionala: %s", experiencePost.getEmotionalDamage()));
        holder.tvPrepared.setText(String.format("Nivel pregatire: %d", experiencePost.getLevelOfPreparedness()));
        holder.ivBackground.setImageResource(experiencePost.getImage());

        holder.itemView.setOnClickListener(v -> {
            boolean isExpanded = holder.clExtend.getVisibility() == View.VISIBLE;
            holder.clExtend.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return experiencePosts.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ExperiencePost> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()) {
                filteredList.addAll(experiencePostsComplete);
            } else {
                for(ExperiencePost post : experiencePostsComplete) {
                    String title = post.getDisasterType();
                    if(title.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(post);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            experiencePosts.clear();
            experiencePosts.addAll((Collection<? extends ExperiencePost>) results.values);
            notifyDataSetChanged();
        }
    };

    public void addExperiencePost(ExperiencePost experiencePost) {
        experiencePosts.add(experiencePost);
        experiencePostsComplete.add(experiencePost);
        notifyItemInserted(experiencePosts.size() - 1);
    }

    public static class ExpPostViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBackground;
        TextView tvTitle, tvName, tvDescription, tvDamage, tvDamageLevel, tvEmotionalDamage, tvPrepared;
        ConstraintLayout clExtend;
        public ExpPostViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackground = itemView.findViewById(R.id.balint_franceska_experience_post_iv_background);
            tvTitle = itemView.findViewById(R.id.balint_franceska_experience_post_tv_title);
            tvDescription = itemView.findViewById(R.id.balint_franceska_experience_post_tv_description);
            tvDamage = itemView.findViewById(R.id.balint_franceska_experience_post_tv_damage);
            tvDamageLevel = itemView.findViewById(R.id.balint_franceska_experience_post_tv_damage_level);
            tvEmotionalDamage = itemView.findViewById(R.id.balint_franceska_experience_post_tv_emotional_damage);
            tvName = itemView.findViewById(R.id.balint_franceska_experience_post_tv_user);
            tvPrepared = itemView.findViewById(R.id.balint_franceska_experience_post_tv_prepared);
            clExtend = itemView.findViewById(R.id.balint_franceska_experience_post_cl_extended);
        }
    }
}

