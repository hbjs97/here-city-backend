package com.herecity.tour.domain.vo

enum class Scope(val korName: String) {
    PUBLIC("전체공개"),
    ANONYMOUS("익명공개"), // 전체공개인데, 작성자의 닉네임이 숨겨진다.
    PRIVATE("비공개")
}
