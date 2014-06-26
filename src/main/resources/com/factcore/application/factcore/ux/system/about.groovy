package com.factcore.application.factcore.ux.system

import com.factcore.Kernel
import com.factcore.oops.IQException

/**
 * FactCore (c) 2013
 * Module: factcore.ux
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
} else throw new IQException("urn:factcore:ux:render:oops:missing-focus");
