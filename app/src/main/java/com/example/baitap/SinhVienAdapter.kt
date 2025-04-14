package com.example.baitap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SinhVienAdapter(val studentList: MutableList<DataSinhVien>) : RecyclerView.Adapter<SinhVienAdapter.ListSinhVienViewHolder>() {
    class ListSinhVienViewHolder(studentView: View) : RecyclerView.ViewHolder(studentView) {
        val hoten: TextView = studentView.findViewById(R.id.htsinhvien)
        val mssv: TextView = studentView.findViewById(R.id.mssinhvien)
        val checkbox: CheckBox = studentView.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSinhVienViewHolder {
        val studentView = LayoutInflater.from(parent.context).inflate(R.layout.item_sinh_vien, parent, false)
        return ListSinhVienViewHolder(studentView)
    }

    override fun onBindViewHolder(holder: ListSinhVienViewHolder, position: Int) {
        val student = studentList[position]
        holder.hoten.text = student.hoten
        holder.mssv.text = student.maso.toString()

        holder.checkbox.isChecked = false
        // Xóa listener cũ để tránh chồng chéo
        holder.checkbox.setOnCheckedChangeListener(null)
        // Gán listener mới với absoluteAdapterPosition
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                removeItem(position)
            }
        }
    }

    override fun getItemCount() = studentList.size

    private fun removeItem(position: Int) {
        studentList.removeAt(position)
        notifyItemRemoved(position)
        // Cập nhật các item còn lại sau vị trí bị xóa
        notifyItemRangeChanged(position, studentList.size)
    }
}