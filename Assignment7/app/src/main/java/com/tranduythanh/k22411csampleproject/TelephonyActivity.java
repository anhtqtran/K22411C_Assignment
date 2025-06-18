package com.tranduythanh.k22411csampleproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.adapters.TelephonyInforAdapter;
import com.tranduythanh.models.TelephonyInfor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TelephonyActivity extends AppCompatActivity {

    ListView lvTelephony;
    TelephonyInforAdapter adapter;
    ArrayList<TelephonyInfor> allContacts = new ArrayList<>();

    // Các đầu số của nhà mạng
    final String[] viettelPrefixes = {"086", "096", "097", "098", "032", "033", "034", "035", "036", "037", "038", "039"};
    final String[] mobifonePrefixes = {"089", "090", "093", "070", "079", "077", "076", "078"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephony);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        getAllContacts();
        addEvents();
    }

    private void addEvents() {
        lvTelephony.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TelephonyInfor ti = adapter.getItem(i);
                makeAPhoneCall(ti);
            }
        });
    }

    private void makeAPhoneCall(TelephonyInfor ti) {
        Uri uri = Uri.parse("tel:" + ti.getPhone());
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(uri);
        startActivity(intent);
    }

    private void getAllContacts() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        allContacts.clear();

        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(nameIndex); //Get Name
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String phone = cursor.getString(phoneIndex); //Get Phone Number

            TelephonyInfor ti = new TelephonyInfor();
            ti.setName(name);
            ti.setPhone(phone);
            allContacts.add(ti);
        }
        cursor.close();
        adapter.clear();
        adapter.addAll(allContacts);
    }

    private void addViews() {
        lvTelephony = findViewById(R.id.lvTelephonyInfor);
        adapter = new TelephonyInforAdapter(this, R.layout.item_telephony_infor);
        lvTelephony.setAdapter(adapter);
    }

    public void directCall(TelephonyInfor ti) {
        Uri uri = Uri.parse("tel:" + ti.getPhone());
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(uri);
        startActivity(intent);
    }

    public void dialupCall(TelephonyInfor ti) {
        Uri uri = Uri.parse("tel:" + ti.getPhone());
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.telephony_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnu_viettel) {
            filterContacts(viettelPrefixes);
        } else if (item.getItemId() == R.id.mnu_mobifone) {
            filterContacts(mobifonePrefixes);
        } else if (item.getItemId() == R.id.mnu_other_networks) {
            filterOtherContacts();
        } else if (item.getItemId() == R.id.mnu_all) {
            adapter.clear();
            adapter.addAll(allContacts);
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterContacts(String[] prefixes) {
        ArrayList<TelephonyInfor> filteredList = new ArrayList<>();
        for (TelephonyInfor contact : allContacts) {
            for (String prefix : prefixes) {
                if (contact.getPhone().startsWith(prefix)) {
                    filteredList.add(contact);
                    break;
                }
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
    }

    private void filterOtherContacts() {
        ArrayList<TelephonyInfor> filteredList = new ArrayList<>();
        List<String> viettelList = Arrays.asList(viettelPrefixes);
        List<String> mobifoneList = Arrays.asList(mobifonePrefixes);

        for (TelephonyInfor contact : allContacts) {
            boolean isViettel = false;
            for(String prefix : viettelList) {
                if(contact.getPhone().startsWith(prefix)) {
                    isViettel = true;
                    break;
                }
            }
            if(isViettel) continue;

            boolean isMobifone = false;
            for(String prefix : mobifoneList) {
                if(contact.getPhone().startsWith(prefix)) {
                    isMobifone = true;
                    break;
                }
            }
            if(!isMobifone) {
                filteredList.add(contact);
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
    }
}