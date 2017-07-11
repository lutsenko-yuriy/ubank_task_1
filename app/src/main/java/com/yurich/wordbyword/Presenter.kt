package com.yurich.wordbyword

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * Created by yurich on 11.07.17.
 */
object Presenter {

    lateinit var view: StringDisplayerView

    var jobDone = false

    fun wordsWithTimeout(string: String, timeInMillis: Long = 100): Disposable {

        val words = string.split(" ")
        return Observable.fromIterable(words)
                .concatMap {
                    Observable.zip(
                            Observable.just(it, " "),
                            Observable.interval(0, it.length * timeInMillis, TimeUnit.MILLISECONDS),
                            BiFunction<String, Long, String> {
                                word, long -> word
                            }
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.updateString(it)
                        },
                        {},
                        {
                            jobDone = true
                        }
                )
    }

}