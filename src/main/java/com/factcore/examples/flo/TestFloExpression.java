package com.factcore.examples.flo;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.builder.PredicateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scorpio (c) 2014
 * Module: com.factcore.examples.camel
 * User  : lee
 * Date  : 27/06/2014
 * Time  : 5:18 PM
 */
public class TestFloExpression implements Expression {
	static private final Logger log = LoggerFactory.getLogger(TestFloExpression.class);

	public TestFloExpression() {
	}

	// Expression
	@Override
	public <T> T evaluate(Exchange exchange, Class<T> type) {
		log.error("Evaluate: "+exchange+" -> "+type);
		return (T) PredicateBuilder.constant(true);
	}

}
