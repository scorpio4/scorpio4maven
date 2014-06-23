package factcore.iq.capability.xmpp
/**
 * FactCore (c) 2013
 * Module: factcore.iq.capability.xmpp
 * User  : lee
 * Date  : 28/11/2013
 * Time  : 3:54 PM
 *
 *
 */

if (model.body && model.body.startsWith("/")) {
    int space = model.body.indexOf(" ");
    String cmd = model.body.substring(1,space?space:model.body.length());
    model.body = model.body.substring(cmd.length()+1);
    Object result = iq.perform( "urn:factcore.iq.capability.xmpp.command."+cmd, model);
    if (result) {
        xmpp.send( result.toString(), model.from )
        return true;
    } else {
        xmpp.send("unknown command: "+model.body, model.from);
    }
}
return false;
