package com.example.fitnessmaster.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitnessmaster.Constants;
import com.example.fitnessmaster.R;
import com.example.fitnessmaster.databinding.CategoryItemLayoutBinding;
import com.example.fitnessmaster.models.CategoryItem;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<CategoryItem> categoryItems = List.of(new CategoryItem("All", "https://static.vecteezy.com/system/resources/previews/040/322/315/non_2x/ai-generated-human-muscle-structure-isolated-on-transparent-background-free-png.png", Constants.SLUG_ALL), new CategoryItem("Neck", "https://png.pngtree.com/png-vector/20240724/ourmid/pngtree-3d-a-man-couldnot-bear-neck-pain-on-transparent-background-png-image_13230983.png", Constants.SLUG_NECK), new CategoryItem("Waist", "https://media.alaskapublic.org/wp-content/uploads/2024/07/Lower-back-pain-600x600-1.png", Constants.SLUG_WAIST), new CategoryItem("Shoulders", "https://img.lovepik.com/free-png/20220126/lovepik-pectoralis-png-image_401795990_wh1200.png", Constants.SLUG_SHOULDERS), new CategoryItem("Chest", "https://i.pinimg.com/originals/75/c1/23/75c123dc30439199362e5801d05375eb.png", Constants.SLUG_CHEST), new CategoryItem("Cardio", "https://png.pngtree.com/png-clipart/20240731/original/pngtree-a-vector-illustration-of-the-human-heart-and-its-anatomy-on-png-image_15671383.png", Constants.SLUG_CARDIO), new CategoryItem("Back", "https://static.vecteezy.com/system/resources/previews/035/647/720/non_2x/ai-generated-human-muscle-structure-isolated-on-transparent-background-free-png.png", Constants.SLUG_BACK));
    private final OnItemClickListener onItemClickListener;

    public CategoryAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryItem item, int position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryItemLayoutBinding binding = CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        CategoryItem categoryItem = categoryItems.get(position);
        viewHolder.binding.tvCategoryName.setText(categoryItem.getTitle());
        Glide.with(viewHolder.binding.getRoot().getContext())
                .load(categoryItem.getImage())
                .placeholder(R.drawable.ic_gym)
                .centerCrop()
                .into(viewHolder.binding.ivCategory);
        viewHolder.binding.categoryCv.setOnClickListener(view -> {
            onItemClickListener.onItemClick(categoryItem, position);
        });
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CategoryItemLayoutBinding binding;

        public ViewHolder(@NonNull CategoryItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
