package space.healthdevs.diastats.ui.activities.register

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.mcxiaoke.koi.log.logd
import space.healthdevs.diastats.models.CognitoUserState
import space.healthdevs.diastats.utils.hasEmailPattern
import space.healthdevs.diastats.utils.hasPasswordPatternFromAws
import java.lang.Exception

class RegisterViewModel : ViewModel() {

    val cognitoUserStateObservable: MutableLiveData<CognitoUserState> = MutableLiveData()

    private val signupHandler = object: SignUpHandler {
        override fun onSuccess(user: CognitoUser?,
                               signUpConfirmationState: Boolean,
                               cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?) {
            cognitoUserStateObservable.value = CognitoUserState(true, "Sign up successful!")
        }

        override fun onFailure(exception: Exception?) {
            logd("RegisterViewModel", "Exception: $exception")
            cognitoUserStateObservable.value = CognitoUserState(false, "Sign up failed, try again later!")
        }
    }

    fun registerUser(userPool: CognitoUserPool, email: String, password: String, passwordConfirm: String) {
        if (email.hasEmailPattern()) {
            if (arePasswordsOk(password, passwordConfirm)) {
                val userAttrs = CognitoUserAttributes()
                userPool.signUpInBackground(email, password, userAttrs, null, signupHandler)
            }
        } else
            cognitoUserStateObservable.value = CognitoUserState(false, "Incorrect email pattern!")
    }

    private fun arePasswordsOk(password: String, passwordConfirm: String): Boolean {
        if (password != passwordConfirm) {
            cognitoUserStateObservable.value = CognitoUserState(false, "Passwords doesn't match!")
            return false
        }

        if (!password.hasPasswordPatternFromAws()){
            cognitoUserStateObservable.value = CognitoUserState(false,
                    "Your password should be at least 8 " +
                    "characters long, containing numbers and digits!")
            return false
        }

        return true
    }
}