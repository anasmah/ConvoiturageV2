package com.example.softandroid.convoituragev2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softandroid.convoituragev2.Listener.VolleyCallBack;
import com.example.softandroid.convoituragev2.entity.Couvoiturage;
import com.example.softandroid.convoituragev2.entity.Personne;
import com.example.softandroid.convoituragev2.service.Services;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mac on 14/01/2017.
 */
public class CouvoiturageArrayAdapter extends ArrayAdapter<Couvoiturage> {

    private Services services =null;
    private Personne personne = null;

    public CouvoiturageArrayAdapter(Context context, List<Couvoiturage> objects, Services services, Personne personne) {
        super(context,R.layout.resultat_recherche, objects);
        this.services =services;
        this.personne =personne;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Couvoiturage c = getItem(position);
        LinearLayout linearLayout = null;
        if(convertView == null){
            linearLayout = new LinearLayout(getContext());
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layoutInflater.inflate(R.layout.resultat_recherche,linearLayout,true);
        }else{
            linearLayout = (LinearLayout)convertView;
        }
        TextView nomPublier = (TextView)linearLayout.findViewById(R.id.nomPublier);
        nomPublier.setText(c.getPublier().getNom());
        TextView prenomPublier = (TextView)linearLayout.findViewById(R.id.prenomPublier);
        prenomPublier.setText(c.getPublier().getPrenom());

        TextView villeD = (TextView)linearLayout.findViewById(R.id.villeD);
        villeD.setText(c.getVilleD());
        TextView villeA = (TextView)linearLayout.findViewById(R.id.villeA);
        villeA.setText(c.getVilleA());

        TextView date = (TextView)linearLayout.findViewById(R.id.date);
        date.setText( new SimpleDateFormat("dd/MM/yyyy HH:mm").format(c.getDate()));

        TextView voiture = (TextView)linearLayout.findViewById(R.id.voiture);
        voiture.setText(c.getMarque());

        TextView nbPlaces = (TextView)linearLayout.findViewById(R.id.nbPlaces);
        nbPlaces.setText(String.valueOf(c.getPlace()));

        TextView message = (TextView)linearLayout.findViewById(R.id.message);
        message.setText(c.getMessage());

        Button button = (Button)linearLayout.findViewById(R.id.insrire);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               services.insrireCouvoiturage(personne, c, new VolleyCallBack<Boolean>() {
                   @Override
                   public void sameOpperation() {

                   }

                   @Override
                   public void onSuccess(Boolean result) {
                       Toast.makeText(getContext(),"Votre inscription a été prise en charge",Toast.LENGTH_LONG).show();
                   }

                   @Override
                   public void errorNetwork() {
                       Toast.makeText(getContext(),"Erreur de connexion",Toast.LENGTH_LONG).show();
                   }
               });
           }
       });

        return linearLayout;
    }

}
