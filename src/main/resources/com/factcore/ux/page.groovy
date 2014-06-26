package com.factcore.ux

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
    return render.page(focus);
} else throw new IQException("urn:factcore:ux:render:oops:missing-focus");
