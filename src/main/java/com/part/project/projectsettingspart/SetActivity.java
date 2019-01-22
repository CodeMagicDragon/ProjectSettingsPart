package com.part.project.projectsettingspart;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import com.part.project.projectsettingspart.model.Card;
import com.part.project.projectsettingspart.model.CardDao;
import com.part.project.projectsettingspart.model.SetActions;

public class SetActivity extends DeleteDialogAbstractActivity
{
    ListView setList;
    Button buttonAdd;
    Toolbar actionToolbar;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    String[] setNames;
    String deletedSetName;
    CardDao cd;
    Intent intent;
    boolean editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        setList = findViewById(R.id.set_edit_list);
        buttonAdd = findViewById(R.id.set_add_button);
        sp = getApplicationContext().getSharedPreferences("edit_card_sp", Context.MODE_PRIVATE);
        cd = App.getInstance().getAppDatabase().getCardDao();
        spEditor = sp.edit();
        deletedSetName = "";
        editMode = true;
        setTitle("Сеты");
        //actionToolbar = findViewById(R.id.edit_toolbar);
        setList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id)
            {
                if (editMode)
                {
                    intent = new Intent(SetActivity.this, CardListEditActivity.class);
                    intent.putExtra("set_name", setNames[p]);
                    startActivity(intent);
                }
                else
                {
                    spEditor.putBoolean("deleted_element", true);
                    spEditor.apply();
                    deletedSetName = setNames[p];
                    DeleteDialogFragment delDialog = new DeleteDialogFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    delDialog.show(ft, "dialog");
                }
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(SetActivity.this, CardListEditActivity.class);
                intent.putExtra("set_name", "");
                startActivity(intent);
            }
        });
    }

    public void renewAdapter()
    {
        setNames = (new SetActions()).loadSetNames();
        setList.setAdapter(new ArrayAdapter<String>(this, R.layout.app_list_item, setNames));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
        renewAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.menu_active_mode_item:
                if (item.getTitle().equals(getResources().getString(R.string.edit)))
                {
                    item.setTitle(getResources().getString(R.string.del));
                }
                else
                {
                    item.setTitle(getResources().getString(R.string.edit));
                }
                editMode = !editMode;
                break;
            /*case R.id.menu_edit_mode_item:
                activeMode.setTitle("EDIT");
                editMode = true;
                break;
            case R.id.menu_delete_mode_item:
                activeMode.setTitle("DELETE");
                editMode = false;
                break;*/
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onOkButtonClick()
    {
        Card[] cards = cd.getBySetName(deletedSetName);
        for (int i = 0; i < cards.length; i++)
        {
            cd.delete(cards[i]);
        }
        renewAdapter();
    }
}
