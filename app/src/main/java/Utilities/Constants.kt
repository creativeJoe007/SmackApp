package Utilities

const val BASE_URL = "https://creative-chat-api.herokuapp.com/v1/"
const val SOCKET_URL = "https://creative-chat-api.herokuapp.com/"
const val REGISTRATION_URL = "${BASE_URL}account/register"
const val LOGIN_URL = "${BASE_URL}account/login"
const val CREATE_USER_URL = "${BASE_URL}user/add"
const val GET_USER_URL = "${BASE_URL}user/byEmail/"
const val GET_CHANNELS_URL = "${BASE_URL}channel"

// BROADCAST CONSTANT
const val BROADCAST_USER_DATA_CHANGE = "BROADCAST_USER_DATA_CHANGE"