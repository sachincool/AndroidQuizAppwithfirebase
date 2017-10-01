package luthra.harshit.androidquizappwithfirebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import luthra.harshit.androidquizappwithfirebase.Interface.ItemClickListener;
import luthra.harshit.androidquizappwithfirebase.ViewHolder.CategoryViewHolder;
import luthra.harshit.androidquizappwithfirebase.modal.Category;

import static android.support.v7.widget.AppCompatDrawableManager.get;


public class CategoryFragment extends Fragment {
    View myFragment;
RecyclerView.LayoutManager layoutManager;
FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categories;
    RecyclerView listCategory;
        public static CategoryFragment newInstance(){
            CategoryFragment categoryfrag=new CategoryFragment();
            return categoryfrag;
        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database =database.getInstance();
        categories=database.getReference("Category");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment=inflater.inflate(R.layout.fragment_category,container,false);
        listCategory=(RecyclerView)myFragment.findViewById(R.id.list_Category);
        listCategory.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);
        loadcategories();
        return myFragment;
    }

    private void loadcategories() {
        adapter=new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(Category.class,R.layout.category_layout,CategoryViewHolder.class,categories) {

            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                viewHolder.category_name.setText((model.getName()));
                Picasso.with(getActivity())
                        .load(model.getImage())
                        .into(viewHolder.category_image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(),String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }
}

