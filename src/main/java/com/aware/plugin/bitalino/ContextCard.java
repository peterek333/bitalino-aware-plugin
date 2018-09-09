package com.aware.plugin.bitalino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.aware.utils.IContextCard;

public class ContextCard implements IContextCard {

    //Constructor used to instantiate this card
    public ContextCard() {
    }

    @Override
    public View getContextCard(Context context) {
        //Load card layout
        View card = LayoutInflater.from(context).inflate(R.layout.card, null);

        //Return the card to AWARE/apps
        return card;
    }
}
