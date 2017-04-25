package com.ttjkst.service.Util;

import java.util.UUID;

public abstract class IdBuilder {

	public static String stringId(){
	return UUID.randomUUID().toString();	
	}
}
