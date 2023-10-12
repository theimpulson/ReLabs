package io.aayush.relabs.network.data.user

data class User(
    val about: String = String(),
    val activity_visible: Boolean = false,
    val age: Int = 0,
    val alert_optout: List<Any> = emptyList(),
    val allow_post_profile: String = String(),
    val allow_receive_news_feed: String = String(),
    val allow_send_personal_conversation: String = String(),
    val allow_view_identities: String = String(),
    val allow_view_profile: String = String(),
    val avatar_urls: Map<String, String?> = emptyMap(),
    val can_ban: Boolean = false,
    val can_converse: Boolean = false,
    val can_edit: Boolean = false,
    val can_follow: Boolean = false,
    val can_ignore: Boolean = false,
    val can_post_profile: Boolean = false,
    val can_view_profile: Boolean = false,
    val can_view_profile_posts: Boolean = false,
    val can_warn: Boolean = false,
    val content_show_signature: Boolean = false,
    val creation_watch_state: String = String(),
    val custom_fields: CustomFields = CustomFields(),
    val custom_title: String = String(),
    val dob: Dob = Dob(),
    val email: String = String(),
    val email_on_conversation: Boolean = false,
    val gravatar: String = String(),
    val interaction_watch_state: String = String(),
    val is_admin: Boolean = false,
    val is_banned: Boolean = false,
    val is_followed: Boolean = false,
    val is_ignored: Boolean = false,
    val is_moderator: Boolean = false,
    val is_staff: Boolean = false,
    val is_super_admin: Boolean = false,
    val last_activity: Int = 0,
    val location: String = String(),
    val message_count: Int = 0,
    val profile_banner_urls: Map<String, String?> = emptyMap(),
    val push_on_conversation: Boolean = false,
    val push_optout: List<Any> = emptyList(),
    val question_solution_count: Int = 0,
    val reaction_score: Int = 0,
    val receive_admin_email: Boolean = false,
    val register_date: Int = 0,
    val show_dob_date: Boolean = false,
    val show_dob_year: Boolean = false,
    val signature: String = String(),
    val timezone: String = String(),
    val trophy_points: Int = 0,
    val usa_tfa: Boolean = false,
    val user_id: Int = 0,
    val user_title: String = String(),
    val username: String = String(),
    val view_url: String = String(),
    val visible: Boolean = false,
    val vote_score: Int = 0,
    val warning_points: Int = 0,
    val website: String = String()
) {
    val userTitle: UserTitle
        get() {
            return with(user_title) {
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
