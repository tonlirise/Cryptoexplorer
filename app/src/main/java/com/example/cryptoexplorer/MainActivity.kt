package com.example.cryptoexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cryptoexplorer.api.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable = ApiFactory.apiService.getTopCoinInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                       Log.d("TEST_OF_LOADING_DATA", it.toString())
            },{
                Log.d("TEST_OF_LOADING_DATA", it?.message?:"no message")
            })
        compositeDisposable.add(disposable)

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}