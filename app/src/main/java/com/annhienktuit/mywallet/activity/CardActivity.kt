package com.annhienktuit.mywallet.activity

import android.R.attr.label
import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.annhienktuit.mywallet.R
import com.annhienktuit.mywallet.`object`.Card
import com.annhienktuit.mywallet.utils.Extensions.toast
import com.annhienktuit.mywallet.utils.FirebaseUtils
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_card.*


class CardActivity : AppCompatActivity() {
    val user: FirebaseUser? = FirebaseUtils.firebaseAuth.currentUser
    var ref = FirebaseDatabase
        .getInstance("https://my-wallet-80ed7-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .getReference("datas").child(user?.uid.toString()).child("cards")
    var card: Card? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        var pos = intent.getIntExtra("position", 0)
        ref.keepSynced(true)
        getDatabase(ref.child("card" + (pos + 1)),object : OnGetDataListener{
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                card = dataSnapshot.getValue(Card::class.java)
                setData(card)
            }

            override fun onStart() {
                Log.d("khaidf", "Start getting card database")
            }

            override fun onFailure() {
                Log.d("khaidf", "Failed to get card database")
            }
        })
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        btn_back_card.setOnClickListener {
            finish()
        }
        btnCopyName.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied", card!!.namePerson.toString())
            toast("Copied to clipboard")
            clipboard.setPrimaryClip(clip)
        }
        btnCopyAccountNumber.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied", card?.accountNumber.toString())
            toast("Copied to clipboard")
            clipboard.setPrimaryClip(clip)
        }
        btnCopyCardNumber.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied", card?.cardNumber.toString())
            toast("Copied to clipboard")
            clipboard.setPrimaryClip(clip)
        }
    }
    fun setData(card: Card?) {
        numberCard.text = card?.cardNumber.toString()
        dateValid.text = card?.expiredDate.toString()
        nameCard.text = card?.namePerson.toString()
        bankingName.text = card?.bankName.toString()
        NameContent.text = card?.namePerson.toString()
        NoAccount.text = card?.accountNumber.toString()
        NoCard.text = card?.cardNumber.toString()
    }
    interface OnGetDataListener {
        fun onSuccess(dataSnapshot: DataSnapshot)
        fun onStart()
        fun onFailure()
    }
    fun getDatabase(ref: DatabaseReference?, listener: OnGetDataListener?) {
        listener?.onStart()
        ref?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listener?.onSuccess(snapshot)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("khaidf", "get database error")
                listener?.onFailure()
            }
        })
    }
}