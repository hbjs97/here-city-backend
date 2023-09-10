package com.herecity.activity.application.port.outbound

import com.herecity.activity.domain.entity.Activity
import com.herecity.common.adapter.mariadb.BaseCommandRepository

interface ActivityCommandOutputPort : BaseCommandRepository<Activity, Long>
