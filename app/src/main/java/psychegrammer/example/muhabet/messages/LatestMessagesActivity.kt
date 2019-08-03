package psychegrammer.example.muhabet.messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*
import kotlinx.android.synthetic.main.latest_message_row.view.*
import psychegrammer.example.muhabet.R
import psychegrammer.example.muhabet.messages.NewMessageActivity.Companion.USER_KEY
import psychegrammer.example.muhabet.models.ChatMessage
import psychegrammer.example.muhabet.models.User
import psychegrammer.example.muhabet.models.views.LatestMessageRow
import psychegrammer.example.muhabet.registerlogin.RegisterActivity

class LatestMessagesActivity : AppCompatActivity() {

    var chatPartnerUser: User? = null

    companion object {
        var currentUser: User? = null
        val TAG = "LatestMessages"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)


        recyclerview_latest_messages.adapter = adapter
        recyclerview_latest_messages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // set item click listener on your adapter
        adapter.setOnItemClickListener { item, view ->
            val row = item as LatestMessageRow
            val intent = Intent(view.context, ChatLogActivity::class.java)

            intent.putExtra(USER_KEY, row.chatPartnerUser)
            startActivity(intent)
        }

//        adapter.setOnItemClickListener { item, view ->
//
//            val userItem = item as UserItem
//            val intent = Intent(view.context, ChatLogActivity::class.java)
////                    intent.putExtra(USER_KEY, userItem.user.username)
//            intent.putExtra(USER_KEY, userItem.user)
//            startActivity(intent)
//        }

        listenForLastestMessages()

        fetchCurrentUser()

        verifyUserIsLoggedIn()
    }
    val latestMessagesMap = HashMap<String, ChatMessage>()

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            adapter.add(LatestMessageRow(it))
        }
    }



    private fun listenForLastestMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }
            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

        })
    }

    val adapter = GroupAdapter<ViewHolder>()

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                Log.d("LatestMessages", "Current user ${currentUser?.username}")
            }
        })
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        // if user is not logged in
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_new_message -> {
                val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
