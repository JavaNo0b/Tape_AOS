package com.janob.tape_aos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileEditBinding
    lateinit var user : User
    lateinit var tapeDatas : List<Tape>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RoomDB 데이터 받기
        user = TapeDatabase.Instance(this).userDao().getMyUser(1)
        tapeDatas = TapeDatabase.Instance(this).tapeDao().getAll()

        // 초기 설정
        binding.profileUserNameEdittextEt.setText(user.name)
        binding.profileUserCommentEdittextEt.setText(user.comment)

        // 수정 완료 버튼
        binding.profileEditCompleteBtn.setOnClickListener {
            val name = binding.profileUserNameEdittextEt.text.toString()
            val comment = binding.profileUserCommentEdittextEt.text.toString()
            binding.profileUserNameEdittextEt.setText(name)
            binding.profileUserCommentEdittextEt.setText(comment)

            var followerList : List<String> = arrayListOf("follower1", "follower2", "follower3", "follower4", "follower5", "follower6", "follower7", "follower8", "follower9")
            var followingList : List<String> = arrayListOf("following1", "following2", "following3", "following4", "following5", "following6", "following7", "following8", "following9")
            val user = User(1, R.drawable.user_profile_img, name, comment, followerList, followingList, tapeDatas, user.id)
            TapeDatabase.Instance(this).userDao().update(user)

        }

        // 취소 버튼
        binding.profileEditCancelBtnTv.setOnClickListener {
            finish()
        }

    }
}