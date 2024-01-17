package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentTapeCheckBinding

class TapeCheckFragment : Fragment() {

    interface TapeCheckListener {fun onTapeCheck()}
    lateinit var listener: TapeCheckListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(parentFragment is TapeCheckListener)
            listener = parentFragment as TapeCheckListener
        else
            throw Exception("인터페이스 미구현")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTapeCheckBinding.inflate(inflater)
        val recyclerView = binding.songsRecyclerView
        val songDao = TapeDatabase.Instance(requireContext()).songDao()

        val adapter = SavedSongAdapter(ArrayList(songDao.getAll()))
        val manager = LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager

        //adapter.addSongs(ArrayList(songDao.getAll()))
        binding.btnPostContinue.setOnClickListener {
            listener.onTapeCheck()
        }
        return binding.root
    }
}