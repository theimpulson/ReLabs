package io.aayush.relabs.network.data.user

import io.aayush.relabs.network.data.common.Avatar
import io.aayush.relabs.network.data.common.DateTime

data class User(
    val alert_mode: String = String(),
    val avatar: Avatar? = Avatar(),
    val canFollow: Boolean = false,
    val canIgnore: Boolean = false,
    val canReport: Boolean = false,
    val created_at: DateTime = DateTime(),
    val id: Int = 0,
    val isFollowing: Boolean = false,
    val isIgnored: Boolean = false,
    val last_seen_at: DateTime = DateTime(),
    val message_count: Int = 0,
    val name: String = String(),
    val reaction_score: Int = 0,
    val reportUrl: String = String(),
    val title: String = String(),
    val trophy_points: Int = 0,
    val username: String = String(),
    val isAdmin: Boolean = false,
    val isModerator: Boolean = false,
    val canUseMobileApp: Boolean = false,
    val showNativeInlineAds: Boolean = false,
    val showNativeInterstitialAds: Boolean = false
) {
    val userTitle: UserTitle
        get() {
            return with(title) {
                when {
                    contains(Regex("(Admin|Lead)")) -> UserTitle.Admin
                    contains(Regex("(SM|Sr//. Mod|Senior Mod|Senior Moderator|Moderator Emeritus)")) -> UserTitle.SM
                    contains(Regex("(FSM|Forum Moderator)")) -> UserTitle.FSM
                    contains(Regex("(RD|Recognized Developer)")) -> UserTitle.RD
                    contains(Regex("(RC|Recognized Contributor)")) -> UserTitle.RC
                    else -> UserTitle.Member
                }
            }
        }
}
