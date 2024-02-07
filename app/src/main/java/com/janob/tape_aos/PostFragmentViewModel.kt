package com.janob.tape_aos

import androidx.lifecycle.ViewModel

class PostFragmentViewModel: ViewModel() {

     val apiFetchr = ApiFetchr()

     var tapeImg : String = ""
     var tapeTitle :String = ""
     var tapeContent :String = ""

     var songDTOList :List<SongDTO> =  mutableListOf<SongDTO>()



}