package com.khtn.mangashare.profile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.adapter.ReportImageAdapter
import com.khtn.mangashare.model.picItem
import com.khtn.mangashare.model.transactionItem
import com.khtn.mangashare.profile.adapter.TransactionAdapter
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.activity_view_report.*

class TransactionActivity : AppCompatActivity() {
    var items= ArrayList<transactionItem>()
    lateinit var adapter: TransactionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        setupRV()

        backPressTransaction.setOnClickListener {
            finish()
        }
    }

    private fun setupRV() {
        items.add(transactionItem("23.000 VND","100"))
        items.add(transactionItem("47.000 VND","200"))
        items.add(transactionItem("118.000 VND","500"))
        items.add(transactionItem("235.000 VND","1.000"))
        items.add(transactionItem("454.000 VND","2.000"))
        items.add(transactionItem("685.000 VND","3.000"))
        items.add(transactionItem("1.150.000 VND","5.000"))
        items.add(transactionItem("2.300.000 VND","10.000"))
        adapter = TransactionAdapter(items)
        transactionRV.adapter=adapter
        transactionRV.layoutManager=LinearLayoutManager(this)
    }
}