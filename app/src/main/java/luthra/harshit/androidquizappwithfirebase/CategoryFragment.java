package luthra.harshit.androidquizappwithfirebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CategoryFragment extends Fragment {
    View myFragment;
RecyclerView.LayoutManager layoutManager;

    RecyclerView listCategory;
        public static CategoryFragment newInstance(){
            CategoryFragment categoryfrag=new CategoryFragment();
            return categoryfrag;
        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment=inflater.inflate(R.layout.fragment_category,container,false);
        return myFragment;
    }
}

