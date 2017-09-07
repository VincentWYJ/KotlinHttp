package com.dylan.kotlinhttp.view

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.dylan.kotlinhttp.R
import com.dylan.kotlinhttp.ui.bindView
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.HttpVersion
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.params.BasicHttpParams
import org.apache.http.params.HttpConnectionParams
import org.apache.http.params.HttpProtocolParams
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Thread
import java.lang.Runnable
import android.os.Message
import android.util.Log
import com.dylan.kotlinhttp.Utils.Utils
import org.apache.http.protocol.HTTP

class HttpClientActivity : AppCompatActivity(){
    private val TAG: String = "HttpClientActivity"
    private val CODE: String = "code"
    private val RESULT: String = "result"
    private val mHtmlContent: TextView by bindView(R.id.html_content)

    private val mHandler = object: Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
                mHtmlContent.text = msg.data.getString(RESULT)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.http_client)

        Thread(Runnable() {
            run() {
                useHttpClientGet("http://www.baidu.com")
            }
        }).start()
    }

    private fun createHttpClient(): HttpClient{
        val mDefaultHttpParams = BasicHttpParams()
        HttpConnectionParams.setConnectionTimeout(mDefaultHttpParams, 15000)
        HttpConnectionParams.setSoTimeout(mDefaultHttpParams, 15000)
        HttpConnectionParams.setTcpNoDelay(mDefaultHttpParams, true)
        HttpProtocolParams.setVersion(mDefaultHttpParams, HttpVersion.HTTP_1_1)
//        HttpProtocolParams.setContentCharset(mDefaultHttpParams, HTTP.UTF_8)
        HttpProtocolParams.setUseExpectContinue(mDefaultHttpParams, true)
        return DefaultHttpClient(mDefaultHttpParams)
    }

    private fun useHttpClientGet(url: String) {
        val mHttpGet = HttpGet(url)
        mHttpGet.addHeader("Connection", "Keep-Alive")
        try {
            val mHttpClient: HttpClient = createHttpClient()
            val mHttpResponse: HttpResponse = mHttpClient.execute(mHttpGet)
            val mHttpEntity: HttpEntity = mHttpResponse.entity
            val mInputStream: InputStream = mHttpEntity.content
            val code = mHttpResponse.statusLine.statusCode
            val respose: String = converStreamToString(mInputStream)
//            Utils.showLog(TAG, "code: $code\nresult: $respose")
            val bundle = Bundle()
            bundle.putString(CODE, "" + code)
            bundle.putString(RESULT, respose)
            val message = Message()
            message.what = 0
            message.data = bundle
            mHandler.sendMessage(message)
            mInputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun converStreamToString(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuffer()
        var line: String? = reader.readLine()
        while (line != null) {
            sb.append(line + "\n")
            line = reader.readLine()
        }
        return sb.toString()
    }
}