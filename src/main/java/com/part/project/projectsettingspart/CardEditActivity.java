package com.part.project.projectsettingspart;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.part.project.projectsettingspart.model.Card;
import com.part.project.projectsettingspart.model.CardDao;

public class CardEditActivity extends AppCompatActivity
{
    EditText cardName;
    EditText cardFirstText;
    EditText cardSecondText;
    Button okButton;
    Button cancelButton;
    String setName;
    int buttonId;
    CardDao cd;
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_edit);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        cardName = findViewById(R.id.edit_card_name);
        cardFirstText = findViewById(R.id.edit_card_first_text);
        cardSecondText = findViewById(R.id.edit_card_second_text);
        okButton = findViewById(R.id.edit_card_ok_button);
        cancelButton = findViewById(R.id.edit_card_cancel_button);
        buttonId = (getIntent()).getIntExtra("card_id", -1);
        setName = (getIntent()).getStringExtra("set_name");
        cd = App.getInstance().getAppDatabase().getCardDao();
        if (buttonId == -1)
        {
            card = new Card();
        }
        else
        {
            card = App.getInstance().getAppDatabase().getCardDao().getById(buttonId);
            cardName.setText(card.name);
            cardFirstText.setText(card.firstText);
            cardSecondText.setText(card.secondText);
        }
        // set text
        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                card.name = cardName.getText().toString();
                Card c = cd.getByName(card.name);
                if (c == null || c.id == buttonId)
                {
                    card.firstText = cardFirstText.getText().toString();
                    card.secondText = cardSecondText.getText().toString();
                    card.setName = setName;
                    if (buttonId == -1)
                    {
                        cd.insert(card);
                    }
                    else
                    {
                        cd.update(card);
                    }
                    finish();
                }
                else
                {
                    Toast.makeText(CardEditActivity.this, "Имя карты недопустимо", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
    }
}
