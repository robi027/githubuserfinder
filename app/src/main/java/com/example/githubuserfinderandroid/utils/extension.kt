package com.example.githubuserfinderandroid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


fun RecyclerView.listenerScroll(llm: LinearLayoutManager, listener: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (llm.itemCount - llm.findLastVisibleItemPosition() < 3) {
                listener()
            }
        }
    })
}

fun controllerLoadingLayout(loadingLayout: MutableList<View>, selectedView: View?) {
    loadingLayout.map {
        if (it == selectedView) {
            it.visibility = View.VISIBLE
        } else {
            it.visibility = View.GONE
        }
    }
}

fun EditText.listenerText(listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        private var timer: Timer = Timer()

        override fun afterTextChanged(editable: Editable?) {
            this@listenerText.removeTextChangedListener(this)

            val originValue = editable.toString()

            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    listener(originValue)
                }
            }, 1000)
            this@listenerText.addTextChangedListener(this)

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    })
}

@SuppressLint("ClickableViewAccessibility")
fun RecyclerView.hideKeyboard(context: Context) {
    this.setOnTouchListener { v, p1 ->
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
        false
    }
}