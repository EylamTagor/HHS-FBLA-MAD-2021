package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.saved;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.ContextCompat.startActivity;

public class SavedRVAdapter extends RecyclerView.Adapter<SavedRVAdapter.StaticRVViewHolder> {
    private ArrayList<SavedRVModel> savedJobs;
    int row_index = -1;
    private FirebaseUser fbuser;
    private FirebaseFirestore db;

    /**
     * Initializes the fields in the adapter. Constructor
     * @param items the savedJobs items passed in that need to be displayed.
     */
    public SavedRVAdapter(ArrayList<SavedRVModel> items) {
        this.savedJobs = items;
    }

    /**
     * Interface that handles and coordinates clicking
     */
    public interface ClickListener {

        void onPositionClicked(int position);

        void onLongClicked(int position);
    }


    /**
     *
     * The ViewHolder will be used to display items of the adapter using onBindViewHolder.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType  The view type of the new View.
     * @return the view holder to be used
     */
    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_job, parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return staticRVViewHolder;
    }

    /**
     *Called by RecyclerView to display the data at the specified position.
     *This method should update the contents of the RecyclerView.ViewHolder.itemView to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        SavedRVModel currentItem = savedJobs.get(position);
        holder.link = currentItem.getLink();
        holder.title.setText(currentItem.getJobTitle());
        db = FirebaseFirestore.getInstance();
        fbuser = FirebaseAuth.getInstance().getCurrentUser();
        //placeholder, need to sort to find name of business given ID of the holder.
        db.collection("businesses").document(currentItem.getbusinessID()).get().addOnSuccessListener(documentSnapshot -> {
            Business biz = documentSnapshot.toObject(Business.class);
            holder.businessName.setText(biz.getName());
            if (biz.getLogo() != null && !biz.getLogo().equalsIgnoreCase("")) {
                Picasso.get().load(Uri.parse(biz.getLogo())).into(holder.businessLogo);
            }
            else{
                holder.businessLogo.setImageResource(R.drawable.ic_no_business_logo);
            }
        });
    }

    /**
     *
     * @return the amount of savedjob models.
     */
    @Override
    public int getItemCount() {
        return savedJobs.size();
    }


    /**
     * The ViewHolder will be used to display items.
     */
    public static class StaticRVViewHolder extends RecyclerView.ViewHolder {
        CircleImageView businessLogo;
        TextView title;
        Button apply;
        //have to sort to find
        TextView businessName;
        LinearLayout savedJobLayout;
        String link;
        /**
         *
         * Initalizes the recyclerview view holder. Links XML.
         *
         * @param savedJobView The view type to be used. View type references savedJobs item XML.
         * @return the view holder to be used
         */
        public StaticRVViewHolder(@NonNull View savedJobView) {
            super(savedJobView);
            apply = savedJobView.findViewById(R.id.saved_apply_button);
            apply.setOnClickListener(view -> {
                startActivity(view.getContext(), new Intent(Intent.ACTION_VIEW, Uri.parse(link)), null);
            });

            title = savedJobView.findViewById(R.id.saved_title);
            businessLogo = savedJobView.findViewById(R.id.saved_business_logo);
            businessName = savedJobView.findViewById(R.id.saved_business_name);
            savedJobLayout =  savedJobView.findViewById(R.id.saved_job_layout);

        }
    }
}

