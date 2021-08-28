package com.bd.deliverytiger.phnsmsreader

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.bd.deliverytiger.phnsmsreader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        checkPermission()

    }

    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
            imageRead()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS), 111)
        }

    }

    private fun imageRead() {

        // public static final String INBOX = "content://sms/inbox";
        // public static final String SENT = "content://sms/sent";
        // public static final String DRAFT = "content://sms/draft";
        // public static final String INBOX = "content://sms/inbox";
        // public static final String SENT = "content://sms/sent";
        // public static final String DRAFT = "content://sms/draft";
        val cursor: Cursor? =
            contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
        var msgData = ""
        if (cursor!!.moveToFirst()) { // must check the result to prevent exception
            do {
                for (index in 0 until cursor.getColumnCount()) {
                    msgData += "\n $index" + cursor.getColumnName(index).toString() + ":" + cursor.getString(index)
                }
                // use msgData
            } while (cursor.moveToNext())
        } else {
            // empty box, no SMS
        }

        binding?.showMsg?.text = msgData
    }
}