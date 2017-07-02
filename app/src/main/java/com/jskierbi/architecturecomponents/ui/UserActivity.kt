package com.jskierbi.architecturecomponents.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jskierbi.architecturecomponents.ArchitectureComponentsApplication
import com.jskierbi.architecturecomponents.R
import com.jskierbi.architecturecomponents.modules.user.User
import com.jskierbi.architecturecomponents.modules.user.UserInformation
import com.jskierbi.architecturecomponents.modules.user.UserManager
import com.jskierbi.commons.logd
import com.jskierbi.commons.loge
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

  private val userManager: UserManager by lazy { UserManager(application as ArchitectureComponentsApplication) }
  var disposable: Disposable = Disposables.empty()!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user)

    uiAddUser.setOnClickListener {
      val userInformation = UserInformation("John", "Doe", 50)
      val user = User()
      user.name = "john_doe" + Math.random()
      user.password = "foobar"
      user.userInformation = userInformation
      userManager.persistUser(user)
    }
    userManager.getUsers().subscribe { it ->
      val size = it.size
      logd("Success got users from api $size")
    }
    disposable.dispose()
    disposable = userManager.findAllUsers().subscribe({
      users ->
      users.forEach { user ->
        uiUserDatabaseLog.append("Found User " + user.name + "\n")
      }
    },{
      loge(it)
    })
  }

  override fun onStop() {
    super.onStop()
    disposable.dispose()
  }
}
