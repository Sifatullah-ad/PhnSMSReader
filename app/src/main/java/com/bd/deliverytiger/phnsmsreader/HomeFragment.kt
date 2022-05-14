package com.bd.deliverytiger.phnsmsreader

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.bd.deliverytiger.phnsmsreader.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentHomeBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermission()

    }

    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
            imageRead()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_SMS), 111)
        }

    }

    private fun imageRead() {

        // public static final String INBOX = "content://sms/inbox";
        // public static final String SENT = "content://sms/sent";
        // public static final String DRAFT = "content://sms/draft";
        // public static final String INBOX = "content://sms/inbox";
        // public static final String SENT = "content://sms/sent";
        // public static final String DRAFT = "content://sms/draft";
        val resolver = requireActivity().contentResolver
        val cursor: Cursor? =
            resolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
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

        Log.d("dataDebug", "$msgData")
        binding?.showMsg?.text = msgData
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}