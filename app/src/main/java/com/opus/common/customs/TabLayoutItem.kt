package com.opus.common.customs

import androidx.fragment.app.Fragment

data class TabLayoutItem(val title: String, val fragmentInitializer: () -> Fragment)