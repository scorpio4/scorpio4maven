package com.scorpio4.examples.flo;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scorpio (c) 2014
 * Module: com.scorpio4.examples.camel
 * User  : lee
 * Date  : 27/06/2014
 * Time  : 5:18 PM
 */
public class TestFloPredicate implements Predicate {
	static private final Logger log = LoggerFactory.getLogger(TestFloPredicate.class);

	public TestFloPredicate() {
	}

	// Predicate
	@Override
	public boolean matches(Exchange exchange) {
		log.error("Matches: "+exchange);
		return true;
	}
}
