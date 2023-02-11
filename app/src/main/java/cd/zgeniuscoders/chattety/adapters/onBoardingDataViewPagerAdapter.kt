package cd.zgeniuscoders.chattety.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import cd.zgeniuscoders.chattety.R
import cd.zgeniuscoders.chattety.models.OnBoardingData


class onBoardingDataViewPagerAdapter(
    private var context: Context,
    private var onBoardingDataModel: List<OnBoardingData>
) : PagerAdapter() {

    override fun getCount(): Int {
        return onBoardingDataModel.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_on_board, null)

        val title = view.findViewById<TextView>(R.id.on_board_title)
        val desc = view.findViewById<TextView>(R.id.on_board_desc)
        val image = view.findViewById<ImageView>(R.id.on_board_img)

        title.text = onBoardingDataModel[position].title
        desc.text = onBoardingDataModel[position].desc
        image.setImageResource(onBoardingDataModel[position].image)


        container.addView(view)
        return view
    }

}