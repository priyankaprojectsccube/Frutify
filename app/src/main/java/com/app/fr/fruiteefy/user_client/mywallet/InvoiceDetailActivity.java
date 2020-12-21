package com.app.fr.fruiteefy.user_client.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Invoice;
import com.app.fr.fruiteefy.user_client.mywallet.adapter.InvoicerecAdapter;

public class InvoiceDetailActivity extends AppCompatActivity {

    TextView addr,selleraddress,invoiceno,orderdate,orderno,subtotal, shipping_cost,promocodereduction,total,servicecharge;

    RecyclerView recvewdata;
    ImageView backimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);

        addr=findViewById(R.id.addr);
        backimg=findViewById(R.id.backimg);
        recvewdata=findViewById(R.id.recvewdata);
        selleraddress=findViewById(R.id.selleraddress);
        invoiceno=findViewById(R.id.invoiceno);
        orderdate=findViewById(R.id.orderdate);
        orderno=findViewById(R.id.orderno);
        servicecharge=findViewById(R.id.servicecharge);
        subtotal=findViewById(R.id.subtotal);
        shipping_cost=findViewById(R.id.shippingcost);
        promocodereduction=findViewById(R.id.promocodereduction);
        total=findViewById(R.id.total);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getIntent().hasExtra("data")){


            Invoice invoice= (Invoice) getIntent().getSerializableExtra("data");
            addr.setText(invoice.getAddress());
            selleraddress.setText(invoice.getSelleraddress());
            invoiceno.setText(getResources().getString(R.string.invoiceno)+" "+invoice.getOrderid());
            orderdate.setText(getResources().getString(R.string.orderdate)+" "+invoice.getOrderdate());
            orderno.setText(getResources().getString(R.string.orderno)+" "+invoice.getOrderno());
            servicecharge.setText(invoice.getServicecharge()+" "+getResources().getString(R.string.currency));
            subtotal.setText(invoice.getSubtotal()+" "+getResources().getString(R.string.currency));
            shipping_cost.setText(invoice.getShippingcost()+" "+getResources().getString(R.string.currency));
            promocodereduction.setText(invoice.getPromocode()+" "+getResources().getString(R.string.currency));
            total.setText(invoice.getFinaltotal()+" "+getResources().getString(R.string.currency));


            Log.d("dasdasdsdsad", String.valueOf(invoice.getmArrSubList().size()));



            InvoicerecAdapter adapter=new InvoicerecAdapter(invoice.getmArrSubList(),InvoiceDetailActivity.this);
            recvewdata.setLayoutManager(new LinearLayoutManager(InvoiceDetailActivity.this));
            recvewdata.setAdapter(adapter);


        }
    }
}
