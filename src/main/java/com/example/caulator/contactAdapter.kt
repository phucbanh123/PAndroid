package com.example.caulator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class ContactAdapter(
    private val contacts: MutableList<contact>, // Nên dùng MutableList để sau này có thể thêm/xóa được
    private val onItemClick: (Int) -> Unit      // Mình sửa lại lỗi gõ 'onTtemClick'. Truyền Int (vị trí) để dễ xóa hơn
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
    }

    // 2. Bơm giao diện (inflate): Lấy file item_contact.xml gán vào cho mỗi dòng
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_item, parent, false)
        return ViewHolder(view)
    }

    // 3. Đổ dữ liệu: Lấy dữ liệu từ danh sách Contacts và nhét vào giao diện
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentContact = contacts[position]

        // Gắn chữ
        holder.tvName.text = currentContact.name
        holder.tvPhone.text = currentContact.phone

        // Bắt sự kiện click vào nút Thùng Rác
        holder.imgDelete.setOnClickListener {
            onItemClick(position) // Trả về vị trí để MainActivity biết dòng nào bị xóa
        }
    }

    // 4. Cho RecyclerView biết danh sách có tổng cộng bao nhiêu dòng
    override fun getItemCount(): Int {
        return contacts.size
    }
}