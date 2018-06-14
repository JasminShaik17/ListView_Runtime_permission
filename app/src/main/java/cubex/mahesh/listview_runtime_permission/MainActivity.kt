package cubex.mahesh.listview_runtime_permission

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {

    var lview:ListView? = null
    var path:String? = null
    var stack:Stack<String>? =null
    var list:Array<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lview = findViewById(R.id.lview)
        stack = Stack( )

        var status = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        if(status == PackageManager.PERMISSION_GRANTED){
            readFiles()
        }else{
            ActivityCompat.requestPermissions(this,
              arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    0)
        }
    }

    fun readFiles( )
    {
             path= "/storage/emulated/0/"
            var f = File(path)
            if(!f.exists()){
                path = "/storage/sdcard0/"
                f = File(path)
            }
        stack?.push(path)
            list =   f.list()
      var myAdapter = ArrayAdapter<String>(this,
              R.layout.myindiview,list)
        lview?.adapter = myAdapter
        lview?.setOnItemClickListener(
                object : AdapterView.OnItemClickListener {
                    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        path = path+list!!.get(position) +"/"
                        stack?.push(path)
                        var f_new = File(path)
                        if(f_new.isDirectory){

                             list =   f_new.list()
        var myAdapter = ArrayAdapter<String>(this@MainActivity,
                                    R.layout.myindiview,list)
                            lview?.adapter = myAdapter

                        }
                    }
                }
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            readFiles()
        }else{
   Toast.makeText(this,
           "With Out Permission , U can't continute with application",
           Toast.LENGTH_LONG).show()
        }
    }

    fun back(v:View)
    {
        if(stack?.size!! > 0) {
             stack?.pop()
            path = stack?.peek()

            var f_new = File(path)
            var list =   f_new.list()
            var myAdapter = ArrayAdapter<String>(this@MainActivity,
                    R.layout.myindiview,list)
            lview?.adapter = myAdapter
            Toast.makeText(this@MainActivity,
                    path,Toast.LENGTH_LONG).show()
        }

    }

}
