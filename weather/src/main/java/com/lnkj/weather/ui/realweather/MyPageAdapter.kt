package com.lnkj.weather.ui.realweather

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/10 14:27
 * @描述
 */
class MyPageAdapter(private val fm: FragmentManager, private val fragments: MutableList<Fragment>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    init {
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun saveState(): Parcelable? {
       return null
    }

//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val instantiateItem =
//            super.instantiateItem(container, position) as Fragment
//        val item = fragments[position]
//        return if (instantiateItem === item) {
//            instantiateItem
//        } else { //如果集合中对应下标的fragment和fragmentManager中的对应下标的fragment对象不一致，那么就是新添加的，所以自己add进入；这里为什么不直接调用super方法呢，因为fragment的mIndex搞的鬼，以后有机会再补一补。
//            fm.beginTransaction().add(container.id, item)
//                .commitNowAllowingStateLoss()
//            item
//        }
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        val fragment = `object` as Fragment
//        //如果getItemPosition中的值为PagerAdapter.POSITION_NONE，就执行该方法。
//        if (fragments.contains(fragment)) {
//            super.destroyItem(container, position, fragment)
//            return
//        }
//        //自己执行移除。因为mFragments在删除的时候就把某个fragment对象移除了，所以一般都得自己移除在fragmentManager中的该对象。
//        fm.beginTransaction().remove(fragment).commitNowAllowingStateLoss()
//    }
//
//    fun updateData(mlist: MutableList<Fragment>?) {
//        if (mlist == null) return
//        fragments.clear()
//        fragments.addAll(mlist)
//        notifyDataSetChanged()
//    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}