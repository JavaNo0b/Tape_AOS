package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentTapeCheckBinding

class TapeCheckFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTapeCheckBinding.inflate(inflater)
        val recyclerView = binding.songsRecyclerView

        val adapter = SavedSongAdapter()
        val manager = LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager

        val songDao = TapeDatabase.Instance(requireContext()).songDao()
        adapter.addSongs(ArrayList(songDao.getAll()))

        return binding.root
    }
}