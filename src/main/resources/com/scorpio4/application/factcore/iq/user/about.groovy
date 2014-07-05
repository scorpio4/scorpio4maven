package com.scorpio4.application.scorpio4.iq.user

/**
 * scorpio4 (c) 2013
 * Module: scorpio4.application.scorpio4.iq.user
 * User  : lee
 * Date  : 27/11/2013
 * Time  : 9:30 AM
 *
 *
 */

Map settings = [:]

settings.put("user", user.getConfiguration() )
settings.put("iq", iq.getConfiguration() )

return settings;