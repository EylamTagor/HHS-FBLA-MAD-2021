package com.hhsfbla.hhs_fbla_mad_2021.recyclerviews.jobs;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.JobOffer;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobsRVAdapter extends RecyclerView.Adapter<JobsRVAdapter.StaticRVViewHolder> implements Filterable {
    private ArrayList<JobsRVModel> jobs;
    private ArrayList<JobsRVModel> jobsFull;
    private ArrayList<JobOffer> savedJobs;
    private User user;
    private FirebaseUser fbuser;
    private FirebaseFirestore db;

    int row_index = -1;

    /**
     * Initializes the fields in the adapter. Constructor
     * @param items the job items passed in that need to be displayed.
     */
    public JobsRVAdapter(ArrayList<JobsRVModel> items) {
        this.jobs = items;
        jobsFull = new ArrayList<>(items);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job, parent,false);
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
        JobsRVModel currentItem = jobs.get(position);
        holder.description.setText(currentItem.getJobDescription());
        holder.title.setText(currentItem.getJobTitle());

        //getting the business logo from firebase
        db.collection("businesses").document(currentItem.getbusinessID()).get().addOnSuccessListener(documentSnapshot -> {
            Business biz = documentSnapshot.toObject(Business.class);
            holder.businessName.setText(biz.getName());
            if (biz.getLogo() != null && !biz.getLogo().equalsIgnoreCase("")) {
                Picasso.get().load(Uri.parse(biz.getLogo())).into(holder.businessLogo);
            }
        });

    }


    /**
     *
     * @return the amount of job models to be displayed
     */
    @Override
    public int getItemCount() {
        return jobs.size();
    }

    /**
     *
     * @return the filter that filters the list of jobs
     */
    @Override
    public Filter getFilter() {
        return jobsFilter;
    }

    //filter
    private Filter jobsFilter = new Filter(){

        /**
         * Filters the job results based on the inputted text
         * @param charSequence the text that the user inputs, the constraint used to filter the data
         * @return the filtered results from the operation
         */
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<JobsRVModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(jobsFull);
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(JobsRVModel item : jobsFull){
                    if(item.getJobTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        /**
         * publishes the filter results, updates the list of the search results.
         * @param charSequence the constraint used to filter the data
         * @param filterResults the results of the filter.
         */
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            jobs.clear();
            jobs.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    /**
     * Removes a job. To be called once a job is saved.
     * @param position of the model in the list to be removed
     */
    public void removeAt(int position) {
        jobs.remove(position);
        jobsFull.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, jobs.size());
    }

    /**
     *
     * @return the list of saved jobs.
     */
    public ArrayList<JobOffer> getSavedJobs() {
        return savedJobs;
    }

    /**
     *
     * The ViewHolder will be used to display items of the adapter using onBindViewHolder.
     *
     */
    public class StaticRVViewHolder extends RecyclerView.ViewHolder{
        CircleImageView businessLogo;
        TextView title;
        TextView businessName;
        LinearLayout job_layout;
        TextView description;
        Button saveButton;
        private boolean isSaved;


        /**
         * Connects the fields to the XML of the jobs item. Initializes variables for display, including retrieving data from firebase.
         *
         * @param jobView the Job xml layout reference
         */
        public StaticRVViewHolder(@NonNull View jobView) {
            super(jobView);

            db = FirebaseFirestore.getInstance();
            fbuser = FirebaseAuth.getInstance().getCurrentUser();
            db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                User u = documentSnapshot.toObject(User.class);

            });
            isSaved = false;
            title = jobView.findViewById(R.id.job_title);
            businessLogo = jobView.findViewById(R.id.job_business_logo);
            businessName = jobView.findViewById(R.id.job_business_name);
            job_layout =  jobView.findViewById(R.id.job_layout);
            description = jobView.findViewById(R.id.job_description);
            saveButton = jobView.findViewById(R.id.job_save_button);
            saveButton.setOnClickListener(new View.OnClickListener() {

                /**
                 * Handles what happens a job is saved.
                 *
                 * @param view the view of the item that was clicked
                 */
                @Override
                public void onClick(View view) {
                    isSaved = true;
                    int num = getAdapterPosition();
                    JobOffer savedJob = jobsFull.get(num).getJob();
                    //remove item from list if saved
                    removeAt(num);
                    //Update firebase data.
                    db.collection("jobOffers")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(document.toObject(JobOffer.class).getBusinessID().equals(savedJob.getBusinessID())
                                                    && document.toObject(JobOffer.class).getJobTitle().equals(savedJob.getJobTitle())){
                                                db.collection("users").document(fbuser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                                                    User u = documentSnapshot.toObject(User.class);
                                                    u.addJobOffer(document.getId());
                                                    db.collection("users").document(fbuser.getUid()).set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d("SUCCESS", "DocumentSnapshot successfully written!");
                                                        }
                                                    });

                                                });
                                            }
                                        }
                                    } else {

                                    }
                                }
                            });
                    saveButton.setBackgroundResource(R.drawable.ic_saved);
                }
            });
        }
    }
}

