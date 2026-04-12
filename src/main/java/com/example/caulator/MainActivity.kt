package com.example.caulator

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    // Khai báo các biến ở đây để có thể dùng chung trong toàn bộ class
    private lateinit var rvProducts: RecyclerView
    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<contact>() // Dùng mutableListOf để có thể thêm/xóa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Ánh xạ các thành phần từ giao diện (XML) sang code (Kotlin)
        rvProducts = findViewById(R.id.rvProducts)
        val inputName = findViewById<TextInputEditText>(R.id.input_name)
        val inputPhone = findViewById<TextInputEditText>(R.id.input_phone)
        val btnAdd = findViewById<Button>(R.id.btn_add)

        // 2. Thêm dữ liệu mẫu ban đầu (như bạn đã làm)
        contactList.add(contact("quan", "0123456789"))

        // 3. Khởi tạo Adapter đúng chuẩn
        // Phần { position -> ... } chính là hành động Xóa khi bấm vào hình thùng rác
        adapter = ContactAdapter(contactList) { position ->
            contactList.removeAt(position) // Xóa dữ liệu khỏi danh sách
            adapter.notifyItemRemoved(position) // Báo hiệu cho giao diện xóa dòng đó đi
            adapter.notifyItemRangeChanged(position, contactList.size) // Cập nhật lại vị trí
        }

        // 4. Cài đặt RecyclerView
        rvProducts.layoutManager = LinearLayoutManager(this)
        rvProducts.adapter = adapter

        // 5. Xử lý sự kiện khi bấm nút THÊM
        btnAdd.setOnClickListener {
            val nameText = inputName.text.toString().trim()
            val phoneText = inputPhone.text.toString().trim()

            // Kiểm tra xem người dùng đã nhập đủ chưa
            if (nameText.isNotEmpty() && phoneText.isNotEmpty()) {

                // Tạo một liên lạc mới và nhét vào danh sách
                val newContact = contact(nameText, phoneText)
                contactList.add(newContact)

                // Báo cho Adapter biết vừa có người mới thêm vào ở cuối danh sách
                adapter.notifyItemInserted(contactList.size - 1)

                // Xóa chữ ở ô nhập liệu để sẵn sàng nhập người tiếp theo
                inputName.text?.clear()
                inputPhone.text?.clear()

                // Tự động cuộn màn hình xuống cuối cùng để nhìn thấy người vừa thêm
                rvProducts.scrollToPosition(contactList.size - 1)

            } else {
                // Hiển thị thông báo nhỏ nếu nhập thiếu
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}