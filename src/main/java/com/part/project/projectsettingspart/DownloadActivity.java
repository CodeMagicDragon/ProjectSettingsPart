package com.part.project.projectsettingspart;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private List<CategoryModel> result;
    private CategoryAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("cards");
        result = new ArrayList<>();
        recyclerView = findViewById(R.id.category_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new CategoryAdapter(result, DownloadActivity.this);
        recyclerView.setAdapter(adapter);
        updateList();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 0:
                break;
            case 1:
                break;
        }

        return super.onContextItemSelected(item);
    }


    private void updateList(){
        reference.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                result.add(dataSnapshot.getValue(CategoryModel.class));

                adapter = new CategoryAdapter(result, DownloadActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                CategoryModel model = dataSnapshot.getValue(CategoryModel.class);
                int index = getItemIndex(model);
                result.set(index, model);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                CategoryModel model = dataSnapshot.getValue(CategoryModel.class);
                int index = getItemIndex(model);
                result.remove(index);
                adapter.notifyItemRemoved(index);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private int getItemIndex (CategoryModel category)
    {
        int index = -1;
        for (int i = 0; i < result.size(); i++)
        {
            if (result.get(i).word.equals(category.word))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
    }
}