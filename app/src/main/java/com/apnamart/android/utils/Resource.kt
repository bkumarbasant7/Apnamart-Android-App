package com.apnamart.android.utils

sealed class Resource<T>(val data: T? = null, val error: Throwable? = null) {
    class SUCCESS<T>(data: T) : Resource<T>(data)
    class LOADING<T>(data: T? = null) : Resource<T>(data)
    class ERROR<T>(error: Throwable, data: T? = null) : Resource<T>(data, error)
}
