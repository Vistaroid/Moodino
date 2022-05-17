package com.iranmobiledev.moodino.ui.view

import android.animation.LayoutTransition
import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.ItemActivityGroupViewBinding
import com.iranmobiledev.moodino.ui.entry.adapter.ChildActivityAdapter

class ActivityGroupView(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    private val binding: ItemActivityGroupViewBinding
    var expanded: Boolean = false

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemActivityGroupViewBinding.inflate(inflater, this, false)
        val attr = context.obtainStyledAttributes(attributeSet, R.styleable.ActivityGroupView)

        val expand = attr.getBoolean(R.styleable.ActivityGroupView_expanded, false)
        val title = attr.getString(R.styleable.ActivityGroupView_title)

        title?.let { setTitle(it) }
        setupRecyclerView()

        if (!expand)
            collapse()

        binding.root.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        setupClicks()
    }

    private fun setupClicks() {
        binding.titleLayout.setOnClickListener { if (expanded) collapse() else expand() }
        binding.expandIv.setOnClickListener { if (expanded) collapse() else expand() }
    }

    private fun expand() {
        binding.activityGroupRv.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        rotateExpandImage()
        expanded = true
    }

    private fun rotateExpandImage() {
        binding.expandIv.rotation = binding.expandIv.rotation + 180
    }

    private fun collapse() {
        binding.activityGroupRv.visibility = View.GONE
        rotateExpandImage()
        expanded = false
    }

    private fun setTitle(title: String) {
        binding.title.text = title
    }

    private fun setupRecyclerView() {
        //binding.activityGroupRv.layoutManager = GridLayoutManager(context, 5)
        val layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW)
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.activityGroupRv.layoutManager = layoutManager
    }

    fun setAdapter(groupAdapter: ChildActivityAdapter) {
        binding.activityGroupRv.adapter = groupAdapter
    }

    fun getRoot(): View {
        return binding.root
    }
}