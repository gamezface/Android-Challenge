package com.gamezface.presentation.state

class ViewState<D>(
    val status: Status,
    val data: D? = null,
    val throwable: Throwable? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): ViewState<T> =
            ViewState(status = Status.SUCCESS, data = data, throwable = null)

        fun <T> error(throwable: Throwable?): ViewState<T> =
            ViewState(status = Status.ERROR, data = null, throwable = throwable)

        fun <T> loading(): ViewState<T> =
            ViewState(status = Status.LOADING, data = null, throwable = null)
    }

    fun handleIt(
        success: (D?) -> Unit = {},
        error: (Throwable?) -> Unit = {},
        loading: () -> Unit = {},
        stopLoading: () -> Unit = {}
    ): ViewState<D> {
        when (this.status) {
            Status.LOADING -> {
                loading()
            }
            Status.SUCCESS -> {
                success(data)
                stopLoading()
            }
            Status.ERROR -> {
                error(throwable)
                stopLoading()
            }
        }
        return this
    }
}