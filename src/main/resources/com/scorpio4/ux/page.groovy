package com.scorpio4.ux

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
    return render.page(focus);
} else throw new IQException("urn:scorpio4:ux:render:oops:missing-focus");
