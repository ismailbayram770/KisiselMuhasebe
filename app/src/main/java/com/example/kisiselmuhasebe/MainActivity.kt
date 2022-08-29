package com.example.kisiselmuhasebe

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kisiselmuhasebe.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    lateinit var manager: NotificationManager
    lateinit var channel: NotificationChannel
    lateinit var notificationBuilder: Notification.Builder

    private val channelId: String = "com.tutorials.localnotification"
    private val description: String = "Notification Sample Description"
    private var notoficationId = 1001
    private val requestCode = 1002


    public var ToplamGelir=0
    public var ToplamGider=0
    public var Kalan=0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_alinanborclar,R.id.nav_verilenborclar
               ,R.id.nav_grafikler,R.id.nav_kullanicibilgileri
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        var tarihtxt=findViewById<TextView>(R.id.textView)
        var toplamgelirtxt=findViewById<TextView>(R.id.textView2)
        var toplamgidertxt=findViewById<TextView>(R.id.textView3)
        var kalantxt=findViewById<TextView>(R.id.textView4)



        val currentDate= LocalDate.now().month
        tarihtxt.text=currentDate.toString()

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate2 = sdf.format(Date())


        try
        {
            val veritabani=this.openOrCreateDatabase("KisiselMuhasebe",Context.MODE_PRIVATE,null)
            veritabani.execSQL("CREATE TABLE IF NOT EXISTS KullaniciBilgileri(id INTEGER PRIMARY KEY,isim VARCHAR,soyisim VARCHAR,email VARCAHR,telefon VARCHAR)")
            veritabani.execSQL("CREATE TABLE IF NOT EXISTS GelirBilgileri(id INTEGER PRIMARY KEY,GelirMiktar DOUBLE,GelirKategori VARCHAR,GelirTarih VARCHAR)")
            veritabani.execSQL("CREATE TABLE IF NOT EXISTS GiderBilgileri(id INTEGER PRIMARY KEY,GiderMiktar DOUBLE,GiderKategori VARCHAR,GiderTarih VARCHAR,GiderGun VARVHAR,GiderAy VARCHAR,GiderYıl VARCHAR)")
            veritabani.execSQL("CREATE TABLE IF NOT EXISTS AlinanBorcBilgileri(id INTEGER PRIMARY KEY,AlinanBorcMiktar DOUBLE,AlinanBorcAlmaTarihi VARCHAR,AlinanBorcVermeTarihi VARCHAR)")
            veritabani.execSQL("CREATE TABLE IF NOT EXISTS VerilenBorcBilgileri(id INTEGER PRIMARY KEY,VerilenBorcMiktar DOUBLE,VerilenBorcVermeTarihi VARCHAR,VerilenBorcAlmaTarihi VARCHAR)")




            val cursor=veritabani.rawQuery("SELECT * FROM GelirBilgileri",null)
            val gelirmiktarColumnIndex=cursor.getColumnIndex("GelirMiktar")
            val gelirkategoriColumnIndex=cursor.getColumnIndex("GelirKategori")
            val gelirtarihColumnIndex=cursor.getColumnIndex("GelirTarih")

            while (cursor.moveToNext())
            {
                ToplamGelir=ToplamGelir+cursor.getInt(gelirmiktarColumnIndex)
            }
            cursor.close()

            val cursor4=veritabani.rawQuery("SELECT * FROM AlinanBorcBilgileri",null)
            val alinanborcmiktarColumnIndex=cursor4.getColumnIndex("AlinanBorcMiktar")
            val alinanborctarihColumnIndex=cursor4.getColumnIndex("AlinanBorcVermeTarihi")
            var alinanborcsayacı=0
            while (cursor4.moveToNext())
            {
                ToplamGelir = ToplamGelir + cursor4.getInt(alinanborcmiktarColumnIndex)
                if(currentDate2.toString()==cursor4.getString(alinanborctarihColumnIndex))
                {
                    alinanborcsayacı++
                }
            }
            cursor4.close()
            if(alinanborcsayacı!=0)
            {
                BildirimOlusturma("Alinan borcun verme zamanı geldi")
            }

            toplamgelirtxt.text="Toplam Gelir: ${ToplamGelir} "


            val cursor2=veritabani.rawQuery("SELECT * FROM GiderBilgileri",null)
            val gidermiktarColumnIndex=cursor2.getColumnIndex("GiderMiktar")

            while (cursor2.moveToNext())
            {
                ToplamGider = ToplamGider + cursor2.getInt(gidermiktarColumnIndex)
            }
            cursor2.close()

            val cursor5=veritabani.rawQuery("SELECT * FROM VerilenBorcBilgileri",null)
            val verilenborcmiktarColumnIndex=cursor5.getColumnIndex("VerilenBorcMiktar")
            val verilenborctarihColumnIndex=cursor5.getColumnIndex("VerilenBorcAlmaTarihi")
            var verilenborcsayacı=0
            while (cursor5.moveToNext())
            {
                ToplamGider = ToplamGider + cursor5.getInt(verilenborcmiktarColumnIndex)

                if(currentDate2.toString()==cursor5.getString(verilenborctarihColumnIndex))
                {
                    verilenborcsayacı++
                }
            }
            cursor5.close()
            if(verilenborcsayacı!=0)
            {
                BildirimOlusturma("Verilen borcun alma zamanı geldi")
            }

            toplamgidertxt.text="Toplam Gider: ${ToplamGider} "

            Kalan=ToplamGelir-ToplamGider
            kalantxt.text="Kalan: ${Kalan} "



            val cursor3=veritabani.rawQuery("SELECT * FROM KullaniciBilgileri",null)
            val idColumnIndex=cursor3.getColumnIndex("id")
            val isimColumnIndex=cursor3.getColumnIndex("isim")

            var say=0
            while (cursor3.moveToNext())
            {
                say=say+1

            }
            cursor3.close()
            if (say==0)
            {
                var intent= Intent(applicationContext,KullaniciEkle::class.java)
                startActivity(intent)
            }

        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean
    {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {

        return super.onOptionsItemSelected(item)
    }

    fun GelirEkleAc(view: View)
    {
        var intent= Intent(applicationContext,GelirEkle::class.java)
        startActivity(intent)
    }
    fun GiderEkleAc(view: View)
    {
        var intent= Intent(applicationContext,GiderEkle::class.java)
        startActivity(intent)
    }

    fun AlinanBocEkle(view:View)
    {
        var intent= Intent(applicationContext,AlinanBorcEkle::class.java)
        startActivity(intent)
    }

    fun VerilenBocEkle(view:View)
    {
        var intent= Intent(applicationContext,VerilenBorcEkle::class.java)
        startActivity(intent)
    }
    fun KullaniciGuncelleAc(view:View)
    {
        var intent= Intent(applicationContext,KullaniciBilgileriniGuncelleme::class.java)
        startActivity(intent)
    }

    fun GiderSilme(view: View)
    {

        val builder=AlertDialog.Builder(this)
        builder.setTitle("UYARI")
        builder.setMessage("Silmek istediğinize emin misiniz?")
        builder.setNegativeButton("HAYIR", DialogInterface.OnClickListener{dialog ,id ->
            //HAYIR DENİRSE
        })
        builder.setPositiveButton("EVET", DialogInterface.OnClickListener{dialog ,id ->
            //EVET DENİRSE

            val gideridtxt=findViewById<TextView>(R.id.alinanborcidtxt)
            val giderid=java.lang.Long.parseLong(gideridtxt.text.toString())
            try
            {
                val veritabani = this.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE, null)
                val sqlstring="DELETE FROM GiderBilgileri WHERE id=?"
                val statement=veritabani.compileStatement(sqlstring)
                statement.bindLong(1,giderid)
                statement.execute()
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }

            var intent= Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)

            val toast: Toast = Toast.makeText(applicationContext,"Gider Silindi", Toast.LENGTH_LONG)
            toast.show()

        })
        builder.show()
    }

    fun GelirSilme(view: View)
    {

        val builder=AlertDialog.Builder(this)
        builder.setTitle("UYARI")
        builder.setMessage("Silmek istediğinize emin misiniz?")
        builder.setNegativeButton("HAYIR", DialogInterface.OnClickListener{dialog ,id ->
            //HAYIR DENİRSE
        })
        builder.setPositiveButton("EVET", DialogInterface.OnClickListener{dialog ,id ->
            //EVET DENİRSE

            val geliridtxt=findViewById<TextView>(R.id.geliridtxt)
            val gelirid=java.lang.Long.parseLong(geliridtxt.text.toString())
            try
            {
                val veritabani = this.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE, null)
                val sqlstring="DELETE FROM GelirBilgileri WHERE id=?"
                val statement=veritabani.compileStatement(sqlstring)
                statement.bindLong(1,gelirid)
                statement.execute()
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }

            var intent= Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)

            val toast: Toast = Toast.makeText(applicationContext,"Gelir Silindi", Toast.LENGTH_LONG)
            toast.show()

        })
        builder.show()
    }

    fun AlinanBorcSilme(view: View)
    {

        val builder=AlertDialog.Builder(this)
        builder.setTitle("UYARI")
        builder.setMessage("Silmek istediğinize emin misiniz?")
        builder.setNegativeButton("HAYIR", DialogInterface.OnClickListener{dialog ,id ->
            //HAYIR DENİRSE
        })
        builder.setPositiveButton("EVET", DialogInterface.OnClickListener{dialog ,id ->
            //EVET DENİRSE

            val alinanborcidtxt=findViewById<TextView>(R.id.alinanborcidtxt)
            val alinanborcid=java.lang.Long.parseLong(alinanborcidtxt.text.toString())
            try
            {
                val veritabani = this.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE, null)
                val sqlstring="DELETE FROM AlinanBorcBilgileri WHERE id=?"
                val statement=veritabani.compileStatement(sqlstring)
                statement.bindLong(1,alinanborcid)
                statement.execute()
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }

            var intent= Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)

            val toast: Toast = Toast.makeText(applicationContext,"Alinan Borc Silindi", Toast.LENGTH_LONG)
            toast.show()
        })
        builder.show()
    }

    fun VerilenBorcSilme(view: View)
    {

        val builder=AlertDialog.Builder(this)
        builder.setTitle("UYARI")
        builder.setMessage("Silmek istediğinize emin misiniz?")
        builder.setNegativeButton("HAYIR", DialogInterface.OnClickListener{dialog ,id ->
            //HAYIR DENİRSE
        })
        builder.setPositiveButton("EVET", DialogInterface.OnClickListener{dialog ,id ->
            //EVET DENİRSE

            val verilenborcidtxt=findViewById<TextView>(R.id.verilenborcidtxt)
            val verilenborcid=java.lang.Long.parseLong(verilenborcidtxt.text.toString())
            try
            {
                val veritabani = this.openOrCreateDatabase("KisiselMuhasebe", Context.MODE_PRIVATE, null)
                val sqlstring="DELETE FROM VerilenBorcBilgileri WHERE id=?"
                val statement=veritabani.compileStatement(sqlstring)
                statement.bindLong(1,verilenborcid)
                statement.execute()
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }

            var intent= Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)

            val toast: Toast = Toast.makeText(applicationContext,"Verilen Borc Silindi", Toast.LENGTH_LONG)
            toast.show()
        })
        builder.show()
    }

    fun BildirimOlusturma(deger:String)
    {
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, LauncherActivity::class.java)
        val pendingIntent = PendingIntent
            .getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val notificationTitle = "Borc İslemleri"
        val notificationContent = deger

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {

            channel = NotificationChannel(channelId, description,
                NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.lightColor = Color.BLUE
            channel.enableVibration(true)

            manager.createNotificationChannel(channel)

            notificationBuilder = Notification.Builder(this, channelId)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setSmallIcon(R.drawable.hesap)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.hesap))
                .setContentIntent(pendingIntent)
        }
        else
        {
            notificationBuilder = Notification.Builder(this)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setSmallIcon(R.drawable.hesap)
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.hesap))
                .setContentIntent(pendingIntent)
        }

        manager.notify(notoficationId++, notificationBuilder.build())

    }


}


