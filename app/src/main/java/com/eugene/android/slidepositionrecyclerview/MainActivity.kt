package com.eugene.android.slidepositionrecyclerview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.rd.PageIndicatorView
import kotlin.math.abs
import kotlin.math.roundToInt


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var width: Int? = null

    private lateinit var viewPager1: ViewPager2
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager2: ViewPager2

    private lateinit var pageIndicatorView: PageIndicatorView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.main_page_rv)
        viewPager1 = findViewById(R.id.main_page_view_page)
        viewPager2 = findViewById(R.id.main_page_view_page_2)
        pageIndicatorView = findViewById(R.id.pageIndicatorView)


        val list: MutableList<String> = mutableListOf()
        list.apply {
            add("Измените пароль, чтобы сохранять банковские карты для оплаты счетов")
            add("Подключите ежемесячные напоминания о передаче показаний")
            add("Подключите электронную квитанцию, чтобы сохранить весь архив квитанций")
            add("Добавьте другие лицевые счета, чтобы управлять ими из этого аккаунта")
            add("Обновите приложение, чтобы использовать все его возможности")
            add("Меньше бумаги — больше деревьев. Откажитесь от бумажной квитанции ")
        }

        pageIndicatorView.count = list.size

        recyclerView.adapter = MyAdapter2(list)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        viewPager1.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            adapter = MyAdapter(list)
        }

        viewPager2.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            adapter = MyAdapter(list)
        }

        val transformer = CompositePageTransformer()

        transformer.addTransformer(MarginPageTransformer(4))
        transformer.addTransformer(ViewPager2.PageTransformer { page, position ->
            page.scaleY = 0.7F + 0.3F
        })
        val transformer2 = CompositePageTransformer()

        transformer2.addTransformer(MarginPageTransformer(4))
        transformer2.addTransformer(ViewPager2.PageTransformer { page, position ->
            val v = 1 - abs(position)

            page.scaleY = 0.7F + v * 0.3F
        })
        viewPager1.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pageIndicatorView.selection = position
                val myPosition = position + 1
                Log.e(TAG, "position: $position, myPosition: $myPosition, list.size ${list.size}")
                when (list.size) {
                    1 -> {
                        viewPager1.setPadding(0, 0, 0, 0)
                        pageIndicatorView.visibility = View.GONE
                    }
                    else -> {
                        when (myPosition) {
                            list.size -> viewPager1.setPadding(136, 0, 0, 0)
                            else -> viewPager1.setPadding(0, 0, 136, 0)
                        }
                    }
                }
            }
        })

        viewPager1.setPageTransformer(transformer)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val myPosition = position + 1
                Log.e(TAG, "position: $position, myPosition: $myPosition, list.size ${list.size}")
                when (list.size) {
                    1 -> viewPager2.setPadding(0, 0, 0, 0)
                    else -> {
                        when (myPosition) {
                            list.size -> viewPager2.setPadding(136, 0, 0, 0)
                            else -> viewPager2.setPadding(0, 0, 136, 0)
                        }
                    }
                }
            }
        })
        viewPager2.setPageTransformer(transformer2)
    }

    private inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = itemView.findViewById(R.id.rv_text_view)
        private var linearLayout: LinearLayout = itemView.findViewById(R.id.rv_ll)
        private var card: CardView = itemView.findViewById(R.id.rv_card)

        fun bind(list: List<String>, position: Int) {
            textView.text = list[position]
        }
    }

    private inner class MyAdapter(var list: List<String>) : RecyclerView.Adapter<MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val view = layoutInflater.inflate(R.layout.rv_bunner, parent, false)
            return MyHolder(view)
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            val adapterList: String = list[position]
            holder.bind(list, position)
        }

        override fun getItemCount() = list.size

    }

    private inner class MyHolder2(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = itemView.findViewById(R.id.rv_text_view)
        private var linearLayout: LinearLayout = itemView.findViewById(R.id.rv_ll)
        private var card: CardView = itemView.findViewById(R.id.rv_card)

        fun bind(list: List<String>, position: Int) {
            textView.text = list[position]
        }
    }

    private inner class MyAdapter2(var list: List<String>) : RecyclerView.Adapter<MyHolder2>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder2 {
            val view = layoutInflater.inflate(R.layout.rv_bunner_2, parent, false)
            return MyHolder2(view)
        }

        override fun onBindViewHolder(holder: MyHolder2, position: Int) {
            val adapterList: String = list[position]
            holder.bind(list, position)
        }

        override fun getItemCount() = list.size

    }

    private fun dpToPixel(dp: Float): Int {
        val density = resources.displayMetrics.density
        return (dp * density).roundToInt()
    }
}

