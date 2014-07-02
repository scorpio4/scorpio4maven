package com.scorpio4.examples.flo;


import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Scorpio (c) 2014
 * Module: com.scorpio4.examples.camel
 * User  : lee
 * Date  : 21/06/2014
 * Time  : 9:20 PM
 */
public class HelloWorld {
	static private final Logger log = LoggerFactory.getLogger(HelloWorld.class);

	public HelloWorld() {
		log.debug("Hello World");
	}

	public HelloWorld(String hi) {
		log.debug("!! "+hi+" !!");
	}

	@Handler
	public void doGreeting(Exchange exchange) {
		Map<String,Object> headers = exchange.getIn().getHeaders();
		log.debug("doGreeting: "+exchange);
		log.debug("header: "+headers);
		log.debug("body: "+exchange.getIn().getBody());

		headers.put("hello.world", new Date());
		if (exchange.getIn().getBody()==null) {
			ArrayList arrayList = new ArrayList();
			arrayList.add("Hello World");
			arrayList.add("Greetings Earthling");
			exchange.getOut().setBody(arrayList);
		} else {
			exchange.getOut().setBody(exchange.getIn().getBody());
		}
	}
}
