package psychegrammer.example.muhabet.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import psychegrammer.example.muhabet.R
import psychegrammer.example.muhabet.models.User

class ChatLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)


//        val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
//          USER_KEY is a static constant

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user.username



        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())

        recyclerview_chat_log.adapter = adapter
    }
}

class ChatFromItem: Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
    }

}

class ChatToItem: Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
    }

}
