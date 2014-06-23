package factcore.application.factcore.iq.user

/**
 * FactCore (c) 2013
 * Module: factcore.application.factcore.iq.user
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