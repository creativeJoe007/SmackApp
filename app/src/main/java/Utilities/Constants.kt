package Utilities

const val BASE_URL = "https://creative-chat-api.herokuapp.com/v1/"
const val REGISTRATION_URL = "${BASE_URL}account/register"
const val LOGIN_URL = "${BASE_URL}account/login"
const val CREATE_USER_URL = "${BASE_URL}user/add"
const val GET_USER_URL = "${BASE_URL}user/byEmail/"

// BROADCAST CONSTANT
const val BROADCAST_USER_DATA_CHANGE = "BROADCAST_USER_DATA_CHANGE"