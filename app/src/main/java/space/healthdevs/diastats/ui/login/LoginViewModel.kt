package space.healthdevs.diastats.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException
import com.mcxiaoke.koi.log.logd
import org.koin.standalone.KoinComponent
import space.healthdevs.diastats.models.CognitoUserState
import space.healthdevs.diastats.utils.hasEmailPattern
import space.healthdevs.diastats.utils.hasPasswordPatternFromAws

class LoginViewModel : ViewModel(), KoinComponent {

    val cognitoUserStateObservable: MutableLiveData<CognitoUserState> = MutableLiveData()
    private var password: String = ""

    private val authHandler = object : AuthenticationHandler {
        override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
            cognitoUserStateObservable.value = CognitoUserState(true,
                    "User successfully signed in!")
            logd("LoginViewModel", "session: username -> ${userSession?.username}, device -> ${newDevice?.deviceAttributes}")
        }

        override fun onFailure(exception: Exception?) {
            if (exception is UserNotConfirmedException)
                cognitoUserStateObservable.value = CognitoUserState(false,
                        "You need to confirm your email before login!")
            else
                cognitoUserStateObservable.value = CognitoUserState(false,
                        "Something went wrong, try again later!")
            logd("LoginViewModel", "Exception: $exception")
        }

        override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation?, userId: String?) {
            val authDetails = AuthenticationDetails(userId, password, null)
            authenticationContinuation?.setAuthenticationDetails(authDetails)
            authenticationContinuation?.continueTask()
        }

        override fun authenticationChallenge(continuation: ChallengeContinuation?) {
            continuation?.continueTask()
        }

        override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {
            continuation?.continueTask()
        }

    }

    fun signInUser(userPool: CognitoUserPool, email: String, password: String) {
        if (email.hasEmailPattern()) {
            if (password.hasPasswordPatternFromAws()) {
                this.password = password
                userPool.getUser(email).signOut()
                userPool.getUser(email).getSessionInBackground(authHandler)
            } else {
                cognitoUserStateObservable.value = CognitoUserState(false,
                        "Your password should be at least 8 " +
                                "characters long, containing numbers and digits!")
            }
        } else {
            cognitoUserStateObservable.value = CognitoUserState(false,
                    "Incorrect email pattern!")
        }
    }
}