package com.example.fitnessmaster.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitnessmaster.R;
import com.example.fitnessmaster.databinding.ExerciseItemLayoutBinding;
import com.example.fitnessmaster.models.ExerciseItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ExerciseItem> exerciseItems;
    private final OnItemClickListener onItemClickListener;
    private final Context context;

    public ExerciseAdapter(Context context, ExerciseAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setExerciseItems(List<ExerciseItem> exerciseItems) {
        this.exerciseItems = exerciseItems;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ExerciseItem item, int position);

        void onFavouriteClick(ExerciseItem item, int position);

        void onShareItem(ExerciseItem item, int position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExerciseItemLayoutBinding binding = ExerciseItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ExerciseAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ExerciseAdapter.ViewHolder viewHolder = (ExerciseAdapter.ViewHolder) holder;
        ExerciseItem exerciseItem = exerciseItems.get(position);
        if (exerciseItem.getIsFavourite()) {
            viewHolder.binding.favouritesBtn.setImageResource(R.drawable.ic_favorite);
        } else {
            viewHolder.binding.favouritesBtn.setImageResource(R.drawable.ic_favorite_border);
        }
        viewHolder.binding.tvBodyPart.setText(exerciseItem.getBodyPart());
        viewHolder.binding.tvEquipment.setText(exerciseItem.getEquipment());
        viewHolder.binding.tvName.setText(exerciseItem.getName());
//        Picasso
//                .get()
//                .load(exerciseItem.getGifUrl())
////                .centerCrop()
//                .into(viewHolder.binding.ivExercise);
        Log.d("onBindViewHolderasdd", "onBindViewHolder: " + exerciseItem.getGifUrl());
        Glide.with(context)
                .asGif()
                .load(exerciseItem.getGifUrl())
                .placeholder(R.drawable.ic_gym)
//                .error(R.drawable.home_bg)
                .centerCrop()
                .into(viewHolder.binding.ivExercise);
        viewHolder.binding.exerciseCv.setOnClickListener(view -> onItemClickListener.onItemClick(exerciseItem, position));
        viewHolder.binding.exerciseCv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onShareItem(exerciseItem, position);
                return true;
            }
        });
        viewHolder.binding.favouritesBtn.setOnClickListener(view -> onItemClickListener.onFavouriteClick(exerciseItem, position));
    }

    @Override
    public int getItemCount() {

        return exerciseItems == null ? 0 : exerciseItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ExerciseItemLayoutBinding binding;

        public ViewHolder(@NonNull ExerciseItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
