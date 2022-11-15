package com.herecity.user.application.service

import com.herecity.user.application.port.output.FetchUserPort
import org.springframework.stereotype.Service

@Service
class UserService(private val fetchUserPort: FetchUserPort)
