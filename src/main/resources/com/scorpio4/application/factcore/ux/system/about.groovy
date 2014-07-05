package com.scorpio4.application.scorpio4.ux.system

import com.scorpio4.Kernel
import com.scorpio4.oops.IQException

/**
 * scorpio4 (c) 2013
 * Module: scorpio4.ux
 * User  : lee
 * Date  : 28/11/2013
 * Time  : 11:10 PM
 *
 *
 */

if (focus) {
    focus.putAll(System.getProperties());
    Runtime runtime = Runtime.getRuntime();

    focus.addPhrase("totalMemory", runtime.totalMemory())
    focus.addPhrase("freeMemory", runtime.freeMemory())
    focus.addPhrase("maxMemory", runtime.maxMemory())
    focus.addPhrase("pid", Kernel.getPID());

    return render.page(focus);
} else throw new IQException("urn:scorpio4:ux:render:oops:missing-focus");
