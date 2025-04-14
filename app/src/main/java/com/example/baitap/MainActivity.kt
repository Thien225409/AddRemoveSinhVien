package com.example.baitap

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var adapter: SinhVienAdapter
    private val studentList = mutableListOf<DataSinhVien>()
    private lateinit var recycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        adapter = SinhVienAdapter(studentList)
        recycleView = findViewById(R.id.dssinhvien)
        recycleView.adapter = adapter
        recycleView.setHasFixedSize(true)
        recycleView.layoutManager = LinearLayoutManager(this)
        findViewById<View>(R.id.add).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add -> {
                val hoten = findViewById<EditText>(R.id.name).text.toString().trim()
                val mssv = findViewById<EditText>(R.id.mssv).text.toString().trim()
                when {
                    hoten.isEmpty() -> {
                        Toast.makeText(this, "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show()
                        return
                    }
                    mssv.isEmpty() -> {
                        Toast.makeText(this, "Vui lòng nhập mã số sinh viên", Toast.LENGTH_SHORT).show()
                        return
                    }
                    mssv.toIntOrNull() == null -> {
                        Toast.makeText(this, "Mã số sinh viên phải là số", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                val mssvInt = try {
                    mssv.toInt()
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Mã số sinh viên không hợp lệ", Toast.LENGTH_SHORT).show()
                    return
                }

                studentList.add(DataSinhVien(hoten, mssvInt))
                adapter.notifyItemInserted(studentList.size - 1)
                recycleView.scrollToPosition(studentList.size - 1)

                findViewById<EditText>(R.id.name).text.clear()
                findViewById<EditText>(R.id.mssv).text.clear()
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Ẩn bàn phím khi chạm ngoài EditText
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}
