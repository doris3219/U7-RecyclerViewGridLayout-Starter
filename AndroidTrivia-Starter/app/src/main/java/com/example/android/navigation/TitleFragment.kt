package com.example.android.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,      //調用以增加片段的佈局
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
                R.layout.fragment_title, container, false)

        binding.playButton.setOnClickListener { view: View ->
            view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }

        setHasOptionsMenu(true)

        Log.i("TitleFragment", "onCreateView called")

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {        //添加選項菜單並為菜單資源文件充氣
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {      //單擊菜單項時採取適當操作的方法。在這種情況下，操作是導航到id與所選菜單項相同的片段
        return NavigationUI.onNavDestinationSelected(item!!,
                view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context?) {              //當片段與其所有者活動相關聯時調用
        super.onAttach(context)
        Log.i("TitleFragment", "onAttach called")
    }
    override fun onCreate(savedInstanceState: Bundle?) {    //對於片段進行初始片段創建（佈局除外）
        super.onCreate(savedInstanceState)
        Log.i("TitleFragment", "onCreate called")
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {       //在所有者活動onCreate()完成時調用。在調用此方法之前，您的片段將無法訪問活動。
        super.onActivityCreated(savedInstanceState)
        Log.i("TitleFragment", "onActivityCreated called")
    }
    override fun onStart() {        //在片段可見時調用
        super.onStart()
        Log.i("TitleFragment", "onStart called")
    }
    override fun onResume() {       //在片段獲得用戶關注時調用
        super.onResume()
        Log.i("TitleFragment", "onResume called")
    }
    override fun onPause() {        //在片段失去用戶注意力時調用
        super.onPause()
        Log.i("TitleFragment", "onPause called")
    }
    override fun onStop() {         //當片段在屏幕上不再可見時調用
        super.onStop()
        Log.i("TitleFragment", "onStop called")
    }
    override fun onDestroyView() {      //在不再需要片段的視圖時調用，以清理與該視圖關聯的資源
        super.onDestroyView()
        Log.i("TitleFragment", "onDestroyView called")
    }
    override fun onDetach() {
        super.onDetach()
        Log.i("TitleFragment", "onDetach called")
    }
}