package project.movies.searchformovies.utility

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData

class NonRepeatLiveData<T>(default: T) : MutableLiveData<T>(default) {

    private var isNotRepeat = false
    private var newValue: Any? = default

    @MainThread
    override fun setValue(value: T?) {
        isNotRepeat = newValue != value

        if (isNotRepeat) newValue = value

        if (isNotRepeat) super.setValue(value)
    }
}