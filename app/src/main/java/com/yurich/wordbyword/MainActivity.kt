package com.yurich.wordbyword

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), StringDisplayerView {
    @SuppressLint("SetTextI18n")
    override fun updateString(word: String?) {
        displayed.text = "${displayed.text.toString()}$word"
    }

    val composite = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayed.text = ""
        Presenter.view = this
    }

    override fun onStart() {
        super.onStart()
        composite.add(Presenter.wordsWithTimeout(getString(R.string.test_text)))
    }

    override fun onStop() {
        super.onStop()
        composite.dispose()
    }
}

