package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.part.project.projectsettingspart.model.Card;

import java.util.HashSet;
import java.util.Set;

public class CardListEditActivity extends AppCompatActivity
{
    ListView cardList;
    Button createCardButton;
    Button okButton;
    Button cancelButton;
    EditText textSetName;
    Card[] cardSet;
    Set<Integer> baseCardNames;
    String setName;
    String baseSetName;
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    boolean firstResume;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list_edit);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        cardList = findViewById(R.id.card_list);
        createCardButton = findViewById(R.id.create_card_button);
        okButton = findViewById(R.id.set_ok_button);
        cancelButton = findViewById(R.id.set_cancel_button);
        textSetName = findViewById(R.id.edit_card_set_name);
        setName = getIntent().getStringExtra("set_name");
        baseCardNames = new HashSet<>();
        firstResume = true;
        baseSetName = setName;
        textSetName.setText(setName);
        getSupportActionBar().hide();
        sp = (getApplicationContext()).getSharedPreferences("edit_card_sp", Context.MODE_PRIVATE);
        spEditor = sp.edit();
        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id)
            {
                intent = new Intent(CardListEditActivity.this, CardEditActivity.class);
                intent.putExtra("card_id", cardSet[p].id);
                intent.putExtra("set_name", baseSetName);
                startActivity(intent);
            }
        });
        createCardButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(CardListEditActivity.this, CardEditActivity.class);
                intent.putExtra("card_id", -1);
                intent.putExtra("set_name", baseSetName);
                startActivity(intent);
            }
        });
        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setName = textSetName.getText().toString();
                //setName += "(user)";
                Card[] c = App.getInstance().getAppDatabase().getCardDao().getBySetName(setName);
                if (!setName.equals("") && (c.equals(null) || c.length == 0 || setName.equals(baseSetName))) // ??
                {
                    for (int i = 0; i < cardSet.length; i++)
                    {
                        cardSet[i] = App.getInstance().getAppDatabase().getCardDao().getById(cardSet[i].id);
                        cardSet[i].setName = setName;
                        App.getInstance().getAppDatabase().getCardDao().update(cardSet[i]);
                    }
                    finish();
                }
                else
                {
                    Toast.makeText(CardListEditActivity.this, "Имя сета недопустимо", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < cardSet.length; i++)
                {
                    if (!baseCardNames.contains(cardSet[i].id))
                    {
                        cardSet[i] = App.getInstance().getAppDatabase().getCardDao().getById(cardSet[i].id);
                        cardSet[i].setName = baseSetName;
                        App.getInstance().getAppDatabase().getCardDao().delete(cardSet[i]);
                    }
                }
                finish();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
        cardSet = App.getInstance().getAppDatabase().getCardDao().getBySetName(baseSetName);
        String[] cardNames = new String[cardSet.length];
        for (int i = 0; i < cardSet.length; i++)
        {
            cardNames[i] = cardSet[i].name;
            if (firstResume)
            {
                baseCardNames.add(cardSet[i].id);
            }
        }
        cardList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cardNames));
        firstResume = false;
        // get card set and set adapter
    }
}
