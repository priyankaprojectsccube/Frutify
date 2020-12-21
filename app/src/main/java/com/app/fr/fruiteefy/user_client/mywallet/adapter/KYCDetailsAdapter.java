package com.app.fr.fruiteefy.user_client.mywallet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.PositionInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class KYCDetailsAdapter extends RecyclerView.Adapter<KYCDetailsAdapter.MyViewHolder> {
    ArrayList<HashMap<String, String>> kcdetailslist;
    Context mctx;


    public KYCDetailsAdapter(ArrayList<HashMap<String, String>> kcdetailslist, Context mctx) {
        this.kcdetailslist = kcdetailslist;
        this.mctx = mctx;

    }

    @NonNull
    @Override
    public KYCDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_kycdetails, parent, false);

        return new KYCDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KYCDetailsAdapter.MyViewHolder holder, int position) {
        final HashMap<String, String> items = kcdetailslist.get(position);
        Log.d("documentid",items.get("Id"));
        holder.documentid.setText(items.get("Id"));

        Log.d("type",items.get("Type"));


        if(items.get("Type").equalsIgnoreCase("IDENTITY_PROOF")){
            holder.document_type.setText("Preuve d'identité (Passeport, Permis,CNI)");
        }
         else if(items.get("Type").equalsIgnoreCase("REGISTRATION_PROOF")){
            holder.document_type.setText("Preuve d’enregistrement (Kbis/Journal officiel/déclaration URSSAF ou SIRENE)");
        }
        else if(items.get("Type").equalsIgnoreCase("ARTICLES_OF_ASSOCIATION")){
            holder.document_type.setText("Statuts complets, à jour et signés");
        }
        else if(items.get("Type").equalsIgnoreCase("SHAREHOLDER_DECLARATION")){
            holder.document_type.setText("Déclaration d’actionnaire");
        }
        else if(items.get("Type").equalsIgnoreCase("ADDRESS_PROOF")){
            holder.document_type.setText("Preuve d’adresse (Facture EDF,...)");
        }

        if (items.get("Status").equalsIgnoreCase("CREATED")) {
            holder.kycstatus.setText("En attente de validation");

        }
        else if(items.get("Status").equalsIgnoreCase("VALIDATION_ASKED"))
        {
            holder.kycstatus.setText("En attente de validation");
        }
        else if(items.get("Status").equalsIgnoreCase("REFUSED"))
        {
            holder.kycstatus.setText("Refusé");
        }
        else if(items.get("Status").equalsIgnoreCase("OUT_OF_DATE"))
        {
            holder.kycstatus.setText("Refusé");
        }
        else if(items.get("Status").equalsIgnoreCase("VALIDATED")){
            holder.kycstatus.setText("Validé");
        }


    }

    @Override
    public int getItemCount() {

        return kcdetailslist.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView documentid, document_type, kycstatus;

        public MyViewHolder(View view) {
            super(view);

            documentid = view.findViewById(R.id.documentid);
            document_type = view.findViewById(R.id.document_type);
            kycstatus = view.findViewById(R.id.kycstatus);

        }
    }


}

