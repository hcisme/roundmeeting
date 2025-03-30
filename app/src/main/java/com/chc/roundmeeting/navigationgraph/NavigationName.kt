package com.chc.roundmeeting.navigationgraph

object NavigationName {
    const val LOGIN_PAGE = "/login"

    const val HOME_PAGE = "/"

    const val JOIN_MEETING_SETTING = "/join_meeting_setting"

    const val QUICK_MEETING_SETTING = "/quick_meeting_setting"

    const val MEETING_ROOM = "/meeting_room"

    val GET_NAV_DEEP_LINK_MEETING_ROOM =
        { packageName: String -> "${packageName}:/${MEETING_ROOM}" }
}
