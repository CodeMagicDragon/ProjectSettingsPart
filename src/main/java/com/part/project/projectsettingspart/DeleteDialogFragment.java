package com.part.project.projectsettingspart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Delete;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.part.project.projectsettingspart.model.Card;
import com.part.project.projectsettingspart.model.CardDao;

public class DeleteDialogFragment extends DialogFragment
{
    SharedPreferences sp;
    boolean deleteSet;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        sp = (getActivity().getApplicationContext()).getSharedPreferences("edit_card_sp", Context.MODE_PRIVATE);
        deleteSet = sp.getBoolean("deleted_element", false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (deleteSet)
        {
            builder.setTitle("Удаление сета");
            builder.setMessage("Вы уверены, что хотите удалить сет?");
        }
        else
        {
            builder.setTitle("Удаление карты");
            builder.setMessage("Вы уверены, что хотите удалить карту?");
        }
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                // delete set / card
                CardDao cd = App.getInstance().getAppDatabase().getCardDao();
                ((DeleteDialogAbstractActivity)getActivity()).onOkButtonClick();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
