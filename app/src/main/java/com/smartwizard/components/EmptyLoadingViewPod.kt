package com.smartwizard.components


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import butterknife.ButterKnife
import com.smartwizard.R
import kotlinx.android.synthetic.main.empty_view_loading.view.*

class EmptyLoadingViewPod : RelativeLayout
{
    /**
     * When Importing this class to use in another project, make sure to import other resources as well
     */

    /* @BindView(R.id.loadingAnimView)
       var loadingView: LottieAnimationView? = null*/
    /*@BindView(R.id.tv_empty_pull_btn)
    var pullToRefreshBtn: TextView? = null
    @BindView(R.id.tv_empty_refresh)
    var pullToRefreshTv: TextView? = null
    @BindView(R.id.errorView)
    var errorView: LinearLayout? = null
    @BindView(R.id.tv_empty)
    var emptyTv: TextView? = null*/

    private var onRefreshListener: OnRefreshListener? = null

    private var currentState: EMPTY_VIEW_STATE? = null


    constructor(context: Context) : super(context)
    {
//        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
//        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    {
//        this.context = context
    }

    fun setText(text: String)
    {
        tv_empty!!.text = text
    }

    fun setOnRefreshListener(onRefreshListener: OnRefreshListener)
    {
        this.onRefreshListener = onRefreshListener
    }

    fun setEmptyText(text: String)
    {
        tv_empty!!.text = text
    }

    fun hidePullToRefresh()
    {
        tv_empty_pull_btn!!.visibility = View.GONE
        tv_empty_pull_btn!!.visibility = View.GONE
    }

    fun showPullToRefresh()
    {
        tv_empty_pull_btn!!.visibility = View.VISIBLE
        tv_empty_refresh!!.visibility = View.VISIBLE
    }

    override fun onFinishInflate()
    {
        super.onFinishInflate()
        ButterKnife.bind(this)
        toInitialState()
        tv_empty_pull_btn!!.setOnClickListener {
            if (onRefreshListener != null)
                onRefreshListener!!.onRefreshButtonClicked()
        }
    }

    fun toLoadingView()
    {
        errorView!!.visibility = View.GONE
        loadingRootView!!.visibility = View.VISIBLE
//        loadingView!!.playAnimation()
    }

    fun toInitialState()
    {
        errorView!!.visibility = View.GONE
        /*    if (loadingView!!.isAnimating)
                loadingView!!.pauseAnimation()*/
        loadingRootView!!.visibility = View.GONE
    }

    fun toSearchView()
    {
        errorView!!.visibility = View.GONE
        loadingRootView!!.visibility = View.VISIBLE
        /*  loadingView!!.setAnimation("search_products.json")
          loadingView!!.playAnimation()*/
    }


    fun toErrorView()
    {
        errorView!!.visibility = View.VISIBLE
        /* if (loadingView!!.isAnimating)
             loadingView!!.pauseAnimation()*/
        loadingRootView!!.visibility = View.GONE

    }

    fun setCurrentState(currentState: EMPTY_VIEW_STATE)
    {
        this.currentState = currentState
        when (currentState)
        {
            EMPTY_VIEW_STATE.NONE ->
            {
                toInitialState()
            }
            EMPTY_VIEW_STATE.LOADING ->
            {
                toLoadingView()
            }
            EMPTY_VIEW_STATE.SEARCHING ->
            {
                toSearchView()
            }
            EMPTY_VIEW_STATE.NETWORK_ERROR ->
            {
                toErrorView()
                tv_empty!!.text = context.getString(R.string.title_msg_emptyView)
                tv_empty_pull_btn!!.text = context.getString(R.string.title_msg_try_again)
            }
            EMPTY_VIEW_STATE.UNEXPECTED_ERROR ->
            {
                toErrorView()
                toErrorView()
                tv_empty!!.text = context.getString(R.string.title_msg_unexpected_error_occured)
                tv_empty_pull_btn!!.text = context.getString(R.string.title_msg_try_again_for_error)

            }
        }
    }

     enum class EMPTY_VIEW_STATE
    {
        INITIAL ,
        NETWORK_ERROR,
        LOADING,
        UNEXPECTED_ERROR,
        SEARCHING,
        NONE
    }

    interface OnRefreshListener
    {
        fun onRefreshButtonClicked()
    }

}
